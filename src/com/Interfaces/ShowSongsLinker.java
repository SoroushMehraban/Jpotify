package com.Interfaces;

import com.Panels.CenterPanelSections.SongPanel;

import java.util.ArrayList;

public interface ShowSongsLinker {
    void showSongs(ArrayList<SongPanel> songPanels);
    /**
     * This method only shows song panels related to a playlist.
     * @param playListTitle title of playlist as a key
     */
    void showPlayListSongs(String playListTitle);
}
