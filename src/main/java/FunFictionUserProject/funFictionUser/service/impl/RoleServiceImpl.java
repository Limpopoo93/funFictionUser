package FunFictionUserProject.funFictionUser.service.impl;

import FunFictionUserProject.funFictionUser.repository.RoleRepository;
import FunFictionUserProject.funFictionUser.service.RoleService;
import FunFictionUserProject.funFictionUser.view.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional
    @Override
    public Role save(Role role) {
        log.info("role by save in roleService");
        return roleRepository.save(role);
    }

    @Override
    public Role findById(Long id) {
        log.info("id by findById in roleService");
        return roleRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void delete(Role role) {
        log.info("role by delete in roleService");
        roleRepository.delete(role);
    }

    @Override
    public List<Role> findAll() {
        log.info("list role by findAll in roleService");
        return roleRepository.findAll();
    }

    @Transactional
    @Override
    public Role saveAndFlush(Role role) {
        log.info("role by saveAndFlush in roleService");
        return roleRepository.saveAndFlush(role);
    }

    @Override
    public Role findByRole(String name) {
        log.info("name by findByRole in roleService");
        return roleRepository.findByRole(name);
    }
}
