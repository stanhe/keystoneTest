package com.example.stan.keystonetest.model;

/**
 * Created by stan on 16/8/19.
 */
public class User {
    public User(String name, String password, String type) {
        this.name = name;
        this.password = password;
        this.type = type;
    }
    public String name;
    public String password;
    public String type;
}
