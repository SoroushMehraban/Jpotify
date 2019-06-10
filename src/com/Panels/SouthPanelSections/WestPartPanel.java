package com.Panels.SouthPanelSections;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This inner class is about west part panel of com.Panels.GeneralPanels.SouthPanel for Jpotify.
 * it shows us 2 things: Song Name and Artist Name.
 *
 * @author Soroush Mehraban & Morteza Damghani
 * @version 1.0
 */
public class WestPartPanel extends JPanel {
    private JLabel songName;
    private JLabel artistName;
    private BufferedImage plusIcon;
    private JLabel plusLabel;

    /**
     * Class Constructor.
     * @throws IOException if opening plus icon failed.
     */
    public WestPartPanel() throws IOException {
        //setting layout to Box layout and Line axis:
        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        //setting background:
        this.setBackground(Color.getHSBColor(0, 0, 0.16f));
        //creating song name label:
        songName = new JLabel("Song Name");
        songName.setForeground(Color.getHSBColor(0, 0, 0.7f));
        //creating artist name label:
        artistName = new JLabel("Artist Name");
        artistName.setForeground(Color.getHSBColor(0, 0.09f, 0.36f));
        //creating plus label with icon:
        plusIcon = ImageIO.read(new File("Icons/Plus-no-select.png"));
        plusLabel = new JLabel(new ImageIcon(plusIcon));
        createplusLabelAction();
        //putting components in panel:
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5,5,0,0);
        constraints.gridx = 0;
        constraints.gridy = 0;
        this.add(songName, constraints);
        constraints.gridx = 1;
        constraints.gridy = 0;
        this.add(plusLabel,constraints);
        constraints.insets = new Insets(0,5,0,0);
        constraints.gridx = 0;
        constraints.gridy = 1;
        this.add(artistName,constraints);
    }
    /**
     * This method demonstrate what happens if mouse pressed,entered or exited from plus Icon beside Song name.
     * when mouse pressed: ...
     * when mouse entered: it made that icon look brighter.
     * when mouse exited: it changes to previous form.
     */
    private void createplusLabelAction(){
        plusLabel.addMouseListener(new MouseAdapter(
        ) {
                /*@Override
                public void mousePressed(MouseEvent e) {
                    changePlayLabel(pauseLabel);
                }*/

            @Override
            public void mouseEntered(MouseEvent e) {
                try {
                    plusIcon = ImageIO.read(new File("Icons/Plus.png"));
                    plusLabel.setIcon(new ImageIcon(plusIcon));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                try {
                    plusIcon = ImageIO.read(new File("Icons/Plus-no-select.png"));
                    plusLabel.setIcon(new ImageIcon(plusIcon));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });
    }
}