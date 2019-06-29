package com.Panels.WestPanelSections.WestPanelListeners;

import com.GUIFrame.GUIFrame;
import com.Panels.GeneralPanels.WestPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * playlist plus icon listener does this jobs:
 * -when mouse Clicked:
 * it shows a JOptionPane for user to enter playlist title and description
 * if user pressed ok after that, it creates one,else it doesn't create anything.
 * -when mouse Entered:
 * it updates playlist plus icon to become brighter
 * -when mouse Exited:
 * it updates playlist plus icon and turn to previous form.
 *
 * @author Morteza Damghani & Soroush Mehraban
 * @version 1.0
 */
public class PlaylistsPlusIconListener extends MouseAdapter {
    private JLabel icon;

    /**
     * Class constructor, only sets plus icon
     *
     * @param icon plus icon.
     */
    public PlaylistsPlusIconListener(JLabel icon) {
        this.icon = icon;
    }

    @Override
    public void mouseClicked(MouseEvent e) {//showing a JOptionPane to enter playlist title and description:
        JTextField titleField = new JTextField(10);
        JTextField descriptionField = new JTextField(10);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Title:"));
        myPanel.add(titleField);
        myPanel.add(Box.createHorizontalStrut(50));
        myPanel.add(Box.createVerticalStrut(100));
        myPanel.add(new JLabel("Description:"));
        myPanel.add(descriptionField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please Enter Title and Description", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {//if user pressed ok
            GUIFrame.createPlayList(titleField.getText(), descriptionField.getText());//creating new playlist
            GUIFrame.reload();
        }
    }


    @Override
    public void mouseEntered(MouseEvent e) {
        icon.setIcon(WestPanel.setIconSize("Icons/Plus.png", 10));

    }

    @Override
    public void mouseExited(MouseEvent e) {
        icon.setIcon(WestPanel.setIconSize("Icons/Plus-no-select.PNG", 10));

    }


}
