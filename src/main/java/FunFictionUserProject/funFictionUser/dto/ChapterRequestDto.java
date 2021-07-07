package FunFictionUserProject.funFictionUser.dto;

import FunFictionUserProject.funFictionUser.view.Chapter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChapterRequestDto implements Serializable {
    private Long id;
    private int numberChapter;
    private String nameChapter;
    private String textChapter;
    private long idFunFic;
    private List<CommentRequestDto> commentRequestDtos;

    public static ChapterRequestDto fromFunFic(Chapter chapter, List<CommentRequestDto> commentRequestDto) {
        ChapterRequestDto chapterRequestDto = new ChapterRequestDto();
        chapterRequestDto.setId(chapter.getId());
        chapterRequestDto.setNumberChapter(chapter.getNumberChapter());
        chapterRequestDto.setNameChapter(chapter.getNameChapter());
        chapterRequestDto.setTextChapter(chapter.getTextChapter());
        chapterRequestDto.setCommentRequestDtos(commentRequestDto);
        chapterRequestDto.setIdFunFic(chapter.getFunFiction().getId());
        return chapterRequestDto;
    }

    public static ChapterRequestDto fromChapter(Chapter chapter) {
        ChapterRequestDto chapterRequestDto = new ChapterRequestDto();
        chapterRequestDto.setId(chapter.getId());
        chapterRequestDto.setNumberChapter(chapter.getNumberChapter());
        chapterRequestDto.setNameChapter(chapter.getNameChapter());
        chapterRequestDto.setTextChapter(chapter.getTextChapter());
        chapterRequestDto.setIdFunFic(chapter.getFunFiction().getId());
        return chapterRequestDto;
    }
}
