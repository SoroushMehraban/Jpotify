package com.Panels.GeneralPanels;

import com.Panels.NorthPanelSections.ConnectedUserPanel;
import com.Panels.WestPanelSections.WestPanelListeners.HomePanelListener;
import com.Panels.WestPanelSections.WestPanelListeners.SongsPanelListener;

import javax.swing.*;
import java.awt.*;

/**
 * this class handles the operations which are related to the east panel of the program.
 *
 * @author Morteza Damghani & Soroush Mehraban
 * @version 1.0
 */

public class EastPanel extends JPanel {
    private static JPanel friendActivityPanel;
    private static JLabel friendActivityLabel;
    private static JLabel friendActivityIcon;
    private JPanel northContainer;

    public EastPanel() {
        this.setLayout(new BorderLayout());

        this.setBackground(new Color(23, 23, 23));
        friendActivityPanel = new JPanel();
        friendActivityPanel.setBackground(new Color(23, 23, 23));
        friendActivityPanel.setLayout(new BoxLayout(friendActivityPanel, BoxLayout.LINE_AXIS));
        friendActivityIcon = new JLabel();
        friendActivityIcon.setIcon(WestPanel.setIconSize("Icons/Friend Activity.jpg", 20));
        friendActivityLabel = new JLabel(" Friend Activity");
        friendActivityLabel.setForeground(Color.WHITE);
        friendActivityPanel.add(friendActivityIcon);
        friendActivityPanel.add(friendActivityLabel);

        northContainer = new JPanel();
        northContainer.setLayout(new BoxLayout(northContainer, BoxLayout.PAGE_AXIS));
        northContainer.setOpaque(false);
        northContainer.add(friendActivityPanel, BorderLayout.NORTH);

        this.add(northContainer, BorderLayout.NORTH);
    }

    /**
     * This method simply add new ConnectedUserPanel to north of east panel/
     *
     * @param newConnectedUserPanel desired jpanel to add
     */
    public void addToNorth(ConnectedUserPanel newConnectedUserPanel) {
        northContainer.add(newConnectedUserPanel, 1);
        northContainer.add(Box.createVerticalStrut(5), 2);
    }
}
