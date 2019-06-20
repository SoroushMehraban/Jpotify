package com.Panels.CenterPanelSections;

import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

/**
 * Album panel which contains list of music which is chosen by user.
 *
 * @author Soroush Mehraban
 * @version 1.0
 */
class PlayListPanel extends MusicPanel {

    /**
     * Constructor which set information need to show in super class.
     *
     * @param image         image of panel to show at first in above.
     * @param title         title to show under the image.
     * @param description   description to show under the title.
     */
    PlayListPanel(BufferedImage image, String title, String description) {
        super(image, title, description);
    }

    /**
     * setting a listener for album which is given in parameters.
     * @param mouseListener a mouse listener to set
     */
    void setPlayListListener(MouseListener mouseListener){
        this.addMouseListener(mouseListener);
    }
}
