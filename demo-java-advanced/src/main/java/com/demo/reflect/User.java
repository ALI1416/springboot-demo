package com.demo.reflect;

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
@Anno({"class", "anno"})
public class User {

    @Anno(value = "field", bool = true)
    private Long id;
    @Anno(value = "field")
    private String account;
    private Integer gender;

    @Anno("constructor")
    public User() {

    }

    @Anno(bool = true)
    public User(Long id, String account, Integer gender) {
        this.id = id;
        this.account = account;
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    @Anno("field")
    public void setId(@Anno("parameter") Long id) {
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
