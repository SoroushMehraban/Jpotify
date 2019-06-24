package com.Panels.WestPanelSections.WestPanelListeners;

import com.GUIFrame.GUIFrame;
import com.Panels.GeneralPanels.WestPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static com.GUIFrame.GUIFrame.getWestPanel;
import static com.Panels.GeneralPanels.WestPanel.setIconSize;


public class AlbumsPanelListener extends MouseAdapter
{

    private JLabel icon;
    private JLabel label;
    private Font font;
    private JPanel albumsPanel;
    private int numberOfClicks;
    //private JPanel changedAlbumPanel;
    private JPanel westPanel;
    public AlbumsPanelListener(JLabel icon,JLabel label)
    {
        this.icon=icon;
        this.label=label;
        font=label.getFont();
        numberOfClicks=0;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        ++numberOfClicks;

        if(numberOfClicks%2==1)
        {
            ArrayList<String> albumTitles;
            albumTitles= GUIFrame.getAlbumTitles();
            albumsPanel=WestPanel.getAlbumsPanel();
            albumsPanel.setBackground(new Color(23,23,23));
            albumsPanel.setLayout(new BoxLayout(albumsPanel,BoxLayout.PAGE_AXIS));
            albumsPanel.add(Box.createVerticalStrut(10));
            JLabel temp;
            for(int i=0;i<=albumTitles.size()-1;++i)
            {
                temp=new JLabel(albumTitles.get(i));
                temp.setForeground(Color.WHITE);
                temp.addMouseListener(new AlbumsTitlesListener(temp));
                albumsPanel.add(temp);
                albumsPanel.add(Box.createVerticalStrut(10));

            }

            WestPanel.setAlbumsPanel(albumsPanel);
            GUIFrame.reload();

        }
        else
        {
            albumsPanel=WestPanel.getAlbumsPanel();
            albumsPanel.setLayout(new BoxLayout(albumsPanel,BoxLayout.LINE_AXIS));
           while (albumsPanel.getComponentCount()>=3)
           {
               albumsPanel.remove(2);
           }
            WestPanel.setAlbumsPanel(albumsPanel);
           GUIFrame.reload();

        }




    }



    @Override
    public void mouseEntered(MouseEvent e) {
        icon.setIcon(setIconSize("Icons/Album-selected.png"));
        label.setFont(new Font("Serif", Font.BOLD, 14));

    }

    @Override
    public void mouseExited(MouseEvent e) {
        icon.setIcon(setIconSize("Icons/Album-no-selected.png"));
        label.setFont(font);

    }
}
