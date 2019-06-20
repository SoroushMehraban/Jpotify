package com.Panels.CenterPanelSections;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Album panel which contains list of music which is chosen by user.
 *
 * @author Soroush Mehraban
 * @version 1.0
 */
class PlayListPanel extends MusicPanel {
    private ArrayList<SongPanel> playListSongs;
    /**
     * Constructor which set information need to show in super class.
     *
     * @param image         image of panel to show at first in above.
     * @param title         title to show under the image.
     * @param description   description to show under the title.
     */
    PlayListPanel(BufferedImage image, String title, String description) {
        super(image, title, description);
        playListSongs = new ArrayList<>();
        createPlayListListener();
    }

    private void createPlayListListener(){
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                PlayListPanel source = (PlayListPanel) e.getSource();
                source.setBackground(new Color(23,23,23));
            }

            /*@Override
            public void mousePressed(MouseEvent e) {
                AlbumPanel source = (AlbumPanel)e.getSource();
                source.setBackground(new Color(23,23,23));
            }*/

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
}
