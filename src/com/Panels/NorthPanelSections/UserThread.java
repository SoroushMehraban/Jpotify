package com.Panels.NorthPanelSections;

import com.GUIFrame.GUIFrame;
import com.Panels.CenterPanelSections.SharedSongPanel;
import com.Panels.CenterPanelSections.SongPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class UserThread extends Thread {
    private OutputStream clientSocketOutputStream;
    private InputStream clientSocketInputStream;
    private PrintWriter clientSocketWriter;
    private Scanner clientSocketReader;
    private String songTitle;
    private String songArtist;
    private String previousTitleReceived;
    private String previousArtistReceived;
    private JPanel connectedServerPanel;
    private JLabel connectedServerName;
    private JPanel serverInformationPanel;
    private JLabel state;
    private ArrayList<SharedSongPanel> sharedSongPanels;
    private ArrayList<ConnectedUserPanel> connectedUserPanels;
    private boolean gettingSharedSongsList;
    private boolean requestMade;
    private int requestDownloadIndex;
    private String connectedUser;
    private String IPv4;


    UserThread(String IPv4) {
        //  requestDownloadIndex = -1; //default invalid index;
        this.IPv4 = IPv4;
        connectedUserPanels = new ArrayList<>();
    }

    String getConnectedUser() {
        return connectedUser;
    }

    String getIPv4() {
        return IPv4;
    }

    void setRequestMade(boolean requestMade) {
        this.requestMade = requestMade;
    }

    void setRequestDownloadIndex(int requestDownloadIndex) {
        this.requestDownloadIndex = requestDownloadIndex;
    }

    @Override
    public void run() {
        try {
            Socket clientSocket = new Socket(IPv4, 2019);//making a connection to server
            createIO(clientSocket);//creating IO streams.

            System.out.println("I am Client!");

            connectedUser = clientSocketReader.nextLine();//reading server user name
            clientSocketWriter.println("user Received");//telling server that we received the name
            System.out.println("I connect to:" + connectedUser);

            System.out.println("sending username...");
            clientSocketWriter.println(GUIFrame.getUsername());//sending server our username
            System.out.println("sent");

            if (clientSocketReader.nextLine().equals("user Received")) {//if server received our user
                System.out.println("getting connected username...");
                GUIFrame.addConnectedUserNameJCombobox(connectedUser);//setting connected user to show in JCombobox.
                System.out.println("Username connected setted");

                while (true) {
                    System.out.println("First of loop");
                    if (gettingSharedSongsList) {//if we suppose to get shared Songs.
                        createAndShowSharedSongs();
                    } else {
                        if (songTitle == null && songArtist == null) {
                            clientSocketWriter.println("nothingPlayed");
                            clientSocketWriter.println("nothingPlayed");
                            System.out.println("Default sent");
                        } else {
                            clientSocketWriter.println(songArtist);
                            System.out.println("Artist: " + songArtist);
                            clientSocketWriter.println(songTitle);
                        }
                        String firstInputSocket = clientSocketReader.nextLine();////expected to be song title
                        System.out.println("First input received:" + firstInputSocket);

                        if (firstInputSocket.equals("get Shared Songs")) {
                            for (SongPanel sharedSong : GUIFrame.getSharedSongs()) {
                                System.out.println("Sending a shared songs.......");
                                clientSocketWriter.println(sharedSong.getMp3Info().getTitle());
                                clientSocketWriter.println(sharedSong.getMp3Info().getArtist());
                                if (!clientSocketReader.nextLine().equals("song received"))
                                    System.err.println("an error occurred");

                            }
                            clientSocketWriter.println("completed !");
                            System.out.println("Done sending shared songs!");
                        }
                        /*else if(firstInputSocket.contains("index")){
                            clientSocketWriter.println("Index Received");//saying to other pear that received index
                            int index = Integer.parseInt(firstInputSocket.split(" ")[1]);
                            System.out.println("index received to send mp3:"+index);
                            String inputDirectory = GUIFrame.getSharedSongs().get(index).getMp3Info().getInputFileDirectory();

                            FileInputStream fis = new FileInputStream(inputDirectory);
                            System.out.println("sending mp3 file...");

                            int count;
                            while ((count = fis.read()) != -1) {
                                clientSocketOutputStream.write(count);
                            }
                            fis.close();
                            System.out.println("Sent!");
                            while(!clientSocketReader.nextLine().equals("downloaded")){
                                clientSocketOutputStream.flush();
                            }
                            //clientSocketOutputStream.write(-1);//for saying other pear it sent
                        }*/
                        else {
                            String secondInputSocket = clientSocketReader.nextLine();//expected to be song artist
                            JLabel titleLabel = new JLabel(firstInputSocket);
                            JLabel artistLabel = new JLabel(secondInputSocket);
                            if (!firstInputSocket.equals("nothingPlayed") && !firstInputSocket.equals("song received")) {
                                if (previousArtistReceived == null || (!previousTitleReceived.equals(firstInputSocket) && !previousArtistReceived.equals(secondInputSocket))) {
                                    previousArtistReceived = secondInputSocket;
                                    previousTitleReceived = firstInputSocket;

                                    ConnectedUserPanel connectedUserPanel = new ConnectedUserPanel(connectedUser, firstInputSocket, secondInputSocket);
                                    if (connectedUserPanels.size() > 0) {
                                        ConnectedUserPanel lastConnectedUserPanel = connectedUserPanels.get(connectedUserPanels.size() - 1);
                                        lastConnectedUserPanel.setStopped();//stop last one (changing playing label)
                                    }
                                    connectedUserPanels.add(connectedUserPanel);
                                    GUIFrame.getEastPanel().addToNorth(connectedUserPanel);
                                }

                            }
                            if (requestMade) {//if we made a request from one user to get shared songs
                                System.out.println("a request made");
                                clientSocketWriter.println("get Shared Songs");//sending a request
                                requestMade = false;
                                gettingSharedSongsList = true;
                            }
                        }
                    }
                    GUIFrame.reload();
                    Thread.sleep(2000);
                }
            }
        } catch (InterruptedException | NoSuchElementException e1) {
            System.err.println("socket ended");
            connectedUserPanels.get(0).setOffline();
            GUIFrame.reload();
        } catch (IOException e2) {
            System.err.println("socket connecting error");
        }
    }

    private void createAndShowSharedSongs() {
        System.out.println("Trying to get shared songs...");
        sharedSongPanels = new ArrayList<>();
        gettingSharedSongsList = false;
        String firstInput;
        String secondInput;
        BufferedImage defaultImage = null;
        try {
            defaultImage = ImageIO.read(new File("RadioSongs/defaultImage.png"));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading shared default image", "An Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
        System.out.println("image loaded");
        while (true) {
            firstInput = clientSocketReader.nextLine();//expect to be song title
            if (firstInput.equals("completed !"))
                break;
            System.out.println(firstInput);
            secondInput = clientSocketReader.nextLine();//expect to be song artist
            System.out.println(secondInput);
            clientSocketWriter.println("song received");
            SharedSongPanel newSharedSong = new SharedSongPanel(defaultImage, firstInput, secondInput, sharedSongPanels, connectedUser);
            sharedSongPanels.add(newSharedSong);
        }
        sharedSongPanels.remove(0);//it's invalid
        GUIFrame.showSharedSongs(sharedSongPanels);
    }

    /*private void downloadNewSharedSong() {
        gettingSharedSong = false;//changing to default for next time, until it is  set by our user.
        try {

            FileOutputStream fos = new FileOutputStream("SharedSongs/test.mp3");
            System.out.println("Downloading...");
            int count;
            while ((count = clientSocketInputStream.read()) != -1) {
                fos.write(count);
            }
            fos.close();
            System.out.println("downloaded!");
        } catch (FileNotFoundException e) {
            System.err.println("error creating output file");
        } catch (IOException e) {
            System.out.println("error writing output file");
        }
    }*/

    /**
     * This methods adds a panel in east to show connected user, it's made of:
     * 1)serverInformationPanel: a panel to show information about user and indicate that user is online or not:
     * 2)Songs that user play for last time(it's not set in this method)
     */
    private void createConnectedServerPanel() {
        connectedServerPanel = new JPanel();//main panel
        connectedServerPanel.setLayout(new BoxLayout(connectedServerPanel, BoxLayout.PAGE_AXIS));
        connectedServerPanel.setBackground(new Color(23, 23, 23));

        serverInformationPanel = new JPanel();
        serverInformationPanel.setBackground(new Color(23, 23, 23));
        serverInformationPanel.setLayout(new BoxLayout(serverInformationPanel, BoxLayout.LINE_AXIS));

        System.out.println("getting connected username...");
        GUIFrame.addConnectedUserNameJCombobox(connectedUser);//setting connected user to show in JCombobox.
        System.out.println("Username connected setted");
        connectedServerName = new JLabel(" " + connectedUser);
        connectedServerName.setForeground(Color.WHITE);

        JLabel connectedServerIcon = new JLabel(GUIFrame.setIconSize("Icons/User.PNG", 20));
        serverInformationPanel.add(connectedServerIcon);
        serverInformationPanel.add(connectedServerName);

        serverInformationPanel.add(Box.createHorizontalStrut(5));
        state = new JLabel();
        state.setIcon(GUIFrame.setIconSize("Icons/green.PNG", 10));
        serverInformationPanel.add(state);

        connectedServerPanel.add(serverInformationPanel);
        //GUIFrame.getEastPanel().addToNorth(connectedServerPanel);
        GUIFrame.reload();

    }

    private void createIO(Socket clientSocket) {
        try {
            clientSocketOutputStream = clientSocket.getOutputStream();
        } catch (IOException e) {
            System.err.println("error getting input stream");
        }
        try {
            clientSocketInputStream = clientSocket.getInputStream();
        } catch (IOException e) {
            System.err.println("error getting output stream");
        }
        clientSocketWriter = new PrintWriter(clientSocketOutputStream, true);
        clientSocketReader = new Scanner(clientSocketInputStream);
    }

    void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }
}
