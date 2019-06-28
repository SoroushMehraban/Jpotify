package com.Panels.NorthPanelSections;

import com.GUIFrame.GUIFrame;
import com.Panels.CenterPanelSections.SharedSongPanel;
import com.Panels.GeneralPanels.WestPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class EastPanelClientThread extends Thread {
    private boolean gettingSharedSongs;
    private int requestDownloadIndex;

    private ArrayList<UserThread> userThreads;
    EastPanelClientThread() {
        requestDownloadIndex = -1; //invalid index for checking in thread
        userThreads = new ArrayList<>();
    }

    /**
     * this method gives a user which we want to see user's playlist.
     * for doing so, it search for each userThread in userThreads and after finding, it sets a RequestMade.
     * @param user user we want to get him/her his/her shared songs.
     */
    public void setShowSharedSongs(String user) {
        for(UserThread userThread : userThreads){
            if(userThread.getConnectedUser().equals(user))
                userThread.setRequestMade(true);
        }
    }

    /**
     * This method set a request download index
     * @param requestDownloadIndex index of song in shared song we want to download
     * @param connectedUser user we want to get Him song
     */
    public void setRequestDownloadIndex(int requestDownloadIndex, String connectedUser) {
        this.requestDownloadIndex = requestDownloadIndex;
        for(UserThread userThread : userThreads)
            if(userThread.getConnectedUser().equals(connectedUser)){
                //TODO
                break;
            }
    }

    @Override
    public void run() {
        JTextField hostNameField = new JTextField(10);
        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Host Name:"));
        myPanel.add(hostNameField);
        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please Enter a Host Name", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            UserThread userThread = new UserThread(hostNameField.getText());
            userThreads.add(userThread);
            userThread.start();
        }
    }

    public void setSongTitle(String songTitle) {
        for(UserThread userThread : userThreads)
            userThread.setSongTitle(songTitle);
    }

    public void setSongArtist(String songArtist) {
        for(UserThread userThread : userThreads)
            userThread.setSongArtist(songArtist);
    }
}
