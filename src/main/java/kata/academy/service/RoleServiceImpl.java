package kata.academy.service;

import kata.academy.model.Role;
import kata.academy.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public List<Role> getRoleById(List<Long> roles) {
        List<Role> result = new ArrayList<>();
        for (Long id : roles) {
            result.add(roleRepository.findRoleById(id));
        }
        return result;
    }
}
