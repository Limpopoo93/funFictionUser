package FunFictionUserProject.funFictionUser.controller;

import FunFictionUserProject.funFictionUser.dto.UserListDto;
import FunFictionUserProject.funFictionUser.exeption.EntityNotFoundException;
import FunFictionUserProject.funFictionUser.service.FunFictionService;
import FunFictionUserProject.funFictionUser.service.GenreService;
import FunFictionUserProject.funFictionUser.service.RoleService;
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

import static FunFictionUserProject.funFictionUser.util.DataConstant.*;
import static FunFictionUserProject.funFictionUser.util.UrlConstant.*;

@RestController
@RequestMapping(ADMIN)
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private List<Role> userRoles = new ArrayList<>();
    private final FunFictionService funFictionService;
    private final GenreService genreService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, FunFictionService funFictionService, GenreService genreService) {
        this.userService = userService;
        this.roleService = roleService;
        this.funFictionService = funFictionService;
        this.genreService = genreService;
    }

    /*
    Регистрации админа (2)
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
        return new ResponseEntity<>(userResult, HttpStatus.CREATED);
    }

    /*
    функция для назначения админом со списка юзеров (8)
     */
    @GetMapping(UPDATE_ADMIN)
    public ResponseEntity<User> updatedUserByAdmin(@PathVariable(ID) Long id) {
        User userSearch = userService.findById(id);
        Role role = roleService.findByRole(ROLE_ADMIN);
        userRoles.add(role);
        userSearch.setRoles(userRoles);
        userService.saveAndFlush(userSearch);
        return new ResponseEntity<>(userSearch, HttpStatus.OK);
    }

    /*
    лист всех пользователей со страницы админа (5)
     */
    @GetMapping(LIST_USER)
    public ResponseEntity<List<UserListDto>> getAllByUser() {
        List<User> users = userService.findAll();
        List<UserListDto> userList = new ArrayList<>();
        for (User user : users) {
            UserListDto userListDto = new UserListDto();
            userList.add(userListDto.fromUser(user));
        }
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    /*
    удаление юзера со страницы админа (6)
     */
    @DeleteMapping(DELETE_USER)
    public ResponseEntity<User> deleteUserByAdmin(@PathVariable(ID) Long id) {
        User user = userService.findById(id);
        userService.delete(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    блокировка юзера со страницы админа (7)
    */
    @DeleteMapping(BLOCK_USER)
    public ResponseEntity<User> blockUserByAdmin(@PathVariable(ID) Long id) {
        User user = userService.findById(id);
        user.setStatus(Status.BLOCK);
        User userResult = userService.saveAndFlush(user);
        return new ResponseEntity<>(userResult, HttpStatus.OK);
    }

    /*
    переход на личную информацию каждого юзера со страницы админа (9) (10) (11)
    */
    @GetMapping(INFO_USER)
    public ResponseEntity<User> infoUserByAdmin(@PathVariable(ID) Long id) {
        User user = userService.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /*
    удалить произведение со списка произведений юзера (12)
    */
    @DeleteMapping(DELETE_FUN_FIC_ADMIN)
    public ResponseEntity<FunFiction> deleteFunFunficByAdmin(@PathVariable(ID) Long id) {
        FunFiction funFiction = funFictionService.findById(id);
        funFictionService.delete(funFiction);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    редактировать произведение со списка произведений юзера (12)
    */
    @PostMapping(UPDATE_FUN_FIC_ADMIN)
    public ResponseEntity<FunFiction> updatedFunFunficByAdmin(@RequestBody FunFiction funFiction) {
        FunFiction funFictionResult = funFictionService.findById(funFiction.getId());
        funFictionResult.setNameFun(funFiction.getNameFun());
        funFictionResult.setShortDescription(funFiction.getShortDescription());
        Genre genre = genreService.findByTypeGenre(funFiction.getGenre().getTypeGenre());
        funFictionResult.setGenre(genre);
        funFictionService.saveAndFlush(funFictionResult);
        return new ResponseEntity<>(funFictionResult, HttpStatus.OK);
    }
}
