package com.Panels.GeneralPanels;

import com.MP3.CustomPlayer;
import com.Panels.SouthPanelSections.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * South Panel of program
 * at center of panel:
 * -user can play,pause music or shuffle between musics.
 * -in musicPlayerPaneL: it shows current time of music and total of time and user can drag music wherever he/she wants.
 *
 * @author Soroush Mehraban & Morteza Damghani
 * @version 1.0
 */
public class SouthPanel extends JPanel{
    private JProgressBar soundBar;
    private WestPartPanel westPart;
    private EastPartPanel eastPart;
    private JPanel centerPart;
    private ProgressPanel progressPanel;
    private PlayPanel playPanel;
    private CustomPlayer customPlayer;

    /**
     * Class Constructor
     * @throws IOException if reading PlayPanel icons failed.
     */
    public SouthPanel() throws IOException {
        //setting South Panel layout:
        this.setLayout(new BorderLayout());

        //setting background color of com.Panels.GeneralPanels.SouthPanel:
        this.setBackground(new Color(41,41,41));

        //-----------------------------------------------------------------------------
        //center part where PlayPanel and MusicPlayBar located on:
        centerPart = new JPanel();
        centerPart.setLayout(new BoxLayout(centerPart, BoxLayout.PAGE_AXIS));
        centerPart.setBackground(new Color(41,41,41));
        this.add(centerPart, BorderLayout.CENTER);

        centerPart.add(Box.createVerticalStrut(5));
        playPanel = new PlayPanel();//creating new PlayPanel
        centerPart.add(playPanel);//adding playPanel to center part of south panel

        progressPanel = new ProgressPanel();
        centerPart.add(progressPanel);//adding progressPanel to center part of south panel
        centerPart.add(Box.createVerticalStrut(5));
        //----------------------------------------------------------------------------

        westPart = new WestPartPanel();
        this.add(westPart,BorderLayout.WEST);

        eastPart = new EastPartPanel();
        this.add(eastPart,BorderLayout.EAST);
    }

    /**
     * this method runs when a musicPanel clicked.
     * it handle player by giving it to it's components.
     * @param directory directory of music
     */
    public void play(String directory) {
        if(customPlayer != null)//if previous music is playing
            customPlayer.close();
        customPlayer = new CustomPlayer(directory);
        playPanel.playMusic(customPlayer);
        progressPanel.controlMusic(customPlayer);
    }
}