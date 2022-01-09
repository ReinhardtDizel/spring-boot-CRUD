package kata.academy.config;

import kata.academy.converter.RoleToStringConverter;
import kata.academy.converter.StringToRoleConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyConfig implements WebMvcConfigurer {


    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new RoleToStringConverter());
        registry.addConverter(new StringToRoleConverter());
    }

}
