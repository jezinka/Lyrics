package com.projects.jezinka.lyrics.model;

public class Track {
    private Song item;

    public String getTitle() {
        return item.getName();
    }

    public String getArtist() {
        return item.getArtistName();
    }

    @Override
    public String toString() {
        return item.toString();
    }
}
