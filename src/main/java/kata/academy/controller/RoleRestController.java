package kata.academy.controller;

import kata.academy.model.Role;
import kata.academy.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/role")
public class RoleRestController {

    private RoleService roleService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<Role>> getAll() {
        return Optional
                .ofNullable(roleService.getAll())
                .map(users -> ResponseEntity.ok().body(users))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }
}
