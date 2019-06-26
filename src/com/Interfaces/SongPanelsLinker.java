package com.Interfaces;

import com.Panels.CenterPanelSections.AlbumPanel;
import com.Panels.CenterPanelSections.PlayListPanel;

import java.util.HashMap;

/**
 * This interface links playlist panel and AppStorage with center part of center panel to
 * control album panels and playlist panels.
 */
public interface SongPanelsLinker {

    HashMap<String, AlbumPanel> getAlbumPanels();

    HashMap<String, PlayListPanel> getPlayListPanels();
}
