package com.Panels.SouthPanelSections;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

    /**
     * Class Constructor.
     */
    public ProgressPanel() {
        //setting layout to Box layout and Line axis:
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        //setting background:
        this.setBackground(new Color(41,41,41));
        //creating time labels:
        currentTimeLabel = new JLabel("00:00");
        currentTimeLabel.setForeground(new Color(179,179,179));
        totalTimeLabel = new JLabel("05:37");
        totalTimeLabel.setForeground(new Color(179,179,179));
        //customizing progress bar colors:
        UIManager.put("ProgressBar.background", new Color(92,84,84));
        UIManager.put("ProgressBar.foreground", new Color(179,179,179));
        //creating music player bar:
        musicPlayerBar = new JProgressBar();
        musicPlayerBar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        musicPlayerBar.setPreferredSize(new Dimension(430, 7));
        musicPlayerBar.setMaximumSize(musicPlayerBar.getPreferredSize());
        musicPlayerBar.setMinimumSize(musicPlayerBar.getPreferredSize());
        musicPlayerBar.setBackground(new Color(92,84,84));
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
    private void createMusicPlayerBarAction(){
        musicPlayerBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                musicPlayerBar.setValue((int) (e.getX() / ((double) musicPlayerBar.getWidth()) * 100));
                setMusicCurrentTime();
            }
        });
        musicPlayerBar.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                musicPlayerBar.setValue((int) (e.getX() / ((double) musicPlayerBar.getWidth()) * 100));
                setMusicCurrentTime();
            }
        });

    }

    /**
     * this method set current time to time which music player bar points on it.
     */
    private void setMusicCurrentTime(){
        String[] totalTimeString = totalTimeLabel.getText().split(":");
        int totalTime = (Integer.parseInt(totalTimeString[0]) * 60) + Integer.parseInt(totalTimeString[1]);
        int currentTime = (int)(totalTime * (musicPlayerBar.getValue()/100.0));
        int hour = currentTime / 60;
        int min = currentTime % 60;
        String currentTimeString = String.format("%02d:%02d",hour,min);
        currentTimeLabel.setText(currentTimeString);
    }
}
