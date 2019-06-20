package com.Panels.CenterPanelSections;

import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

class AlbumPanel extends MusicPanel {

    /**
     * Constructor which set information need to show in super class and set a listener for album which is given in parameters.
     *
     * @param image         image of panel to show at first in above.
     * @param title         title to show under the image.
     * @param description   description to show under the title.
     */
    AlbumPanel(BufferedImage image, String title, String description) {
        super(image, title, description);
    }

    void setAlbumListener(MouseListener mouseListener){
        this.addMouseListener(mouseListener);
    }
}
