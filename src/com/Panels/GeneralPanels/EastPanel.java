package com.Panels.GeneralPanels;

import javax.swing.*;
import java.awt.*;

public class EastPanel extends JPanel
{
    public EastPanel()
    {
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        this.setBackground(new Color(23,23,23));
        this.setPreferredSize(new Dimension(150,600));
        this.add(Box.createVerticalStrut(20));
        JLabel title=new JLabel("         Friend  Activity");
        title.setForeground(Color.WHITE);
        this.add(title);
        this.add(Box.createVerticalStrut(50));








    }





}
