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

    public final StringPath category_1 = createString("category_1");

    public final StringPath category_2 = createString("category_2");

    public final StringPath category_3 = createString("category_3");

    public final NumberPath<Integer> favorite_N = createNumber("favorite_N", Integer.class);

    public final StringPath genre_code = createString("genre_code");

    public final NumberPath<Integer> is_deleted = createNumber("is_deleted", Integer.class);

    public final StringPath judge = createString("judge");

    public final NumberPath<Integer> O_price = createNumber("O_price", Integer.class);

    public final NumberPath<Integer> payment_N = createNumber("payment_N", Integer.class);

    public final NumberPath<Integer> product_brand = createNumber("product_brand", Integer.class);

    public final StringPath product_code = createString("product_code");

    public final StringPath product_img_0 = createString("product_img_0");

    public final StringPath product_img_1 = createString("product_img_1");

    public final StringPath product_img_2 = createString("product_img_2");

    public final StringPath product_img_3 = createString("product_img_3");

    public final StringPath product_img_4 = createString("product_img_4");

    public final StringPath product_img_5 = createString("product_img_5");

    public final StringPath product_name = createString("product_name");

    public final DateTimePath<java.util.Date> product_R_date = createDateTime("product_R_date", java.util.Date.class);

    public final NumberPath<Integer> R_price = createNumber("R_price", Integer.class);

    public final NumberPath<Integer> review_N = createNumber("review_N", Integer.class);

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

