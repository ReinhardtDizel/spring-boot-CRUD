package kata.academy.config;

import kata.academy.config.convereter.RolesListConverter;
import kata.academy.dto.UserDto;
import kata.academy.model.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    private RolesListConverter rolesListConverter;

    @Autowired
    public void setRolesListConverter(RolesListConverter rolesListConverter) {
        this.rolesListConverter = rolesListConverter;
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE)
                .setFieldAccessLevel(AccessLevel.PUBLIC);

        mapper.typeMap(User.class, UserDto.class)
                .addMappings(mapping -> mapping.using(rolesListConverter)
                        .map(User::getRoles, UserDto::setRoles));
        return mapper;
    }
}
