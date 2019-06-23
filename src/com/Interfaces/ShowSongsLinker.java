package com.Interfaces;

import com.Panels.CenterPanelSections.SongPanel;
import java.util.HashSet;

public interface ShowSongsLinker {
    void showSongs(HashSet<SongPanel> songPanels);
    void setPlaylistIsRunning(boolean value);
}
