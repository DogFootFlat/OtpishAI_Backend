package otpishAI.otpishAI_Backend.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReview is a Querydsl query type for Review
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReview extends EntityPathBase<Review> {

    private static final long serialVersionUID = 657524984L;

    public static final QReview review = new QReview("review");

    public final NumberPath<Long> productNum = createNumber("productNum", Long.class);

    public final StringPath reviewContent = createString("reviewContent");

    public final ArrayPath<String[], String> reviewImg = createArray("reviewImg", String[].class);

    public final NumberPath<Long> reviewNum = createNumber("reviewNum", Long.class);

    public final StringPath reviewOwner = createString("reviewOwner");

    public final DateTimePath<java.time.LocalDateTime> reviewRdate = createDateTime("reviewRdate", java.time.LocalDateTime.class);

    public final StringPath reviewTitle = createString("reviewTitle");

    public QReview(String variable) {
        super(Review.class, forVariable(variable));
    }

    public QReview(Path<? extends Review> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReview(PathMetadata metadata) {
        super(Review.class, metadata);
    }

}

