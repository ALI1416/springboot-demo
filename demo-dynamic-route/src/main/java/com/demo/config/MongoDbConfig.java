package com.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.sql.Timestamp;
import java.util.Date;

/**
 * <h1>MongoDB配置</h1>
 *
 * <p>
 * createDate 2021/11/23 09:46:17
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Configuration
public class MongoDbConfig {

    /**
     * 自定义转换器(Date转Timestamp)
     */
    @Bean
    public MongoCustomConversions date2Timestamp() {
        return MongoCustomConversions.create(mongoConverterConfigurationAdapter -> //
                mongoConverterConfigurationAdapter.registerConverter(new Date2TimestampConverter()) //
        );
    }

    /**
     * Date转Timestamp转换器(读)
     */
    @ReadingConverter
    public static class Date2TimestampConverter implements Converter<Date, Timestamp> {
        @Override
        public Timestamp convert(Date date) {
            return new Timestamp(date.getTime());
        }
    }

}
