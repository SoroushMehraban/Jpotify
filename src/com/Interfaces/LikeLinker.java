package com.Interfaces;


/**
 * and interface that links center part of center panel with play panel in south panel.
 *
 * @author Soroush Mehraban & Morteza Damghani
 * @version 1.0
 */
public interface LikeLinker {
    /**
     * when like button pressed. if it wasn't press before, this method adds music to favorite play list.
     *
     * @param directory directory of current music.
     */
    void addToFavoritePlayList(String directory);

    /**
     * when like button pressed. if it was press before, this method adds music to favorite play list.
     *
     * @param directory directory of current music.
     */
    void removeFromFavoritePlayList(String directory);

    /**
     * determine if song is liked before or not.
     *
     * @param directory directory of song
     */
    boolean isSongLiked(String directory);
}
