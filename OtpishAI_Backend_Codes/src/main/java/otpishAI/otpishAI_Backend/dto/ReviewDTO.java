package otpishAI.otpishAI_Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {

    private Long reviewNum;

    private String reviewOwner;

    private Long productNum;

    private String reviewTitle;

    private String reviewContent;

    private String[] reviewImg = new String[0];

    private LocalDateTime reviewRdate;

}
