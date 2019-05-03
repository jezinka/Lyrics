package com.projects.jezinka.lyrics.model;

import java.util.List;

public class Item {
    private String name;
    private List<Artist> artists;

    @Override
    public String toString() {
        return artists.get(0) + ": " + name;
    }
}
