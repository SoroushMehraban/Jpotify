package com.GUIFrame;

import com.MP3.MP3Info;
import com.Panels.CenterPanelSections.SongPanel;
import com.Panels.GeneralPanels.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * the window GUI where User can play a music.
 * when user run this program, its only create one instance of this class, so it's a singleton class.
 *
 * @author Soroush Mehraban & Morteza Damghani
 * @version 1.0
 */
public class GUIFrame extends JFrame {
    private static GUIFrame guiFrame;
    private static SouthPanel southPanel;
    private static CenterPanel centerPanel;
    /**
     * Class Constructor
     */
    private GUIFrame() throws IOException {
        this.setLayout(new BorderLayout()); //frame layout
        this.setSize(940,512); //frame length : 940 * 512
        this.setLocationRelativeTo(null); //setting frame at the center of screen
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //closing the program when user close the window.
        this.setMinimumSize(new Dimension(940,512));
        centerPanel = new CenterPanel();
        this.add(centerPanel,BorderLayout.CENTER);
        southPanel = new SouthPanel();
        this.add(southPanel,BorderLayout.SOUTH);
        this.setVisible(true);
        //setting like linker between playPanel in southPanel and centerPart in centerPanel:
        southPanel.getPlayPanel().setLikeLinker(centerPanel.getCenterPart());
        //temp code to determine it can show albums and musics:
        try {
            MP3Info mp3Info = new MP3Info("Soroush Tabarsi - Grey (Ft Seventh Soul) [128].mp3");
            ArrayList<MP3Info> mp3Infos = new ArrayList<>();
            mp3Infos.add(mp3Info);
            mp3Infos.add(mp3Info);
            mp3Infos.add(mp3Info);
            mp3Infos.add(mp3Info);
            mp3Infos.add(mp3Info);
            mp3Infos.add(mp3Info);
            mp3Infos.add(mp3Info);
            mp3Infos.add(mp3Info);
            addAlbum(mp3Info.getAlbum(),mp3Infos,"Sample text to show");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * getting instance of class.
     * @return a unique com.GUIFrame.GUIFrame object.
     */
    public static GUIFrame getInstance() throws IOException {
        if(guiFrame == null)
            guiFrame = new GUIFrame();
        return guiFrame;
    }

    /**
     * reloading com.GUIFrame.GUIFrame when components changes.
     */
    public static void reload(){
        guiFrame.repaint();
        guiFrame.revalidate();
    }

    /**
     * this method play clicked music and then can be controlled in south panel.
     * @param songPanel song we want to play.
     */
    public static void playClickedMusic(SongPanel songPanel, HashSet<SongPanel> albumSongPanels){
        southPanel.play(songPanel, albumSongPanels);
    }

    /**
     * this method add an album which is selected in library.
     * @param albumTitle title of album to be shown.
     * @param albumMusicsInfo list of all musics info which related to a album.
     * @param description description to be shown.
     */
    public static void addAlbum(String albumTitle, ArrayList<MP3Info> albumMusicsInfo, String description) {
        centerPanel.getCenterPart().addAlbum(albumTitle,albumMusicsInfo,description);
    }

    /**
     * Creating playlist which is implemented in center part of centerPanel(this method works as a linker)
     * @param title title of play list.
     * @param description description to show under the title.
     */
    public static void createPlayList(String title, String description){
        centerPanel.getCenterPart().createPlayList(title,description);
    }
    /**
     * this method adds a song to given playlist.(works as a linker)
     * @param playListTitle  title of playlist as a key of HashMap.
     * @param songDirectory directory of music to add.
     */
    public static void addSongToPlayList(String playListTitle, String songDirectory){
        centerPanel.getCenterPart().addSongToPlayList(playListTitle,songDirectory);
    }

    /**
     * This method works as a linker between west panel and center panel and shows all songs existing in library.
     */
    public static void showAllSongs(){
        centerPanel.getCenterPart().showAllSongs();
    }

    /**
     * this method works as a linker between west part panel and center panel and shows all songs related to an album
     * @param albumTitle album title as a key.
     */
    public static void showAlbumSongs(String albumTitle){
        centerPanel.getCenterPart().showAlbumSongs(albumTitle);
    }
    /**
     * this method works as a linker between west part panel and center panel and shows all songs related to a playList
     * @param playlistTitle playlist title as a key.
     */
    public static void showPlaylistSongs(String playlistTitle){
        centerPanel.getCenterPart().showPlayListSongs(playlistTitle);
    }
}
