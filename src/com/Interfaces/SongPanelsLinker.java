package com.Interfaces;

import com.Panels.CenterPanelSections.PlayListPanel;
import com.Panels.CenterPanelSections.SongPanel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This interface links playlist panel and AppStorage with center part of center panel to
 * control album panels and playlist panels.
 */
public interface SongPanelsLinker {

    ArrayList<SongPanel> getAllSongPanels();

    HashMap<String, PlayListPanel> getPlayListPanels();
}
