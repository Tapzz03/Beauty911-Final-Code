package com.beauty911;

public class User {
    private String name;
    private String surname;
    private String username;
    private String email;
    private String phone;
    private String address;

    public User(String name, String surname, String username, String email, String phone, String address) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    // Add getters and an empty constructor for Firestore
    public User() {}

    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
}
