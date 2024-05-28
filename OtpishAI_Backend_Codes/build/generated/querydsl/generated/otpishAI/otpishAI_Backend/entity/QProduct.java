package otpishAI.otpishAI_Backend.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = 1793978191L;

    public static final QProduct product = new QProduct("product");

    public final StringPath category1 = createString("category1");

    public final StringPath category2 = createString("category2");

    public final StringPath category3 = createString("category3");

    public final NumberPath<Integer> favorite = createNumber("favorite", Integer.class);

    public final StringPath genreCode = createString("genreCode");

    public final NumberPath<Integer> isDeleted = createNumber("isDeleted", Integer.class);

    public final StringPath judge = createString("judge");

    public final NumberPath<Integer> oPrice = createNumber("oPrice", Integer.class);

    public final NumberPath<Integer> payment = createNumber("payment", Integer.class);

    public final StringPath productBrand = createString("productBrand");

    public final StringPath productCode = createString("productCode");

    public final NumberPath<Integer> productGender = createNumber("productGender", Integer.class);

    public final StringPath productImg0 = createString("productImg0");

    public final StringPath productImg1 = createString("productImg1");

    public final StringPath productImg2 = createString("productImg2");

    public final StringPath productImg3 = createString("productImg3");

    public final StringPath productImg4 = createString("productImg4");

    public final StringPath productImg5 = createString("productImg5");

    public final StringPath productName = createString("productName");

    public final NumberPath<Integer> productNum = createNumber("productNum", Integer.class);

    public final DateTimePath<java.util.Date> productRdate = createDateTime("productRdate", java.util.Date.class);

    public final StringPath productRegistrant = createString("productRegistrant");

    public final NumberPath<Integer> review = createNumber("review", Integer.class);

    public final NumberPath<Integer> rPrice = createNumber("rPrice", Integer.class);

    public QProduct(String variable) {
        super(Product.class, forVariable(variable));
    }

    public QProduct(Path<? extends Product> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProduct(PathMetadata metadata) {
        super(Product.class, metadata);
    }

}

