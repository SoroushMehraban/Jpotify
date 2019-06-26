package com.Panels.WestPanelSections.WestPanelListeners;

import com.GUIFrame.GUIFrame;
import com.Panels.GeneralPanels.WestPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
        icon.setIcon(WestPanel.setIconSize("Icons/Home.png",20));
        label.setFont(new Font("Serif", Font.BOLD, 16));

    }

    @Override
    public void mouseExited(MouseEvent e) {
        icon.setIcon(WestPanel.setIconSize("Icons/Home-no-select.png",20));
        label.setFont(font);

    }
}
