package com.projects.jezinka.lyrics;

import com.projects.jezinka.lyrics.model.Track;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface SpotifyAPI {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("currently-playing/")
    Call<Track> getCurrentSong(@Query("market") String market, @Header("Authorization") String authHeader);
}
