package com.Panels.WestPanelSections.WestPanelListeners;

import com.GUIFrame.GUIFrame;
import com.MP3.MP3Info;
import com.Panels.GeneralPanels.WestPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * This class is a listener for Library Panel which exists in West panel. it has 3 actions:
 * -Mouse Clicked: it opens a File Chooser for user to open mp3 files.(multi file supported)
 * -Mouse Entered: it change library icon and become bigger.
 * -Mouse exited: it turns to previous form.
 *
 * @author Morteza Damghani & Soroush Mehraban
 * @version 1.0
 */
public class LibraryPanelListener extends MouseAdapter {
    private JLabel icon;
    private JLabel label;
    private JLabel plusIcon;
    private Font font;

    /**
     * Class constructor.
     * it sets given parameters:
     *
     * @param icon     icon of library to have a action on it.
     * @param label    a label which says it's a "Library"
     * @param plusIcon plus icon to show beside.
     */
    public LibraryPanelListener(JLabel icon, JLabel label, JLabel plusIcon) {
        this.icon = icon;
        this.label = label;
        this.plusIcon = plusIcon;
        font = label.getFont();

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        ArrayList<MP3Info> outputArray = new ArrayList<>();//this array sends MP3Info list with their album to show in center panel.
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);//set to accept files and directories.
        fileChooser.showOpenDialog(GUIFrame.getInstance());//showing open dialog and setting it's owner which is GUIFrame.
        File inputFile = fileChooser.getSelectedFile();//getting selected files.

        if (inputFile != null) {//if user didn't cancel choosing
            if (inputFile.isFile()) {//if user chooses file
                addFile(inputFile);
            } else if (inputFile.isDirectory()) {//if user chooses directory
                if (Objects.requireNonNull(inputFile.listFiles()).length > 0) { //if I/O Error didn't occur(requireNonNull) and Folder is not empty{
                    try {
                        addFiles(inputFile);
                    } catch (IOException | NoSuchFieldException e1) {
                        JOptionPane.showMessageDialog(null, "Error reading mp3 file for MP3Info", "An Error Occurred", JOptionPane.ERROR_MESSAGE);
                    }
                } else
                    JOptionPane.showMessageDialog(null, "Folder is empty!", "Wrong directory input", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }


    @Override
    public void mouseEntered(MouseEvent e) {
        icon.setIcon(WestPanel.setIconSize("Icons/Library.PNG", 20));
        label.setFont(new Font("Serif", Font.BOLD, 16));
        plusIcon.setIcon(WestPanel.setIconSize("Icons/Plus.png", 10));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        icon.setIcon(WestPanel.setIconSize("Icons/Library-no-select.PNG", 20));
        label.setFont(font);
        plusIcon.setIcon(WestPanel.setIconSize("Icons/Plus-no-select.png", 10));

    }

    /**
     * This method adds a song to our library if chosen file is a mp3 file.
     *
     * @param inputFile input file chosen by user.
     */
    private void addFile(File inputFile) {

        if (inputFile.getName().endsWith(".mp3")) {//if chosen file is a mp3 file

            ArrayList<MP3Info> outputArray = new ArrayList<>();//this array sends a song with it's album to show in center panel..

            try {
                MP3Info inputMP3 = new MP3Info(inputFile.getAbsolutePath());//creating MP3Info object of that file.
                outputArray.add(inputMP3);
                GUIFrame.addAlbum(inputMP3.getAlbum(), outputArray);//adding this music to show in center panel.
            } catch (IOException | NoSuchFieldException e) {
                JOptionPane.showMessageDialog(null, "Error reading mp3 file for MP3Info", "An Error Occurred", JOptionPane.ERROR_MESSAGE);
            }
        } else
            JOptionPane.showMessageDialog(null, "You should choose mp3 file", "Wrong file input", JOptionPane.INFORMATION_MESSAGE);


    }

    /**
     * this method adds list of songs in their albums to our library if chosen folder contains mp3 files.
     *
     * @param inputFile chosen folder by user
     */
    private void addFiles(File inputFile) throws IOException, NoSuchFieldException {
        ArrayList<MP3Info> outputArray = new ArrayList<>();//this array sends songs with their album to show in center panel..

        File[] inputFiles = inputFile.listFiles();
        for (int i = 0; i <= Objects.requireNonNull(inputFiles).length - 1; i++) //collecting all mp3 files in folder
            if (inputFiles[i].getName().endsWith(".mp3")) //if it's a mp3 file we add it
                outputArray.add(new MP3Info(inputFiles[i].getAbsolutePath()));

        ArrayList<String> albumNames = new ArrayList<>();
        if (outputArray.size() != 0)//if there exists mp3 file in chosen folder
            albumNames.add(outputArray.get(0).getAlbum());//creating first album names. help us for loop below here
        else
            JOptionPane.showMessageDialog(null, "Your directory doesn't has any mp3 file", "Wrong Directory", JOptionPane.INFORMATION_MESSAGE);

        boolean albumMatched;
        for (int i = 1; i <= outputArray.size() - 1; i++) {//creating list of albums
            albumMatched = false;
            for (int j = 0; j < albumNames.size() - 1; j++) {
                if (albumNames.get(j).equals(outputArray.get(i).getAlbum())) {//if album was exist before.
                    albumMatched = true;
                    break;
                }
            }
            if (!albumMatched) {
                albumNames.add(outputArray.get(i).getAlbum());
            }
        }

        for (int i = 0; i <= albumNames.size() - 1; i++) {//adding mp3's with similar albums to library
            ArrayList<MP3Info> mainOutput = new ArrayList<>();
            String currentAlbumName = albumNames.get(i);
            for (int j = 0; j <= outputArray.size() - 1; j++)
                if (currentAlbumName.equals(outputArray.get(j).getAlbum())) {
                    mainOutput.add(outputArray.get(j));
                }

            GUIFrame.addAlbum(albumNames.get(i), mainOutput);
        }
    }
}