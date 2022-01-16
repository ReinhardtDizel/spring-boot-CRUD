package kata.academy.service;

import kata.academy.model.Role;

import java.util.List;

public interface RoleService {
    Role getRoleByName(String name);

    List<Role> getAll();

    List<Role> getRoleById(List<Long> roles);
}