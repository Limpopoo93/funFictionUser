package FunFictionUserProject.funFictionUser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest implements Serializable {
    private String textComments;
    private String email;
    private Long idChapter;
}
