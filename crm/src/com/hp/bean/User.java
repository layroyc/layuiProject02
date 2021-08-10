package com.hp.bean;

public class User {
    private int id;
    private String username;
    private String password;
    private String real_name;
    private String img;
    private int type;
    private int is_del;
    private String create_time;
    private String modify_time;

    //无参
    public User() {
        super();
    }
    //有参
    public User(int id, String username, String password, String real_name, String img, int type, int is_del, String create_time, String modify_time) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.real_name = real_name;
        this.img = img;
        this.type = type;
        this.is_del = is_del;
        this.create_time = create_time;
        this.modify_time = modify_time;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", real_name='" + real_name + '\'' +
                ", img='" + img + '\'' +
                ", type=" + type +
                ", is_del=" + is_del +
                ", create_time='" + create_time + '\'' +
                ", modify_time='" + modify_time + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIs_del() {
        return is_del;
    }

    public void setIs_del(int is_del) {
        this.is_del = is_del;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getModify_time() {
        return modify_time;
    }

    public void setModify_time(String modify_time) {
        this.modify_time = modify_time;
    }
}
