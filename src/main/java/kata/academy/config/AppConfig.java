package kata.academy.config;

import com.sun.xml.bind.v2.TODO;
import kata.academy.config.convereter.RoleNameConverter;
import kata.academy.dto.RoleDto;
import kata.academy.model.Role;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableCaching
public class AppConfig {

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

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("data");
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        //TODO Конфигурировать RestTemplate
        return builder.build();
    }
}
