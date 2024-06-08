package otpishAI.otpishAI_Backend.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import otpishAI.otpishAI_Backend.entity.StringArrayType;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewDTO {

    private Long reviewNum;

    private String reviewOwner;

    private Long productNum;

    private String reviewTitle;

    private String reviewContent;

    private String[] reviewImg = new String[0];

    private LocalDateTime reviewRdate;

}
