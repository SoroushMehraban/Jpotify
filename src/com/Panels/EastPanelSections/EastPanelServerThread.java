package com.Panels.EastPanelSections;

import com.GUIFrame.GUIFrame;
import com.Panels.CenterPanelSections.SongPanel;
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
    private int requestDownloadIndex;
    private String friendUser;

    public EastPanelServerThread() {
        showSharedSongs = new HashMap<>();//this HashMap helps for each thread to send
        requestDownloadIndex = -1; //invalid index for checking in thread
    }

    /**
     * This method put a user of showSharedSongs user to true state, so we request him to get his shared songs.
     * @param user user we want to get him/her his/her shared songs.
     */
    public void setShowSharedSongs(String user) {
        showSharedSongs.put(user, true);
    }

    /**
     * This method set a request download index
     * @param requestDownloadIndex index of song in shared song we want to download
     * @param friendUser user we want to get Him song
     */
    public void setRequestDownloadIndex(int requestDownloadIndex, String friendUser) {
        this.requestDownloadIndex = requestDownloadIndex;
        this.friendUser = friendUser;
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
                    String firstInputSocket = serverSocketReader.nextLine();
                    System.out.println("First input received:"+firstInputSocket);

                    if(firstInputSocket.equals("get Shared Songs")){
                        for(SongPanel sharedSong : GUIFrame.getSharedSongs()){
                            System.out.println("Sending a shared songs.......");
                            serverSocketWriter.println(sharedSong.getMp3Info().getTitle());
                            serverSocketWriter.println(sharedSong.getMp3Info().getArtist());
                            if(!serverSocketReader.nextLine().equals("song received"))
                                System.err.println("an error occurred");

                        }
                        serverSocketWriter.println("completed !");
                        System.out.println("Done sending shared songs!");
                    }
                    else {
                        String SecondInputSocket = serverSocketReader.nextLine();
                        System.out.println(SecondInputSocket);

                        JLabel titleLabel = new JLabel(firstInputSocket);
                        JLabel artistLabel = new JLabel(SecondInputSocket);
                        if (!firstInputSocket.equals("nothingPlayed") && !SecondInputSocket.equals("nothingPlayed")) {
                            if (previousArtistReceived == null || (!previousTitleReceived.equals(firstInputSocket) && !previousArtistReceived.equals(SecondInputSocket))) {
                                previousArtistReceived = SecondInputSocket;
                                previousTitleReceived = firstInputSocket;

                                connectedUserPanel.removeAll();
                                connectedUserPanel.add(userInformationPanel);
                                // connectedUserPanel.add(Box.createVerticalStrut(10));
                                connectedUserPanel.add(titleLabel);
                                //connectedUserPanel.add(Box.createVerticalStrut(10));
                                connectedUserPanel.add(artistLabel);
                            }
                        }
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
