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

    void addSong(String directory, LyricsLinker lyricsLinker){
        try {
            MP3Info mp3Info = new MP3Info(directory);
            String description = "This song belongs to "+mp3Info.getAlbum()+" album";
            SongPanel songPanel = new SongPanel(mp3Info,description,lyricsLinker);
            playListSongs.add(songPanel);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading mp3 file");
        } catch (NoSuchFieldException e) {
            JOptionPane.showMessageDialog(null, "Error find mp3 file");
        } catch (InvalidDataException | UnsupportedTagException e) {
            JOptionPane.showMessageDialog(null, "Error reading mp3 image");
        }

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
