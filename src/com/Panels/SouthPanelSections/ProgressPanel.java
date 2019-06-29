package com.Panels.SouthPanelSections;

import com.MP3.CustomPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * this class is about Music Player bar which located on south panel.
 * it has a ProgressBar which goes forward parallel with playing music.
 * and has a two label revealing current time and total time of music.
 *
 * @author Soroush Mehraban & Morteza Damghani
 * @version 1.0
 */
public class ProgressPanel extends JPanel {
    private JProgressBar musicPlayerBar;
    private JLabel currentTimeLabel;
    private JLabel totalTimeLabel;
    private CustomPlayer player;

    /**
     * Class Constructor.
     */
    public ProgressPanel() {
        //setting layout to Box layout and Line axis:
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        //setting background:
        this.setBackground(new Color(41, 41, 41));
        //creating time labels:
        currentTimeLabel = new JLabel("00:00");
        currentTimeLabel.setForeground(new Color(179, 179, 179));
        totalTimeLabel = new JLabel("00:00");
        totalTimeLabel.setForeground(new Color(179, 179, 179));
        //customizing progress bar colors:
        UIManager.put("ProgressBar.background", new Color(92, 84, 84));
        UIManager.put("ProgressBar.foreground", new Color(179, 179, 179));
        //creating music player bar:
        musicPlayerBar = new JProgressBar();
        musicPlayerBar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        musicPlayerBar.setPreferredSize(new Dimension(430, 7));
        musicPlayerBar.setMaximumSize(musicPlayerBar.getPreferredSize());
        musicPlayerBar.setMinimumSize(musicPlayerBar.getPreferredSize());
        musicPlayerBar.setBackground(new Color(92, 84, 84));
        //adding components to pannel:
        this.add(currentTimeLabel);
        this.add(Box.createHorizontalStrut(10));
        this.add(musicPlayerBar);
        createMusicPlayerBarAction();
        this.add(Box.createHorizontalStrut(10));
        this.add(totalTimeLabel);
    }

    /**
     * This method demonstrate what happens if mouse pressed or dragged on music player bar.
     * when mouse pressed: it changes music to time where user clicks.
     * when mouse dragged: it drags progress bar with mouse and after that,plays music from that point.
     */
    private void createMusicPlayerBarAction() {
        musicPlayerBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseAction(e);
            }
        });
        musicPlayerBar.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                mouseAction(e);
            }
        });

    }

    /**
     * this method update progressbar and do necessary actions.
     * if music is playing, it resume from given point chosen by user
     * else it resume only a moment to update where it is, then it become pause.
     * after all it set current time no matter playing or not.
     */
    private void mouseAction(MouseEvent e) {
        if (player != null) {
            musicPlayerBar.setValue((int) (e.getX() / ((double) musicPlayerBar.getWidth()) * 100));
            if (player.isPlaying())
                player.resume(e.getX() / ((double) musicPlayerBar.getWidth()));
            else {
                player.resume(e.getX() / ((double) musicPlayerBar.getWidth()));
                player.pause();
            }
            setMusicCurrentTime();
        }
    }

    /**
     * this method set current time to time which music player bar points on it.
     */
    private void setMusicCurrentTime() {
        int currentTime;
        try {
            currentTime = player.getCurrentSeconds();
        } catch (IOException e) {//if stream is closed, our method then do nothing
            return;
        }
        int min = currentTime / 60;
        int sec = currentTime % 60;
        String currentTimeString = String.format("%02d:%02d", min, sec);
        currentTimeLabel.setText(currentTimeString);
    }

    /**
     * this method called when a musicPanel clicked. it control the playing music by changing MusicPlayerBar.
     *
     * @param player player we want to control.
     */
    public void controlMusic(CustomPlayer player) {
        this.player = player;//setting player to control it in listeners
        int totalmin = player.getTotalSeconds() / 60;//getting music's  total minutes.
        int totalSec = player.getTotalSeconds() % 60;//getting music's total  seconds.
        String totalTimeString = String.format("%02d:%02d", totalmin, totalSec);//creating a formatted String to show in jlabel
        totalTimeLabel.setText(totalTimeString);//setting JLabel's text
        Thread t = new Thread(new Runnable() {//creating thread
            @Override
            public void run() {//creating thread to control player parallel with our program.
                while (true) {//this loop runs until music's fileInputStream closed (music finished).
                    if (player.isPlaying()) {//changing progressbar if music is playing
                        try {
                            musicPlayerBar.setValue((int) ((player.getCurrentSeconds() / (double) player.getTotalSeconds()) * 100));
                        } catch (IOException e) {//if music finished. our job is done and thread is going to be terminated.
                            break;
                        }
                    }
                    setMusicCurrentTime();
                    try {
                        Thread.sleep(500);//sleep like 0.5 sec to perform better
                    } catch (InterruptedException e) {
                        JOptionPane.showMessageDialog(null, "Error controlling mp3 file");
                    }
                }
            }
        });
        t.start();
    }
}
