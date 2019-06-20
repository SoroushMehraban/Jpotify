package com.Panels.CenterPanelSections;

import com.Interfaces.ShowSongsLinker;
import com.MP3.MP3Info;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Center part of CenterPanel.
 * this part shows user albums,playlist, or list of albumSongs based on user choices.
 *
 * @author Soroush Mehraban & Morteza Damghani
 * @version 1.0
 */
public class CenterPart extends JPanel implements ShowSongsLinker {
    private HashMap<String,AlbumPanel> albumPanels;
    private ArrayList<PlayListPanel> playListPanels;
    private State state;
    private GridBagConstraints constraints;

    /**
     * Class Constructor.
     */
    public CenterPart() {
        this.setLayout(new GridBagLayout());//setting panel layout
        constraints = new GridBagConstraints();//creating panel constraints to denote where components should located on.
        constraints.insets = new Insets(0,0,15,15);//denoting spaces between components.
        this.setBackground(new Color(23,23,23));//setting panel background

        albumPanels = new HashMap<>();//list of albumPanels.
        playListPanels = new ArrayList<>();

        //creating default playLists:
        try {
            BufferedImage favoriteSongsImage = ImageIO.read(new File("Images/FavoriteSong.png"));
            PlayListPanel favoriteSongs = new PlayListPanel(favoriteSongsImage,"Favorite Songs","Favorite albumSongs chosen by user");
            playListPanels.add(favoriteSongs);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading favorite albumSongs image");
        }
        try {
            BufferedImage sharedSongImage = ImageIO.read(new File("Images/SharedSongs.jpg"));
            PlayListPanel sharedSongs = new PlayListPanel(sharedSongImage,"Shared Songs","Shared albumSongs between users");
            playListPanels.add(sharedSongs);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading shared albumSongs image");
        }

    }

    /**
     * when this method calls, it shows albums in center part of Center panel.
     */
    public void showHome(){
        this.removeAll();//removing all components in center part
        //initializing grids:
        int gridx = 0;
        int gridy = 0;
        //creating album label to show at top of albums:
        JLabel albumLabel = new JLabel("Albums:");
        albumLabel.setForeground(new Color(219,219,219));
        constraints.gridy = gridy;
        constraints.gridx = gridx;
        this.add(albumLabel, constraints);
        gridy++;//going to next line
        //showing album panels:
        for(AlbumPanel albumPanel: albumPanels.values()){
            constraints.gridx = gridx;
            constraints.gridy = gridy;
            this.add(albumPanel, constraints);
            if(gridx < 3)
                gridx++;
            else{
                gridy++;
                gridx = 0;
            }
        }
        //creating playList label to show at top of playLists:
        gridx--;
        gridy++;
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        JLabel playListLabel = new JLabel("PlayLists:");
        playListLabel.setForeground(new Color(219,219,219));
        this.add(playListLabel, constraints);
        gridy++;
        for(PlayListPanel playListPanel: playListPanels){
            constraints.gridy = gridy;
            constraints.gridx = gridx;
            this.add(playListPanel, constraints);
            if(gridx < 3)
                gridx++;
            else{
                gridy++;
                gridx = 0;
            }
        }
        state = State.HOME;
        //updating center part of center panel:
        this.repaint();
        this.revalidate();
    }

    /**
     * this method shows given songPanels in center part of center panel.
     *
     * @param songPanels desired song panel to show.
     */
    @Override
    public void showSongs(HashSet<SongPanel> songPanels){
        this.removeAll();//removing all components.
        //initializing grids:
        int gridx = 0;
        int gridy = 0;
        //showing music panels:
        for(SongPanel songPanel: songPanels){
            constraints.gridx = gridx;
            constraints.gridy = gridy;
            this.add(songPanel, constraints);
            if(gridx < 3) {
                gridx++;
            }
            else{
                gridx = 0;
                gridy++;
            }
        }
        state = State.SONG;
        //updating center part of center panel:
        this.repaint();
        this.revalidate();
    }

    /**
     * this method add an album to albumSongs HashMap if it's not exist
     * or add new songs to existing album if given songs are new.
     *
     * @param albumTitle title of album which is a key of HashMap
     * @param albumMusicsInfo list of albumSongs info which has similar albums.
     * @param description description to be shown
     */
    public void addAlbum(String albumTitle, ArrayList<MP3Info> albumMusicsInfo, String description){
        if(!albumPanels.containsKey(albumTitle)) {//if album is a new album
            AlbumPanel albumPanel = createAlbumPanel(albumMusicsInfo,description);
            albumPanels.put(albumTitle, albumPanel);
            showHome();//showing home after created new album to show it's added.
        }
        else//if album added before we just add new songs
            albumPanels.get(albumTitle).addNewSongs(albumMusicsInfo,description);

        for(SongPanel songPanel : albumPanels.get(albumTitle).getSongPanels())//setting list of albumSongs in every SongPanel object so it knows album belongs to
            songPanel.setAlbumSongPanels(albumPanels.get(albumTitle).getSongPanels());//this helps us to know what we should play next.
    }

    /**
     *this method create an album panel which if user press it, it shows music panels related to this album.
     * it gives image and title from first mp3info in given ArrayList.
     *
     * @param albumMusicsInfo list of music infos has similar album name
     * @param description description to be show in album panel.
     * @return an album panel
     */
    private AlbumPanel createAlbumPanel(ArrayList<MP3Info> albumMusicsInfo,String description){
        MP3Info firstMP3Info = albumMusicsInfo.get(0);
        AlbumPanel album = null;
        try {//creating an album panel with its listener
            album = new AlbumPanel(firstMP3Info.getImage(),firstMP3Info.getTitle(),description,albumMusicsInfo,this);
        } catch (InvalidDataException | IOException | UnsupportedTagException e) {
            JOptionPane.showMessageDialog(null, "Error reading mp3 file image");
        }
        return album;
    }

    /**
     * getting state to indicate which state we are if we want to change that.
     * @return present state
     */
    public State getState() {
        return state;
    }
}
