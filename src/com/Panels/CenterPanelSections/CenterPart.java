package com.Panels.CenterPanelSections;

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

/**
 * Center part of CenterPanel.
 * this part shows user albums,playlist, or list of musics based on user choices.
 *
 * @author Soroush Mehraban & Morteza Damghani
 * @version 1.0
 */
public class CenterPart extends JPanel {
    private ArrayList<MusicPanel> musicPanels;
    private GridBagConstraints constraints;

    /**
     * Class Constructor.
     */
    public CenterPart() {
        this.setLayout(new GridBagLayout());//setting panel layout
        constraints = new GridBagConstraints();//creating panel constraints to denote where components should located on.
        constraints.insets = new Insets(0,0,15,15);//denoting spaces between components.
        this.setBackground(new Color(23,23,23));//setting panel background
        musicPanels = new ArrayList<>();//list of music panel which every one of them has a picture,title and description
        //temporary part for testing it works true:
        try {
            MP3Info mp3Info = new MP3Info("Soroush Tabarsi - Grey (Ft Seventh Soul) [128].mp3");
            String description = "a first line for description which is long and should go to next line" + '\n' + "line after \\n";
            addPanel(mp3Info.getImage(),mp3Info.getTitle(),description);
            addPanel(mp3Info.getImage(),mp3Info.getTitle(),description);
            addPanel(mp3Info.getImage(),mp3Info.getTitle(),description);
            addPanel(mp3Info.getImage(),mp3Info.getTitle(),description);
            addPanel(mp3Info.getImage(),mp3Info.getTitle(),description);
            addPanel(mp3Info.getImage(),mp3Info.getTitle(),description);
            addPanel(mp3Info.getImage(),mp3Info.getTitle(),description);
        } catch (IOException | UnsupportedTagException | InvalidDataException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method adds music panels to our center part.
     */
    private void showList(){
        this.removeAll();
        int gridx = 0;
        int gridy = 0;
        for(MusicPanel musicPanel: musicPanels){
            constraints.gridx = gridx;
            constraints.gridy = gridy;
            this.add(musicPanel, constraints);
            if(gridx < 3)
                gridx++;
            else{
                gridx = 0;
                gridy++;
            }
        }
//        GUIFrame.reload();
    }

    /**
     * add a given music panel to centerPanel(more details implements later)
     *
     * @param image image of music
     * @param title title of music
     * @param description description to be shown in panel
     */
    public void addPanel(BufferedImage image,String title, String description){
        MusicPanel musicPanel = new MusicPanel(image, title, description);//creating a desired music panel
        musicPanels.add(musicPanel);//adding given musicPanel to music panels.
        showList();//update our list to show given musicPanel.
    }
}
