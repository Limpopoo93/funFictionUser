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
            /*
ИСПОЛЬЗУЕТСЯ
 */
    @PostMapping("/save")
    public ResponseEntity<UserListDto> save(@RequestBody User user, HttpSession session) {
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
        return new ResponseEntity<>(userListDto, HttpStatus.CREATED);
    }

    /*
    Вход юзера со страницы входа (3)
     */
            /*
ИСПОЛЬЗУЕТСЯ
 */
    @PostMapping("/login")
    public ResponseEntity<UserListDto> loginUser(@RequestBody User user, HttpSession session) {
        User userResult = userService.findByLogin(user.getLogin());
        if (userResult == null) {
            throw new EntityNotFoundException(User.class, user);
        }
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
            /*
ИСПОЛЬЗУЕТСЯ
 */
    @PutMapping("/updateUser")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User userResult = userService.findById(user.getId());
        if (userResult == null) {
            throw new EntityNotFoundException(User.class, userResult);
        }
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
добавление комментария
*/
            /*
ИСПОЛЬЗУЕТСЯ
 */
    @PostMapping("/commentSave")
    public ResponseEntity<CommentRequest> updatedFunFicByUser(@RequestBody CommentRequest commentDto) {
        User user = userService.findByEmail(commentDto.getEmail());
        if (user == null) {
            throw new EntityNotFoundException(User.class, user);
        }
        Chapter chapter = chapterService.findById(commentDto.getIdChapter());
        if (chapter == null) {
            throw new EntityNotFoundException(Chapter.class, chapter);
        }
        Comments comments = new Comments();
        comments.setTextComment(commentDto.getTextComments());
        comments.setCreated(new Date());
        comments.setChapter(chapter);
        comments.setUser(user);
        commentsService.save(comments);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

}
