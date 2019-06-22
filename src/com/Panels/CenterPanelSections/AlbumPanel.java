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
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Album panel which contains list of music with similar album names.
 *
 * @author Soroush Mehraban
 * @version 1.0
 */
class AlbumPanel extends MusicPanel {
    private HashSet<SongPanel> songPanels;
    private ShowSongsLinker showSongsLinker;

    /**
     * Constructor which set information need to show in super class.
     * it also set a showSongLinker which helps us to show album Songs in center panel.
     *
     * @param image           image of panel to show at first in above.
     * @param title           title to show under the image.
     * @param description     description to show under the title.
     * @param albumMusicsInfo information about songs belongs to this album.
     * @param showSongsLinker a linker which helps us to show songs in center panel.
     * @param lyricsLinker    a linker which helps us to show lyrics of song.
     */
    AlbumPanel(BufferedImage image, String title, String description, ArrayList<MP3Info> albumMusicsInfo, ShowSongsLinker showSongsLinker, LyricsLinker lyricsLinker) {
        super(image, title, description);
        songPanels = createSongPanels(albumMusicsInfo,lyricsLinker);
        createAlbumListener();
        this.showSongsLinker = showSongsLinker;
    }

    HashSet<SongPanel> getSongPanels() {
        return songPanels;
    }

    /**
     * creating album listener to do this things:
     * if mouse entered: it became brighter.
     * if mouse exited: it turn to its previous type.
     * if mouse pressed: it show albumSongs related to this album.
     */
    private void createAlbumListener(){
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                AlbumPanel source = (AlbumPanel)e.getSource();
                source.setBackground(new Color(23,23,23));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                showSongsLinker.showSongs(songPanels);
                AlbumPanel source = (AlbumPanel)e.getSource();
                source.setBackground(new Color(23,23,23));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                AlbumPanel source = (AlbumPanel)e.getSource();
                source.setBackground(new Color(41,41,41));
            }
        });
    }

    /**
     * this method gives list of mp3infos and return set of song panels.
     * @param mp3Infos list of song infos.
     * @return set of song panels.
     */
    private HashSet<SongPanel> createSongPanels(ArrayList<MP3Info> mp3Infos, LyricsLinker lyricsLinker){
        HashSet<SongPanel> songPanels = new HashSet<>();
        for(MP3Info mp3Info: mp3Infos ) {
            try {
                songPanels.add(new SongPanel(mp3Info, mp3Info.getArtist(),lyricsLinker));
            } catch (InvalidDataException | IOException | UnsupportedTagException e) {
                JOptionPane.showMessageDialog(null, "Error reading mp3 file image");
            }
        }
        return songPanels;
    }

    /**
     * this method called if new songs related to album added, so it added all of them to songPanels
     * we use HashSet for songPanels to avoid song duplication.
     * @param albumMusicsInfo  new songs info added by user.
     */
    void addNewSongs(ArrayList<MP3Info> albumMusicsInfo,LyricsLinker lyricsLinker){
        HashSet<SongPanel> newSongs = createSongPanels(albumMusicsInfo,lyricsLinker);
        songPanels.addAll(newSongs);
    }
}
