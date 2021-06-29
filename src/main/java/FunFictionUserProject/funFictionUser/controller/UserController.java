package FunFictionUserProject.funFictionUser.controller;

import FunFictionUserProject.funFictionUser.dto.UserListDto;
import FunFictionUserProject.funFictionUser.exeption.EntityNotFoundException;
import FunFictionUserProject.funFictionUser.service.CommentsService;
import FunFictionUserProject.funFictionUser.service.FunFictionService;
import FunFictionUserProject.funFictionUser.service.GenreService;
import FunFictionUserProject.funFictionUser.service.UserService;
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
import static FunFictionUserProject.funFictionUser.util.DataConstant.*;
import static FunFictionUserProject.funFictionUser.util.UrlConstant.*;
import static FunFictionUserProject.funFictionUser.util.UrlConstant.USER;

@RestController
@RequestMapping(USER)
public class UserController {
    private final UserService userService;

    private final FunFictionService funFictionService;

    private final GenreService genreService;

    private final CommentsService commentsService;

    @Autowired
    public UserController(UserService userService, FunFictionService funFictionService, GenreService genreService, CommentsService commentsService) {
        this.userService = userService;
        this.funFictionService = funFictionService;
        this.genreService = genreService;
        this.commentsService = commentsService;
    }

    /*
    Сохранение юзера со страницы регистрации (1)
     */
    @PostMapping(SAVE)
    public ResponseEntity<User> save(@RequestBody User user, HttpSession session) {
        User userCheck = userService.findByLogin(user.getLogin());
        if (userCheck != null) {
            throw new EntityNotFoundException(User.class, user);
        }
        user.setStatus(Status.ACTIVE);
        user.setCreated(new Date());
        user.setUpdated(new Date());
        User userResult = userService.registerUser(user);
        session.setAttribute(USER_RESULT, userResult);
        //session.setAttribute("token", token);
        return new ResponseEntity<>(userResult, HttpStatus.CREATED);
    }

    /*
    Вход юзера со страницы входа (3)
     */
    @PostMapping(LOGIN_URL)
    public ResponseEntity<UserListDto> loginUser(@RequestBody User user, HttpSession session) {
        User userResult = userService.findByLogin(user.getLogin());
        if (userResult == null) {
            throw new EntityNotFoundException(User.class, user);
        }
        if(userResult.getStatus().equals(Status.BLOCK) || userResult.getStatus().equals(Status.DELETE)){
            throw new EntityNotFoundException(User.class, user);
        }
        UserListDto userList = fromUser(userResult);
        session.setAttribute(USER, user);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    /*
    Настройка личных предпочтений после регистрации (15, 16)
     */
    @PostMapping(SETTING_USER)
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
    @PutMapping(UPDATE_USER)
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
    @GetMapping(LIST_FUN_FIC)
    public ResponseEntity<List<FunFiction>> listAllFunFiction(@RequestBody User user) {
        List<FunFiction> funFictions = funFictionService.findFunFictionByUserId(user.getId());
        return new ResponseEntity<>(funFictions, HttpStatus.OK);
    }

    /*
    удалить произведение со страницы личной информации (22)
     */
    @DeleteMapping(DELETE_FUN_FIC)
    public ResponseEntity<FunFiction> deleteFunFicByUser(@PathVariable(ID) Long id) {
        FunFiction funFiction = funFictionService.findById(id);
        funFictionService.delete(funFiction);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    добавление произведения со страницы личной информации (23)
    */
    @PostMapping(ADD_FUN_FIC)
    public ResponseEntity<FunFiction> addFunFicByUser(@RequestBody FunFiction funFiction, @RequestBody User user) {
        Genre genre = genreService.findByTypeGenre(funFiction.getGenre().getTypeGenre());
        funFiction.setGenre(genre);
        User userResult = userService.findById(user.getId());
        funFiction.setUser(user);
        FunFiction funFictionResult = funFictionService.save(funFiction);
        return new ResponseEntity<>(funFictionResult, HttpStatus.OK);
    }

    /*
    редактирование произведения со страницы личной информации (21)
    */
    @PostMapping(UPDATE_FUN_FIC)
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
    @GetMapping(SEARCH_FUN_FIC)
    public ResponseEntity<List<FunFiction>> searchFunFiction(@PathVariable(NAME_USER) String name) {
        List<FunFiction> funFictionList;
        funFictionList = funFictionService.findByNameFunContaining(name);
        if (funFictionList.isEmpty()) {
            List<Comments> comments = commentsService.findByTextCommentContaining(name);
            if (comments != null) {
                for (Comments comment : comments) {
                    FunFiction funFictionResult = funFictionService.findById(comment.getFunFiction().getId());
                    funFictionList.add(funFictionResult);
                }
            }
        }
        return new ResponseEntity<>(funFictionList, HttpStatus.OK);
    }
}
