package com.Panels.WestPanelSections.WestPanelListeners;

import com.GUIFrame.GUIFrame;
import com.Panels.GeneralPanels.WestPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * playlist panel's listener does this jobs:
 * -when mouse Clicked:
 * if mouse clicked in odd times, it shows playlist titles and if user click on them, it show that playlist in center panel.
 * if mouse clicked in even times, it close playlist titles and only shows playlist button.
 * -when mouse Entered:
 * it updates playlist image and become bolder.
 * -when mouse Exited:
 * it updates playlist image and turn to previous form.
 *
 * @author Morteza Damghani & Soroush Mehraban
 * @version 1.0
 */
public class PlaylistsListener extends MouseAdapter {

    private JLabel textLabel;
    private JLabel icon;
    private Font font;
    private JPanel playlistsPanel;//not necessary(press alt + enter after deleting this comment)
    private int numberOfClicks;

    /**
     * Class constructor.
     * setting given parameters and default font.
     *
     * @param icon      icon of playlist
     * @param textLabel a text label which it is : " Playlists  "
     */
    public PlaylistsListener(JLabel icon, JLabel textLabel) {
        this.icon = icon;
        this.textLabel = textLabel;
        font = textLabel.getFont();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        ++numberOfClicks;

        if (numberOfClicks % 2 == 1) {//if it's clicked in odd times:
            ArrayList<String> playlistTitles = GUIFrame.getPlayistTitles();
            playlistsPanel = WestPanel.getPlayListsPanel();
            playlistsPanel.setBackground(new Color(23, 23, 23));//not necessary, it set in west panel
            playlistsPanel.setLayout(new BoxLayout(playlistsPanel, BoxLayout.PAGE_AXIS));
            playlistsPanel.add(Box.createVerticalStrut(10));

            JLabel playlistTitle;
            for (int i = 0; i <= playlistTitles.size() - 1; ++i) {//creating playlist title labels
                playlistTitle = new JLabel(playlistTitles.get(i));
                playlistTitle.setForeground(Color.WHITE);
                playlistTitle.addMouseListener(new PlaylistsTitlesListener(playlistTitle));//adding listener as a UI
                playlistsPanel.add(playlistTitle);
                playlistsPanel.add(Box.createVerticalStrut(10));
            }

            WestPanel.setPlayListsPanel(playlistsPanel);//not necessary
            GUIFrame.reload();//not necessary

        } else {
            playlistsPanel = WestPanel.getPlayListsPanel();
            playlistsPanel.setLayout(new BoxLayout(playlistsPanel, BoxLayout.LINE_AXIS));
            while (playlistsPanel.getComponentCount() >= 4) {//removing playlist titles
                playlistsPanel.remove(3);
            }
            WestPanel.setPlayListsPanel(playlistsPanel);//not necessary
            GUIFrame.reload();//not necessary
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        icon.setIcon(WestPanel.setIconSize("Icons/p-selected.png", 20));
        textLabel.setFont(new Font("Serif", Font.BOLD, 16));

    }

    @Override
    public void mouseExited(MouseEvent e) {
        icon.setIcon(WestPanel.setIconSize("Icons/p-no-selected.png", 20));
        textLabel.setFont(font);
    }

}
