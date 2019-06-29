package com.Interfaces;

import java.util.ArrayList;

public interface LyricsLinker {
    /**
     * This method shows chosen song lyrics if exists or shows list of reasons why it couldn't shows.
     *
     * @param lyricsLines line of lyrics.
     */
    void showLyrics(ArrayList<String> lyricsLines);
}
