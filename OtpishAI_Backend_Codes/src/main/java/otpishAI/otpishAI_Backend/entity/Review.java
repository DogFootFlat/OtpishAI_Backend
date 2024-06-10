package otpishAI.otpishAI_Backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "review_img")
    private String[] reviewImg = new String[0];

    @Column(name="review_r_date")
    private LocalDateTime reviewRdate;



}
