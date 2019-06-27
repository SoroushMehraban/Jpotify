package com.Panels.EastPanelSections;

import com.GUIFrame.GUIFrame;
import com.Panels.GeneralPanels.EastPanel;
import com.Panels.GeneralPanels.WestPanel;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class EastPanelServerThread extends Thread {
    private String songTitle;
    private String songArtist;

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
            serverSocketWriter.println("Hi,you are connected to this server.");
            System.out.println(serverSocketReader.nextLine());
            serverSocketWriter.println(GUIFrame.getUsername());
            //ObjectInputStream objectReader=new ObjectInputStream(serverSocketInputStream);
            //GUIFrame recievedGUIFrameObject=(GUIFrame) objectReader.readObject();


            JPanel connectedUserPanel = new JPanel();//main panel
            connectedUserPanel.setLayout(new BoxLayout(connectedUserPanel, BoxLayout.PAGE_AXIS));
            connectedUserPanel.setBackground(new Color(23, 23, 23));
            JPanel userInformationPanel = new JPanel();
            userInformationPanel.setLayout(new BoxLayout(userInformationPanel, BoxLayout.LINE_AXIS));
            userInformationPanel.setBackground(new Color(23, 23, 23));
            JLabel connectedUserName = new JLabel(" " + serverSocketReader.nextLine());
            connectedUserName.setForeground(Color.WHITE);
            JLabel connectedUserIcon = new JLabel(WestPanel.setIconSize("Icons/User.PNG", 20));
            userInformationPanel.add(connectedUserIcon);
            userInformationPanel.add(connectedUserName);
            connectedUserPanel.add(userInformationPanel);
            GUIFrame.getEastPanel().add(connectedUserPanel);

            while (true) {
                //System.out.println("here");
                if (songTitle != null && songArtist != null) {
                    serverSocketWriter.println(songTitle);
                    serverSocketWriter.println(songArtist);
                } else {
                    serverSocketWriter.println("nothingPlayed");
                    serverSocketWriter.println("nothingPlayed");
                }
                String serverSongName = serverSocketReader.nextLine();
                String serverSongArtist = serverSocketReader.nextLine();
               // System.out.println(serverSongArtist);
                JLabel titleLabel = new JLabel(serverSongName);
                JLabel artistLabel = new JLabel(serverSongArtist);
                if (serverSongName.equals("nothingPlayed") && serverSongArtist.equals("nothingPlayed")) {
                    continue;
                }
                if ((songTitle == null && songArtist == null) || (!songTitle.equals(serverSongName) && !songArtist.equals(serverSongArtist))) {
                    connectedUserPanel.removeAll();
                    connectedUserPanel.add(userInformationPanel);
                    connectedUserPanel.add(Box.createVerticalStrut(10));
                    connectedUserPanel.add(titleLabel);
                    connectedUserPanel.add(Box.createVerticalStrut(10));
                    connectedUserPanel.add(artistLabel);

                }
                    /*connectedServerPanel.repaint();
                    connectedServerPanel.revalidate();*/
                GUIFrame.reload();
                Thread.sleep(2000);
            }


            //mainSocket.accept();

        } catch (Exception e) {
            //this.interrupt();
            System.err.println("(server socket)CAN NOT CONNECT.THERE IS A PROBLEM. ");

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
