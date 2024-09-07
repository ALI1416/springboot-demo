package com.demo.entity.influx;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * <h1>TestClass</h1>
 *
 * <p>
 * createDate 2024/09/07 15:37:13
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
@Setter
@Measurement(name = "test")
public class TestClass {

    /**
     * tag1
     */
    @Column(tag = true)
    private String tag1;
    /**
     * tag2
     */
    @Column(tag = true)
    private String tag2;
    /**
     * 值1
     */
    @Column
    Long value1;
    /**
     * 值2
     */
    @Column
    Long value2;
    /**
     * 时间戳
     */
    @Column(timestamp = true)
    Instant time;

}
