package otpishAI.otpishAI_Backend.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCart is a Querydsl query type for Cart
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCart extends EntityPathBase<Cart> {

    private static final long serialVersionUID = -710380352L;

    public static final QCart cart = new QCart("cart");

    public final NumberPath<Long> cartNum = createNumber("cartNum", Long.class);

    public final StringPath detailCode = createString("detailCode");

    public final NumberPath<Long> oPrice = createNumber("oPrice", Long.class);

    public final StringPath productCode = createString("productCode");

    public final NumberPath<Long> quantity = createNumber("quantity", Long.class);

    public final NumberPath<Long> rPrice = createNumber("rPrice", Long.class);

    public final StringPath username = createString("username");

    public QCart(String variable) {
        super(Cart.class, forVariable(variable));
    }

    public QCart(Path<? extends Cart> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCart(PathMetadata metadata) {
        super(Cart.class, metadata);
    }

}

