package com.Panels.CenterPanelSections;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * this class is a panel which shows an album,playlist or a music based on user choices.
 * it has three part which located vertically in a boxLayout:
 * -picture: a picture for album,playlist or music
 * -title: title of album,playlist or music
 * -description: description for album,playlist or music.
 */
class MusicPanel extends JPanel{
    private Image showingImage;
    private JLabel musicLabel;
    private JLabel title;
    private JLabel description;

    /**
     * class constructor.
     * @param musicImage an which image which is showed at first.
     * @param title title of music,album or playlist
     * @param description description for music,album or playlist
     */
    public MusicPanel(BufferedImage musicImage, String title, String description) {
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));//setting panel layout
        this.setBackground(Color.getHSBColor(0, 0, 0.09f));//setting panel default background
        this.setPreferredSize(new Dimension(200,270));//setting panel's preferred size
        createMusicPanelListener();//creating panel listener

        showingImage = musicImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH);//making a specified size for image
        this.musicLabel = new JLabel(new ImageIcon(showingImage));//creating a label to show image

        this.title = new JLabel();//creating title label
        this.title.setForeground(Color.getHSBColor(0,0,0.86f));//setting title color
        this.title.setText(title);//setting title text

        String stringModified = description.replace("\n","<br/>");//fixing all \n with <br/> as a line breaker.
        description ="<html><p style=\"width:170px\">"+stringModified+"</p></html>";//creating a paragraph width which line breaks after that.


        this.description = new JLabel();//creating description label
        this.description.setText(description);//setting description text
        this.description.setForeground(Color.getHSBColor(0,0,0.47f));//setting description color
        //adding components to panel:
        this.add(this.musicLabel);
        this.add(this.title);
        this.add(this.description);
    }

    /**
     * this method creates a Mouse listener for music panel
     * it does two things:
     * -when mouse entered: it changes to a brighter color.
     * -when mouse exited: it backs to previous color it had.
     */
    private void createMusicPanelListener(){
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                MusicPanel source = (MusicPanel)e.getSource();
                source.setBackground(Color.getHSBColor(0, 0, 0.09f));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                MusicPanel source = (MusicPanel)e.getSource();
                source.setBackground(Color.getHSBColor(0, 0, 0.16f));
            }
        });
    }
}
