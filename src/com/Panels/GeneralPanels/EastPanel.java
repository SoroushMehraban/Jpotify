package com.Panels.GeneralPanels;

import com.Panels.WestPanelSections.WestPanelListeners.HomePanelListener;
import com.Panels.WestPanelSections.WestPanelListeners.SongsPanelListener;

import javax.swing.*;
import java.awt.*;

public class EastPanel extends JPanel
{
    private static JPanel friendActivityPanel;
    private static JLabel friendActivityLabel;
    private static JLabel friendActivityIcon;
    public EastPanel()
    {
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        this.setBackground(new Color(23,23,23));
        this.setPreferredSize(new Dimension(150,600));
        this.add(Box.createVerticalStrut(20));
        friendActivityPanel=new JPanel();
        friendActivityPanel.setBackground(new Color(23,23,23));
        friendActivityPanel.setLayout(new BoxLayout(friendActivityPanel,BoxLayout.LINE_AXIS));
        friendActivityIcon=new JLabel();
        friendActivityIcon.setIcon(WestPanel.setIconSize("Icons/Friend Activity.jpg",20));
        friendActivityLabel=new JLabel(" Friend Activity");
        friendActivityLabel.setForeground(Color.WHITE);
        friendActivityPanel.add(friendActivityIcon);
        friendActivityPanel.add(friendActivityLabel);
        this.add(friendActivityPanel);
        this.add(Box.createVerticalStrut(50));








    }







}
