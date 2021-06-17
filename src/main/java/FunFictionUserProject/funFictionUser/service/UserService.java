package FunFictionUserProject.funFictionUser.service;

import FunFictionUserProject.funFictionUser.view.User;

import java.util.List;

public interface UserService {
    User save(User user);

    User findById(Long id);

    void delete(User user);

    List<User> findAll();

    User saveAndFlush(User user);
}
