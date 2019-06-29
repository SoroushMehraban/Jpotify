package com.Panels.NorthPanelSections;

import com.GUIFrame.GUIFrame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**
 * it implements the action of clicking the plus button near the JCombo box.
 *
 * @author Soroush Mehraban & Morteza Damghani
 * @version 1.0
 */
public class ScrollerPlusIconListener extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
        EastPanelClientThread mainClientThread = new EastPanelClientThread();
        mainClientThread.start();
        GUIFrame.setMainClientThread(mainClientThread);
    }


}
