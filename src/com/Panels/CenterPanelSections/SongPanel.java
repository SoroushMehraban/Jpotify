package com.Panels.CenterPanelSections;

import com.GUIFrame.GUIFrame;
import com.MP3.MP3Info;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * This subclass created if the music panel we want is a song and if user click on that, it plays desired song.
 *
 * @author Soroush Mehraban & Morteza Damghani
 * @version 1.0
 */
class SongPanel extends  MusicPanel {
    private MP3Info mp3Info;

    /**
     * Constructor which set information need to show in super class and create a listener for song
     *
     * @param mp3Info information about mp3 we want to play
     * @param description description to show under the title.
     */
    SongPanel(MP3Info mp3Info, String description) throws InvalidDataException, IOException, UnsupportedTagException {
        super(mp3Info.getImage(),mp3Info.getTitle(),description);
        this.mp3Info = mp3Info;

        createSongListener();
    }

    /**
     * this method creates a Mouse listener for song panel
     * it does two things:
     * -when mouse entered: it changes to a brighter color.
     * -when mouse exited: it backs to previous color it had.
     */
    private void createSongListener(){
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                GUIFrame.playClickedMusic(mp3Info.getInputFileDirectory());
            }
            @Override
            public void mouseExited(MouseEvent e) {
                MusicPanel source = (MusicPanel)e.getSource();
                source.setBackground(new Color(23,23,23));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                MusicPanel source = (MusicPanel)e.getSource();
                source.setBackground(new Color(41,41,41));
            }
        });
    }
}
