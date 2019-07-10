package com.bookswap.model;


import com.bookswap.model.user.User;

import java.util.ArrayList;
import java.util.List;


//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class Campus {
    private String name;

    private Address address;

    private School school;

    private List<User> user = new ArrayList<>();

    public Campus() {}

    public Campus(String name, Address address, School school, List<User> user) {
        super();
        this.name = name;
        this.address = address;
        this.school = school;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }
}
