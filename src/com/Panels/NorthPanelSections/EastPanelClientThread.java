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
    private static ArrayList<UserThread> userThreads = new ArrayList<>();


    /**
     * this method send a request to given user to turn back all songs user has in shared songs.
     *
     * @param user user we want to get him/her his/her shared songs.
     */
    public void setShowSharedSongsReqest(String user) {
        for(UserThread userThread : userThreads)
            if(userThread.getConnectedUser().equals(user)) {
                userThread.setRequestMade(true);
                break;
            }
    }

    /**
     * This method set a request download index to user we want to download from
     * @param requestDownloadIndex index of song in shared song we want to download
     * @param connectedUser user we want to get Him song
     */
    public void setRequestDownloadIndex(int requestDownloadIndex, String connectedUser) {
        for (UserThread userThread : userThreads)
            if (userThread.getConnectedUser().equals(connectedUser)) {
                userThread.setRequestDownloadIndex(requestDownloadIndex);
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
            boolean canAdd = true;
            System.err.println("current threads:"+userThreads.size());
            for(UserThread existingThread : userThreads){
                if (existingThread.getIPv4().equals(userThread.getIPv4())){
                    canAdd = false;
                    JOptionPane.showMessageDialog(null, "You are already connected!","Warning!",JOptionPane.WARNING_MESSAGE);
                    break;
                }
            }
            if(canAdd) {
                userThreads.add(userThread);
                System.err.println();
                userThread.start();
            }
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
