package com.Panels.GeneralPanels;

import com.Interfaces.PlayControlLinker;
import com.MP3.CustomPlayer;
import com.Panels.CenterPanelSections.SongPanel;
import com.Panels.SouthPanelSections.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

/**
 * South Panel of program
 * at center of panel:
 * -user can play,pause music or shuffle between musics.
 * -in musicPlayerPaneL: it shows current time of music and total of time and user can drag music wherever he/she wants.
 *
 * @author Soroush Mehraban & Morteza Damghani
 * @version 1.0
 */
public class SouthPanel extends JPanel implements PlayControlLinker {
    private JProgressBar soundBar;
    private WestPartPanel westPart;
    private EastPartPanel eastPart;
    private JPanel centerPart;
    private ProgressPanel progressPanel;
    private PlayPanel playPanel;
    private CustomPlayer customPlayer;
    private SongPanel currentSongPanel;
    private ArrayList<SongPanel> currentAlbumSongPanels;
    private boolean newRunning;
    private boolean isRepeating;
    private boolean isShuffling;

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
        playPanel.setPlayControlLinker(this);
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
     * this method runs when a songPanel prssed.
     * it handle player by giving it to it's components.
     * @param songPanel panel of song we want to play.(help us to determine what is next)
     */
    public void play(SongPanel songPanel, HashSet<SongPanel> albumSongPanelsSet) {
        ArrayList<SongPanel> albumSongPanels = new ArrayList<>(albumSongPanelsSet);
        //setting current song and albums if we want to go forward and backward:
        currentSongPanel = songPanel;
        currentAlbumSongPanels = albumSongPanels;

        if(customPlayer != null) {//if previous music is playing
            customPlayer.close();
            newRunning = true;//this terminate previous thread which running a song.
        }
        Thread t = new Thread(new Runnable() {//creating a thread to run desired song
            @Override
            public void run() {
                newRunning = false;// this cause outer loop doesn't break.(if we changed newRunning above to break it)
                outer: for (int i = albumSongPanels.indexOf(songPanel); i < albumSongPanels.size()  ; i++) {
                    System.out.println("index played: "+i);//temporary to show it works correctly.
                    currentSongPanel = albumSongPanels.get(i);//changing current song panel to play next one
                    customPlayer = new CustomPlayer(currentSongPanel.getInputFileDirectory());
                    playPanel.playMusic(customPlayer);
                    progressPanel.controlMusic(customPlayer);
                    westPart.updateNames(songPanel.getInputFileDirectory());
                    while(!customPlayer.isComplete()) {//wait until song finish playing(end of playing).
                        if(newRunning)
                            break outer;
                    }
                    if(isRepeating)//if isRepeating is true(its button pressed) we minus our index so next ++ is become current music
                        i--;
                    if(isShuffling){//if IsShuffling is true(its button prssed):
                        Random random = new Random();
                        int randomIndex = random.nextInt(albumSongPanels.size()) % albumSongPanels.size() - 1;
                        //random index has -1 at end of that because loop first make ++ of that and then run loops
                        //if we didnt do that, if randomIndex become last index, after ++ in loop it ends the loop
                        i = randomIndex;
                    }
                }
            }
        });
        t.start();
    }

    @Override
    public void goForward() {
        if(currentAlbumSongPanels != null) {//if music is playing
            int indexOfNextSong = (currentAlbumSongPanels.indexOf(currentSongPanel) + 1) % currentAlbumSongPanels.size() ;
            SongPanel nextSong = currentAlbumSongPanels.get(indexOfNextSong);
            play(nextSong, new HashSet<>(currentAlbumSongPanels));
        }
    }

    @Override
    public void goBack() {
        if(currentAlbumSongPanels != null) {//if music is playing
            int indexOfPreviousSong = (currentAlbumSongPanels.indexOf(currentSongPanel) - 1);
            if(indexOfPreviousSong == -1)
                indexOfPreviousSong = currentAlbumSongPanels.size() -1;
            SongPanel previousSong = currentAlbumSongPanels.get(indexOfPreviousSong);
            play(previousSong, new HashSet<>(currentAlbumSongPanels));
        }
    }

    @Override
    public void setRepeat(boolean isRepeating) {
        this.isRepeating = isRepeating;
    }

    @Override
    public void setShuffle(boolean isShuffling) {
        this.isShuffling = isShuffling;
    }
}