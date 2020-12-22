package com.arcode.daggerlogin.Root;

import com.arcode.daggerlogin.Login.LoginActivity;
import com.arcode.daggerlogin.Login.LoginModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, LoginModule.class})
public interface ApplicationComponent {
    void inject(LoginActivity target);
}
