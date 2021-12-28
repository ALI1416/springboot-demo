package com.demo.config;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.sql.Timestamp;
import java.util.Date;

/**
 * <h1>MongoDB配置</h1>
 *
 * <p>
 * 作用：去除_class字段，节省储存空间<br>
 * 注意：继承关系的类，对应的collection名字不能相同<br>
 * 例如：B extends A，那么A和B类对应的collection名字不能都为"abc"
 * </p>
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

    @Bean
    public MappingMongoConverter mappingMongoConverter(MongoDatabaseFactory factory, MongoMappingContext context,
                                                       BeanFactory beanFactory) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
        MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver, context);
        mappingConverter.setCustomConversions(beanFactory.getBean(CustomConversions.class));
        // 去除_class字段
        mappingConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return mappingConverter;
    }

    /**
     * 自定义转换器
     */
    @Bean
    public MongoCustomConversions customConversions() {
        return MongoCustomConversions.create(mongoConverterConfigurationAdapter -> {
            mongoConverterConfigurationAdapter.registerConverter(new Date2TimestampConverter());
        });
    }

    /**
     * Date转Timestamp转换器
     */
    @ReadingConverter
    public static class Date2TimestampConverter implements Converter<Date, Timestamp> {
        @Override
        public Timestamp convert(Date date) {
            return new Timestamp(date.getTime());
        }
    }


}
