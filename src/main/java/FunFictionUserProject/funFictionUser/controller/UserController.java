package FunFictionUserProject.funFictionUser.controller;

import FunFictionUserProject.funFictionUser.dto.AuthenticationRequestDto;
import FunFictionUserProject.funFictionUser.dto.RegisterRequestDto;
import FunFictionUserProject.funFictionUser.security.JwtTokenProvider;
import FunFictionUserProject.funFictionUser.service.ChapterService;
import FunFictionUserProject.funFictionUser.service.FunFictionService;
import FunFictionUserProject.funFictionUser.service.GenreService;
import FunFictionUserProject.funFictionUser.service.UserService;
import FunFictionUserProject.funFictionUser.view.Chapter;
import FunFictionUserProject.funFictionUser.view.FunFiction;
import FunFictionUserProject.funFictionUser.view.Genre;
import FunFictionUserProject.funFictionUser.view.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
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

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/registration")
    public String registration(Model model, RegisterRequestDto registerRequestDto) {
        return "registration";
    }

    @GetMapping("/comeIn")
    public String comeIn(Model model, AuthenticationRequestDto authenticationRequestDto) {
        return "comeIn";
    }

    @PostMapping("/save")
    public String save(RegisterRequestDto registerRequestDto, Model model, HttpSession session) {
        try {
            if (!registerRequestDto.getPassword().equals(registerRequestDto.getSecondPassword())) {
                throw new BadCredentialsException("password haven't correct");
            }
            User userCheck = userService.findByLogin(registerRequestDto.getUsername());
            if (userCheck != null) {
                throw new UsernameNotFoundException("User with username: " + userCheck.getLogin() + " not found");
            }
            User toUser = registerRequestDto.toUser();
            User user = userService.register(toUser);
            String username = registerRequestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, registerRequestDto.getPassword()));
            String token = jwtTokenProvider.createToken(username, user.getRoles());
            session.setAttribute("user", user);
            session.setAttribute("token", token);
            return "main";
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("/login")
    public String loginUser(AuthenticationRequestDto requestDto, Model model, HttpSession session) {
        try {
            String username = requestDto.getLogin();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByLogin(username);

            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
                throw new BadCredentialsException("password dont correct");
            }

            String token = jwtTokenProvider.createToken(username, user.getRoles());
            session.setAttribute("user", user);
            session.setAttribute("token", token);
            return "main";
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
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
            throw new BadCredentialsException("Invalid username or password");
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
            throw new BadCredentialsException("Invalid username or password");
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
            throw new BadCredentialsException("Invalid username or password");
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
            throw new BadCredentialsException("Invalid username or password");
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
            throw new BadCredentialsException("Invalid username or password");
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
            throw new BadCredentialsException("Invalid username or password");
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
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
