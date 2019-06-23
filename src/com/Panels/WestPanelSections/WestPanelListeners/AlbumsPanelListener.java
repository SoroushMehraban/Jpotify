package com.Panels.WestPanelSections.WestPanelListeners;

import com.GUIFrame.GUIFrame;
import com.Panels.GeneralPanels.WestPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class AlbumsPanelListener extends MouseAdapter
{

    private JLabel icon;
    private JLabel label;
    private Font font;
    private JPanel albumsPanel;
    //private JPanel changedAlbumPanel;
    private JPanel westPanel;
    public AlbumsPanelListener(JLabel icon,JLabel label)
    {
        this.icon=icon;
        this.label=label;
        font=label.getFont();

    }

    @Override
    public void mouseClicked(MouseEvent e) {

        ArrayList<String> albumTitles=new ArrayList<>();
        albumTitles= GUIFrame.getAlbumTitles();
        albumsPanel=WestPanel.getAlbumsPanel();
        albumsPanel.setBackground(new Color(23,23,23));
        albumsPanel.setLayout(new BoxLayout(albumsPanel,BoxLayout.PAGE_AXIS));
        albumsPanel.add(Box.createVerticalStrut(10));
        //westPanel=WestPanel.getWestPanel();
        JLabel temp;
        for(int i=0;i<=albumTitles.size()-1;++i)
        {
            temp=new JLabel(albumTitles.get(i));
            temp.setForeground(Color.WHITE);
            albumsPanel.add(temp);
        }
        //westPanel.add(albumsPanel);
        WestPanel.setAlbumsPanel(albumsPanel);


    }



    @Override
    public void mouseEntered(MouseEvent e) {
        icon.setIcon(WestPanel.setIconSize("Icons/Album-selected.png"));
        label.setFont(new Font("Serif", Font.BOLD, 16));

    }

    @Override
    public void mouseExited(MouseEvent e) {
        icon.setIcon(WestPanel.setIconSize("Icons/Album-no-selected.png"));
        label.setFont(font);

    }
}
