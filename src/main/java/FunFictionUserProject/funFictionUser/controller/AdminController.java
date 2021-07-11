package FunFictionUserProject.funFictionUser.controller;

import FunFictionUserProject.funFictionUser.dto.ChapterRequestDto;
import FunFictionUserProject.funFictionUser.dto.FunFicRequestDto;
import FunFictionUserProject.funFictionUser.dto.UserListDto;
import FunFictionUserProject.funFictionUser.exeption.EntityNotFoundException;
import FunFictionUserProject.funFictionUser.service.*;
import FunFictionUserProject.funFictionUser.view.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static FunFictionUserProject.funFictionUser.util.DataConstant.*;
import static FunFictionUserProject.funFictionUser.util.UrlConstant.*;
import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Slf4j
@RestController
@RequestMapping(ADMIN)
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private List<Role> userRoles = new ArrayList<>();
    private final FunFictionService funFictionService;
    private final GenreService genreService;
    private final CommentsService commentsService;
    private final TagsService tagsService;
    private final ChapterService chapterService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, FunFictionService funFictionService, GenreService genreService, CommentsService commentsService, TagsService tagsService, ChapterService chapterService) {
        this.userService = userService;
        this.roleService = roleService;
        this.funFictionService = funFictionService;
        this.genreService = genreService;
        this.commentsService = commentsService;
        this.tagsService = tagsService;
        this.chapterService = chapterService;
    }

    /*
    Регистрации админа (2)
     */
    @PostMapping(SAVE)
    public ResponseEntity<User> save(@RequestBody User user, HttpSession session) {
        User userCheck = userService.findByLogin(user.getLogin());
        log.info("user find by login method save in adminController");
        if (userCheck != null) {
            throw new EntityNotFoundException("user not found");
        }
        user.setStatus(Status.ACTIVE);
        user.setCreated(new Date());
        user.setUpdated(new Date());
        User userResult = userService.registerUser(user);
        log.info("user registerUser by login method save in adminController");
        session.setAttribute(USER_RESULT, userResult);
        return new ResponseEntity<>(userResult, HttpStatus.CREATED);
    }

    /*
    функция для назначения админом со списка юзеров (8)
     */
    @GetMapping("/updatedUserByAdmin/{id}")
    public ResponseEntity<UserListDto> updatedUserByAdmin(@PathVariable(ID) Long id) {
        User userSearch = userService.findById(id);
        log.info("user findById method updatedUserByAdmin in adminController");
        if (userSearch == null) {
            throw new EntityNotFoundException("user not found");
        }
        Role role = roleService.findByRole(ROLE_ADMIN);
        log.info("role findByRole method updatedUserByAdmin in adminController");
        userRoles.add(role);
        userSearch.setRoles(userRoles);
        User updatedUser = userService.saveAndFlush(userSearch);
        log.info("user saveAndFlush method updatedUserByAdmin in adminController");
        UserListDto userListDto = UserListDto.fromUser(updatedUser);
        return new ResponseEntity<>(userListDto, HttpStatus.OK);
    }

    /*
    лист всех пользователей со страницы админа (5)
     */
    @GetMapping("/allUserByAdmin")
    public ResponseEntity<List<UserListDto>> getAllByUser() {
        List<User> users = userService.findAll();
        log.info("user list findAll method getAllByUser in adminController");
        List<UserListDto> userList = new ArrayList<>();
        for (User user : users) {
            userList.add(UserListDto.fromUser(user));
        }
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    /*
    удаление юзера со страницы админа (6)
     */
    @DeleteMapping("/deleteUserByAdmin/{id}")
    public ResponseEntity<List<UserListDto>> deleteUserByAdmin(@PathVariable(ID) Long id) {
        User user = userService.findById(id);
        log.info("user findById method deleteUserByAdmin in adminController");
        if (user == null) {
            throw new EntityNotFoundException("user not found");
        }
        user.setStatus(Status.DELETE);
        userService.saveAndFlush(user);
        log.info("user saveAndFlush method deleteUserByAdmin in adminController");
        List<User> userList = userService.findAll();
        List<UserListDto> userDtoList = new ArrayList<>();
        for (User users : userList) {
            userDtoList.add(UserListDto.fromUser(users));
        }
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

    /*
    блокировка юзера со страницы админа (7)
    */
    @GetMapping("/blockUserByAdmin/{id}")
    public ResponseEntity<List<UserListDto>> blockUserByAdmin(@PathVariable(ID) Long id) {
        User user = userService.findById(id);
        log.info("user findById method blockUserByAdmin in adminController");
        if (user == null) {
            throw new EntityNotFoundException("user not found");
        }
        user.setStatus(Status.BLOCK);
        userService.saveAndFlush(user);
        log.info("user saveAndFlush method blockUserByAdmin in adminController");
        List<User> userList = userService.findAll();
        List<UserListDto> userDtoList = new ArrayList<>();
        for (User users : userList) {
            userDtoList.add(UserListDto.fromUser(users));
        }
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

    /*
    переход на личную информацию каждого юзера со страницы админа (9) (10) (11)
    */
    @GetMapping(INFO_USER)
    public ResponseEntity<User> infoUserByAdmin(@PathVariable(ID) Long id) {
        User user = userService.findById(id);
        log.info("user findById method infoUserByAdmin in adminController");
        if (user == null) {
            throw new EntityNotFoundException("user not found");
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /*
    удалить произведение со списка произведений юзера (12)
    */
    @DeleteMapping(DELETE_FUN_FIC_ADMIN)
    public ResponseEntity<FunFiction> deleteFunFunficByAdmin(@PathVariable(ID) Long id) {
        FunFiction funFiction = funFictionService.findById(id);
        log.info("funFiction findById method deleteFunFunficByAdmin in adminController");
        if (funFiction == null) {
            throw new EntityNotFoundException("fun fic not found");
        }
        funFictionService.delete(funFiction);
        log.info("funFiction delete method deleteFunFunficByAdmin in adminController");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    редактировать произведение со списка произведений юзера (12)
    */
    @PostMapping(UPDATE_FUN_FIC_ADMIN)
    public ResponseEntity<FunFiction> updatedFunFunficByAdmin(@RequestBody FunFiction funFiction) {
        FunFiction funFictionResult = funFictionService.findById(funFiction.getId());
        log.info("funFiction findById method updatedFunFunficByAdmin in adminController");
        if (funFictionResult == null) {
            throw new EntityNotFoundException("fun fic not found");
        }
        funFictionResult.setNameFun(funFiction.getNameFun());
        funFictionResult.setShortDescription(funFiction.getShortDescription());
        Genre genre = genreService.findByTypeGenre(funFiction.getGenre().getTypeGenre());
        log.info("genre findByTypeGenre method updatedFunFunficByAdmin in adminController");
        funFictionResult.setGenre(genre);
        funFictionService.saveAndFlush(funFictionResult);
        log.info("genre saveAndFlush method updatedFunFunficByAdmin in adminController");
        return new ResponseEntity<>(funFictionResult, HttpStatus.OK);
    }
    /*
добавление произведения от имени админа
*/
    @PostMapping("/addFunFicByAdmin")
    public ResponseEntity<FunFicRequestDto> addFunFicByUser(@RequestBody FunFicRequestDto funFiction) throws IOException {
        User user = userService.findById(funFiction.getIdUser());
        if (user == null) {
            throw new EntityNotFoundException("user not found");
        }

        FunFiction funFictionResult = new FunFiction();
        List<Tags> tagsList = new ArrayList<>();
        Genre genre = genreService.findByTypeGenre(funFiction.getGenre());
        log.info("genre findByTypeGenre addFunFicByUser in workingController");
        funFictionResult.setGenre(genre);
        funFictionResult.setCreated(new Date());
        funFictionResult.setNameFun(funFiction.getNameFun());
        funFictionResult.setShortDescription(funFiction.getShortDescription());
        funFictionResult.setLike(0);
        funFictionResult.setRating(0.0);
        funFictionResult.setUser(user);
        for (String tags : funFiction.getTypeTags()) {
            Tags tagsResult = tagsService.findByTypeTags(tags);
            log.info("tags findByTypeTags addFunFicByUser in workingController");
            tagsList.add(tagsResult);
        }
        funFictionResult.setTags(tagsList);
        FunFiction funFictionNew = funFictionService.save(funFictionResult);
        log.info("fun fic save addFunFicByUser in workingController");
        FunFicRequestDto funFicRequestDto = FunFicRequestDto.fromFunFic(funFictionNew);

        return new ResponseEntity<>(funFicRequestDto, HttpStatus.OK);
    }

    /*
    добавление глав после добавления fun fic.
    */
    @PostMapping("/addChapterByAdmin")
    public ResponseEntity<ChapterRequestDto> addChapter(@RequestBody ChapterRequestDto chapterRequestDto) {
        FunFiction funFiction = funFictionService.findById(chapterRequestDto.getIdFunFic());
        log.info("fun fic findById addChapter in workingController");
        if (funFiction == null) {
            throw new EntityNotFoundException("fun fic not found");
        }
        Chapter chapter = new Chapter();
        chapter.setNumberChapter(chapterRequestDto.getNumberChapter());
        chapter.setNameChapter(chapterRequestDto.getNameChapter());
        chapter.setTextChapter(chapterRequestDto.getTextChapter());
        chapter.setFunFiction(funFiction);
        ChapterRequestDto chapterRequestDtoNew = ChapterRequestDto.fromChapter(chapterService.save(chapter));
        log.info("chapter save addChapter in workingController");
        return new ResponseEntity<>(chapterRequestDtoNew, HttpStatus.OK);
    }


    /*
добавление произведения от имени админа по id User
*/
    @PostMapping("/addFunFicByAdminThisUser")
    public ResponseEntity<FunFicRequestDto> addFunFicByUserId(@RequestBody FunFicRequestDto funFiction) {
        User user = userService.findById(funFiction.getIdUser());
        if (user == null) {
            throw new EntityNotFoundException("user not found");
        }
        FunFiction funFictionResult = new FunFiction();
        List<Tags> tagsList = new ArrayList<>();
        Genre genre = genreService.findByTypeGenre(funFiction.getGenre());
        log.info("genre findByTypeGenre addFunFicByUser in workingController");
        funFictionResult.setGenre(genre);
        funFictionResult.setCreated(new Date());
        funFictionResult.setNameFun(funFiction.getNameFun());
        funFictionResult.setShortDescription(funFiction.getShortDescription());
        funFictionResult.setLike(0);
        funFictionResult.setRating(0.0);
        funFictionResult.setUser(user);
        for (String tags : funFiction.getTypeTags()) {
            Tags tagsResult = tagsService.findByTypeTags(tags);
            log.info("tags findByTypeTags addFunFicByUser in workingController");
            tagsList.add(tagsResult);
        }
        funFictionResult.setTags(tagsList);
        FunFiction funFictionNew = funFictionService.save(funFictionResult);
        log.info("fun fic save addFunFicByUser in workingController");
        FunFicRequestDto funFicRequestDto = FunFicRequestDto.fromFunFic(funFictionNew);
        return new ResponseEntity<>(funFicRequestDto, HttpStatus.OK);
    }

}
