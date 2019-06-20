package com.Interfaces;

/**
 * Interface created to link south panel with playPanel.
 *
 * @author Soroush Mehraban & Morteza Damghani
 * @version 1.0
 */
public interface PlayControlLinker {
    /**
     * Changing song to previous one.
     */
    void goForward();

    /**
     * changing song to next one.
     */
    void goBack();

    /**
     * if isRepeating is true, it repeats current song, else it go to next one after finished.
     * @param isRepeating indicate that it should be repeat or not.
     */
    void setRepeat(boolean isRepeating);
}
