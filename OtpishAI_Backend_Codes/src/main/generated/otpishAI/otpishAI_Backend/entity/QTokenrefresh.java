package otpishAI.otpishAI_Backend.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTokenrefresh is a Querydsl query type for Tokenrefresh
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTokenrefresh extends EntityPathBase<Tokenrefresh> {

    private static final long serialVersionUID = 268744802L;

    public static final QTokenrefresh tokenrefresh = new QTokenrefresh("tokenrefresh");

    public final StringPath expiration = createString("expiration");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath refresh = createString("refresh");

    public final StringPath username = createString("username");

    public QTokenrefresh(String variable) {
        super(Tokenrefresh.class, forVariable(variable));
    }

    public QTokenrefresh(Path<? extends Tokenrefresh> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTokenrefresh(PathMetadata metadata) {
        super(Tokenrefresh.class, metadata);
    }

}

