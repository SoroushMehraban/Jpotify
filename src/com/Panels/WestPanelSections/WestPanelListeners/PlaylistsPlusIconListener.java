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
        //GUIFrame.addSongToPlayList();

    }


    @Override
    public void mouseEntered(MouseEvent e) {
        icon.setIcon(WestPanel.setPlusIconSize("Icons/Plus.png"));

    }

    @Override
    public void mouseExited(MouseEvent e) {
        icon.setIcon(WestPanel.setPlusIconSize("Icons/Plus-no-select.PNG"));

    }




}
