package kata.academy.config.convereter;

import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;


@Component
public class RoleNameConverter extends AbstractConverter<String, String> {
    @Override
    protected String convert(String roleName) {
        return roleName.replaceFirst("ROLE_", "");
    }
}
