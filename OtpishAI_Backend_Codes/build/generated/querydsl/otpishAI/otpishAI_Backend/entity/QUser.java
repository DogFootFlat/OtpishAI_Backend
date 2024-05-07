package otpishAI.otpishAI_Backend.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -709827221L;

    public static final QUser user = new QUser("user");

    public final StringPath addr = createString("addr");

    public final DateTimePath<java.util.Date> birth = createDateTime("birth", java.util.Date.class);

    public final StringPath email = createString("email");

    public final NumberPath<Integer> gender = createNumber("gender", Integer.class);

    public final StringPath genre1 = createString("genre1");

    public final StringPath genre2 = createString("genre2");

    public final StringPath genre3 = createString("genre3");

    public final StringPath is_secessioned = createString("is_secessioned");

    public final StringPath is_suspended = createString("is_suspended");

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final StringPath phone = createString("phone");

    public final StringPath profile_img = createString("profile_img");

    public final StringPath role = createString("role");

    public final StringPath username = createString("username");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

