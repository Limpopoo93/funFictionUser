package FunFictionUserProject.funFictionUser.controller;

import FunFictionUserProject.funFictionUser.exeption.EntityNotFoundException;
import FunFictionUserProject.funFictionUser.security.JwtTokenProvider;
import FunFictionUserProject.funFictionUser.service.RoleService;
import FunFictionUserProject.funFictionUser.service.UserService;
import FunFictionUserProject.funFictionUser.view.Role;
import FunFictionUserProject.funFictionUser.view.Status;
import FunFictionUserProject.funFictionUser.view.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final RoleService roleService;
    private List<Role> userRoles = new ArrayList<>();

    @Autowired
    public AdminController(UserService userService, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager, RoleService roleService) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.roleService = roleService;
    }

    /*
    Регистрации админа (2)
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
функция для назначения админом со списка юзеров (8)
 */
    @GetMapping("/updatedUserByAdmin/{id}")
    public ResponseEntity<User> updatedUserByAdmin(@PathVariable("id") Long id) {
        try {
            User userSearch = userService.findById(id);
            Role role = roleService.findByRole("ROLE_ADMIN");
            userRoles.add(role);
            userSearch.setRoles(userRoles);
            userService.saveAndFlush(userSearch);
            return new ResponseEntity<>(userSearch, HttpStatus.OK);
        } catch (AuthenticationException e) {
            throw new EntityNotFoundException(User.class, id);
        }
    }

    /*
    лист всех пользователей со страницы админа (5)
     */
    @GetMapping("/allUserByAdmin")
    public ResponseEntity<List<User>> getAllByUser() {
        List<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /*
    удаление юзера со страницы админа (6)
     */
    @DeleteMapping("/deleteUserByAdmin/{id}")
    public ResponseEntity<User> deleteUserByAdmin(@PathVariable("id") Long id) {
        User user = userService.findById(id);
        userService.delete(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
блокировка юзера со страницы админа (7)
 */
    @DeleteMapping("/blockUserByAdmin/{id}")
    public ResponseEntity<User> blockUserByAdmin(@PathVariable("id") Long id) {
        User user = userService.findById(id);
        user.setStatus(Status.BLOCK);
        User userResult = userService.saveAndFlush(user);
        return new ResponseEntity<>(userResult, HttpStatus.OK);
    }

    /*
    переход на личную информацию каждого юзера со страницы админа (9) (10) (11)
    */
    @GetMapping("/infoUserByAdmin/{id}")
    public ResponseEntity<User> infoUserByAdmin(@PathVariable("id") Long id) {
        User user = userService.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
