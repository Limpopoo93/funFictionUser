package FunFictionUserProject.funFictionUser.service.impl;

import FunFictionUserProject.funFictionUser.repository.RoleRepository;
import FunFictionUserProject.funFictionUser.repository.UserRepository;
import FunFictionUserProject.funFictionUser.service.UserService;
import FunFictionUserProject.funFictionUser.view.Role;
import FunFictionUserProject.funFictionUser.view.Status;
import FunFictionUserProject.funFictionUser.view.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User saveAndFlush(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public User register(User user) {
        Role roleUser = roleRepository.findByRole("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);
        user.setCreated(new Date());
        user.setUpdated(new Date());
        User registeredUser = userRepository.save(user);
        log.info("IN register - user: {} successfully registered", registeredUser);
        return registeredUser;
    }
}
