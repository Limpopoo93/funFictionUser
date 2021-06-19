package FunFictionUserProject.funFictionUser.controller;

import FunFictionUserProject.funFictionUser.dto.AuthenticationRequestDto;
import FunFictionUserProject.funFictionUser.dto.RegisterRequestDto;
import FunFictionUserProject.funFictionUser.security.JwtTokenProvider;
import FunFictionUserProject.funFictionUser.service.UserService;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
//@RestController
//@RequestMapping("user")
public class UserController {
    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

        //@GetMapping("/")
       // @RequestMapping("/")
       // @ResponseBody
        //public String main() {
        //    return "main";
        //}

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
            return "index";
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("/login")
    public String login(AuthenticationRequestDto requestDto, Model model, HttpSession session) {
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
            return "index";
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
