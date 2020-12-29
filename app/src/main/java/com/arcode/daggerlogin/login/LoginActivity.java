package com.arcode.daggerlogin.login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.arcode.daggerlogin.R;
import com.arcode.daggerlogin.root.App;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity implements LoginActivityMVP.View {
    @Inject
    LoginActivityMVP.Presenter presenter;

    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((App) getApplication()).getComponent().inject(this);

        bindUI();

        btnLogin.setOnClickListener(v -> presenter.loginButtonClicked());

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.getCurrentUser();
    }

    private void bindUI() {
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
    }

    @Override
    public String getFirstName() {
        return this.edtUsername.getText().toString();
    }

    @Override
    public String getLastName() {
        return this.edtPassword.getText().toString();
    }

    @Override
    public void showUserNotAvailable() {
        Toast.makeText(this, "El usuario no esta disponible", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInputError() {
        Toast.makeText(this, "Correo o contraseña incorrectas", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUserSaved() {
        Toast.makeText(this, "Usuario guardado correctamente", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setFirstName(String firstName) {
        this.edtUsername.setText(firstName);
    }

    @Override
    public void setLastName(String lastName) {
        this.edtPassword.setText(lastName);
    }
}