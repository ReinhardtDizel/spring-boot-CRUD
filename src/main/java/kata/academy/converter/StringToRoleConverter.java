package kata.academy.converter;

import kata.academy.model.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class StringToRoleConverter implements Converter<String, Role> {

    @Override
    public Role convert(String source) {
        Role role = new Role();
        role.setName(source);
        return role;
    }

    @Bean
    public ConversionService getConversionService() {
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
        Set<Converter> converters = new HashSet<>();
        converters.add(new StringToRoleConverter());
        bean.setConverters(converters);
        return bean.getObject();
    }
}
