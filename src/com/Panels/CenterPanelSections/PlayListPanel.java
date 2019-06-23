package com.Panels.CenterPanelSections;

import com.Interfaces.LyricsLinker;
import com.Interfaces.ShowSongsLinker;
import com.MP3.MP3Info;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

/**
 * Album panel which contains list of music which is chosen by user.
 *
 * @author Soroush Mehraban
 * @version 1.0
 */
public class PlayListPanel extends MusicPanel {
    private HashSet<SongPanel> playListSongs;
    private ShowSongsLinker showSongsLinker;
    private String title;

    /**
     * Constructor which set information need to show in super class.
     *
     * @param image         image of panel to show at first in above.
     * @param title         title to show under the image.
     * @param description   description to show under the title.
     */
    PlayListPanel(BufferedImage image, String title, String description, ShowSongsLinker showSongsLinker) {
        super(image, title, description);
        this.title = title;
        playListSongs = new HashSet<>();
        this.showSongsLinker = showSongsLinker;
        createPlayListListener();
    }

    public String getTitle() {
        return title;
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
                showSongsLinker.showPlayListSongs(title);
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
        Set<SongPanel> tempSynchronized = Collections.synchronizedSet(playListSongs);
        Iterator<SongPanel> it = tempSynchronized.iterator();
        while(it.hasNext()){
            SongPanel songPanel = it.next();
            if(songPanel.getSongTitle().equals(title))
                it.remove();
        }
        playListSongs = new HashSet<>(tempSynchronized);
    }

    HashSet<SongPanel> getPlayListSongs() {
        return playListSongs;
    }
}
