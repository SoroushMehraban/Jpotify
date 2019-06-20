package com.Panels.CenterPanelSections;

import com.Interfaces.ShowSongsLinker;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Album panel which contains list of music which is chosen by user.
 *
 * @author Soroush Mehraban
 * @version 1.0
 */
class PlayListPanel extends MusicPanel {
    private HashSet<SongPanel> playListSongs;
    private ShowSongsLinker showSongsLinker;

    /**
     * Constructor which set information need to show in super class.
     *
     * @param image         image of panel to show at first in above.
     * @param title         title to show under the image.
     * @param description   description to show under the title.
     */
    PlayListPanel(BufferedImage image, String title, String description, ShowSongsLinker showSongsLinker) {
        super(image, title, description);
        playListSongs = new HashSet<>();
        this.showSongsLinker = showSongsLinker;
        createPlayListListener();
    }

    private void createPlayListListener(){
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                PlayListPanel source = (PlayListPanel) e.getSource();
                source.setBackground(new Color(23,23,23));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                showSongsLinker.showSongs(playListSongs);
                PlayListPanel source = (PlayListPanel) e.getSource();
                source.setBackground(new Color(23,23,23));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                PlayListPanel source = (PlayListPanel) e.getSource();
                source.setBackground(new Color(41,41,41));
            }
        });
    }

    void addSong(SongPanel songPanel){
        playListSongs.add(songPanel);
    }
    void removeSong(String title){
        for(SongPanel songPanel : playListSongs)
            if(songPanel.getSongTitle().equals(title))
                playListSongs.remove(songPanel);
    }
}
