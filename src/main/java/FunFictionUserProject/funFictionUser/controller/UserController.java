package FunFictionUserProject.funFictionUser.controller;

import FunFictionUserProject.funFictionUser.dto.*;
import FunFictionUserProject.funFictionUser.exeption.EntityNotFoundException;
import FunFictionUserProject.funFictionUser.service.*;
import FunFictionUserProject.funFictionUser.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static FunFictionUserProject.funFictionUser.dto.UserListDto.fromUser;

//@Controller
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    private final FunFictionService funFictionService;

    private final GenreService genreService;

    private final CommentsService commentsService;

    private final ChapterService chapterService;

    @Autowired
    public UserController(UserService userService, FunFictionService funFictionService, GenreService genreService, CommentsService commentsService, ChapterService chapterService) {
        this.userService = userService;
        this.funFictionService = funFictionService;
        this.genreService = genreService;
        this.commentsService = commentsService;
        this.chapterService = chapterService;
    }

    /*
    Сохранение юзера со страницы регистрации (1)
     */
    @PostMapping("/save")
    public ResponseEntity<User> save(@RequestBody User user, HttpSession session) {
        User userCheck = userService.findByLogin(user.getLogin());
        if (userCheck != null) {
            throw new EntityNotFoundException(User.class, user);
        }
        user.setStatus(Status.ACTIVE);
        user.setCreated(new Date());
        user.setUpdated(new Date());
        User userResult = userService.registerUser(user);
        UserListDto userListDto = UserListDto.fromUser(user);
        session.setAttribute("userListDto", userListDto);
        //session.setAttribute("token", token);
        return new ResponseEntity<>(userResult, HttpStatus.CREATED);
    }

    /*
    Вход юзера со страницы входа (3)
     */
    @PostMapping("/login")
    public ResponseEntity<UserListDto> loginUser(@RequestBody User user, HttpSession session) {
        User userResult = userService.findByLogin(user.getLogin());
        if (userResult == null) {
            throw new EntityNotFoundException(User.class, user);
        }
       //List<FunFiction> funFictions = funFictionService.findAll();


       UserListDto userList = fromUser(userResult);
        session.setAttribute("userListDto", userList);

        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    /*
    Настройка личных предпочтений после регистрации (15, 16)
     */
    @PostMapping("/userSettingPersonalData")
    public ResponseEntity<User> userSetSettingPersonalUser(@RequestBody User user) {
        User userResult = userService.findById(user.getId());
        userResult.setBackground(user.getBackground());
        userResult.setLanguage(user.getLanguage());
        userService.saveAndFlush(userResult);
        return new ResponseEntity<>(userResult, HttpStatus.OK);
    }

    /*
    Обновление своей информации со страницы настройки и предпочтений (18, 19)
    */
    @PutMapping("/updateUser")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User userResult = userService.findById(user.getId());
        userResult.setLogin(user.getLogin());
        userResult.setPassword(user.getPassword());
        userResult.setEmail(user.getEmail());
        userResult.setUpdated(new Date());
        userResult.setNameUser(user.getNameUser());
        userResult.setSurnameUser(user.getSurnameUser());
        userResult.setBackground(user.getBackground());
        userResult.setLanguage(user.getLanguage());
        User userCreated = userService.saveAndFlush(userResult);
        return new ResponseEntity<>(userCreated, HttpStatus.OK);
    }

    /*
    Список предпочтений юзера со страницы настройки юзера (20)
    */
    @GetMapping("/listFunFicById/{id}")
    public ResponseEntity<List<FunFicRequestDto>> listAllFunFiction(@PathVariable("id") Long id) {
        List<FunFiction> funFictions = funFictionService.findFunFictionByUserId(id);
        List<FunFicRequestDto> funFicRequestDtoList = new ArrayList<>();
        for (FunFiction funfic: funFictions) {
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
        List<Chapter> chapterList = chapterService.findChapterByFunFictionId(funFiction.getId());
        for (Chapter chapter: chapterList) {
            List<Comments> commentsList = commentsService.findAllByChapterId(chapter.getId());
            for(Comments comments: commentsList){
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
    @PostMapping("/addFunFicByUser")
    public ResponseEntity<FunFiction> addFunFicByUser(@RequestBody FunFicRequestDto funFiction) {
        FunFiction funFictionResult = new FunFiction();
        Genre genre = genreService.findByTypeGenre(funFiction.getGenre());
        User user = userService.findById(funFiction.getIdUser());
        funFictionResult.setGenre(genre);
        funFictionResult.setCreated(new Date());
        funFictionResult.setNameFun(funFiction.getNameFun());
        funFictionResult.setShortDescription(funFiction.getShortDescription());
        funFictionResult.setLike(0);
        funFictionResult.setRating(0.0);
        funFictionResult.setUser(user);
        funFictionService.save(funFictionResult);
        return new ResponseEntity<>(funFictionResult, HttpStatus.OK);
    }

    /*
    редактирование произведения со страницы личной информации (21)
    */
    @PostMapping("/updatedFunFicByUser")
    public ResponseEntity<FunFiction> updatedFunFicByUser(@RequestBody FunFiction funFiction) {
        FunFiction funFictionResult = funFictionService.findById(funFiction.getId());
        if (funFiction.getGenre().getTypeGenre() != null) {
            Genre genre = genreService.findByTypeGenre(funFiction.getGenre().getTypeGenre());
            funFictionResult.setGenre(genre);
        }
        funFictionResult.setNameFun(funFiction.getNameFun());
        funFictionResult.setShortDescription(funFiction.getShortDescription());
        FunFiction funFictionNew = funFictionService.saveAndFlush(funFictionResult);
        return new ResponseEntity<>(funFictionNew, HttpStatus.OK);
    }

    /*
    поиск произведений (25)
    */
    @GetMapping("/searchFunFiction/{name}")
    public ResponseEntity<List<FunFiction>> searchFunFiction(@PathVariable("name") String name) {
        List<FunFiction> funFictionList;
        funFictionList = funFictionService.findByNameFunContaining(name);
        if (funFictionList.isEmpty()) {
            List<Comments> comments = commentsService.findByTextCommentContaining(name);
            if (comments != null) {
                for (Comments comment : comments) {
                    FunFiction funFictionResult = funFictionService.findById(comment.getChapter().getFunFiction().getId());
                    funFictionList.add(funFictionResult);
                }
            }
        }
        return new ResponseEntity<>(funFictionList, HttpStatus.OK);
    }

    /*
Просто лист fun fic
 */
    @GetMapping("/listFunFic")
    public ResponseEntity<List<FunFicRequestDto>> listFunFic() {
       List<FunFiction> funFictionList = funFictionService.findAll();
      List<FunFicRequestDto> funFicRequestDtoList = new ArrayList<>();
        for (FunFiction funfic: funFictionList) {
            funFicRequestDtoList.add(FunFicRequestDto.fromFunFic(funfic));
        }
        return new ResponseEntity<>(funFicRequestDtoList, HttpStatus.OK);
    }
    /*
    вывод статьи (глав) определенного фанфика с list funfic стартовая страница
 */
    @GetMapping("/chapterByFunFic/{id}")
    public ResponseEntity<List<ChapterRequestDto>> chapterByFunFic(@PathVariable("id") Long id) {
     List<Chapter> chapterList=  chapterService.findChapterByFunFictionId(id);
        List<ChapterRequestDto> chapterRequestDtos = new ArrayList<>();
        List<CommentRequestDto> commentRequestDtoList = new ArrayList<>();
        CommentRequestDto commentRequestDto = new CommentRequestDto();
        for (Chapter chapter: chapterList) {
            List<Comments> commentsList = commentsService.findAllByChapterId(chapter.getId());
            for (Comments comments: commentsList){
                commentRequestDto.setId(comments.getId());
                commentRequestDto.setNameUser(comments.getUser().getNameUser());
                commentRequestDto.setSurnameUser(comments.getUser().getSurnameUser());
                commentRequestDto.setCreated(comments.getCreated());
                commentRequestDto.setTextComment(comments.getTextComment());
                commentRequestDto.setIdChapter(chapter.getId());
                commentRequestDtoList.add(commentRequestDto);
            }
            chapterRequestDtos.add(ChapterRequestDto.fromFunFic(chapter,commentRequestDtoList));
        }
        return new ResponseEntity<>(chapterRequestDtos, HttpStatus.OK);
    }

    /*
добавление комментария
*/
    @PostMapping("/commentSave")
    public ResponseEntity<CommentRequest> updatedFunFicByUser(@RequestBody CommentRequest commentDto) {
         User user = userService.findByEmail(commentDto.getEmail());
        Chapter chapter = chapterService.findById(commentDto.getIdChapter());
        Comments comments = new Comments();
        comments.setTextComment(commentDto.getTextComments());
        comments.setCreated(new Date());
        comments.setChapter(chapter);
        comments.setUser(user);
        commentsService.save(comments);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }
    /*
    Вывод всех жанров для добавления fun fic
 */
    @GetMapping("/listAllGenre")
    public ResponseEntity<List<Genre>> listAllGenre() {
    List<Genre> genreList = genreService.findAll();
        return new ResponseEntity<>(genreList, HttpStatus.OK);
    }
}
