package org.example.cookieretceptg27.common.configuration;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Configuration
public class ModelMapperConfiguration
{
    @Bean
    public ModelMapper mapper()
    {
        ModelMapper modelMapper = new ModelMapper();

        Converter<Timestamp, String> timestampToStringConverter = context -> {
            if (context.getSource() == null) {
                return null;
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.format(context.getSource());
        };

        modelMapper.addConverter(timestampToStringConverter);
        modelMapper.getConfiguration().setMatchingStrategy( MatchingStrategies.STRICT );
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.getConfiguration().setFieldMatchingEnabled(true);
        return modelMapper;
    }
}
