package com.demo.annotation;

import org.springframework.stereotype.Component;

/**
 * <h1>User</h1>
 *
 * <p>
 * createDate 2022/04/20 16:17:28
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Component
@MyAnno({"class", "anno"})
public class User {

    @MyAnno(value = "field", bool = true)
    private Long id;
    @MyAnno(value = "field")
    private String account;
    private Integer gender;

    @MyAnno("constructor")
    public User() {

    }

    @MyAnno(bool = true)
    public User(Long id, String account, Integer gender) {
        this.id = id;
        this.account = account;
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    @MyAnno("field")
    public void setId(@MyAnno("parameter") Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", account='" + account + '\'' + ", gender=" + gender + '}';
    }

}
