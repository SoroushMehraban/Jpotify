package com.Interfaces;

import com.Panels.CenterPanelSections.SongPanel;
import java.util.HashSet;

public interface ShowSongsLinker {
    void showSongs(HashSet<SongPanel> songPanels);
    /**
     * This method only shows song panels related to a playlist.
     * @param playListTitle title of playlist as a key
     */
    void showPlayListSongs(String playListTitle);
}
