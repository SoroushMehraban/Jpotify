package com.Interfaces;

import com.Panels.CenterPanelSections.SongPanel;

import java.util.ArrayList;

/**
 * This interface linker help us to link center part of center panel with song panel for adding song to play list.
 *
 * @author Soroush Mehraban & Morteza Damghani
 * @version 1.0
 */
public interface PlaylistOptionLinker {
    /**
     * This method indicate that are we adding song to playlist or not
     * if we adding song to play list, when song panel clicked, it adds that to current playlist after pressing done.
     * else it play desired song.
     *
     * @return desired boolean.
     */
    boolean isAddingSongToPlaylist();

    /**
     * @return true if swap button activated.
     */
    boolean isSwaping();

    /**
     * This method indicate that are we removing song from playlist or not
     * if we removing song from playlist, when song panel clicked, it remove that from current playlist after pressing done.
     * else it play desired song.
     *
     * @return desired boolean
     */
    boolean isRemoveSongFromPlaylist();

    /**
     * if we are aiming to add song to playlist, this method help us to add before pressing done.
     *
     * @return a temporary space for adding panels we want to add.
     */
    ArrayList<SongPanel> getAddingSongPanel();

    /**
     * if we are aiming to remove song from playlist, this method help us to remove before pressing done.
     *
     * @return a temporary space for removing panels we want to add.
     */
    ArrayList<SongPanel> getRemovingSongPanels();


    /**
     * This method swap 2 songs in playlist only if they were selecting before.
     */
    void swapPlayList();

    SongPanel getFirstSelectedSwaping();

    SongPanel getSecondSelectedSwaping();

    void setFirstSelectedSwaping(SongPanel firstSelectedSwaping);

    void setSecondSelectedSwaping(SongPanel secondSelectedSwaping);
}
