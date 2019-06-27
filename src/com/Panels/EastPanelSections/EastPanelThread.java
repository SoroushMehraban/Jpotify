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

public class EastPanelThread extends Thread
{
    @Override
    public void run()  {
        try
        {
            ServerSocket mainSocket=new ServerSocket(2019);
            Socket connectedSocket=mainSocket.accept();
            System.out.println("someone connected.");
            OutputStream serverSocketOutputStream=connectedSocket.getOutputStream();
            InputStream serverSocketInputStream=connectedSocket.getInputStream();
            PrintWriter serverSocketWriter=new PrintWriter(serverSocketOutputStream,true);
            Scanner serverSocketReader=new Scanner(serverSocketInputStream);
            serverSocketWriter.write("Hi,you are connected to this server.");
            System.out.println(serverSocketReader.nextLine());
            ObjectInputStream objectReader=new ObjectInputStream(serverSocketInputStream);
            GUIFrame recievedGUIFrameObject=(GUIFrame) objectReader.readObject();

            JPanel connectedUserPanel=new JPanel();//main panel
            connectedUserPanel.setLayout(new BoxLayout(connectedUserPanel,BoxLayout.PAGE_AXIS));
            connectedUserPanel.setBackground(new Color(23, 23, 23));
            JPanel userInformationPanel=new JPanel();
            userInformationPanel.setLayout(new BoxLayout(userInformationPanel,BoxLayout.LINE_AXIS));
            userInformationPanel.setBackground(new Color(23, 23, 23));
            JLabel connectedUserName=new JLabel(" "+recievedGUIFrameObject.getUserName());
            connectedUserName.setForeground(Color.WHITE);
            JLabel connectedUserIcon=new JLabel(WestPanel.setIconSize("Icons/User.PNG",20));
            userInformationPanel.add(connectedUserIcon);
            userInformationPanel.add(connectedUserName);
            connectedUserPanel.add(userInformationPanel);
            GUIFrame.getEastPanel().add(connectedUserPanel);





            //mainSocket.accept();

        }
        catch (Exception e)
        {
            //this.interrupt();
            System.err.println("(server socket)CAN NOT CONNECT.THERE IS A PROBLEM. ");

        }

    }
    private void startCummunicating()
    {



    }





}
