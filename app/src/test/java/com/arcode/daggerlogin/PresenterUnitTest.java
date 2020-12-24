package com.arcode.daggerlogin;

import com.arcode.daggerlogin.Login.LoginActivityMVP;
import com.arcode.daggerlogin.Login.LoginActivityPresenter;
import com.arcode.daggerlogin.Login.User;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PresenterUnitTest {

    LoginActivityPresenter presenter;
    User user;

    LoginActivityMVP.Model mockModel;
    LoginActivityMVP.View mockView;

    @Before
    public void init() {
        mockModel = mock(LoginActivityMVP.Model.class);
        mockView = mock(LoginActivityMVP.View.class);

        user = new User("Angel", "Rosas");

//        when(mockModel.getUser()).thenReturn(user);

        presenter = new LoginActivityPresenter(mockModel);
        presenter.setView(mockView);
    }

    @Test
    public void noExistsInteractionWithView() {
        presenter.getCurrentUser();
        verify(mockView, times(1)).showUserNotAvailable();
//        verify(mockView, never()).showUserNotAvailable();
    }

    @Test
    public void loadUserFromTheRepoWhenValidUserIsPresent() {
        when(mockModel.getUser()).thenReturn(user);

        presenter.getCurrentUser();

        // Comprobamos la interactuación con el modelo de datos
        verify(mockModel, times(1)).getUser();

        // Comprobamos la interactuación con la vista
        verify(mockView, times(1)).setFirstName("Angel");
        verify(mockView, times(1)).setLastName("Rosas");
        verify(mockView, never()).showUserNotAvailable();

    }

    @Test
    public void showErrorMessageWhenUserIsNull() {
        when(mockModel.getUser()).thenReturn(null);
        presenter.getCurrentUser();

        // Comprobamos la interactuación con el modelo de datos
        verify(mockModel, times(1)).getUser();

        // Comprobamos la interactuación con la vista
        verify(mockView, never()).setFirstName("Angel");
        verify(mockView, never()).setLastName("Rosas");
        verify(mockView, times(1)).showUserNotAvailable();
    }

    @Test
    public void createErrorMessageIfAnyFieldIsEmpty() {
        // Primera prueba poniendo el firtsname vacío
        when(mockView.getFirstName()).thenReturn("");
        when(mockView.getLastName()).thenReturn("");

        presenter.loginButtonClicked();

        verify(mockView, times(1)).getFirstName();
        verify(mockView, times(1)).getLastName();
        verify(mockView, times(1)).showInputError();

        // Segunda prueba poniendo un valor en el campo firstname y dejando vacío el campo lastname
        when(mockView.getFirstName()).thenReturn("Angel");
        when(mockView.getLastName()).thenReturn("");

        presenter.loginButtonClicked();

        verify(mockView, times(2)).getFirstName(); // Las llamadas son acumulativas por eso se pone dos veces
        verify(mockView, times(2)).getLastName(); //
        verify(mockView, times(2)).showInputError(); // El método se llamo antes y de nuevo ahora, en total dos veces.
    }

    @Test
    public void saveValidUser(){
        when(mockView.getFirstName()).thenReturn("Angel");
        when(mockView.getLastName()).thenReturn("Rosas");

        presenter.loginButtonClicked();

        verify(mockView, times(1)).getFirstName();
        verify(mockView, times(1)).getLastName();
        verify(mockModel, times(1)).createUser("Angel", "Rosas");
        verify(mockView, times(1)).showUserSaved();
    }
}
