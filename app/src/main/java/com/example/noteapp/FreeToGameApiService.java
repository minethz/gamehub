package com.example.noteapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FreeToGameApiService {
    @GET("api/games?key=zaf1231")
    Call<List<GameResponse>> getGames();
}
