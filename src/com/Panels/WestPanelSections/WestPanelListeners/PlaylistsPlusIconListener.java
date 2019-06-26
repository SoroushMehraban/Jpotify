package com.Panels.WestPanelSections.WestPanelListeners;

import com.GUIFrame.GUIFrame;
import com.Panels.GeneralPanels.WestPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlaylistsPlusIconListener extends MouseAdapter
{
    private JLabel icon;
    public PlaylistsPlusIconListener(JLabel icon)
    {
        this.icon=icon;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JTextField titleField = new JTextField(10);
        JTextField descriptionField = new JTextField(10);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Title:"));
        myPanel.add(titleField);
        myPanel.add(Box.createHorizontalStrut(50));
        myPanel.add(Box.createVerticalStrut(100));
        myPanel.add(new JLabel("Description:"));
        myPanel.add(descriptionField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please Enter Title and Description", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            GUIFrame.createPlayList(titleField.getText(),descriptionField.getText());
            GUIFrame.reload();
            }






    }


    @Override
    public void mouseEntered(MouseEvent e) {
        icon.setIcon(WestPanel.setIconSize("Icons/Plus.png",10));

    }

    @Override
    public void mouseExited(MouseEvent e) {
        icon.setIcon(WestPanel.setIconSize("Icons/Plus-no-select.PNG",10));

    }




}
