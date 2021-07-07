package FunFictionUserProject.funFictionUser.controller;

import FunFictionUserProject.funFictionUser.dto.ChapterRequestDto;
import FunFictionUserProject.funFictionUser.dto.CommentRequestDto;
import FunFictionUserProject.funFictionUser.dto.FunFicRequestDto;
import FunFictionUserProject.funFictionUser.dto.TagsRequestDto;
import FunFictionUserProject.funFictionUser.exeption.EntityNotFoundException;
import FunFictionUserProject.funFictionUser.service.*;
import FunFictionUserProject.funFictionUser.view.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
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
    @GetMapping("/listFunFicById/{id}")
    public ResponseEntity<List<FunFicRequestDto>> listAllFunFiction(@PathVariable("id") Long id) {
        List<FunFiction> funFictions = funFictionService.findFunFictionByUserId(id);
        log.info("fun fic save findFunFictionByUserId listAllFunFiction in workingController");
        List<FunFicRequestDto> funFicRequestDtoList = new ArrayList<>();
        for (FunFiction funfic : funFictions) {
            funFicRequestDtoList.add(FunFicRequestDto.fromFunFic(funfic));
        }
        return new ResponseEntity<>(funFicRequestDtoList, HttpStatus.OK);
    }

    /*
    удалить произведение со страницы личной информации (22)
    */
    @DeleteMapping("/deleteFunFicByUser/{id}")
    public ResponseEntity<FunFiction> deleteFunFicByUser(@PathVariable("id") Long id) {
        FunFiction funFiction = funFictionService.findById(id);
        log.info("fun fic save findById deleteFunFicByUser in workingController");
        if (funFiction == null) {
            throw new EntityNotFoundException("fun fic not found");
        }
        List<Chapter> chapterList = chapterService.findChapterByFunFictionId(funFiction.getId());
        log.info("list chapter findChapterByFunFictionId deleteFunFicByUser in workingController");
        for (Chapter chapter : chapterList) {
            List<Comments> commentsList = commentsService.findAllByChapterId(chapter.getId());
            log.info("list comments findAllByChapterId deleteFunFicByUser in workingController");
            for (Comments comments : commentsList) {
                commentsService.delete(comments);
                log.info("comments delete deleteFunFicByUser in workingController");
            }
            chapterService.delete(chapter);
            log.info("chapter delete deleteFunFicByUser in workingController");
        }
        funFictionService.delete(funFiction);
        log.info("fun fic delete deleteFunFicByUser in workingController");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    добавление произведения со страницы личной информации (23)
    */
    @PostMapping("/addFunFicByUser")
    public ResponseEntity<FunFicRequestDto> addFunFicByUser(@RequestBody FunFicRequestDto funFiction) {
        FunFiction funFictionResult = new FunFiction();
        List<Tags> tagsList = new ArrayList<>();
        Genre genre = genreService.findByTypeGenre(funFiction.getGenre());
        log.info("genre findByTypeGenre addFunFicByUser in workingController");
        User user = userService.findById(funFiction.getIdUser());
        log.info("user findById addFunFicByUser in workingController");
        funFictionResult.setGenre(genre);
        funFictionResult.setCreated(new Date());
        funFictionResult.setNameFun(funFiction.getNameFun());
        funFictionResult.setShortDescription(funFiction.getShortDescription());
        funFictionResult.setLike(0);
        funFictionResult.setRating(0.0);
        funFictionResult.setUser(user);
        for (String tags : funFiction.getTypeTags()) {
            Tags tagsResult = tagsService.findByTypeTags(tags);
            log.info("tags findByTypeTags addFunFicByUser in workingController");
            tagsList.add(tagsResult);
        }
        funFictionResult.setTags(tagsList);
        FunFiction funFictionNew = funFictionService.save(funFictionResult);
        log.info("fun fic save addFunFicByUser in workingController");
        FunFicRequestDto funFicRequestDto = FunFicRequestDto.fromFunFic(funFictionNew);
        return new ResponseEntity<>(funFicRequestDto, HttpStatus.OK);
    }

    /*
    Просто лист fun fic
    */
    @GetMapping("/listFunFic")
    public ResponseEntity<List<FunFicRequestDto>> listFunFic() {
        List<FunFiction> funFictionList = funFictionService.findAll();
        log.info("list fun fic findAll listFunFic in workingController");
        List<FunFicRequestDto> funFicRequestDtoList = new ArrayList<>();
        for (FunFiction funfic : funFictionList) {
            funFicRequestDtoList.add(FunFicRequestDto.fromFunFic(funfic));
        }
        return new ResponseEntity<>(funFicRequestDtoList, HttpStatus.OK);
    }

    /*
    вывод статьи (глав) определенного фанфика с list funfic стартовая страница
    */
    @GetMapping("/chapterByFunFic/{id}")
    public ResponseEntity<List<ChapterRequestDto>> chapterByFunFic(@PathVariable("id") Long id) {
        List<Chapter> chapterList = chapterService.findChapterByFunFictionId(id);
        log.info("chapter findChapterByFunFictionId chapterByFunFic in workingController");
        List<ChapterRequestDto> chapterRequestDtos = new ArrayList<>();
        List<CommentRequestDto> commentRequestDtoList = new ArrayList<>();
        CommentRequestDto commentRequestDto = new CommentRequestDto();
        for (Chapter chapter : chapterList) {
            List<Comments> commentsList = commentsService.findAllByChapterId(chapter.getId());
            log.info("comments findAllByChapterId chapterByFunFic in workingController");
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
    @GetMapping("/listAllGenre")
    public ResponseEntity<List<Genre>> listAllGenre() {
        List<Genre> genreList = genreService.findAll();
        log.info("genre findAll listAllGenre in workingController");
        return new ResponseEntity<>(genreList, HttpStatus.OK);
    }

    /*
    Вывод всех тэгов
    */
    @GetMapping("/listAllTags")
    public ResponseEntity<List<TagsRequestDto>> listAllTags() {
        List<Tags> tagsList = tagsService.findAll();
        log.info("tags findAll listAllTags in workingController");
        List<TagsRequestDto> tagsRequestList = new ArrayList<>();
        for (Tags tags : tagsList) {
            tagsRequestList.add(TagsRequestDto.fromTags(tags));
        }
        return new ResponseEntity<>(tagsRequestList, HttpStatus.OK);
    }

    /*
    добавление глав после добавления fun fic.
    */
    @PostMapping("/addChapter")
    public ResponseEntity<ChapterRequestDto> addChapter(@RequestBody ChapterRequestDto chapterRequestDto) {
        FunFiction funFiction = funFictionService.findById(chapterRequestDto.getIdFunFic());
        log.info("fun fic findById addChapter in workingController");
        if (funFiction == null) {
            throw new EntityNotFoundException("fun fic not found");
        }
        Chapter chapter = new Chapter();
        chapter.setNumberChapter(chapterRequestDto.getNumberChapter());
        chapter.setNameChapter(chapterRequestDto.getNameChapter());
        chapter.setTextChapter(chapterRequestDto.getTextChapter());
        chapter.setFunFiction(funFiction);
        ChapterRequestDto chapterRequestDtoNew = ChapterRequestDto.fromChapter(chapterService.save(chapter));
        log.info("chapter save addChapter in workingController");
        return new ResponseEntity<>(chapterRequestDtoNew, HttpStatus.OK);
    }
}
