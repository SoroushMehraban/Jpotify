package com.Interfaces;

import com.Panels.CenterPanelSections.PlayListPanel;
import com.Panels.CenterPanelSections.SongPanel;

import java.util.HashSet;

/**
 * This interface linker help us to link center part of center panel with song panel for adding song to play list.
 *
 * @author Soroush Mehraban & Morteza Damghani
 * @version 1.0
 */
public interface AddingSongLinker {
    /**
     * This method indicate that are we adding song to playlist or not
     * if we adding song to play list, when song panel clicked, it adds that to current playlist.
     * else it play desired song.
     *
     * @return desired boolean.
     */
    boolean isAddingSongToPlaylist();

    /**
     * if we are aiming to addi song to playlist, this method help us to get current playlist.
     *
     * @return desired playlist
     */
    HashSet<SongPanel> getAddingSongPanel();
}
