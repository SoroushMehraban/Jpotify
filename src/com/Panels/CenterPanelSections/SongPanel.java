package com.Panels.CenterPanelSections;

import com.GUIFrame.GUIFrame;
import com.Interfaces.AddingSongLinker;
import com.Interfaces.LyricsLinker;
import com.MP3.MP3Info;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashSet;

/**
 * This subclass created if the music panel we want is a song and if user click on that, it plays desired song.
 *
 * @author Soroush Mehraban & Morteza Damghani
 * @version 1.0
 */
public class SongPanel extends  MusicPanel {
    private MP3Info mp3Info;
    private String songTitle;
    private LyricsLinker lyricsLinker;
    private AddingSongLinker addingSongLinker;
    private boolean selected;//helps for adding song to playlist.

    /**
     * Constructor which set information need to show in super class and create a listener for song
     *
     * @param mp3Info information about mp3 we want to play
     * @param description description to show under the title.
     * @param lyricsLinker a linker to show lyrics in center panel.
     */
    SongPanel(MP3Info mp3Info, String description, LyricsLinker lyricsLinker) throws InvalidDataException, IOException, UnsupportedTagException {
        super(mp3Info.getImage(),mp3Info.getTitle(),description);
        songTitle = mp3Info.getTitle();
        this.mp3Info = mp3Info;
        this.lyricsLinker = lyricsLinker;

        addingSongLinker = GUIFrame.getAddingSongLinker();
        createSongListener();
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    String getSongTitle() {
        return songTitle;
    }

    /**
     * this method creates a Mouse listener for song panel
     * it does two things:
     * -when mouse entered: it changes to a brighter color.
     * -when mouse exited: it backs to previous color it had.
     * -when mouse pressed: it shows lyrics of song if exists and play given song.
     */
    private void createSongListener(){
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SongPanel source = (SongPanel)e.getSource();
                if(!addingSongLinker.isAddingSongToPlaylist()) {
                    GUIFrame.playClickedMusic((SongPanel) e.getSource());
                    lyricsLinker.showLyrics(mp3Info.getLyrics());
                }
                else{
                    if(!selected){
                        selected = true;
                        source.setBackground(new Color(41,41,41));
                        addingSongLinker.getCurrentPlaylistPanel().getPlayListSongs().add(source);
                    }
                    else{
                        selected = false;
                        source.setBackground(new Color(23,23,23));
                        addingSongLinker.getCurrentPlaylistPanel().getPlayListSongs().remove(source);
                    }
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if(!selected || !addingSongLinker.isAddingSongToPlaylist()) {
                    MusicPanel source = (MusicPanel) e.getSource();
                    source.setBackground(new Color(23, 23, 23));
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                MusicPanel source = (MusicPanel)e.getSource();
                source.setBackground(new Color(41,41,41));
            }
        });
    }

    public String getInputFileDirectory(){
        return mp3Info.getInputFileDirectory();
    }
}
