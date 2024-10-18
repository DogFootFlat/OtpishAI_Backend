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

    public final ArrayPath<String[], String> category = createArray("category", String[].class);

    public final NumberPath<Long> favorite = createNumber("favorite", Long.class);

    public final StringPath genreCode = createString("genreCode");

    public final NumberPath<Integer> isDeleted = createNumber("isDeleted", Integer.class);

    public final NumberPath<Long> judge = createNumber("judge", Long.class);

    public final NumberPath<Long> oPrice = createNumber("oPrice", Long.class);

    public final NumberPath<Long> payment = createNumber("payment", Long.class);

    public final StringPath productBrand = createString("productBrand");

    public final StringPath productCode = createString("productCode");

    public final NumberPath<Integer> productGender = createNumber("productGender", Integer.class);

    public final ArrayPath<String[], String> productImg = createArray("productImg", String[].class);

    public final ArrayPath<String[], String> productInfo = createArray("productInfo", String[].class);

    public final StringPath productName = createString("productName");

    public final NumberPath<Long> productNum = createNumber("productNum", Long.class);

    public final DateTimePath<java.time.LocalDateTime> productRdate = createDateTime("productRdate", java.time.LocalDateTime.class);

    public final StringPath productRegistrant = createString("productRegistrant");

    public final NumberPath<Long> review = createNumber("review", Long.class);

    public final NumberPath<Long> rPrice = createNumber("rPrice", Long.class);

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

