package com.Panels.WestPanelSections.WestPanelListeners;

import com.GUIFrame.GUIFrame;
import com.MP3.MP3Info;
import com.Panels.GeneralPanels.WestPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;


public class LibraryPanelListener extends MouseAdapter
{

    private JLabel icon;
    private JLabel label;
    private JLabel plusIcon;
    private Font font;
    public LibraryPanelListener(JLabel icon,JLabel label,JLabel plusIcon)
    {
        this.icon=icon;
        this.label=label;
        this.plusIcon=plusIcon;
        font=label.getFont();

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try
        {
            JFileChooser fileChooser=new JFileChooser();
            //JButton butt1=new JButton();
            ArrayList<MP3Info> outputArray=new ArrayList<>();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            int res=fileChooser.showOpenDialog(null);
            File inputFile=fileChooser.getSelectedFile();
            if(inputFile.isFile())
            {
                if(inputFile.getName().endsWith(".mp3"))
                {
                    MP3Info inputMP3=new MP3Info(inputFile.getAbsolutePath());
                    outputArray.add(inputMP3);
                    GUIFrame.addAlbum(inputMP3.getAlbum(),outputArray);


                }

            }
            if(inputFile.isDirectory())
            {
                if(inputFile.listFiles()!=null)
                {
                    try
                    {
                        File inputMP3s[]=inputFile.listFiles();
                        MP3Info temp;
                        for(int i=0;i<=inputFile.listFiles().length-1;++i)
                        {
                            if(inputMP3s[i].getName().endsWith(".mp3"))
                            {
                                temp=new MP3Info(inputMP3s[i].getAbsolutePath());
                                outputArray.add(temp);
                            }

                        }
                        ArrayList<String> albumNames=new ArrayList<>();
                        albumNames.add(outputArray.get(0).getAlbum());
                        boolean result;
                        for(int i=1;i<=outputArray.size()-1;++i)
                        {
                            result=false;
                            for(int j=0;j<i;++j)
                            {
                                if(albumNames.get(j).equals(outputArray.get(i).getAlbum()))
                                {
                                    result=true;
                                    break;
                                }
                            }
                            if(!result)
                            {
                                albumNames.add(outputArray.get(i).getAlbum());
                            }


                        }
                        for(int i=0;i<=albumNames.size()-1;++i)
                        {
                            ArrayList<MP3Info> mainOutput=new ArrayList<>();
                            String tempAlbumName=albumNames.get(i);
                            for(int j=0;j<=outputArray.size()-1;++j)
                            {
                                if(tempAlbumName.equals(outputArray.get(j).getAlbum()))
                                {
                                    mainOutput.add(outputArray.get(j));
                                }


                            }
                            GUIFrame.addAlbum(albumNames.get(i),mainOutput);

                        }





                    }
                    catch(NullPointerException e2)
                    {
                        e2.printStackTrace();
                    }

                }



            }
        }
        catch(Exception e1)
        {
            e1.printStackTrace();

        }


    }


    @Override
    public void mouseEntered(MouseEvent e) {
        icon.setIcon(WestPanel.setIconSize("Icons/Library.PNG"));
        label.setFont(new Font("Serif", Font.BOLD, 16));
        plusIcon.setIcon(WestPanel.setPlusIconSize("Icons/Plus.png"));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        icon.setIcon(WestPanel.setIconSize("Icons/Library-no-select.PNG"));
        label.setFont(font);
        plusIcon.setIcon(WestPanel.setPlusIconSize("Icons/Plus-no-select.png"));

    }
}
