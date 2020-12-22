package com.arcode.daggerlogin.Login;

public interface LoginRepository {
    void saveUser(User user);

    User getUser();
}
