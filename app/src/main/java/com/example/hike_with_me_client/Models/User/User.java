package com.example.hike_with_me_client.Models.User;

import androidx.annotation.NonNull;

import com.example.hike_with_me_client.Models.Objects.Location;

public class User {

    private String id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String hometown;
    private Boolean active;
    private Location location;

    public User() {
    }

    public User(String id, String name, String email, String password, String phoneNumber, String hometown, Boolean active, Location location) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.hometown = hometown;
        this.active = active;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public User setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public User setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getHometown() {
        return hometown;
    }

    public User setHometown(String hometown) {
        this.hometown = hometown;
        return this;
    }

    public Boolean getActive() {
        return active;
    }

    public User setActive(Boolean active) {
        this.active = active;
        return this;
    }

    public Location getLocation() {
        return location;
    }

    public User setLocation(Location location) {
        this.location = location;
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", hometown='" + hometown + '\'' +
                ", active=" + active +
                ", location=" + location +
                '}';
    }
}
