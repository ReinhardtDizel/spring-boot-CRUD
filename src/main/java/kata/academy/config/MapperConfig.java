package kata.academy.config;

import kata.academy.config.convereter.RoleNameConverter;
import kata.academy.dto.RoleDto;
import kata.academy.model.Role;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    private RoleNameConverter roleNameConverter;

    @Autowired
    public void setRoleNameConverter(RoleNameConverter roleNameConverter) {
        this.roleNameConverter = roleNameConverter;
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE)
                .setFieldAccessLevel(AccessLevel.PUBLIC);


        mapper.typeMap(Role.class, RoleDto.class)
                .addMappings(mapping -> mapping.using(roleNameConverter)
                        .map(Role::getName, RoleDto::setName));

        return mapper;
    }
}
