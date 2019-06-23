package com.Panels.WestPanelSections.WestPanelListeners;

import com.GUIFrame.GUIFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class HomePanelListener extends MouseAdapter
{
    private JLabel icon;
    private JLabel label;
    private Font font;
    public HomePanelListener(JLabel icon,JLabel label)
    {
        this.icon=icon;
        this.label=label;
        font=label.getFont();

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        GUIFrame.showHome();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        ImageIcon selectedIcon=new ImageIcon("Icons/Home.png");
        icon.setIcon(selectedIcon);
        label.setFont(new Font("Serif", Font.BOLD, 16));

    }

    @Override
    public void mouseExited(MouseEvent e) {
        ImageIcon noSelectedIcon=new ImageIcon("Icons/Home-no-select.png");
        icon.setIcon(noSelectedIcon);
        label.setFont(font);

    }
}
