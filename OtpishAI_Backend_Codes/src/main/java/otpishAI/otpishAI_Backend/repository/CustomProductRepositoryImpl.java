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

    public Page<Product> listingProduct(String genre, List<String> brand, List<String> category, Pageable pageable){

        //옷 분류로 찾기(0**01**)
        BooleanExpression whereClause = qProduct.genrecode.likeIgnoreCase("%" + genre + "%");
        
        if(!brand.isEmpty() &&!brand.get(0).equals(""))
        {
            whereClause = whereClause.and(brandPredicate(brand));
        }
        if(!category.isEmpty() && !category.get(0).equals("")){
            whereClause = whereClause.and(categoryPredicate(category));
        }

        System.out.println(whereClause);
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
    private BooleanExpression brandPredicate(List<String> brand) {
        BooleanExpression predicate = null;
        for (String keyword : brand) {

            BooleanExpression keywordPredicate =qProduct.productbrand.likeIgnoreCase("%" + keyword + "%");

            predicate = (predicate == null) ? keywordPredicate : predicate.or(keywordPredicate);
        }


        return predicate;
    }
    //카테고리별 찾기
    private BooleanExpression categoryPredicate(List<String> category) {
        BooleanExpression predicate = null;
        for (String keyword : category) {
            BooleanExpression keywordPredicate_1 = qProduct.category1.likeIgnoreCase("%" + keyword + "%");
            BooleanExpression keywordPredicate_2 = qProduct.category2.likeIgnoreCase("%" + keyword + "%");
            BooleanExpression keywordPredicate_3 = qProduct.category3.likeIgnoreCase("%" + keyword + "%");

            BooleanExpression combinedPredicate = keywordPredicate_1.or(keywordPredicate_2).or(keywordPredicate_3);

            predicate = (predicate == null) ? combinedPredicate : predicate.or(combinedPredicate);
        }
        return predicate;

    }
}

