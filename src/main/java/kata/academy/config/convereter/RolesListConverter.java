package kata.academy.config.convereter;

import kata.academy.model.Role;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RolesListConverter extends AbstractConverter<Set<Role>, List<Long>> {
    @Override
    protected List<Long> convert(Set<Role> roles) {
        return roles
                .stream()
                .map(Role::getId)
                .collect(Collectors.toList());
    }
}
