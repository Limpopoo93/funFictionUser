package FunFictionUserProject.funFictionUser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {
    private String textComments;
    private String email;
    private Long idChapter;
}
