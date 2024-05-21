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

    public final StringPath genrecode = createString("genrecode");

    public final NumberPath<Integer> isdeleted = createNumber("isdeleted", Integer.class);

    public final StringPath judge = createString("judge");

    public final NumberPath<Integer> oprice = createNumber("oprice", Integer.class);

    public final NumberPath<Integer> payment = createNumber("payment", Integer.class);

    public final StringPath productbrand = createString("productbrand");

    public final StringPath productcode = createString("productcode");

    public final StringPath productimg0 = createString("productimg0");

    public final StringPath productimg1 = createString("productimg1");

    public final StringPath productimg2 = createString("productimg2");

    public final StringPath productimg3 = createString("productimg3");

    public final StringPath productimg4 = createString("productimg4");

    public final StringPath productimg5 = createString("productimg5");

    public final StringPath productname = createString("productname");

    public final DateTimePath<java.util.Date> productrdate = createDateTime("productrdate", java.util.Date.class);

    public final NumberPath<Integer> review = createNumber("review", Integer.class);

    public final NumberPath<Integer> rprice = createNumber("rprice", Integer.class);

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

