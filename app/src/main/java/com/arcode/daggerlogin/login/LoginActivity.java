package com.arcode.daggerlogin.login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.arcode.daggerlogin.R;
import com.arcode.daggerlogin.http.TwitchAPI;
import com.arcode.daggerlogin.http.twitch.Game;
import com.arcode.daggerlogin.http.twitch.Twitch;
import com.arcode.daggerlogin.root.App;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class LoginActivity extends AppCompatActivity implements LoginActivityMVP.View {
    @Inject
    LoginActivityMVP.Presenter presenter;

    @Inject
    TwitchAPI twitchAPI;

    @BindView(R.id.edtUsername)
    EditText edtUsername;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        ((App) getApplication()).getComponent().inject(this);

//        bindUI();

        btnLogin.setOnClickListener(v -> presenter.loginButtonClicked());

        /* Ejemplo de llamada de la API de twitch con retrofit */
       /* Call<Twitch> call = twitchAPI.getTopGames("3pv98v9ct1bpwrzep1fw4zsfke6n26");
        call.enqueue(new Callback<Twitch>() {
            @Override
            public void onResponse(Call<Twitch> call, Response<Twitch> response) {
                if (response.body() != null) {
                    List<Game>  topGame = response.body().getGame();
                    for (Game game : topGame) {
                        System.out.println(game.getName());
                    }
                }
            }

            @Override
            public void onFailure(Call<Twitch> call, Throwable t) {
                t.printStackTrace();
            }
        });*/
        twitchAPI.getTopGamesObservable("3pv98v9ct1bpwrzep1fw4zsfke6n26")
                .flatMap((Function<Twitch, Observable<Game>>) twitch -> Observable.fromIterable(twitch.getGame()))
                .flatMap((Function<Game, Observable<String>>) game -> Observable.just(game.getName()))
                .filter(s -> s.contains("w") || s.contains("W"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String name) {
                        System.out.println("RxJava says: " + name);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.getCurrentUser();
    }

    private void bindUI() {
        /*edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);*/
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