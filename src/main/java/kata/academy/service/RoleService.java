package kata.academy.service;

import kata.academy.model.Role;

import java.util.List;

public interface RoleService {

    List<Role> getAll();

    List<Role> getRoleById(List<Long> roles);
}