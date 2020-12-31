package com.arcode.daggerlogin.http;

import com.arcode.daggerlogin.http.twitch.Twitch;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface TwitchAPI {
    @GET("games/top")
    Call<Twitch> getTopGames(@Header("Client-Id") String clientId);
}
