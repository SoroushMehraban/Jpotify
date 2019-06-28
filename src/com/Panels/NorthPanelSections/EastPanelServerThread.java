package com.Panels.NorthPanelSections;

import com.GUIFrame.GUIFrame;
import com.Panels.CenterPanelSections.SongPanel;
import com.Panels.GeneralPanels.WestPanel;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class EastPanelServerThread extends Thread {
    private ArrayList<ServerThread> serverThreads;
    private int requestDownloadIndex;

    public EastPanelServerThread() {
        serverThreads = new ArrayList<>();
    }

    /**
     * This method put a user of showSharedSongs user to true state, so we request him to get his shared songs.
     *
     * @param user user we want to get him/her his/her shared songs.
     */
    public void setShowSharedSongs(String user) {
        for (ServerThread serverThread : serverThreads)
            if (serverThread.getConnectedUser().equals(user)) {
                serverThread.setRequestMade(true);
                break;
            }
    }

    /**
     * This method set a request download index
     *
     * @param requestDownloadIndex index of song in shared song we want to download
     * @param connectedUser        user we want to get Him song
     */
    public void setRequestDownloadIndex(int requestDownloadIndex, String connectedUser) {
        this.requestDownloadIndex = requestDownloadIndex;
        for (ServerThread serverThread : serverThreads)
            if (serverThread.getConnectedUser().equals(connectedUser)) {
                //TODO
                break;
            }
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Trying to connect..");
            ServerThread serverThread = new ServerThread();
            System.out.println("Connected!");
            serverThread.start();
            serverThreads.add(serverThread);
        }
    }

    public void setSongTitle(String songTitle) {
        for(ServerThread serverThread : serverThreads)
            serverThread.setSongTitle(songTitle);
    }

    public void setSongArtist(String songArtist) {
        for(ServerThread serverThread : serverThreads)
            serverThread.setSongArtist(songArtist);
    }
}
