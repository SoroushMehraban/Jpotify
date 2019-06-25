package com.Panels.GeneralPanels;

import com.Panels.WestPanelSections.WestPanelListeners.SongsPanelListener;

import javax.swing.*;
import java.awt.*;

public class EastPanel extends JPanel
{
    private static JPanel songsPanel;
    private static JLabel songsIcon;
    private static JLabel songsLabel;
    public EastPanel()
    {
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        this.setBackground(new Color(23,23,23));
        this.setPreferredSize(new Dimension(150,600));
        this.add(Box.createVerticalStrut(20));
        JLabel title=new JLabel("   Friend  Activity   ");
        title.setForeground(Color.WHITE);
        this.add(title);
        this.add(Box.createVerticalStrut(50));
        /*this.add(Box.createVerticalStrut(30));
        songsPanel=new JPanel();
        songsPanel.setBackground(new Color(23,23,23));
        songsPanel.setLayout(new BoxLayout(songsPanel,BoxLayout.LINE_AXIS));
        songsIcon=new JLabel();
        songsIcon.setIcon(setIconSize("Icons/Song-no-selected.png"));
        songsPanel.add(songsIcon);
        songsLabel=new JLabel(" Songs");
        //songsIcon.addMouseListener(new SongsPanelListener(songsIcon,songsLabel));
        //songsLabel.addMouseListener(new SongsPanelListener(songsIcon,songsLabel));
        songsLabel.setForeground(Color.WHITE);
        songsPanel.add(songsLabel);
        this.add(songsPanel);
        this.add(Box.createVerticalStrut(30));*/







    }
    public static ImageIcon setIconSize(String name) {
        ImageIcon output = new ImageIcon(name);
        Image newImage = output.getImage();
        Image newimg = newImage.getScaledInstance(21, 20, Image.SCALE_SMOOTH);
        output = new ImageIcon(newimg);
        return output;
    }




}
