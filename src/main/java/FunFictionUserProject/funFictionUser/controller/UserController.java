package FunFictionUserProject.funFictionUser.controller;

import FunFictionUserProject.funFictionUser.dto.CommentRequest;
import FunFictionUserProject.funFictionUser.dto.UserListDto;
import FunFictionUserProject.funFictionUser.exeption.EntityNotFoundException;
import FunFictionUserProject.funFictionUser.service.*;
import FunFictionUserProject.funFictionUser.view.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

import static FunFictionUserProject.funFictionUser.dto.UserListDto.fromUser;

@Slf4j
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
    public ResponseEntity<UserListDto> save(@RequestBody User user, HttpSession session) {
        User userCheck = userService.findByLogin(user.getLogin());
        log.info("user findByLogin method save in userController");
        if (userCheck == null) {
            throw new EntityNotFoundException("user not found");
        }
        user.setStatus(Status.ACTIVE);
        user.setCreated(new Date());
        user.setUpdated(new Date());
        userService.registerUser(user);
        log.info("user registerUser method save in userController");
        UserListDto userListDto = UserListDto.fromUser(user);
        session.setAttribute("userListDto", userListDto);
        //session.setAttribute("token", token);
        return new ResponseEntity<>(userListDto, HttpStatus.CREATED);
    }

    /*
    Вход юзера со страницы входа (3)
    */
    @PostMapping("/login")
    public ResponseEntity<UserListDto> loginUser(@RequestBody User user, HttpSession session) {
        User userResult = userService.findByLogin(user.getLogin());
        log.info("user findByLogin method loginUser in userController");
        if (userResult == null) {
            throw new EntityNotFoundException("user not found");
        }
        if (userResult.getStatus().equals(Status.BLOCK) || userResult.getStatus().equals(Status.DELETE)){
            throw new EntityNotFoundException("user blocked or deleted");
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
        log.info("user findById method userSetSettingPersonalUser in userController");
        if (userResult == null) {
            throw new EntityNotFoundException("user not found");
        }
        userResult.setBackground(user.getBackground());
        userResult.setLanguage(user.getLanguage());
        userService.saveAndFlush(userResult);
        log.info("user saveAndFlush method userSetSettingPersonalUser in userController");
        return new ResponseEntity<>(userResult, HttpStatus.OK);
    }

    /*
    Обновление своей информации со страницы настройки и предпочтений (18, 19)
    */
    @PutMapping("/updateUser")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User userResult = userService.findById(user.getId());
        log.info("user findById method updateUser in userController");
        if (userResult == null) {
            throw new EntityNotFoundException("user not found");
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
        log.info("user saveAndFlush method updateUser in userController");
        return new ResponseEntity<>(userCreated, HttpStatus.OK);
    }

    /*
    редактирование произведения со страницы личной информации (21)
    */
    @PostMapping("/updatedFunFicByUser")
    public ResponseEntity<FunFiction> updatedFunFicByUser(@RequestBody FunFiction funFiction) {
        FunFiction funFictionResult = funFictionService.findById(funFiction.getId());
        log.info("fun fic findById method updatedFunFicByUser in userController");
        if (funFictionResult == null) {
            throw new EntityNotFoundException("fun fic not found");
        }
        if (funFiction.getGenre().getTypeGenre() != null) {
            Genre genre = genreService.findByTypeGenre(funFiction.getGenre().getTypeGenre());
            log.info("genre findByTypeGenre method updatedFunFicByUser in userController");
            funFictionResult.setGenre(genre);
        }
        funFictionResult.setNameFun(funFiction.getNameFun());
        funFictionResult.setShortDescription(funFiction.getShortDescription());
        FunFiction funFictionNew = funFictionService.saveAndFlush(funFictionResult);
        log.info("fun fic saveAndFlush method updatedFunFicByUser in userController");
        return new ResponseEntity<>(funFictionNew, HttpStatus.OK);
    }

    /*
    поиск произведений (25)
    */
    @GetMapping("/searchFunFiction/{name}")
    public ResponseEntity<List<FunFiction>> searchFunFiction(@PathVariable("name") String name) {
        List<FunFiction> funFictionList = funFictionService.findByNameFunContainingOrderByRatingDesc(name);
        log.info("list fun fic findByNameFunContaining method searchFunFiction in userController");
        if (funFictionList.isEmpty()) {
            List<Comments> comments = commentsService.findByTextCommentContaining(name);
            log.info("list comments findByTextCommentContaining method searchFunFiction in userController");
            if (comments != null) {
                for (Comments comment : comments) {
                    FunFiction funFictionResult = funFictionService.findById(comment.getChapter().getFunFiction().getId());
                    log.info("fun fic findById method searchFunFiction in userController");
                    funFictionList.add(funFictionResult);
                }
            }
        }
        return new ResponseEntity<>(funFictionList, HttpStatus.OK);
    }

    /*
    добавление комментария
    */
    @PostMapping("/commentSave")
    public ResponseEntity<CommentRequest> updatedFunFicByUser(@RequestBody CommentRequest commentDto) {
        User user = userService.findByEmail(commentDto.getEmail());
        log.info("user findByEmail method updatedFunFicByUser in userController");
        if (user == null) {
            throw new EntityNotFoundException("user not found");
        }
        Chapter chapter = chapterService.findById(commentDto.getIdChapter());
        log.info("chapter findById method updatedFunFicByUser in userController");
        if (chapter == null) {
            throw new EntityNotFoundException("chapter not found");
        }
        Comments comments = new Comments();
        comments.setTextComment(commentDto.getTextComments());
        comments.setCreated(new Date());
        comments.setChapter(chapter);
        comments.setUser(user);
        commentsService.save(comments);
        log.info("comments save method updatedFunFicByUser in userController");
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

}
