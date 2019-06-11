package com.Panels.CenterPanelSections;

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
        this.setBackground(Color.getHSBColor(0, 0, 0.09f));//setting panel background
        musicPanels = new ArrayList<MusicPanel>();//list of music panel which every one of them has a picture,title and description
        //temporary part for testing it works true:
        try {
            BufferedImage image = ImageIO.read(new File("tempImage.jpg"));
            String description = "12345678912345678912345678912" + '\n' + "lkfmasdlfj";
            MusicPanel musicPanel = new MusicPanel(image,"Title",description);
            MusicPanel musicPanel2 = new MusicPanel(image,"Title2","description2");
            MusicPanel musicPanel3 = new MusicPanel(image,"Title3","description3");
            MusicPanel musicPanel4 = new MusicPanel(image,"Title4","description4");
            MusicPanel musicPanel5 = new MusicPanel(image,"Title5","description5");
            musicPanels.add(musicPanel);
            musicPanels.add(musicPanel2);
            musicPanels.add(musicPanel3);
            musicPanels.add(musicPanel4);
            musicPanels.add(musicPanel5);
            showList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method adds music panels to our center part.
     */
    public void showList(){
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

}
