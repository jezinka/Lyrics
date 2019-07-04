package com.projects.jezinka.lyrics;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.projects.jezinka.lyrics.model.Track;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SpotifyTrackController implements Callback<Track> {

    private static final String BASE_URL = "https://api.spotify.com/v1/me/player/";
    private Context mContext;

    SpotifyTrackController(Context mContext) {
        this.mContext = mContext;
    }

    public void start(String token) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        SpotifyAPI spotifyAPI = retrofit.create(SpotifyAPI.class);
        Call<Track> call = spotifyAPI.getCurrentSong("ES", "Bearer " + token);
        call.enqueue(this);
    }

    @Override
    public void onResponse(@NonNull Call<Track> call, @NonNull Response<Track> response) {
        if (response.isSuccessful()) {
            Track track = response.body();
            final TextView responseView = ((Activity) mContext).findViewById(R.id.title_text_view);
            if (track != null) {
                responseView.setText(track.toString());
                new LyricsController(mContext).start(track);
            } else {
                responseView.setText(R.string.nothing_playing);
            }
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(@NonNull Call<Track> call, @NonNull Throwable t) {
        t.printStackTrace();
    }
}
