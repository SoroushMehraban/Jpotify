package com.Panels.CenterPanelSections;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Search Box of program which located at the top of the windows.
 * this feature helps user to find a specific music which he/she is looking for.
 *
 * @author Soroush Mehraban & Morteza Damghani
 * @version 1.0
 */
class SearchBox extends JTextField {
    private BufferedImage searchImage;
    private String defaultText;
    /**
     * Class Constructor
     * @param size size of search box.
     * @throws IOException if opening search icon failed.
     */
    public SearchBox(int size) throws IOException {
        super(size);//setting size of search box
        defaultText = "Search...";//setting default text
        searchImage = ImageIO.read(new File("Icons/Search-no-select.png"));//loading search icon
        setOpaque(false);//this remove some pixels behind the borders
        this.setText(defaultText);//setting default text to show at first
        this.setFocusable(false);//at default,user can not type on it.

    }

    /**
     * make component look like a search box!
     * this overridden method adds search icon at left of it and make a oval border out of it.
     * @param g a graphic which is going to be set.
     */
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());//setting search box background which is white(JTextfield background)
        g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 50);//make a oval border.
        g.drawImage(searchImage, 2,2,15,15,this);//draw search icon at the left.
        this.setMargin(new Insets(2,15,2,2));//setting start pointer of writing text
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {//if mouse Entered,user can write on it.
                SearchBox source = (SearchBox)e.getComponent();
                source.setFocusable(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {//if mouse exited, user can not write anymore(should be changed!)
                SearchBox source = (SearchBox)e.getComponent();
                if(source.getText().equals(""))//if user didn't write anything, we set the default search box text.
                    source.setText(defaultText);
                source.setFocusable(false);
            }
        });
        this.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {//what happens if get focused.
                JTextField source = (JTextField)e.getComponent();
                if(source.getText().equals(defaultText))//if default text is there, our program clearing it.
                    source.setText("");
                source.removeFocusListener(this);
            }
        });
        super.paintComponent(g);

    }

    /**
     * this method paints search box border to be look like oval shape
     * @param g graphic which is going to be set.
     */
    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(getForeground());
        g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 50);
    }
}