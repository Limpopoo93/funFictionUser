package FunFictionUserProject.funFictionUser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDto implements Serializable {
    private Long id;
    private String textComment;
    private Date created;
    private String nameUser;
    private String surnameUser;
    private Long idChapter;
}
