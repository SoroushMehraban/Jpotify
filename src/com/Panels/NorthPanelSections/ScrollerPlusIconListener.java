package com.Panels.NorthPanelSections;

import com.GUIFrame.GUIFrame;
import com.Panels.GeneralPanels.WestPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.Socket;

public class ScrollerPlusIconListener extends MouseAdapter
{

    @Override
    public void mouseClicked(MouseEvent e) {
        JTextField hostNameField = new JTextField(10);
        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("IP Address:"));
        myPanel.add(hostNameField);
        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please Enter a Host Name", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION)
        {
            try
            {
                Socket clientSocket=new Socket(hostNameField.getText(),2019);
            }
            catch(Exception e1)
            {

            }
        }





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


}
