package com.Panels.NorthPanelSections;

import com.GUIFrame.GUIFrame;
import com.Panels.GeneralPanels.WestPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ScrollerPlusIconListener extends MouseAdapter implements Runnable
{

    @Override
    public void mouseClicked(MouseEvent e) {
       this.run();

    }


    @Override
    public void mouseEntered(MouseEvent e) {
       // icon.setIcon(WestPanel.setIconSize("Icons/Song-selected.png"));
        //label.setFont(new Font("Serif", Font.BOLD, 16));

    }

    @Override
    public void mouseExited(MouseEvent e) {
        //icon.setIcon(WestPanel.setIconSize("Icons/Song-no-selected.png"));
        //label.setFont(font);

    }


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
                //ObjectOutputStream objectWriter=new ObjectOutputStream(clientSocketOutputStream);
                //objectWriter.writeObject(GUIFrame.getGUIFrame());
                //ObjectInputStream objectReader=new ObjectInputStream(clientSocketInputStream);
                //GUIFrame recievedGUIFrameObject=(GUIFrame)objectReader.readObject();







            }
            catch(Exception e1)
            {
                System.err.println("(socket)CAN NOT CONNECT TO THE INPUT HOST NAME.");
            }
        }





    }
}
