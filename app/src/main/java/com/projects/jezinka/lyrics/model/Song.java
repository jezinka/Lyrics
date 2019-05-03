package com.projects.jezinka.lyrics.model;

import java.util.List;

public class Song {

    private String name;
    private List<Artist> artists;

    public String getName() {
        return name;
    }

    public String getArtistName() {
        return artists.get(0).toString();
    }


    @Override
    public String toString() {
        return artists.get(0) + ": " + name;
    }
}
