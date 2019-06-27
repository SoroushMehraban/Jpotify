package com.Panels.NorthPanelSections;

import com.GUIFrame.GUIFrame;
import com.Panels.GeneralPanels.WestPanel;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class EastPanelClientThread extends Thread {

    @Override
    public void run() {
        JTextField hostNameField = new JTextField(10);
        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Host Name:"));
        myPanel.add(hostNameField);
        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please Enter a Host Name", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION)
        {
            try
            {

                //GUIFrame.getMainThread().interrupt();
                Socket clientSocket=new Socket(hostNameField.getText(),2019);
                OutputStream clientSocketOutputStream=clientSocket.getOutputStream();
                InputStream clientSocketInputStream=clientSocket.getInputStream();
                PrintWriter clientSocketWriter=new PrintWriter(clientSocketOutputStream,true);
                Scanner clientSocketReader=new Scanner(clientSocketInputStream);
                Scanner consolInput=new Scanner(System.in);
                System.out.println(clientSocketReader.nextLine());
                System.out.println("write a message for server:");
                clientSocketWriter.println(consolInput.nextLine());
                clientSocketWriter.println(GUIFrame.getUsername());

                JPanel connectedServerPanel=new JPanel();//main panel
                connectedServerPanel.setLayout(new BoxLayout(connectedServerPanel,BoxLayout.PAGE_AXIS));
                connectedServerPanel.setBackground(new Color(23, 23, 23));

                JPanel serverInformationPanel=new JPanel();
                serverInformationPanel.setLayout(new BoxLayout(serverInformationPanel,BoxLayout.LINE_AXIS));
                serverInformationPanel.setBackground(new Color(23, 23, 23));
                JLabel connectedServerName=new JLabel(" "+clientSocketReader.nextLine());
                connectedServerName.setForeground(Color.WHITE);
                JLabel connectedServerIcon=new JLabel(WestPanel.setIconSize("Icons/User.PNG",20));
                serverInformationPanel.add(connectedServerIcon);
                serverInformationPanel.add(connectedServerName);
                connectedServerPanel.add(serverInformationPanel);
                GUIFrame.getEastPanel().add(connectedServerPanel);

                //ObjectOutputStream objectWriter=new ObjectOutputStream(clientSocketOutputStream);
                //objectWriter.writeObject(GUIFrame.getGUIFrame());
                //ObjectInputStream objectReader=new ObjectInputStream(clientSocketInputStream);
                //GUIFrame recievedGUIFrameObject=(GUIFrame)objectReader.readObject();











                GUIFrame.reload();
            }
            catch(Exception e1)
            {
                System.err.println("(socket)CAN NOT CONNECT TO THE INPUT HOST NAME.");
            }
        }





    }







}
