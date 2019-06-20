package com.Panels.CenterPanelSections;

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
import java.util.HashMap;
import java.util.Random;

/**
 * Center part of CenterPanel.
 * this part shows user albums,playlist, or list of songs based on user choices.
 *
 * @author Soroush Mehraban & Morteza Damghani
 * @version 1.0
 */
public class CenterPart extends JPanel {
    private HashMap<String,ArrayList<SongPanel>> songs;
    private ArrayList<AlbumPanel> albumPanels;
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
        songs = new HashMap<>();//list of songs related with own albums.
        albumPanels = new ArrayList<>();//array list of albums panels.
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
        for(AlbumPanel albumPanel: albumPanels){
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
        state = State.HOME;
        //updating center part of center panel:
        this.repaint();
        this.revalidate();
    }
    /**
     * this method shows songs in center part of center panel.
     */
    private void showMusics(String albumTitle){
        this.removeAll();//removing all components.
        //initializing grids:
        int gridx = 0;
        int gridy = 0;
        //showing music panels:
        for(SongPanel songPanel: songs.get(albumTitle)){
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
        state = State.MUSIC;
        //updating center part of center panel:
        this.repaint();
        this.revalidate();
    }

    /**
     * this method add an album to songs HashMap if it's not exist
     * or replace songs in album with new songs which is selected.
     *
     * @param albumTitle title of album which is a key of HashMap
     * @param albumMusicsInfo list of songs info which has similar albums.
     * @param description description to be shown
     */
    public void addAlbum(String albumTitle, ArrayList<MP3Info> albumMusicsInfo, String description){
        if(!songs.containsKey(albumTitle)) {//if album is a new album
            songs.put(albumTitle, createSongPanels(albumMusicsInfo, description));//creating Music panels belongs to one album key
            AlbumPanel albumPanel = createAlbumPanel(albumMusicsInfo,description);//creating album panel(its listener is implemented in createAlbumPanel method)
            albumPanels.add(albumPanel);//adding created
            showHome();//showing home after created new album to show it's added.
        }
        else//if album added before we just replace its music with new one:
            songs.replace(albumTitle, createSongPanels(albumMusicsInfo, description));
    }

    /**
     *this method create an album panel which if user press it, it shows music panels related to this album.
     * @param albumMusicsInfo list of music infos has similar album name
     * @param description description to be show in album panel.
     * @return an album panel
     */
    private AlbumPanel createAlbumPanel(ArrayList<MP3Info> albumMusicsInfo,String description){
        //creating a random number to extract a random image for album:
        Random random = new Random();
        int randIndex = random.nextInt(1000) % albumMusicsInfo.size();

        MP3Info randomMusicInfo = albumMusicsInfo.get(randIndex);//a random music info
        AlbumPanel album = null;
        try {//creating an album panel with its listener
            BufferedImage albumImage = randomMusicInfo.getImage();//image extracted from random music in album
            album = new AlbumPanel(albumImage,randomMusicInfo.getAlbum(), description);//creating an album panel
            createAlbumListener(album, randomMusicInfo.getAlbum());//creating album panels listener
        } catch (InvalidDataException | IOException | UnsupportedTagException e) {
            JOptionPane.showMessageDialog(null, "Error reading mp3 file image");
        }
        return album;
    }

    /**
     * this method gives list of mp3infos and return list of music panels.
     * @param mp3Infos list of music infos.
     * @param description description to be show under each music.
     * @return list of music panels.
     */
    private ArrayList<SongPanel> createSongPanels(ArrayList<MP3Info> mp3Infos, String description){
        ArrayList<SongPanel> songPanels = new ArrayList<>();
        for(MP3Info mp3Info: mp3Infos ) {
            try {
                songPanels.add(new SongPanel(mp3Info, description));
            } catch (InvalidDataException | IOException | UnsupportedTagException e) {
                JOptionPane.showMessageDialog(null, "Error reading mp3 file image");
            }
        }
        return songPanels;
    }

    /**
     * creating album listener to do this things:
     * if mouse entered: it became brighter.
     * if mouse exited: it turn to its previous type.
     * if mouse pressed: it show songs related to this album.
     * @param album desired album to set this listener to it.
     * @param albumTitle title of album helps to show music panels related to it(with help of songs HashMap)
     */
    private void createAlbumListener(AlbumPanel album, String albumTitle){
        album.setAlbumListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                AlbumPanel source = (AlbumPanel)e.getSource();
                source.setBackground(new Color(23,23,23));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                showMusics(albumTitle);
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
     * getting state to indicate which state we are if we want to change that.
     * @return present state
     */
    public State getState() {
        return state;
    }
}
