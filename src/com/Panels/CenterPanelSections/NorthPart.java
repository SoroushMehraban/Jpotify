package com.Panels.CenterPanelSections;

import com.Interfaces.SearchLinker;
import com.Panels.NorthPanelSections.ScrollerPlusIconListener;
import com.jtattoo.plaf.BaseComboBoxUI;
import com.jtattoo.plaf.hifi.HiFiComboBoxUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private BufferedImage plusIcon;
    private JLabel plusLabel;
    /**
     * class constructor
     * @param searchLinker a linker helps to searchBox search in centerPart.
     * @throws IOException if opening search box icon failed.
     */
    public NorthPart(SearchLinker searchLinker) throws IOException {
        //setting north part layout:
        this.setLayout(new BorderLayout());
        //setting background color:
        this.setBackground(new Color(10, 10, 10));

        searchBox = new SearchBox(10,searchLinker);//creating searchbox
        searchBox.setForeground(new Color(168,168,168));//setting search box text color
        this.add(searchBox,BorderLayout.WEST);//adding search box in west of our panel.

        plusIcon = ImageIO.read(new File("Icons/PlusCircle-no-select.png"));//loading plus Icon image
        plusLabel = new JLabel(new ImageIcon(plusIcon));//creating plus label to hold that.
        createPlusListener();//creating it's listener
        plusLabel.addMouseListener(new ScrollerPlusIconListener());

        JComboBox usersBox=new JComboBox();//creating a JComboBox for user.
        usersBox.setPreferredSize(new Dimension(100,25));//setting PreferredSize to show.

        //creating a container to hold plus label and user box:
        JPanel userContainer = new JPanel();
        userContainer.setOpaque(false);//to show north part background behind it.
        userContainer.setLayout(new BoxLayout(userContainer,BoxLayout.LINE_AXIS));
        userContainer.add(plusLabel);
        userContainer.add(Box.createHorizontalStrut(3));
        userContainer.add(usersBox);

        this.add(userContainer,BorderLayout.EAST);
    }
    private void createPlusListener(){
        plusLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //need to be implement later
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                try {
                    plusIcon = ImageIO.read(new File("Icons/PlusCircle.png"));
                    plusLabel.setIcon(new ImageIcon(plusIcon));
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(null, "Error updating plus circle image","An Error Occurred",JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                try {
                    plusIcon = ImageIO.read(new File("Icons/PlusCircle-no-select.png"));
                    plusLabel.setIcon(new ImageIcon(plusIcon));
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(null, "Error updating plus circle image","An Error Occurred",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
