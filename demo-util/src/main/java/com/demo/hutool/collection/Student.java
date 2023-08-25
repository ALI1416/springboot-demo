package com.demo.hutool.collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <h1>Student</h1>
 *
 * <p>
 * createDate 2022/03/09 16:06:15
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Student {

    private long termId;
    private long classId;
    private long studentId;
    private String name;

}
