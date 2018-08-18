package com.example.dlimagecoder.netmodel;

import java.io.Serializable;

public class UserInfo implements Serializable{
    private int userId;
    private String name;
    private int age;
    private String sex;
    private String head;

    public UserInfo(int userId, String name, int age, String sex, String head) {
        this.userId = userId;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.head = head;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }
}
