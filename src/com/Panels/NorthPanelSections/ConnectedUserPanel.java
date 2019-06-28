package com.Panels.NorthPanelSections;

import com.GUIFrame.GUIFrame;

import javax.swing.*;
import java.awt.*;

public class ConnectedUserPanel extends JPanel {
    private String artistFullName;
    private String songFullTitle;
    private String username;
    private long stoppedTime;
    private JLabel state;
    private JLabel playing;

    ConnectedUserPanel(String username, String songTitle, String artist) {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setBackground(new Color(23, 23, 23));

        this.username = username;

        JPanel userInformationPanel = new JPanel();
        userInformationPanel.setBackground(new Color(23, 23, 23));
        userInformationPanel.setLayout(new BoxLayout(userInformationPanel, BoxLayout.LINE_AXIS));

        JPanel songInformationPanel = new JPanel();
        songInformationPanel.setBackground(new Color(23, 23, 23));
        songInformationPanel.setLayout(new BoxLayout(songInformationPanel, BoxLayout.LINE_AXIS));

        JPanel artistInformationPanel = new JPanel();
        artistInformationPanel.setBackground(new Color(23, 23, 23));
        artistInformationPanel.setLayout(new BoxLayout(artistInformationPanel, BoxLayout.LINE_AXIS));

        JLabel connectedClientName = new JLabel(" " + username);
        connectedClientName.setForeground(Color.WHITE);
        songFullTitle = songTitle;
        JLabel songNameLabel = new JLabel(" " + createShortenedText(songTitle));
        songNameLabel.setForeground(new Color(179,179,179));
        artistFullName = artist;
        JLabel artistNameLabel = new JLabel(" " + createShortenedText(artist));
        artistNameLabel.setForeground(new Color(179,179,179));

        JLabel connectedClientIcon = new JLabel(GUIFrame.setIconSize("Icons/User.PNG", 20));
        JLabel songIcon = new JLabel(GUIFrame.setIconSize("Icons/Song-selected.PNG", 15));
        JLabel artistIcon = new JLabel(GUIFrame.setIconSize("Icons/songArtist.PNG", 15));

        playing = new JLabel(GUIFrame.setIconSize("Icons/speaker.PNG", 15));

        state = new JLabel();
        state.setIcon(GUIFrame.setIconSize("Icons/green.PNG", 10));

        userInformationPanel.add(connectedClientIcon);
        userInformationPanel.add(connectedClientName);
        userInformationPanel.add(Box.createHorizontalStrut(5));
        userInformationPanel.add(state);

        songInformationPanel.add(songIcon);
        songInformationPanel.add(songNameLabel);
        songInformationPanel.add(Box.createHorizontalStrut(5));
        songInformationPanel.add(playing);

        artistInformationPanel.add(artistIcon);
        artistInformationPanel.add(artistNameLabel);

        this.add(userInformationPanel);
        this.add(Box.createVerticalStrut(3));
        this.add(songInformationPanel);
        this.add(Box.createVerticalStrut(3));
        this.add(artistInformationPanel);
    }
    public void setOnline(){
        state.setIcon(GUIFrame.setIconSize("Icons/green.PNG", 10));
    }
    void setOffline(){
        state.setIcon(GUIFrame.setIconSize("Icons/red.PNG", 10));
    }

    public String getUsername() {
        return username;
    }

    public String getArtistFullName() {
        return artistFullName;
    }

    public String getSongFullTitle() {
        return songFullTitle;
    }
    void setStopped(){
        playing.setIcon(null);//removing speaker icon
        playing.setForeground(new Color(179,179,179));
        stoppedTime = System.currentTimeMillis();
        Thread updateTime = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    long now = System.currentTimeMillis();
                    long diff = now - stoppedTime;

                    if(diff < 60000){//if it's less than a minute
                        playing.setText(diff/1000 + "S");
                    }
                    else if(diff < 60000 * 60)//if it's less than an hour
                        playing.setText(diff/(1000 * 60) + "M");
                    else if(diff < 6000 * 60 * 24)//if it's less than a day
                        playing.setText(diff/1000 * 60 * 60 + "H");
                    else
                        playing.setText("> day");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        System.err.println("connected thread interrupted");
                    }
                }
            }
        });
        updateTime.start();
    }

    private String createShortenedText(String text){
        if(text.length() <= 12)
            return text;
        else
            return text.substring(0,9) + "...";
    }
}
