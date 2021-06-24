package FunFictionUserProject.funFictionUser.controller;

import FunFictionUserProject.funFictionUser.exeption.EntityNotFoundException;
import FunFictionUserProject.funFictionUser.security.JwtTokenProvider;
import FunFictionUserProject.funFictionUser.service.*;
import FunFictionUserProject.funFictionUser.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@Controller
@RestController
@RequestMapping("/user")
public class UserController {
    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final FunFictionService funFictionService;

    private final GenreService genreService;

    private final ChapterService chapterService;

    private final CommentsService commentsService;

    @Autowired
    public UserController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, PasswordEncoder passwordEncoder, FunFictionService funFictionService, GenreService genreService, ChapterService chapterService, CommentsService commentsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.funFictionService = funFictionService;
        this.genreService = genreService;
        this.chapterService = chapterService;
        this.commentsService = commentsService;
    }

    /*
    Сохранение юзера со страницы регистрации (1)
     */
    @PostMapping("/save")
    public ResponseEntity<User> save(@RequestBody User user) {
           /*
            if (!registerRequestDto.getPassword().equals(registerRequestDto.getSecondPassword())) {
                throw new BadCredentialsException("password haven't correct");
            }
            */
        User userCheck = userService.findByLogin(user.getLogin());
        if (userCheck != null) {
            throw new EntityNotFoundException(User.class, user);
        }
        user.setStatus(Status.ACTIVE);
        user.setCreated(new Date());
        user.setUpdated(new Date());
        User userResult = userService.registerUser(user);
        String username = userResult.getLogin();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, userResult.getPassword()));
        String token = jwtTokenProvider.createToken(username, userResult.getRoles());
        //HttpSession session = null;
        //session.setAttribute("userResult", userResult);
        //session.setAttribute("token", token);
        return new ResponseEntity<>(userResult, HttpStatus.CREATED);
    }

    /*
    Вход юзера со страницы входа (3)
     */
    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User user) {
        String username = user.getLogin();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, user.getPassword()));
        User userResult = userService.findByLogin(username);

        if (user == null) {
            throw new EntityNotFoundException(User.class, user);
        }

        if (!passwordEncoder.matches(user.getPassword(), user.getPassword())) {
            throw new EntityNotFoundException(User.class, user);
        }

        String token = jwtTokenProvider.createToken(username, user.getRoles());
        //session.setAttribute("user", user);
        //session.setAttribute("token", token);
        return new ResponseEntity<>(userResult, HttpStatus.OK);
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
    @PostMapping("/updateUser")
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
    @GetMapping("/listAllFunFiction")
    public ResponseEntity<List<FunFiction>> listAllFunFiction(@RequestBody User user) {
        List<FunFiction> funFictions = funFictionService.findFunFictionByUserId(user.getId());
        return new ResponseEntity<>(funFictions, HttpStatus.OK);
    }

    /*
    удалить произведение со страницы личной информации (22)
     */
    @DeleteMapping("/deleteFunFicByUser/{id}")
    public ResponseEntity<FunFiction> deleteFunFicByUser(@PathVariable("id") Long id) {
        FunFiction funFiction = funFictionService.findById(id);
        funFictionService.delete(funFiction);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    добавление произведения со страницы личной информации (23)
    */
    @PostMapping("/addFunFicByUser")
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
    public ResponseEntity<List<FunFiction>> searchFunFiction(@PathVariable("id") String name) {
        List<FunFiction> funFictionList = new ArrayList<>();
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
