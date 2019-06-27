package com.Panels.EastPanelSections;

import com.GUIFrame.GUIFrame;
import com.Panels.GeneralPanels.WestPanel;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class EastPanelServerThread extends Thread {
    private String songTitle;
    private String songArtist;
    private String previousTitleReceived;
    private String previousArtistReceived;
    private JPanel userInformationPanel;
    private JLabel state;
    private HashMap<String, Boolean> showSharedSongs;

    public EastPanelServerThread() {
        showSharedSongs = new HashMap<>();//this HashMap helps for each thread to send
    }

    public void setShowSharedSongs(String user) {
        showSharedSongs.put(user, true);
    }

    @Override
    public void run() {
        try {
            ServerSocket mainSocket = new ServerSocket(2019);
            Socket connectedSocket = mainSocket.accept();
            System.out.println("someone connected.");

            OutputStream serverSocketOutputStream = connectedSocket.getOutputStream();
            InputStream serverSocketInputStream = connectedSocket.getInputStream();
            PrintWriter serverSocketWriter = new PrintWriter(serverSocketOutputStream, true);
            Scanner serverSocketReader = new Scanner(serverSocketInputStream);

            System.out.println("sending username..");
            serverSocketWriter.println(GUIFrame.getUsername());
            System.out.println("sent");
            System.out.println("trying to receive...");

            if (serverSocketReader.nextLine().equals("user Received")) {
                System.out.println("client received my messaage");
                JPanel connectedUserPanel = new JPanel();//main panel
                connectedUserPanel.setLayout(new BoxLayout(connectedUserPanel, BoxLayout.PAGE_AXIS));
                connectedUserPanel.setBackground(new Color(23, 23, 23));
                userInformationPanel = new JPanel();
                userInformationPanel.setLayout(new BoxLayout(userInformationPanel, BoxLayout.LINE_AXIS));
                userInformationPanel.setBackground(new Color(23, 23, 23));

                System.out.println("getting connected username...");
                String connectedUser = serverSocketReader.nextLine();
                System.out.println("connected:" + connectedUser);
                serverSocketWriter.println("user Received");
                GUIFrame.addConnectedUserNameJCombobox(connectedUser);//setting connected user to show in JCombobox.
                JLabel connectedUserName = new JLabel(" " + connectedUser);
                System.out.println("Username connected setted");
                connectedUserName.setForeground(Color.WHITE);

                JLabel connectedUserIcon = new JLabel(WestPanel.setIconSize("Icons/User.PNG", 20));
                userInformationPanel.add(connectedUserIcon);
                userInformationPanel.add(connectedUserName);

                userInformationPanel.add(Box.createHorizontalStrut(5));
                state = new JLabel();
                state.setIcon(WestPanel.setIconSize("Icons/green.PNG", 10));
                userInformationPanel.add(state);

                connectedUserPanel.add(userInformationPanel);
                GUIFrame.getEastPanel().addToNorth(connectedUserPanel);
                GUIFrame.reload();
                while (true) {
                    System.out.println("start of loop");
                    if (songTitle != null && songArtist != null) {
                        serverSocketWriter.println(songTitle);
                        serverSocketWriter.println(songArtist);
                        serverSocketWriter.flush();
                    } else {
                        serverSocketWriter.println("nothingPlayed");
                        serverSocketWriter.println("nothingPlayed");
                        serverSocketWriter.flush();
                        System.out.println("Default sent");
                    }
                    String serverSongName = serverSocketReader.nextLine();
                    System.out.println(serverSongName);
                    String serverSongArtist = serverSocketReader.nextLine();
                    System.out.println(serverSongArtist);

                    JLabel titleLabel = new JLabel(serverSongName);
                    JLabel artistLabel = new JLabel(serverSongArtist);
                    if (serverSongName.equals("nothingPlayed") && serverSongArtist.equals("nothingPlayed")) {
                        System.out.println("sleeping 2s..");
                        Thread.sleep(2000);
                        System.out.println("Skipping...");
                        continue;
                    }
                    if (previousArtistReceived == null || (!previousTitleReceived.equals(serverSongName) && !previousArtistReceived.equals(serverSongArtist))) {
                        previousArtistReceived = serverSongArtist;
                        previousTitleReceived = serverSongName;

                        connectedUserPanel.removeAll();
                        connectedUserPanel.add(userInformationPanel);
                       // connectedUserPanel.add(Box.createVerticalStrut(10));
                        connectedUserPanel.add(titleLabel);
                        //connectedUserPanel.add(Box.createVerticalStrut(10));
                        connectedUserPanel.add(artistLabel);
                    }
                    //connectedUserPanel.repaint();
                    //connectedUserPanel.revalidate();
                    GUIFrame.reload();
                    Thread.sleep(2000);
                }


                //mainSocket.accept();
            }
        } catch (Exception e) {
            //this.interrupt();
            System.err.println("(server socket)CAN NOT CONNECT.THERE IS A PROBLEM. ");
            state.setIcon(WestPanel.setIconSize("Icons/red.PNG", 10));
            GUIFrame.reload();
        }
    }

    private void startCummunicating() {


    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }
}
