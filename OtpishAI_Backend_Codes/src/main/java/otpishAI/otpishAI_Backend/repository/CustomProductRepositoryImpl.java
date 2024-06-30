package otpishAI.otpishAI_Backend.repository;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import otpishAI.otpishAI_Backend.entity.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;@Repository
@AllArgsConstructor
public class CustomProductRepositoryImpl implements CustomProductRepository {

    private final EntityManager entityManager;

    @Override
    public Page<Product> listingProduct(String genre, List<String> brand, List<String> category, String productName, String productCode, String productRegistrant, Pageable pageable, Boolean isSearch) {
        StringBuilder sqlBuilder = new StringBuilder();
        StringBuilder sqlBuilderFTC = new StringBuilder();

        // 기본 SELECT 문
        sqlBuilder.append("SELECT * FROM otpishai_schema.product WHERE 1 = 1");
        sqlBuilderFTC.append("SELECT COUNT(*) FROM otpishai_schema.product WHERE 1 = 1");

        sqlBuilder.append(" AND genre_code LIKE '%").append(genre).append("%'");
        sqlBuilderFTC.append(" AND genre_code LIKE '%").append(genre).append("%'");


        // 나머지 조건들을 OR 또는 AND로 묶기
        StringBuilder otherConditions = new StringBuilder();
        StringBuilder otherConditionsFTC = new StringBuilder();
        String connector = isSearch ? " OR " : " AND ";

        if (!productName.equals("")) {
            if (otherConditions.length() > 0) {
                otherConditions.append(connector);
                otherConditionsFTC.append(connector);
            }
            otherConditions.append("product_name LIKE '%").append(productName).append("%'");
            otherConditionsFTC.append("product_name LIKE '%").append(productName).append("%'");
        }

        if (!productCode.equals("")) {
            if (otherConditions.length() > 0) {
                otherConditions.append(connector);
                otherConditionsFTC.append(connector);
            }
            otherConditions.append("product_code LIKE '%").append(productCode).append("%'");
            otherConditionsFTC.append("product_code LIKE '%").append(productCode).append("%'");
        }

        if (!productRegistrant.equals("")) {
            if (otherConditions.length() > 0) {
                otherConditions.append(connector);
                otherConditionsFTC.append(connector);
            }
            otherConditions.append("product_registrant LIKE '%").append(productRegistrant).append("%'");
            otherConditionsFTC.append("product_registrant LIKE '%").append(productRegistrant).append("%'");
        }

        if (!brand.isEmpty() && !brand.get(0).equals("")) {
            if (otherConditions.length() > 0) {
                otherConditions.append(connector);
                otherConditionsFTC.append(connector);
            }
            otherConditions.append("product_brand IN :brands");
            otherConditionsFTC.append("product_brand IN :brands");
        }

        if (!category.isEmpty() && !category.get(0).equals("")) {
            if (otherConditions.length() > 0) {
                otherConditions.append(connector);
                otherConditionsFTC.append(connector);
            }
            for (int i = 0; i < category.size(); i++) {
                if (i > 0) {
                    otherConditions.append(" OR ");
                    otherConditionsFTC.append(" OR ");
                }
                otherConditions.append("array_to_string(category, ',') LIKE '%").append(category.get(i)).append("%'");
                otherConditionsFTC.append("array_to_string(category, ',') LIKE '%").append(category.get(i)).append("%'");
            }
        }

        // isSearch가 true이면 다른 조건들을 OR로 묶어야 하므로 괄호로 묶기
        if (isSearch && otherConditions.length() > 0) {
            sqlBuilder.append(" AND (").append(otherConditions).append(")");
            sqlBuilderFTC.append(" AND (").append(otherConditionsFTC).append(")");
        } else if (otherConditions.length() > 0) {
            sqlBuilder.append(" AND ").append(otherConditions);
            sqlBuilderFTC.append(" AND ").append(otherConditionsFTC);
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
