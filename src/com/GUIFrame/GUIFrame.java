package com.GUIFrame;

import com.Interfaces.PlaylistOptionLinker;
import com.MP3.MP3Info;
import com.Panels.CenterPanelSections.SongPanel;
import com.Panels.GeneralPanels.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

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
    private static WestPanel westPanel;
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
        westPanel=new WestPanel();
        JScrollPane leftJScrollPane=new JScrollPane(westPanel);
        leftJScrollPane.setPreferredSize(new Dimension(120,600));
        CenterPanel.customizeJScrollPane(leftJScrollPane);
        this.add(leftJScrollPane,BorderLayout.WEST);
        this.setVisible(true);
        //setting like linker between playPanel in southPanel and centerPart in centerPanel:
        southPanel.getPlayPanel().setLikeLinker(centerPanel.getCenterPart());

        showHome();//showing home by default
    }

    /**
     * getting instance of class.
     * @return a unique com.GUIFrame.GUIFrame object.
     */
    public static GUIFrame getInstance() {
        if(guiFrame == null) {
            try {
                guiFrame = new GUIFrame();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error creating GUIFrame");
            }
        }
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
     * This method works as a linker and show home after Home clicked in west panel.
     */
    public static void showHome(){
        centerPanel.getCenterPart().showHome();
    }
    /**
     * this method play clicked music and then can be controlled in south panel.
     * @param songPanel song we want to play.
     */
    public static void playClickedMusic(SongPanel songPanel){
        southPanel.play(songPanel, centerPanel.getCenterPart().getCurrentPlaying());
    }

    /**
     * this method add an album which is selected in library.
     * @param albumTitle title of album to be shown.
     * @param albumMusicsInfo list of all musics info which related to a album.
     */
    public static void addAlbum(String albumTitle, ArrayList<MP3Info> albumMusicsInfo) {
        centerPanel.getCenterPart().addAlbum(albumTitle,albumMusicsInfo);
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

    /**
     * This method work as a linker.
     * @return list of playlist titles.
     */
    public static ArrayList<String> getPlayistTitles(){
        return centerPanel.getCenterPart().getPlayistTitles();
    }

    /**
     * this method work as a linker
     * @return list of album titles.
     */
    public static ArrayList<String> getAlbumTitles(){
        return centerPanel.getCenterPart().getAlbumTitles();
    }
    public static PlaylistOptionLinker getAddingAndRemovingSongLinker(){
        return centerPanel.getCenterPart();
    }
    public static void setWestPanel(WestPanel changedWestPane)
    {
        westPanel=changedWestPane;
    }
    public static WestPanel getWestPanel()
    {
        return westPanel;

    }

}
