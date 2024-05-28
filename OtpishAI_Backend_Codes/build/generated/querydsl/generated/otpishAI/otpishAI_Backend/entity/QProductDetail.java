package otpishAI.otpishAI_Backend.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProductDetail is a Querydsl query type for ProductDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductDetail extends EntityPathBase<ProductDetail> {

    private static final long serialVersionUID = -2091254656L;

    public static final QProductDetail productDetail = new QProductDetail("productDetail");

    public final StringPath detailCode = createString("detailCode");

    public final NumberPath<Integer> oPrice = createNumber("oPrice", Integer.class);

    public final StringPath productColor = createString("productColor");

    public final NumberPath<Integer> productInven = createNumber("productInven", Integer.class);

    public final NumberPath<Integer> productNum = createNumber("productNum", Integer.class);

    public final StringPath productSize = createString("productSize");

    public final NumberPath<Integer> rPrice = createNumber("rPrice", Integer.class);

    public QProductDetail(String variable) {
        super(ProductDetail.class, forVariable(variable));
    }

    public QProductDetail(Path<? extends ProductDetail> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductDetail(PathMetadata metadata) {
        super(ProductDetail.class, metadata);
    }

}

