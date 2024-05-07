package otpishAI.otpishAI_Backend.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import otpishAI.otpishAI_Backend.entity.Product;
import otpishAI.otpishAI_Backend.entity.QProduct;

import java.util.List;

@Repository
@AllArgsConstructor
public class CustomProductRepositoryImpl implements CustomProductRepository{

    private final JPAQueryFactory jpaQueryFactory;
    private final QProduct qProduct = QProduct.product;

    public Page<Product> listingProduct(String genre, List<Integer> brand, List<String> category, Pageable pageable){

        //옷 분류로 찾기
        BooleanExpression whereClause = qProduct.genre_code.likeIgnoreCase("%" + genre + "%");
        
        if(!(brand.get(0) == 0 && brand.isEmpty()))
        {
            whereClause.and(brandPredicate(brand));
        }
        if(!(category.get(0).equals("") && category.isEmpty())){
            whereClause.and(categoryPredicate(category));
        }

        List<Product> results = jpaQueryFactory
                .selectFrom(qProduct)
                .where(whereClause)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long totalCount = jpaQueryFactory
                .selectFrom(qProduct)
                .where(whereClause)
                .fetchCount();

        return new PageImpl<>(results, pageable, totalCount);
    }
    //브랜드 별 찾기
    private BooleanExpression brandPredicate(List<Integer> brand) {
        BooleanExpression predicate = null;
        for (Integer keyword : brand) {

            BooleanExpression keywordPredicate = qProduct.product_brand.eq(keyword);

            predicate = (predicate == null) ? keywordPredicate : predicate.or(keywordPredicate);
        }


        return predicate;
    }
    //카테고리별 찾기
    private BooleanExpression categoryPredicate(List<String> category) {
        BooleanExpression predicate = null;
        for (String keyword : category) {
            BooleanExpression keywordPredicate_1 = qProduct.category_1.contains(keyword);
            BooleanExpression keywordPredicate_2 = qProduct.category_2.contains(keyword);
            BooleanExpression keywordPredicate_3 = qProduct.category_3.contains(keyword);

            BooleanExpression combinedPredicate = keywordPredicate_1.or(keywordPredicate_2).or(keywordPredicate_3);

            predicate = (predicate == null) ? combinedPredicate : predicate.or(combinedPredicate);
        }
        return predicate;

    }
}

