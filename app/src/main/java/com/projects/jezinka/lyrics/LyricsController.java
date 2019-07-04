package com.projects.jezinka.lyrics;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.projects.jezinka.lyrics.model.Lyrics;
import com.projects.jezinka.lyrics.model.Track;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LyricsController implements Callback<Lyrics> {

    private static final String BASE_URL = "https://api.lyrics.ovh/v1/";
    private Context mContext;

    LyricsController(Context mContext) {
        this.mContext = mContext;
    }

    public void start(Track track) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        LyricsAPI lyricsAPI = retrofit.create(LyricsAPI.class);
        Call<Lyrics> call = lyricsAPI.getLyrics(track.getArtist(), track.getTitle());
        call.enqueue(this);
    }

    @Override
    public void onResponse(@NonNull Call<Lyrics> call, @NonNull Response<Lyrics> response) {
        if (response.isSuccessful()) {
            Lyrics lyrics = response.body();
            final TextView responseView = ((Activity) mContext).findViewById(R.id.response_text_view);
            if (lyrics != null) {
                responseView.setText(lyrics.toString());
            } else {
                responseView.setText(R.string.cannot_fetch);
            }
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(@NonNull Call<Lyrics> call, @NonNull Throwable t) {
        t.printStackTrace();
    }
}
