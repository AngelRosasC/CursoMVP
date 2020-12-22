package com.arcode.daggerlogin.Login;

import android.text.TextUtils;

import androidx.annotation.Nullable;

public class LoginActivityPresenter implements LoginActivityMVP.Presenter {
    @Nullable
    private LoginActivityMVP.View view;
    private LoginActivityMVP.Model model;

    public LoginActivityPresenter(LoginActivityMVP.Model model) {
        this.model = model;
    }

    @Override
    public void setView(LoginActivityMVP.View view) {
        this.view = view;
    }

    @Override
    public void loginButtonClicked() {
        if (view != null) {
            String firstName = view.getFirstName().trim();
            String lastName = view.getLastName().trim();
            if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName)) {
                view.showInputError();
            } else {
                model.createUser(firstName, lastName);
                view.showUserSaved();
            }
        }
    }

    @Override
    public void getCurrentUser() {
        if (view != null) {
            User user = model.getUser();
            if (user == null) {
                view.showUserNotAvailable();
            } else {
                view.setFirstName(user.getFirstName());
                view.setLastName(user.getLastName());
            }
        }
    }
}
