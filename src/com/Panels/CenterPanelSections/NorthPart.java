package com.Panels.CenterPanelSections;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * north part of center panel. it has this features:
 *-Search box: user can search musics here.
 *-login box: user can login here to make a connection with network.
 *
 * @author Soroush Mehraban & Morteza Damghani
 * @version 1.0
 */
public class NorthPart extends JPanel {
    private JTextField searchBox;
    private BufferedImage leftArrowNoSelectedImage;
    private BufferedImage leftArrowImage;
    private JLabel leftArrowLabel;

    /**
     * class constructor
     * @throws IOException if opening search box icon failed.
     */
    public NorthPart() throws IOException {
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        this.setBackground(new Color(23,23,23));

        leftArrowNoSelectedImage = ImageIO.read(new File("Icons/LeftArrow-no-select.png"));
        leftArrowImage = ImageIO.read(new File("Icons/LeftArrow.png"));
        leftArrowLabel = new JLabel(new ImageIcon(leftArrowNoSelectedImage));
        this.add(leftArrowLabel);
        this.add(Box.createHorizontalStrut(10));

        searchBox = new SearchBox(10);
        searchBox.setForeground(new Color(168,168,168));
        this.add(searchBox);
    }

    public BufferedImage getLeftArrowNoSelectedImage() {
        return leftArrowNoSelectedImage;
    }

    public BufferedImage getLeftArrowImage() {
        return leftArrowImage;
    }
    public void setLeftArrowListener(MouseListener mouseListener){
        leftArrowLabel.addMouseListener(mouseListener);
    }
    public void setLeftArrowIcon(BufferedImage image){
        leftArrowLabel.setIcon(new ImageIcon(image));
    }
}
