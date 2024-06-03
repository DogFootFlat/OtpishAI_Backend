package otpishAI.otpishAI_Backend.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCustomers is a Querydsl query type for Customers
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCustomers extends EntityPathBase<Customers> {

    private static final long serialVersionUID = 99583669L;

    public static final QCustomers customers = new QCustomers("customers");

    public final StringPath addr = createString("addr");

    public final NumberPath<Long> age = createNumber("age", Long.class);

    public final DateTimePath<java.time.LocalDateTime> birth = createDateTime("birth", java.time.LocalDateTime.class);

    public final StringPath email = createString("email");

    public final NumberPath<Integer> gender = createNumber("gender", Integer.class);

    public final BooleanPath is_secessioned = createBoolean("is_secessioned");

    public final BooleanPath is_suspended = createBoolean("is_suspended");

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final StringPath phone = createString("phone");

    public final ArrayPath<String[], String> preferGenre = createArray("preferGenre", String[].class);

    public final StringPath profile_img = createString("profile_img");

    public final StringPath role = createString("role");

    public final StringPath username = createString("username");

    public QCustomers(String variable) {
        super(Customers.class, forVariable(variable));
    }

    public QCustomers(Path<? extends Customers> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCustomers(PathMetadata metadata) {
        super(Customers.class, metadata);
    }

}

