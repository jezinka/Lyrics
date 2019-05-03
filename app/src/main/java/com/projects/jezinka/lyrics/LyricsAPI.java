package com.projects.jezinka.lyrics;

import com.projects.jezinka.lyrics.model.Lyrics;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LyricsAPI {

    @GET("{artist}/{title}/")
    Call<Lyrics> getLyrics(@Path("artist") String artist, @Path("title") String title);
}
