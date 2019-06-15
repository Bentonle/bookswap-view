package com.bookswap.model.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.bookswap.model.Address;
import com.bookswap.model.Campus;
import com.bookswap.model.Role;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private String firstName;
    private String lastName;

    private String email;
    private String username;
    private String password;

    private Collection<Role> roles;
    private Campus campus;
    private List<Address> address = new ArrayList<>();

    public User() {}

    public User(String firstName, String lastName, String email, String username, String password,
                Collection<Role> roles, Campus campus, List<Address> address) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.campus = campus;
        this.address = address;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public Campus getCampus() {
        return campus;
    }

    public void setCampus(Campus campus) {
        this.campus = campus;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }



}