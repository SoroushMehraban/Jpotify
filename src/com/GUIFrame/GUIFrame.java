package com.GUIFrame;

import com.Interfaces.PlaylistOptionLinker;
import com.Interfaces.ShowSongsLinker;
import com.MP3.MP3Info;
import com.Panels.CenterPanelSections.SongPanel;
import com.Panels.GeneralPanels.*;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * the window GUI where User can play a music.
 * when user run this program, its only create one instance of this class, so it's a singleton class.
 *
 * @author Soroush Mehraban & Morteza Damghani
 * @version 1.0
 */
public class GUIFrame extends JFrame {
    private static GUIFrame guiFrame;
    private static SouthPanel southPanel;
    private static CenterPanel centerPanel;
    private static WestPanel westPanel;
    private static JPanel artworkPanel;
    private static String username;
    /**
     * Class Constructor
     */
    private GUIFrame() throws IOException {
        JTextField usernameField = new JTextField(10);
        //JTextField descriptionField = new JTextField(10);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Username:"));
        myPanel.add(usernameField);
        //myPanel.add(Box.createHorizontalStrut(50));
        //myPanel.add(Box.createVerticalStrut(100));
        //myPanel.add(new JLabel("Description:"));
        //myPanel.add(descriptionField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please Enter Your Username", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            username=usernameField.getText();
            System.out.println(username);
            this.setLayout(new BorderLayout()); //frame layout
            this.setSize(940,512); //frame length : 940 * 512
            this.setLocationRelativeTo(null); //setting frame at the center of screen
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //closing the program when user close the window.
            this.setMinimumSize(new Dimension(940,512));

            centerPanel = new CenterPanel();
            this.add(centerPanel,BorderLayout.CENTER);

            southPanel = new SouthPanel();
            southPanel.setLyricsLinker(centerPanel.getCenterPart());
            this.add(southPanel,BorderLayout.SOUTH);

            westPanel=new WestPanel();
            artworkPanel = new JPanel();
            artworkPanel.setLayout(new BoxLayout(artworkPanel,BoxLayout.LINE_AXIS));
            JPanel westContainer = createWestContainer(westPanel,artworkPanel);
            this.add(westContainer,BorderLayout.WEST);

            this.setVisible(true);
            //setting like linker between playPanel in southPanel and centerPart in centerPanel:
            southPanel.getPlayPanel().setLikeLinker(centerPanel.getCenterPart());
            showHome();//showing home by default


        }

    }

    /**
     * getting instance of class.
     * @return a unique com.GUIFrame.GUIFrame object.
     */
    public static GUIFrame getInstance() {
        if(guiFrame == null) {
            try {
                guiFrame = new GUIFrame();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error creating GUIFrame");
            }
        }
        return guiFrame;
    }

    /**
     * reloading com.GUIFrame.GUIFrame when components changes.
     */
    public static void reload(){
        guiFrame.repaint();
        guiFrame.revalidate();
    }

    /**
     * This method works as a linker and show home after Home clicked in west panel.
     */
    public static void showHome(){
        centerPanel.getCenterPart().showHome();
    }
    /**
     * this method play clicked music and then can be controlled in south panel.
     * it also reload artwork panel and shows new artwork of playing music.
     * @param songPanel song we want to play.
     */
    public static void playClickedMusic(SongPanel songPanel){
        try {
            Image playingArtwork = songPanel.getMp3Info().getImage().getScaledInstance(150,150,Image.SCALE_SMOOTH);
            artworkPanel.removeAll();//removing previous artwork in artwork panel
            artworkPanel.add(new JLabel(new ImageIcon(playingArtwork)));//adding new artwork to show
            reload();//reload to show the changes
            southPanel.play(songPanel, centerPanel.getCenterPart().getCurrentPlaying());
        } catch (InvalidDataException | IOException | UnsupportedTagException e) {
            JOptionPane.showMessageDialog(null, "Error reading artwork image for showing in west panel","An Error Occurred",JOptionPane.ERROR_MESSAGE);
        }
    }
    public static String getUsername()
    {
        return username;
    }

    /**
     * this method add an album which is selected in library.
     * @param albumTitle title of album to be shown.
     * @param albumMusicsInfo list of all musics info which related to a album.
     */
    public static void addAlbum(String albumTitle, ArrayList<MP3Info> albumMusicsInfo) {
        centerPanel.getCenterPart().addAlbum(albumTitle,albumMusicsInfo);
    }

    /**
     * Creating playlist which is implemented in center part of centerPanel(this method works as a linker)
     * @param title title of play list.
     * @param description description to show under the title.
     */
    public static void createPlayList(String title, String description){
        centerPanel.getCenterPart().createPlayList(title,description);
    }
    /**
     * this method adds a song to given playlist.(works as a linker)
     * @param playListTitle  title of playlist as a key of HashMap.
     * @param songDirectory directory of music to add.
     */
    public static void addSongToPlayList(String playListTitle, String songDirectory){
        centerPanel.getCenterPart().addSongToPlayList(playListTitle,songDirectory);
    }

    /**
     * This method works as a linker between west panel and center panel and shows all songs existing in library.
     */
    public static void showAllSongs(){
        centerPanel.getCenterPart().showAllSongs();
    }

    /**
     * this method works as a linker between west part panel and center panel and shows all songs related to an album
     * @param albumTitle album title as a key.
     */
    public static void showAlbumSongs(String albumTitle){
        centerPanel.getCenterPart().showAlbumSongs(albumTitle);
    }
    /**
     * this method works as a linker between west part panel and center panel and shows all songs related to a playList
     * @param playlistTitle playlist title as a key.
     */
    public static void showPlaylistSongs(String playlistTitle){
        centerPanel.getCenterPart().showPlayListSongs(playlistTitle);
    }

    /**
     * This method work as a linker.
     * @return list of playlist titles.
     */
    public static ArrayList<String> getPlayistTitles(){
        return centerPanel.getCenterPart().getPlayistTitles();
    }

    /**
     * this method work as a linker
     * @return list of album titles.
     */
    public static ArrayList<String> getAlbumTitles(){
        return centerPanel.getCenterPart().getAlbumTitles();
    }
    public static PlaylistOptionLinker getPlaylistOptionLinker(){
        return centerPanel.getCenterPart();
    }
    public static WestPanel getWestPanel()
    {
        return westPanel;

    }
    public static ShowSongsLinker getShowSongsLinker(){
        return centerPanel.getCenterPart();
    }


    /**
     * this method customize JScrollPane's color to fit in center part theme.
     * @param jScrollPane our jScrollPane to to be customized.
     */
    public static void customizeJScrollPane(JScrollPane jScrollPane){
        jScrollPane.getHorizontalScrollBar().setUI(createBasicScrollBarUI());
        jScrollPane.getVerticalScrollBar().setUI(createBasicScrollBarUI());
        jScrollPane.setBackground(new Color(23,23,23));
    }

    /**
     * This method create Basic Scroll Bar UI which sets color of JScroll bar.
     * @return desired basic scroll bar UI.
     */
    private static BasicScrollBarUI createBasicScrollBarUI(){
        return new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors(){
                this.thumbColor = new Color(84,84,84);
                this.trackColor = new Color(23,23,23);
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return new BasicArrowButton(orientation,
                        new Color(23,23,23),
                        new Color(23,23,23),
                        new Color(84,84,84),
                        new Color(23,23,23));
            }

            protected JButton createDecreaseButton(int orientation)  {
                return new BasicArrowButton(orientation,
                        new Color(23,23,23),
                        new Color(23,23,23),
                        new Color(84,84,84),
                        new Color(23,23,23));
            }

        };
    }

    /**
     * this private method creates a container which holds west panel and artwork panel
     * @param westPanel program west panel
     * @param artworkPanel artwork panel which shows artworks after playing song.
     * @return desired container
     */
    private JPanel createWestContainer(WestPanel westPanel, JPanel artworkPanel){
        JPanel westContainer = new JPanel();
        westContainer.setLayout(new BoxLayout(westContainer,BoxLayout.PAGE_AXIS));
        JScrollPane leftJScrollPane=new JScrollPane(westPanel);
        leftJScrollPane.setPreferredSize(new Dimension(150,600));
        customizeJScrollPane(leftJScrollPane);
        westContainer.add(leftJScrollPane);
        westContainer.add(artworkPanel);
        westContainer.setPreferredSize(new Dimension(150,600));
        return westContainer;
    }
}
