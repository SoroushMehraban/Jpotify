package com.Panels.CenterPanelSections;

import com.GUIFrame.GUIFrame;
import com.Interfaces.PlaylistOptionLinker;
import com.Interfaces.ShowSongsLinker;
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
public class SongPanel extends MusicPanel {
    private MP3Info mp3Info;
    private String songTitle;
    private PlaylistOptionLinker playlistOptionLinker;
    private ShowSongsLinker showSongsLinker;
    private boolean selected;//helps for adding song to playlist.

    /**
     * Constructor which set information need to show in super class and create a listener for song
     *
     * @param mp3Info     information about mp3 we want to play
     * @param description description to show under the title.
     */
    SongPanel(MP3Info mp3Info, String description) throws InvalidDataException, IOException, UnsupportedTagException {
        super(mp3Info.getImage(), mp3Info.getTitle(), description);
        songTitle = mp3Info.getTitle();
        this.mp3Info = mp3Info;

        playlistOptionLinker = GUIFrame.getPlaylistOptionLinker();
        showSongsLinker = GUIFrame.getShowSongsLinker();
        createSongListener();
    }

    /**
     * This method only calls if new adding panel appears and unselect song panel if it's selected in previous panel.
     */
    void unSelect() {
        this.selected = false;
    }

    public String getInputFileDirectory() {
        return mp3Info.getInputFileDirectory();
    }

    public MP3Info getMp3Info() {
        return mp3Info;
    }

    /**
     * this method creates a Mouse listener for song panel
     * it does two things:
     * -when mouse entered: it changes to a brighter color.
     * -when mouse exited: it backs to previous color it had.
     * -when mouse pressed: it shows lyrics of song if exists and play given song.
     */
    private void createSongListener() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SongPanel source = (SongPanel) e.getSource();//source is a song panel which mouse clicked on it.
                if (playlistOptionLinker.isAddingSongToPlaylist()) {//if clicked in order to add song to playlist
                    if (!selected) {//if it's not selected before, we add it to addingSongPanel and make it look brighter
                        selected = true;
                        source.setBackground(new Color(41, 41, 41));
                        playlistOptionLinker.getAddingSongPanel().add(0, source);//(it changes playlist after done button clicked!)
                    } else {//else we make look like previous form and remove from addingSongPanel
                        selected = false;
                        source.setBackground(new Color(23, 23, 23));
                        playlistOptionLinker.getAddingSongPanel().remove(source);
                    }
                } else if (playlistOptionLinker.isRemoveSongFromPlaylist()) {//if clicked in order to remove song from playylist.
                    if (!selected) {//if it's not selected we add it to removingSongPanels and make it  look brighter.
                        selected = true;
                        playlistOptionLinker.getRemovingSongPanels().add(source);
                        source.setBackground(new Color(41, 41, 41));
                    } else {//else we make look like previous form and removing from removingSongPanels
                        selected = false;
                        source.setBackground(new Color(23, 23, 23));
                        playlistOptionLinker.getRemovingSongPanels().add(source);// it changes playlist after clicking Done!
                    }
                } else if (playlistOptionLinker.isSwaping()) {//in case if we want to swap:
                    if (!selected) {//if this song panel is not selected before
                        selected = true;//changing state to select
                        if (playlistOptionLinker.getFirstSelectedSwaping() == null)//if we didn't select anything
                            playlistOptionLinker.setFirstSelectedSwaping(source);//set this panel as first panel to swap
                        else {//if we choose a song panel to swap with before
                            playlistOptionLinker.setSecondSelectedSwaping(source);//set this panel as second panel to swap
                            playlistOptionLinker.swapPlayList();//swap them!
                        }
                        source.setBackground(new Color(41, 41, 41));
                    } else {// if this song panel is selected before
                        selected = false;//changing state to unselect
                        if (playlistOptionLinker.getFirstSelectedSwaping() == source)//if it's first panel to swap
                            playlistOptionLinker.setFirstSelectedSwaping(null);//we unselect this as a first one
                        else//it's never gonna happen(due to swapping after second one selected)
                            playlistOptionLinker.setSecondSelectedSwaping(null);
                    }
                } else {//if clicked in order to play song
                    //changing playing song index to first:
                    showSongsLinker.getAllSongsPanel().remove(source);
                    showSongsLinker.getAllSongsPanel().add(0, source);
                    //play clicked music:
                    GUIFrame.playClickedMusic(source);//playing music
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!selected || (!playlistOptionLinker.isAddingSongToPlaylist() && !playlistOptionLinker.isRemoveSongFromPlaylist() && !playlistOptionLinker.isSwaping())) {
                    MusicPanel source = (MusicPanel) e.getSource();
                    source.setBackground(new Color(23, 23, 23));
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                MusicPanel source = (MusicPanel) e.getSource();
                source.setBackground(new Color(41, 41, 41));
            }
        });
    }

    /**
     * this method is called when a message is searched in searchbox, it indicate that it match to this song or not.
     *
     * @param message given search message
     * @return true if matched or false if it's not.
     */
    boolean hasThisName(String message) {
        //creating Strings to compare, with ignoring lower or upper case:
        String songTitle = mp3Info.getTitle().toLowerCase();
        String albumTitle = mp3Info.getAlbum().toLowerCase();
        String artistTtile = mp3Info.getArtist().toLowerCase();
        String searchingMessage = message.toLowerCase();
        //comparing:
        return songTitle.contains(searchingMessage) || albumTitle.contains(searchingMessage) || artistTtile.contains(searchingMessage);
    }
}
