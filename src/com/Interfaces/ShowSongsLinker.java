package com.Interfaces;

import com.Panels.CenterPanelSections.SongPanel;

import java.util.ArrayList;

public interface ShowSongsLinker {
    ArrayList<SongPanel> getAllSongsPanel();

    void showAlbumSongs(String albumTitle);

    /**
     * This method only shows song panels related to a playlist.
     *
     * @param playListTitle title of playlist as a key
     */
    void showPlayListSongs(String playListTitle);
}
