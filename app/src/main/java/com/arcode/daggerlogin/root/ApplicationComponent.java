package com.arcode.daggerlogin.root;

import com.arcode.daggerlogin.login.LoginActivity;
import com.arcode.daggerlogin.login.LoginModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, LoginModule.class})
public interface ApplicationComponent {
    void inject(LoginActivity target);
}
