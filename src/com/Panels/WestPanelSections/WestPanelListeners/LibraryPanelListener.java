package com.Panels.WestPanelSections.WestPanelListeners;

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
        ImageIcon selectedIcon=new ImageIcon("Icons/Library.PNG");
        icon.setIcon(selectedIcon);
        label.setFont(new Font("Serif", Font.BOLD, 16));
        ImageIcon selectedPlusIcon=new ImageIcon("Icons/Plus.png");
        plusIcon.setIcon(selectedPlusIcon);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        ImageIcon noSelectedIcon=new ImageIcon("Icons/Library-no-select.PNG");
        icon.setIcon(noSelectedIcon);
        label.setFont(font);
        ImageIcon noSelectedPlusIcon=new ImageIcon("Icons/Plus-no-select.png");
        plusIcon.setIcon(noSelectedPlusIcon);

    }
}
