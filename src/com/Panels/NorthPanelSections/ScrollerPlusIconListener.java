package com.Panels.NorthPanelSections;

import com.GUIFrame.GUIFrame;
import com.Panels.GeneralPanels.WestPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ScrollerPlusIconListener extends MouseAdapter
{

    @Override
    public void mouseClicked(MouseEvent e){
        EastPanelClientThread mainClientThread=new EastPanelClientThread();
        mainClientThread.start();
    }





}
