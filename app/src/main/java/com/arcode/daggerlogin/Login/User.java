package com.arcode.daggerlogin.Login;

public class User {
    private int Id;
    private String firstName;
    private String lastName;

    public User(String firstName, String lastName) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
    }

    public int getID() {
        return Id;
    }

    public void setID(int Id) {
        this.Id = Id;
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
}
