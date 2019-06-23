package com.Panels.WestPanelSections.WestPanelListeners;

import com.Panels.GeneralPanels.WestPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class LibraryPanelListener extends MouseAdapter
{

    private JLabel icon;
    private JLabel label;
    private JLabel plusIcon;
    private Font font;
    public LibraryPanelListener(JLabel icon,JLabel label,JLabel plusIcon)
    {
        this.icon=icon;
        this.label=label;
        this.plusIcon=plusIcon;
        font=label.getFont();

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }


    @Override
    public void mouseEntered(MouseEvent e) {
        icon.setIcon(WestPanel.setIconSize("Icons/Library.PNG"));
        label.setFont(new Font("Serif", Font.BOLD, 16));
        plusIcon.setIcon(WestPanel.setPlusIconSize("Icons/Plus.png"));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        icon.setIcon(WestPanel.setIconSize("Icons/Library-no-select.PNG"));
        label.setFont(font);
        plusIcon.setIcon(WestPanel.setPlusIconSize("Icons/Plus-no-select.png"));

    }
}
