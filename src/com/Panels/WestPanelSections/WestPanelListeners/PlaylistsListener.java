package com.Panels.WestPanelSections.WestPanelListeners;

import com.GUIFrame.GUIFrame;
import com.Panels.GeneralPanels.WestPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PlaylistsListener extends MouseAdapter
{
    private JLabel label;
    private JLabel icon;
    private Font font;
    private JPanel playlistsPanel;
    private int numberOfClicks;
    public PlaylistsListener(JLabel icon, JLabel label)
    {
        this.icon=icon;
        this.label=label;
        font=label.getFont();

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        ++numberOfClicks;

        if(numberOfClicks%2==1)
        {
            ArrayList<String> playlistsTitles;
            playlistsTitles= GUIFrame.getPlayistTitles();
            playlistsPanel=WestPanel.getPlayListsPanel();
            playlistsPanel.setBackground(new Color(23,23,23));
            playlistsPanel.setLayout(new BoxLayout(playlistsPanel,BoxLayout.PAGE_AXIS));
            playlistsPanel.add(Box.createVerticalStrut(10));
            JLabel temp;
            for(int i=0;i<=playlistsTitles.size()-1;++i)
            {
                temp=new JLabel(playlistsTitles.get(i));
                temp.setForeground(Color.WHITE);
                temp.addMouseListener(new PlaylistsTitlesListener(temp));
                playlistsPanel.add(temp);
                playlistsPanel.add(Box.createVerticalStrut(10));

            }

            WestPanel.setPlayListsPanel(playlistsPanel);
            GUIFrame.reload();

        }
        else
        {
            playlistsPanel=WestPanel.getPlayListsPanel();
            playlistsPanel.setLayout(new BoxLayout(playlistsPanel,BoxLayout.LINE_AXIS));
            while (playlistsPanel.getComponentCount()>=4)
            {
                playlistsPanel.remove(3);
            }
            WestPanel.setPlayListsPanel(playlistsPanel);
            GUIFrame.reload();

        }




    }


    @Override
    public void mouseEntered(MouseEvent e) {
        icon.setIcon(WestPanel.setIconSize("Icons/p-selected.png",20));
        label.setFont(new Font("Serif", Font.BOLD, 16));

    }

    @Override
    public void mouseExited(MouseEvent e) {
        icon.setIcon(WestPanel.setIconSize("Icons/p-no-selected.png",20));
        label.setFont(font);

    }



}
