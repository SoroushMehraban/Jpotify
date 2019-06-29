package com.Panels.CenterPanelSections;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

/**
 * this abstract class is a panel which could be an album,playlist or a song based on what wee need to be implement in subclasses.
 * it has three part which located vertically in a boxLayout:
 * -picture: a picture for album,playlist or music
 * -title: title of album,playlist or music
 * -description: description for album,playlist or music.
 *
 * @author Soroush Mehraban & Morteza Damghani
 * @version 1.0
 */
abstract class MusicPanel extends JPanel {
    private JLabel descriptionLabel;
    private JLabel imageLabel;
    private int imageScale;

    /**
     * a constructor to create a desired panel.
     *
     * @param image       image of panel to show at first in above.
     * @param title       title to show under the image.
     * @param description description to show under the title.
     */
    MusicPanel(BufferedImage image, String title, String description) {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));//setting panel layout
        this.setBackground(new Color(23, 23, 23));//setting panel default background
        this.setPreferredSize(new Dimension(170, 230));//setting panel's preferred size

        imageScale = 170;
        Image showingImage = image.getScaledInstance(170, 170, Image.SCALE_SMOOTH);//making a specified size for image
        imageLabel = new JLabel(new ImageIcon(showingImage));//creating a label to show image

        JLabel titleLabel = new JLabel();//creating title label
        titleLabel.setText(title);//setting title text
        titleLabel.setForeground(new Color(219, 219, 219));//setting title color

        descriptionLabel = new JLabel();//creating description label
        String stringModified = description.replace("\n", "<br/>");//fixing all \n with <br/> as a line breaker.
        description = "<html><p style=\"width:135px\">" + stringModified + "</p></html>";//creating a paragraph width which line breaks after that.
        descriptionLabel.setText(description);//setting description text
        descriptionLabel.setForeground(new Color(120, 120, 120));//setting description color

        //adding components to panel:
        this.add(imageLabel);
        this.add(titleLabel);
        this.add(descriptionLabel);

    }

    JLabel getDescriptionLabel() {
        return descriptionLabel;
    }

    JLabel getImageLabel() {
        return imageLabel;
    }

    int getImageScale() {
        return imageScale;
    }
}
