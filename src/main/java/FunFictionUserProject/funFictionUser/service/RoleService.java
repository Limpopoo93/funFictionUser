package FunFictionUserProject.funFictionUser.service;

import FunFictionUserProject.funFictionUser.view.Role;

import java.util.List;

public interface RoleService {
    Role save(Role role);

    Role findById(Long id);

    void delete(Role role);

    List<Role> findAll();

    Role saveAndFlush(Role role);
}
