package FunFictionUserProject.funFictionUser.controller;

import FunFictionUserProject.funFictionUser.dto.ChapterRequestDto;
import FunFictionUserProject.funFictionUser.dto.CommentRequestDto;
import FunFictionUserProject.funFictionUser.dto.FunFicRequestDto;
import FunFictionUserProject.funFictionUser.dto.TagsRequestDto;
import FunFictionUserProject.funFictionUser.exeption.EntityNotFoundException;
import FunFictionUserProject.funFictionUser.service.*;
import FunFictionUserProject.funFictionUser.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/working")
public class WorkingController {
    private final UserService userService;

    private final FunFictionService funFictionService;

    private final GenreService genreService;

    private final CommentsService commentsService;

    private final ChapterService chapterService;

    private final TagsService tagsService;

    @Autowired
    public WorkingController(UserService userService, FunFictionService funFictionService, GenreService genreService, CommentsService commentsService, ChapterService chapterService, TagsService tagsService) {
        this.userService = userService;
        this.funFictionService = funFictionService;
        this.genreService = genreService;
        this.commentsService = commentsService;
        this.chapterService = chapterService;
        this.tagsService = tagsService;
    }

    /*
Список предпочтений юзера со страницы настройки юзера (20)
*/
            /*
ИСПОЛЬЗУЕТСЯ
 */
    @GetMapping("/listFunFicById/{id}")
    public ResponseEntity<List<FunFicRequestDto>> listAllFunFiction(@PathVariable("id") Long id) {
        List<FunFiction> funFictions = funFictionService.findFunFictionByUserId(id);
        List<FunFicRequestDto> funFicRequestDtoList = new ArrayList<>();
        for (FunFiction funfic : funFictions) {
            funFicRequestDtoList.add(FunFicRequestDto.fromFunFic(funfic));
        }
        return new ResponseEntity<>(funFicRequestDtoList, HttpStatus.OK);
    }

    /*
удалить произведение со страницы личной информации (22)
 */
            /*
ИСПОЛЬЗУЕТСЯ
 */
    @DeleteMapping("/deleteFunFicByUser/{id}")
    public ResponseEntity<FunFiction> deleteFunFicByUser(@PathVariable("id") Long id) {
        FunFiction funFiction = funFictionService.findById(id);
        if (funFiction == null) {
            throw new EntityNotFoundException(FunFiction.class, funFiction);
        }
        List<Chapter> chapterList = chapterService.findChapterByFunFictionId(funFiction.getId());
        for (Chapter chapter : chapterList) {
            List<Comments> commentsList = commentsService.findAllByChapterId(chapter.getId());
            for (Comments comments : commentsList) {
                commentsService.delete(comments);
            }
            chapterService.delete(chapter);
        }
        funFictionService.delete(funFiction);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    добавление произведения со страницы личной информации (23)
    */
            /*
ИСПОЛЬЗУЕТСЯ
 */
    @PostMapping("/addFunFicByUser")
    public ResponseEntity<FunFiction> addFunFicByUser(@RequestBody FunFicRequestDto funFiction) {
        FunFiction funFictionResult = new FunFiction();
        List<Tags> tagsList = new ArrayList<>();
            Genre genre = genreService.findByTypeGenre(funFiction.getGenre());
        User user = userService.findById(funFiction.getIdUser());
        funFictionResult.setGenre(genre);
        funFictionResult.setCreated(new Date());
        funFictionResult.setNameFun(funFiction.getNameFun());
        funFictionResult.setShortDescription(funFiction.getShortDescription());
        funFictionResult.setLike(0);
        funFictionResult.setRating(0.0);
        funFictionResult.setUser(user);
        for(String tags: funFiction.getTypeTags()){
            Tags tagsResult = tagsService.findByTypeTags(tags);
            tagsList.add(tagsResult);
        }
        funFictionResult.setTags(tagsList);
        funFictionService.save(funFictionResult);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
Просто лист fun fic
*/
        /*
ИСПОЛЬЗУЕТСЯ
 */
    @GetMapping("/listFunFic")
    public ResponseEntity<List<FunFicRequestDto>> listFunFic() {
        List<FunFiction> funFictionList = funFictionService.findAll();
        List<FunFicRequestDto> funFicRequestDtoList = new ArrayList<>();
        for (FunFiction funfic : funFictionList) {
            funFicRequestDtoList.add(FunFicRequestDto.fromFunFic(funfic));
        }
        return new ResponseEntity<>(funFicRequestDtoList, HttpStatus.OK);
    }

    /*
    вывод статьи (глав) определенного фанфика с list funfic стартовая страница
 */
        /*
ИСПОЛЬЗУЕТСЯ
 */
    @GetMapping("/chapterByFunFic/{id}")
    public ResponseEntity<List<ChapterRequestDto>> chapterByFunFic(@PathVariable("id") Long id) {
        List<Chapter> chapterList = chapterService.findChapterByFunFictionId(id);
        List<ChapterRequestDto> chapterRequestDtos = new ArrayList<>();
        List<CommentRequestDto> commentRequestDtoList = new ArrayList<>();
        CommentRequestDto commentRequestDto = new CommentRequestDto();
        for (Chapter chapter : chapterList) {
            List<Comments> commentsList = commentsService.findAllByChapterId(chapter.getId());
            for (Comments comments : commentsList) {
                commentRequestDto.setId(comments.getId());
                commentRequestDto.setNameUser(comments.getUser().getNameUser());
                commentRequestDto.setSurnameUser(comments.getUser().getSurnameUser());
                commentRequestDto.setCreated(comments.getCreated());
                commentRequestDto.setTextComment(comments.getTextComment());
                commentRequestDto.setIdChapter(chapter.getId());
                commentRequestDtoList.add(commentRequestDto);
            }
            chapterRequestDtos.add(ChapterRequestDto.fromFunFic(chapter, commentRequestDtoList));
        }
        return new ResponseEntity<>(chapterRequestDtos, HttpStatus.OK);
    }

    /*
Вывод всех жанров для добавления fun fic
*/
            /*
ИСПОЛЬЗУЕТСЯ
 */
    @GetMapping("/listAllGenre")
    public ResponseEntity<List<Genre>> listAllGenre() {
        List<Genre> genreList = genreService.findAll();
        return new ResponseEntity<>(genreList, HttpStatus.OK);
    }

    /*
Вывод всех тэгов
*/
            /*
ИСПОЛЬЗУЕТСЯ
 */
    @GetMapping("/listAllTags")
    public ResponseEntity<List<TagsRequestDto>> listAllTags() {
        List<Tags> tagsList = tagsService.findAll();
        List<TagsRequestDto> tagsRequestList = new ArrayList<>();
        for (Tags tags : tagsList) {
            tagsRequestList.add(TagsRequestDto.fromTags(tags));
        }
        return new ResponseEntity<>(tagsRequestList, HttpStatus.OK);
    }
}
