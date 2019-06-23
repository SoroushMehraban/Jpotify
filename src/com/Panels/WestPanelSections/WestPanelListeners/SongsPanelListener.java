package com.Panels.WestPanelSections.WestPanelListeners;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SongsPanelListener extends MouseAdapter
{

    private JLabel icon;
    private JLabel label;
    private Font font;
    public SongsPanelListener(JLabel icon,JLabel label)
    {
        this.icon=icon;
        this.label=label;
        font=label.getFont();

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }


    @Override
    public void mouseEntered(MouseEvent e) {
        ImageIcon selectedIcon=new ImageIcon("Icons/Song-selected.png");
        icon.setIcon(selectedIcon);
        label.setFont(new Font("Serif", Font.BOLD, 16));

    }

    @Override
    public void mouseExited(MouseEvent e) {
        ImageIcon noSelectedIcon=new ImageIcon("Icons/Song-no-selected.png");
        icon.setIcon(noSelectedIcon);
        label.setFont(font);

    }
}
