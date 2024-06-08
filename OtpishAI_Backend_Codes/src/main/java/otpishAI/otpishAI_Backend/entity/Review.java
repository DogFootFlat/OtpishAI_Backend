package otpishAI.otpishAI_Backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="review_num")
    private Long reviewNum;

    @Column(name="review_owner")
    private String reviewOwner;

    @Column(name="product_num")
    private Long productNum;

    @Column(name="review_title")
    private String reviewTitle;

    @Column(name="review_content")
    private String reviewContent;

    @Type(value = StringArrayType.class)
    @Column(name = "review_img", columnDefinition = "text[]")
    private String[] reviewImg = new String[0];

    @Column(name="review_r_date")
    private LocalDateTime reviewRdate;



}
