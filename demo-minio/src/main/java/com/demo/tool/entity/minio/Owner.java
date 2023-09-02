package com.demo.tool.entity.minio;

/**
 * <h1>所有者</h1>
 *
 * <p>
 * createDate 2022/03/28 16:32:20
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class Owner {

    /**
     * id
     */
    private String id;
    /**
     * 姓名
     */
    private String name;

    public Owner() {
    }

    public Owner(io.minio.messages.Owner owner) {
        this.id = owner.id();
        this.name = owner.displayName();
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

}
