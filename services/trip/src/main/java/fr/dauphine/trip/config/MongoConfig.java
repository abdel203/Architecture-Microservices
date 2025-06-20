package fr.dauphine.trip.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Configuration
public class MongoConfig {

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(OffsetDateTimeToDateConverter.INSTANCE);
        converters.add(DateToOffsetDateTimeConverter.INSTANCE);
        return new MongoCustomConversions(converters);
    }

    @WritingConverter
    enum OffsetDateTimeToDateConverter implements Converter<OffsetDateTime, Date> {
        INSTANCE;

        @Override
        public Date convert(OffsetDateTime source) {
            // on perd l'offset en stockant en UTC ; ajustez si besoin
            return Date.from(source.toInstant());
        }
    }

    @ReadingConverter
    enum DateToOffsetDateTimeConverter implements Converter<Date, OffsetDateTime> {
        INSTANCE;

        @Override
        public OffsetDateTime convert(Date source) {
            // on recrée un OffsetDateTime en UTC ; si vous voulez un fuseau local, changez ZoneOffset.UTC
            return OffsetDateTime.ofInstant(source.toInstant(), ZoneOffset.UTC);
        }
    }
}
