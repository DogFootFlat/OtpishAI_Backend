package otpishAI.otpishAI_Backend.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import otpishAI.otpishAI_Backend.entity.Product;
import otpishAI.otpishAI_Backend.entity.QProduct;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;
@Repository
@AllArgsConstructor
public class CustomProductRepositoryImpl implements CustomProductRepository {

    private final EntityManager entityManager;

    @Override
    public Page<Product> listingProduct(String genre, List<String> brand, List<String> category, Pageable pageable) {

        // 네이티브 쿼리 작성
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM otpishai_schema.product WHERE 1 = 1");
        StringBuilder sqlBuilderFTC = new StringBuilder("SELECT COUNT(*) FROM otpishai_schema.product WHERE 1 = 1");

        if (!genre.equals("")) {
            sqlBuilder.append(" AND ").append("genre_code LIKE '%").append(genre).append("%'");
            sqlBuilderFTC.append(" AND ").append("genre_code LIKE '%").append(genre).append("%'");
        }

        if (!brand.isEmpty() && !brand.get(0).equals("")) {
            sqlBuilder.append(" AND ").append("product_brand IN :brands");
            sqlBuilderFTC.append(" AND ").append("product_brand IN :brands");
        }

        if (!category.isEmpty() && !category.get(0).equals("")) {
            sqlBuilder.append(" AND ").append("(");
            sqlBuilderFTC.append(" AND ").append("(");
            for (int i = 0; i < category.size(); i++) {
                if (i > 0) {
                    sqlBuilder.append(" OR ");
                    sqlBuilderFTC.append(" OR ");
                }
                sqlBuilder.append("'").append(category.get(i)).append("' = ANY(category)");
                sqlBuilderFTC.append("'").append(category.get(i)).append("' = ANY(category)");
            }
            sqlBuilder.append(")");
            sqlBuilderFTC.append(")");
        }



        // 정렬 조건 추가
        Sort sort = pageable.getSort();
        if (sort.isSorted()) {
            StringBuilder orderBy = new StringBuilder(" ORDER BY ");
            boolean first = true;
            for (Sort.Order order : sort) {
                if (!first) {
                    orderBy.append(", ");
                }
                orderBy.append(order.getProperty()).append(" ").append(order.getDirection());
                first = false;
            }

            sqlBuilder.append(orderBy);

        }

        Query nativeQuery = entityManager.createNativeQuery(sqlBuilder.toString(), Product.class);

        Query countQuery = entityManager.createNativeQuery(sqlBuilderFTC.toString());
        // 브랜드 파라미터 설정
        if (!brand.isEmpty() && !brand.get(0).equals("")) {
            nativeQuery.setParameter("brands", brand);
            countQuery.setParameter("brands", brand);
        }
        System.out.println(sqlBuilder);
        Long totalCount = ((Number) countQuery.getSingleResult()).longValue();

        // 페이지네이션 적용
        nativeQuery.setFirstResult((int) pageable.getOffset());
        nativeQuery.setMaxResults(pageable.getPageSize());

        List<Product> resultList = nativeQuery.getResultList();
        return new PageImpl<>(resultList, pageable, totalCount);
    }
}