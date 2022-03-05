package ee.shanel.ideabucket.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ConversionServiceConfiguration implements InitializingBean
{
    private final GenericConversionService applicationConversionService;

    private final List<Converter<?, ?>> converters;

    @Override
    public void afterPropertiesSet()
    {
        converters.forEach(applicationConversionService::addConverter);
    }
}
