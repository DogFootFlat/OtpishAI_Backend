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

    public final NumberPath<Long> oPrice = createNumber("oPrice", Long.class);

    public final StringPath productCode = createString("productCode");

    public final ArrayPath<float[], Float> productColor = createArray("productColor", float[].class);

    public final NumberPath<Long> productInven = createNumber("productInven", Long.class);

    public final NumberPath<Long> productNum = createNumber("productNum", Long.class);

    public final StringPath productSize = createString("productSize");

    public final NumberPath<Long> rPrice = createNumber("rPrice", Long.class);

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

