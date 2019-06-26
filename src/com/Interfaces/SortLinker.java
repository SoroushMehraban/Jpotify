package com.Interfaces;

import com.Panels.CenterPanelSections.PlayListPanel;


/**
 * this interface link center part of center panel with sort part.
 * so sort part can get current playlist panel and do sorting.
 *
 * @author Soroush Mehraban & Morteza Damghani
 * @version 1.0
 */
public interface SortLinker {

    PlayListPanel getCurrentPlaylistPanel();

    void showPlayListSongs(String playListTitle);
}
