package com.Panels.WestPanelSections.WestPanelListeners;

import com.GUIFrame.GUIFrame;
import com.Panels.GeneralPanels.WestPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static com.Panels.GeneralPanels.WestPanel.setIconSize;

/**
 * album panel's listener does this jobs:
 * -when mouse Clicked:
 * if mouse clicked in odd times, it shows album titles and if user click on them, it show that album in center panel.
 * if mouse clicked in even times, it close album titles and only shows Album button.
 * -when mouse Entered:
 * it updates album image and become bolder.
 * -when mouse Exited:
 * it updates album image and turn to previous form.
 *
 * @author Morteza Damghani & Soroush Mehraban
 * @version 1.0
 */
public class AlbumsPanelListener extends MouseAdapter {

    private JLabel icon;
    private JLabel text;
    private Font font;
    private JPanel albumsPanel;//not necessary, it can be local(press alt + enter to correct it)
    private int numberOfClicks;

    /**
     * class Constructor, it sets given icon and text
     * and sets default font.
     *
     * @param icon icon of album panel.
     * @param text text of album panel which is " Albums"
     */
    public AlbumsPanelListener(JLabel icon, JLabel text) {
        this.icon = icon;
        this.text = text;
        font = text.getFont();
        numberOfClicks = 0;//not necessary, default value is 0
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        ++numberOfClicks;

        if (numberOfClicks % 2 == 1) {//if user click in a odd time(first,third,...):
            ArrayList<String> albumTitles = GUIFrame.getAlbumTitles();

            albumsPanel = WestPanel.getAlbumsPanel();
            albumsPanel.setLayout(new BoxLayout(albumsPanel, BoxLayout.PAGE_AXIS));
            albumsPanel.setBackground(new Color(23, 23, 23));//not necessary, we set this background in constructor of west panel
            albumsPanel.add(Box.createVerticalStrut(10));

            JLabel albumTitle;
            for (int i = 0; i <= albumTitles.size() - 1; ++i) {//searching album titles and adding to album panel of west panel
                albumTitle = new JLabel(albumTitles.get(i));//getting album title
                albumTitle.setForeground(Color.WHITE);//setting color of album title
                albumTitle.addMouseListener(new AlbumsTitlesListener(albumTitle));//adding listener
                albumsPanel.add(albumTitle);
                albumsPanel.add(Box.createVerticalStrut(10));//creating spaces between this album title and next one
            }

            WestPanel.setAlbumsPanel(albumsPanel);//not necessary, albumPanel contains address of WestPanel's albumPanel, so u don't need to set it again.
            GUIFrame.reload();//not necessary

        } else { //if user click Albums in a even time:
            albumsPanel = WestPanel.getAlbumsPanel();
            albumsPanel.setLayout(new BoxLayout(albumsPanel, BoxLayout.LINE_AXIS));//not necessary, we set this in west panel
            while (albumsPanel.getComponentCount() >= 3) {//removing
                albumsPanel.remove(2);
            }
            WestPanel.setAlbumsPanel(albumsPanel);//not necessary
            GUIFrame.reload();
        }
    }


    @Override
    public void mouseEntered(MouseEvent e) {
        icon.setIcon(setIconSize("Icons/Album-selected.png", 20));//updating album image
        text.setFont(new Font("Serif", Font.BOLD, 16));//turn to bolder

    }

    @Override
    public void mouseExited(MouseEvent e) {
        icon.setIcon(setIconSize("Icons/Album-no-selected.png", 20));//updating album image
        text.setFont(font);//turn to previous form

    }
}
