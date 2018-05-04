package com.demo.reqresdemo;

/**
 * Created by bhads on 04-May-18.
 */

public class User {

    private String firstName;
    private String lastName;
    private String avatar;
    private int id;

    public User() {
    }
    //Getters and setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
