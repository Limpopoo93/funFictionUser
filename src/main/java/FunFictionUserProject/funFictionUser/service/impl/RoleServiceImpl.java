package FunFictionUserProject.funFictionUser.service.impl;

import FunFictionUserProject.funFictionUser.repository.RoleRepository;
import FunFictionUserProject.funFictionUser.service.RoleService;
import FunFictionUserProject.funFictionUser.view.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Role role) {
        roleRepository.delete(role);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role saveAndFlush(Role role) {
        return roleRepository.saveAndFlush(role);
    }

    @Override
    public Role findByRole(String name) {
        return roleRepository.findByRole(name);
    }
}
