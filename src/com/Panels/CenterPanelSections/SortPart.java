package com.Panels.CenterPanelSections;


import javax.swing.*;
import java.awt.*;

/**
 * SortPart is a part of center panel.
 * When user opens a playlist, if that playlist consist 2 or more songs, sort box of this part appears
 * in the others parts, removeSortBox method called and no sorting box will show at top of center part.
 *
 *
 * @author Soroush Mehraban & Morteza damghani
 * @version 1.0
 */
public class SortPart extends JPanel {
    private JComboBox sortBox;

    /**
     * Class constructor, setting layout and background and creating a sort box.
     */
    public SortPart() {
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(23, 23, 23));//setting panel background

        sortBox = new JComboBox();//creating a JComboBox for user.
        sortBox.setPreferredSize(new Dimension(100, 25));//setting PreferredSize to show.
    }

    /**
     * when this method is called, it shows sort box at left of this panel.
     */
    void showSortBox() {
        if (this.getComponentCount() == 0)
            this.add(sortBox, BorderLayout.WEST);
    }

    /**
     * when this method is called, it removes sort box and nothing will show.
     */
    void removeSortBox() {
        this.removeAll();
    }
}
