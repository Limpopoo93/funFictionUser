package FunFictionUserProject.funFictionUser.controller;

import FunFictionUserProject.funFictionUser.dto.AuthenticationRequestDto;
import FunFictionUserProject.funFictionUser.dto.RegisterRequestDto;
import FunFictionUserProject.funFictionUser.exeption.EntityNotFoundException;
import FunFictionUserProject.funFictionUser.security.JwtTokenProvider;
import FunFictionUserProject.funFictionUser.service.ChapterService;
import FunFictionUserProject.funFictionUser.service.FunFictionService;
import FunFictionUserProject.funFictionUser.service.GenreService;
import FunFictionUserProject.funFictionUser.service.UserService;
import FunFictionUserProject.funFictionUser.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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

    @Autowired
    public UserController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, PasswordEncoder passwordEncoder, FunFictionService funFictionService, GenreService genreService, ChapterService chapterService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.funFictionService = funFictionService;
        this.genreService = genreService;
        this.chapterService = chapterService;
    }
/*
Сохранение юзера со страницы регистрации (1)
 */
    @PostMapping("/save")
    public ResponseEntity<User> save(@RequestBody User user) {
        try {
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
        } catch (AuthenticationException e) {
            throw new EntityNotFoundException(User.class, user);
        }
    }
/*
Вход юзера со страницы входа (3)
 */
    @PostMapping("/login")
    public ResponseEntity<User> loginUser(User user) {
        try {
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
        } catch (AuthenticationException e) {
            throw new EntityNotFoundException(User.class, user);
        }
    }

    //переход на страницу настройки юзера пользователей которые уже были созданы ранее
    @GetMapping("/setting")
    public String settingUserByLogin(RegisterRequestDto registerRequestDto, Model model, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            List<FunFiction> funFictions = funFictionService.findFunFictionByUserId(user.getId());
            model.addAttribute("funFictions", funFictions);
            return "settingUser";
        } catch (AuthenticationException e) {
            throw new EntityNotFoundException(User.class, registerRequestDto);
        }
    }

    //удаление пользователем своего произведения со страницы настройки
    @PostMapping("/deleteFunFiction")
    public String deleteFunFiction(RegisterRequestDto registerRequestDto, FunFiction funFiction, Model model, HttpSession session) {
        try {
            FunFiction funFictionResult = funFictionService.findById(funFiction.getId());
            funFictionService.delete(funFictionResult);
            User user = (User) session.getAttribute("user");
            List<FunFiction> funFictions = funFictionService.findFunFictionByUserId(user.getId());
            model.addAttribute("funFictions", funFictions);
            return "settingUser";
        } catch (AuthenticationException e) {
            throw new EntityNotFoundException(User.class, registerRequestDto);
        }
    }

    @GetMapping("/updatedFunFictionGet/{id}")
    public String updatedFunFictionGet(@PathVariable("id") Long id, Model model, RegisterRequestDto registerRequestDto) {
        FunFiction funFiction = funFictionService.findById(id);
        model.addAttribute("funFiction", funFiction);
        return "funFictionUser";
    }

    //изменение пользователем своего произведения со страницы настройки
    @PostMapping("/updatedFunFiction")
    public String updatedFunFiction(RegisterRequestDto registerRequestDto, FunFiction funFiction, Model model, HttpSession session) {
        try {
            FunFiction funFictionResult = funFictionService.findById(funFiction.getId());
            funFictionResult.setNameFun(funFiction.getNameFun());
            funFictionResult.setShortDescription(funFiction.getShortDescription());
            Genre genre = genreService.findByTypeGenre(funFiction.getGenre().getTypeGenre());
            funFictionResult.setGenre(genre);
            funFictionService.saveAndFlush(funFictionResult);
            User user = (User) session.getAttribute("user");
            List<FunFiction> funFictions = funFictionService.findFunFictionByUserId(user.getId());
            model.addAttribute("funFictions", funFictions);
            return "settingUser";
        } catch (AuthenticationException e) {
            throw new EntityNotFoundException(User.class, registerRequestDto);
        }
    }

    //добавление пользователем своего произведения со страницы настройки
    @PostMapping("/addFunFiction")
    public String addFunFiction(RegisterRequestDto registerRequestDto, FunFiction funFiction, Model model, HttpSession session) {
        try {
            Genre genre = genreService.findByTypeGenre(funFiction.getGenre().getTypeGenre());
            funFiction.setGenre(genre);
            User user = (User) session.getAttribute("user");
            funFiction.setUser(user);
            funFictionService.save(funFiction);
            List<FunFiction> funFictions = funFictionService.findFunFictionByUserId(user.getId());
            model.addAttribute("funFictions", funFictions);
            return "settingUser";
        } catch (AuthenticationException e) {
            throw new EntityNotFoundException(User.class, registerRequestDto);
        }
    }

    //обновление данных user со страницы настройки
    @PostMapping("/updateUser")
    public String updateUser(RegisterRequestDto registerRequestDto, Model model, HttpSession session) {
        try {
            User userResult = (User) session.getAttribute("user");
            userResult.setLogin(registerRequestDto.getUsername());
            userResult.setPassword(registerRequestDto.getPassword());
            userResult.setEmail(registerRequestDto.getEmail());
            userResult.setUpdated(new Date());
            userResult.setNameUser(registerRequestDto.getFirstName());
            userResult.setSurnameUser(registerRequestDto.getLastName());
            User user = userService.saveAndFlush(userResult);
            session.getAttribute("user");
            session.invalidate();
            session.getAttribute("token");
            session.invalidate();
            String token = jwtTokenProvider.createToken(user.getLogin(), user.getRoles());
            session.setAttribute("user", user);
            session.setAttribute("token", token);
            List<FunFiction> funFictions = funFictionService.findFunFictionByUserId(user.getId());
            model.addAttribute("funFictions", funFictions);
            return "settingUser";
        } catch (AuthenticationException e) {
            throw new EntityNotFoundException(User.class, registerRequestDto);
        }
    }

    //delete account user со страницы настройки
    @PostMapping("/deleteUser")
    public String deleteUser(RegisterRequestDto registerRequestDto, Model model, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            userService.delete(user);
            session.getAttribute("user");
            session.invalidate();
            return "index";
        } catch (AuthenticationException e) {
            throw new EntityNotFoundException(User.class, registerRequestDto);
        }
    }

    //читать главы фанфика со страницы setting
    @PostMapping("/readFunFick/{id}")
    public String readFunFick(@PathVariable("id") Long id, RegisterRequestDto registerRequestDto, Model model, HttpSession session) {
        try {
            FunFiction funFiction = funFictionService.findById(id);
            List<Chapter> chapters = chapterService.findChapterByFunFictionId(funFiction.getId());
            model.addAttribute("chapters", chapters);
            return "listChapters";
        } catch (AuthenticationException e) {
            throw new EntityNotFoundException(User.class, id);
        }
    }
}
