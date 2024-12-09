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

import java.util.List;
@Repository
@AllArgsConstructor
public class CustomProductRepositoryImpl implements CustomProductRepository {

    private final EntityManager entityManager;

    @Override
    public Page<Product> listingProduct(String genre, List<String> brand, List<String> category, String productName, String productCode, String productRegistrant, Pageable pageable, Boolean isSearch) {
        StringBuilder sqlBuilder = new StringBuilder();
        StringBuilder sqlBuilderFTC = new StringBuilder();

        // 기본 SELECT 문
        sqlBuilder.append("SELECT * FROM otpishai_schema.product WHERE is_deleted = 0 and 1 = 1");
        sqlBuilderFTC.append("SELECT COUNT(*) FROM otpishai_schema.product WHERE is_deleted = 0 and 1 = 1");

        sqlBuilder.append(" AND genre_code ILIKE '%").append(genre).append("%'");
        sqlBuilderFTC.append(" AND genre_code ILIKE '%").append(genre).append("%'");

        // 나머지 조건들을 OR 또는 AND로 묶기
        StringBuilder otherConditions = new StringBuilder();
        StringBuilder otherConditionsFTC = new StringBuilder();
        String connector = isSearch ? " OR " : " AND ";

        if (!productName.equals("")) {
            if (!otherConditions.isEmpty()) {
                otherConditions.append(connector);
                otherConditionsFTC.append(connector);
            }
            otherConditions.append("product_name ILIKE '%").append(productName).append("%'");
            otherConditionsFTC.append("product_name ILIKE '%").append(productName).append("%'");
        }

        if (!productCode.equals("")) {
            if (!otherConditions.isEmpty()) {
                otherConditions.append(connector);
                otherConditionsFTC.append(connector);
            }
            otherConditions.append("product_code ILIKE '%").append(productCode).append("%'");
            otherConditionsFTC.append("product_code ILIKE '%").append(productCode).append("%'");
        }

        if (!productRegistrant.equals("")) {
            if (!otherConditions.isEmpty()) {
                otherConditions.append(connector);
                otherConditionsFTC.append(connector);
            }
            otherConditions.append("product_registrant ILIKE '%").append(productRegistrant).append("%'");
            otherConditionsFTC.append("product_registrant ILIKE '%").append(productRegistrant).append("%'");
        }

        if (!brand.isEmpty() && !brand.get(0).equals("")) {
            if (!otherConditions.isEmpty()) {
                otherConditions.append(connector);
                otherConditionsFTC.append(connector);
            }
            otherConditions.append("(");
            otherConditionsFTC.append("(");
            for (int i = 0; i < brand.size(); i++) {
                if (i > 0) {
                    otherConditions.append(" OR ");
                    otherConditionsFTC.append(" OR ");
                }
                otherConditions.append("product_brand ILIKE '%").append(brand.get(i)).append("%'");
                otherConditionsFTC.append("product_brand ILIKE '%").append(brand.get(i)).append("%'");
            }
            otherConditions.append(")");
            otherConditionsFTC.append(")");
        }

        if (!category.isEmpty() && !category.get(0).equals("")) {
            if (!otherConditions.isEmpty()) {
                otherConditions.append(connector);
                otherConditionsFTC.append(connector);
            }
            otherConditions.append("(");
            otherConditionsFTC.append("(");
            for (int i = 0; i < category.size(); i++) {
                if (i > 0) {
                    otherConditions.append(" OR ");
                    otherConditionsFTC.append(" OR ");
                }
                otherConditions.append("array_to_string(category, ',') ILIKE '%").append(category.get(i)).append("%'");
                otherConditionsFTC.append("array_to_string(category, ',') ILIKE '%").append(category.get(i)).append("%'");
            }
            otherConditions.append(")");
            otherConditionsFTC.append(")");
        }

        // isSearch가 true이면 다른 조건들을 OR로 묶어야 하므로 괄호로 묶기
        if (isSearch && !otherConditions.isEmpty()) {
            sqlBuilder.append(" AND (").append(otherConditions).append(")");
            sqlBuilderFTC.append(" AND (").append(otherConditionsFTC).append(")");
        } else if (!otherConditions.isEmpty()) {
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

        System.out.println(sqlBuilder);
        Long totalCount = ((Number) countQuery.getSingleResult()).longValue();

        // 페이지네이션 적용
        nativeQuery.setFirstResult((int) pageable.getOffset());
        nativeQuery.setMaxResults(pageable.getPageSize());

        List<Product> resultList = nativeQuery.getResultList();
        return new PageImpl<>(resultList, pageable, totalCount);
    }
}