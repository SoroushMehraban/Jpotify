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
 * this part shows user albums,playlist, or list of musics based on user choices.
 *
 * @author Soroush Mehraban & Morteza Damghani
 * @version 1.0
 */
public class CenterPart extends JPanel {
    private HashMap<String,ArrayList<MusicPanel>> musics;
    private ArrayList<MusicPanel> albumPanels;
    private GridBagConstraints constraints;

    /**
     * Class Constructor.
     */
    public CenterPart() {
        this.setLayout(new GridBagLayout());//setting panel layout
        constraints = new GridBagConstraints();//creating panel constraints to denote where components should located on.
        constraints.insets = new Insets(0,0,15,15);//denoting spaces between components.
        this.setBackground(new Color(23,23,23));//setting panel background
        musics = new HashMap<>();//list of musics related with own libraries.
        albumPanels = new ArrayList<>();//array list of albums to
    }

    /**
     * when this method calls, it shows albums in center part of Center panel.
     */
    private void showHome(){
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
        for(MusicPanel albumPanel: albumPanels){
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
        //updating center part of center panel:
        this.repaint();
        this.revalidate();
    }
    /**
     * this method shows musics in center part of center panel.
     */
    private void showMusics(String albumTitle){
        this.removeAll();//removing all components.
        //initializing grids:
        int gridx = 0;
        int gridy = 0;
        //showing music panels:
        for(MusicPanel musicPanel: musics.get(albumTitle)){
            constraints.gridx = gridx;
            constraints.gridy = gridy;
            this.add(musicPanel, constraints);
            if(gridx < 3) {
                gridx++;
            }
            else{
                gridx = 0;
                gridy++;
            }
        }
        //updating center part of center panel:
        this.repaint();
        this.revalidate();
    }

    /**
     * this method add an album to musics HashMap if it's not exist
     * or replace musics in album with new musics which is selected.
     *
     * @param albumTitle title of album which is a key of HashMap
     * @param albumMusicsInfo list of musics info which has similar albums.
     * @param description description to be shown
     */
    public void addAlbum(String albumTitle, ArrayList<MP3Info> albumMusicsInfo, String description){
        if(!musics.containsKey(albumTitle)) {//if album is a new album
            musics.put(albumTitle, createMusicPanels(albumMusicsInfo, description));//creating Music panels belongs to one album key
            MusicPanel albumPanel = createAlbumPanel(albumMusicsInfo,description);//creating album panel(its listener is implemented in createAlbumPanel method)
            albumPanels.add(albumPanel);//adding created
            showHome();//showing home after created new album to show it's added.
        }
        else//if album added before we just replace its music with new one:
            musics.replace(albumTitle, createMusicPanels(albumMusicsInfo, description));
    }

    /**
     *this method create an album panel which if user press it, it shows music panels related to this album.
     * @param albumMusicsInfo list of music infos has similar album name
     * @param description description to be show in album panel.
     * @return an album panel
     */
    private MusicPanel createAlbumPanel(ArrayList<MP3Info> albumMusicsInfo,String description){
        //creating a random number to extract a random image for album:
        Random random = new Random();
        int randIndex = random.nextInt(1000) % albumMusicsInfo.size();

        MP3Info randomMusicInfo = albumMusicsInfo.get(randIndex);//a random music info
        MusicPanel album = null;
        try {//creating an album panel with its listener
            BufferedImage albumImage = randomMusicInfo.getImage();//image extracted from random music in album
            album = new MusicPanel(albumImage,randomMusicInfo.getAlbum(), description);//creating an album panel
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
    private ArrayList<MusicPanel> createMusicPanels(ArrayList<MP3Info> mp3Infos, String description){
        ArrayList<MusicPanel> musicPanels = new ArrayList<>();
        for(MP3Info mp3Info: mp3Infos ) {
            try {
                musicPanels.add(new MusicPanel(mp3Info, description));
            } catch (InvalidDataException | IOException | UnsupportedTagException e) {
                JOptionPane.showMessageDialog(null, "Error reading mp3 file image");
            }
        }
        return musicPanels;
    }

    /**
     * album listener:
     * if mouse entered: it became brighter.
     * if mouse exited: it turn to its previous type.
     * @param album desired album to set this listener to it.
     * @param albumTitle title of album helps to show music panels related to it(with help of musics HashMap)
     */
    private void createAlbumListener(MusicPanel album, String albumTitle){
        album.setAlbumListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                MusicPanel source = (MusicPanel)e.getSource();
                source.setBackground(new Color(23,23,23));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("album pressed");
                showMusics(albumTitle);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                MusicPanel source = (MusicPanel)e.getSource();
                source.setBackground(new Color(41,41,41));
            }
        });
    }
}
