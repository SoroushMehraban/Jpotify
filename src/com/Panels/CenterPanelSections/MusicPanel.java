package com.Panels.CenterPanelSections;

import com.GUIFrame.GUIFrame;
import com.MP3.MP3Info;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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
    private MP3Info mp3Info;

    /**
     * class constructor
     * @param mp3Info information about mp3 we want to play
     * @param description description to show under the title.
     * @throws InvalidDataException
     * @throws IOException
     * @throws UnsupportedTagException
     */
    MusicPanel(MP3Info mp3Info, String description) throws InvalidDataException, IOException, UnsupportedTagException {
        this.mp3Info = mp3Info;//setting object's mp3 info, helping us for listener method.
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));//setting panel layout
        this.setBackground(new Color(23,23,23));//setting panel default background
        this.setPreferredSize(new Dimension(200,270));//setting panel's preferred size
        createMusicPanelListener();//creating panel listener

        showingImage = mp3Info.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);//making a specified size for image
        this.musicLabel = new JLabel(new ImageIcon(showingImage));//creating a label to show image

        this.title = new JLabel();//creating title label
        this.title.setForeground(new Color(219,219,219));//setting title color
        this.title.setText(mp3Info.getTitle());//setting title text

        String stringModified = description.replace("\n","<br/>");//fixing all \n with <br/> as a line breaker.
        description ="<html><p style=\"width:170px\">"+stringModified+"</p></html>";//creating a paragraph width which line breaks after that.


        this.description = new JLabel();//creating description label
        this.description.setText(description);//setting description text
        this.description.setForeground(new Color(120,120,120));//setting description color
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
            public void mousePressed(MouseEvent e) {
                GUIFrame.playSelectedMusic(mp3Info.getInputFileDirectory());
            }
            @Override
            public void mouseExited(MouseEvent e) {
                MusicPanel source = (MusicPanel)e.getSource();
                source.setBackground(new Color(23,23,23));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                MusicPanel source = (MusicPanel)e.getSource();
                source.setBackground(new Color(41,41,41));
            }
        });
    }
}
