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
public class RoleToStringConverter implements Converter<Role, String> {

    @Override
    public String convert(Role source) {
        return source.getName();
    }

    @Bean
    public ConversionService getConversionService() {
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
        Set<Converter> converters = new HashSet<>();
        converters.add(new RoleToStringConverter());
        bean.setConverters(converters);
        return bean.getObject();
    }
}
