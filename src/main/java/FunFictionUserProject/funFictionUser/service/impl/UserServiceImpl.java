package FunFictionUserProject.funFictionUser.service.impl;

import FunFictionUserProject.funFictionUser.repository.RoleRepository;
import FunFictionUserProject.funFictionUser.repository.UserRepository;
import FunFictionUserProject.funFictionUser.service.UserService;
import FunFictionUserProject.funFictionUser.view.Role;
import FunFictionUserProject.funFictionUser.view.Status;
import FunFictionUserProject.funFictionUser.view.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    //private final BCryptPasswordEncoder passwordEncoder;
    private List<Role> userRoles = new ArrayList<>();

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    @Override
    public User save(User user) {
        log.info("user by save in userService");
        return userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        log.info("id by findById in userService");
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void delete(User user) {
        log.info("user by delete in userService");
        userRepository.delete(user);
    }

    @Override
    public List<User> findAll() {
        log.info("List user by findAll in userService");
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public User saveAndFlush(User user) {
        log.info("user by saveAndFlush in userService");
        return userRepository.saveAndFlush(user);
    }

    @Override
    public User findByLogin(String login) {
        log.info("login by findByLogin in userService");
        return userRepository.findByLogin(login);
    }

    @Transactional
    @Override
    public User registerUser(User user) {
        log.info("user by registerUser in userService");
        Role roleUser = roleRepository.findByRole("ROLE_USER");
        userRoles.add(roleUser);
        user.setPassword(user.getPassword());
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);
        user.setCreated(new Date());
        user.setUpdated(new Date());
        User registeredUser = userRepository.save(user);
        log.info("IN register - user: {} successfully registered", registeredUser);
        return registeredUser;
    }

    @Transactional
    @Override
    public User registerAdmin(User user) {
        log.info("user by registerAdmin in userService");
        Role roleUser = roleRepository.findByRole("ROLE_ADMIN");
        userRoles.add(roleUser);
        user.setPassword(user.getPassword());
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);
        user.setCreated(new Date());
        user.setUpdated(new Date());
        User registeredUser = userRepository.save(user);
        log.info("IN register - user: {} successfully registered", registeredUser);
        return registeredUser;
    }

    @Override
    public User findByEmail(String email) {
        log.info("email by findByEmail in userService");
        return userRepository.findByEmail(email);
    }
}
