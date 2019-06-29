package com.GUIFrame;

import com.Interfaces.PlaylistOptionLinker;
import com.Interfaces.ShowSongsLinker;
import com.Interfaces.SongPanelsLinker;
import com.MP3.AppStorage;
import com.MP3.MP3Info;
import com.Panels.CenterPanelSections.SharedSongPanel;
import com.Panels.CenterPanelSections.SongPanel;
import com.Panels.NorthPanelSections.EastPanelServerThread;
import com.Panels.GeneralPanels.*;
import com.Panels.NorthPanelSections.EastPanelClientThread;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;

/**
 * the window GUI where User can play a music.
 * when user run this program, its only create one instance of this class, so it's a singleton class.
 *
 * @author Soroush Mehraban & Morteza Damghani
 * @version 1.0
 */
public class GUIFrame extends JFrame implements Serializable {
    private static GUIFrame guiFrame;
    private static SouthPanel southPanel;
    private static CenterPanel centerPanel;
    private static WestPanel westPanel;
    private static EastPanel eastPanel;
    private static JPanel artworkPanel;
    private static String username;
    private static EastPanelServerThread mainServerThread;
    private static EastPanelClientThread mainClientThread;
    //private static transient ScrollerPlusIconListener mainClientThread;

    /**
     * Class Constructor
     */
    private GUIFrame() throws IOException {
        JTextField usernameField = new JTextField(10);//creating a field to input user name.
        JPanel myPanel = new JPanel();//creating a label to hold that field.
        myPanel.add(new JLabel("Username:"));//creating a label to show user should input username and adding it to myPanel
        myPanel.add(usernameField);//adding text field to myPanel
        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please Enter Your Username", JOptionPane.OK_CANCEL_OPTION);//showing a JOptionPane to enter input
        if (result == JOptionPane.OK_OPTION) {//if user pressed enter
            guiFrame = this;
            username = usernameField.getText();//setting program username.
            Image frameIcon = ImageIO.read(new File("Icons/frameIcon.png"));
            this.setIconImage(frameIcon);
            this.setLayout(new BorderLayout()); //frame layout
            this.setSize(1050, 512); //frame length : 940 * 512
            this.setLocationRelativeTo(null); //setting frame at the center of screen
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //closing the program when user close the window.
            this.setMinimumSize(new Dimension(1050, 512));

            centerPanel = new CenterPanel();
            this.add(centerPanel, BorderLayout.CENTER);

            southPanel = new SouthPanel();
            southPanel.setLyricsLinker(centerPanel.getCenterPart());
            this.add(southPanel, BorderLayout.SOUTH);

            westPanel = new WestPanel();
            artworkPanel = new JPanel();
            artworkPanel.setLayout(new BoxLayout(artworkPanel, BoxLayout.LINE_AXIS));
            JPanel westContainer = createWestContainer(westPanel, artworkPanel);
            this.add(westContainer, BorderLayout.WEST);
            eastPanel = new EastPanel();
            JScrollPane eastPanelJScrollPane = new JScrollPane(eastPanel);
            eastPanelJScrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
            customizeJScrollPane(eastPanelJScrollPane);
            this.add(eastPanelJScrollPane, BorderLayout.EAST);
            this.setVisible(true);
            //setting like linker between playPanel in southPanel and centerPart in centerPanel:
            southPanel.getPlayPanel().setLikeLinker(centerPanel.getCenterPart());

            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    AppStorage.saveSongs();
                }
            });
            AppStorage.loadSongs();//loading songs if user has from previous run
            showHome();//showing home by default
            mainServerThread = new EastPanelServerThread();
            mainServerThread.start();
            //mainClientThread=new ScrollerPlusIconListener();
            //Thread clientThread=new Thread(mainClientThread);
            //clientThread.start();

            //RadioClient radioClient = new RadioClient();

        }

    }

    /**
     * getting instance of class.
     *
     * @return a unique com.GUIFrame.GUIFrame object.
     */
    public static GUIFrame getInstance() {
        if (guiFrame == null) {
            try {
                guiFrame = new GUIFrame();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error creating GUIFrame","an error occurred",JOptionPane.ERROR_MESSAGE);
            }
        }
        return guiFrame;
    }

    /**
     * reloading com.GUIFrame.GUIFrame when components changes.
     */
    public static void reload() {
        if (guiFrame != null) {
            guiFrame.repaint();
            guiFrame.revalidate();
        }
    }

    /**
     * This method works as a linker and link RadioClient with center part of centeral panel to add radio songs.
     *
     * @param songTitle given song title
     * @param artist    given song artist
     */
    public static void addRadioSong(String songTitle, String artist) {
        centerPanel.getCenterPart().addRadioSong(songTitle, artist);
        centerPanel.getCenterPart().showRadioSongs();
    }

    /**
     * This method works as a linker and show home after Home clicked in west panel.
     */
    public static void showHome() {
        centerPanel.getCenterPart().showHome();
    }

    /**
     * this method play clicked music and then can be controlled in south panel.
     * it also reload artwork panel and shows new artwork of playing music.
     *
     * @param songPanel song we want to play.
     */
    public static void playClickedMusic(SongPanel songPanel) {
        try {
            Image playingArtwork = songPanel.getMp3Info().getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            artworkPanel.removeAll();//removing previous artwork in artwork panel
            artworkPanel.add(new JLabel(new ImageIcon(playingArtwork)));//adding new artwork to show
            reload();//reload to show the changes
            southPanel.play(songPanel, centerPanel.getCenterPart().getCurrentPlaying());
            ArrayList<SongPanel> sharedSongs = centerPanel.getCenterPart().getSharedSongs();
            for (SongPanel sharedSong : sharedSongs) {
                if (sharedSong.getMp3Info().getTitle().equals(songPanel.getMp3Info().getTitle())) {
                    if (mainClientThread != null) {
                        mainClientThread.setSongArtist(sharedSong.getMp3Info().getArtist());
                        mainClientThread.setSongTitle(sharedSong.getMp3Info().getTitle());
                    }
                    mainServerThread.setSongTitle(sharedSong.getMp3Info().getTitle());
                    mainServerThread.setSongArtist(sharedSong.getMp3Info().getArtist());
                    break;
                }
            }
        } catch (InvalidDataException | IOException | UnsupportedTagException e) {
            JOptionPane.showMessageDialog(null, "Error reading artwork image for showing in west panel", "An Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static String getUsername() {
        return username;
    }

    /**
     * This method is a linker between socketTreads and center part of center panel and give socket threads shared songs.
     *
     * @return shared songs of this user.
     */
    public static ArrayList<SongPanel> getSharedSongs() {
        return centerPanel.getCenterPart().getSharedSongs();
    }

    /**
     * a linker between socket threads and center part of center panel.
     *
     * @param sharedSongPanels sharedSongs to show.
     */
    public static void showSharedSongs(ArrayList<SharedSongPanel> sharedSongPanels) {
        centerPanel.getCenterPart().showSharedSongs(sharedSongPanels);
    }

    /**
     * this method add an album which is selected in library.
     *
     * @param albumTitle      title of album to be shown.
     * @param albumMusicsInfo list of all musics info which related to a album.
     */
    public static void addAlbum(String albumTitle, ArrayList<MP3Info> albumMusicsInfo) {
        centerPanel.getCenterPart().addAlbum(albumTitle, albumMusicsInfo);
    }

    /**
     * Creating playlist which is implemented in center part of centerPanel(this method works as a linker)
     *
     * @param title       title of play list.
     * @param description description to show under the title.
     */
    public static void createPlayList(String title, String description) {
        centerPanel.getCenterPart().createPlayList(title, description);
        showHome();
    }

    /**
     * this method adds a song to given playlist.(works as a linker)
     *
     * @param playListTitle title of playlist as a key of HashMap.
     * @param description   description of playlist(helps us to create one if it doesn't exist)
     * @param songDirectory directory of music to add.
     */
    public static void addSongToPlayList(String playListTitle, String description, String songDirectory) {
        centerPanel.getCenterPart().addSongToPlayList(playListTitle, description, songDirectory);
    }

    /**
     * This method works as a linker between west panel and center panel and shows all songs existing in library.
     */
    public static void showAllSongs() {
        centerPanel.getCenterPart().showAllSongs();
    }

    /**
     * this method works as a linker between west part panel and center panel and shows all songs related to an album
     *
     * @param albumTitle album title as a key.
     */
    public static void showAlbumSongs(String albumTitle) {
        centerPanel.getCenterPart().showAlbumSongs(albumTitle);
    }

    /**
     * this method works as a linker between west part panel and center panel and shows all songs related to a playList
     *
     * @param playlistTitle playlist title as a key.
     */
    public static void showPlaylistSongs(String playlistTitle) {
        centerPanel.getCenterPart().showPlayListSongs(playlistTitle);
    }

    /**
     * This method work as a linker.
     *
     * @return list of playlist titles.
     */
    public static ArrayList<String> getPlayistTitles() {
        return centerPanel.getCenterPart().getPlayistTitles();
    }

    /**
     * this method work as a linker
     *
     * @return list of album titles.
     */
    public static ArrayList<String> getAlbumTitles() {
        return centerPanel.getCenterPart().getAlbumTitles();
    }

    public static PlaylistOptionLinker getPlaylistOptionLinker() {
        return centerPanel.getCenterPart();
    }

    public static WestPanel getWestPanel() {
        return westPanel;

    }

    public static ShowSongsLinker getShowSongsLinker() {
        return centerPanel.getCenterPart();
    }

    /**
     * getting this helps us to control song panels in AppStorage and playlist panels.
     */
    public static SongPanelsLinker getSongPanelsLinker() {
        return centerPanel.getCenterPart();
    }


    /**
     * this method customize JScrollPane's color to fit in center part theme.
     *
     * @param jScrollPane our jScrollPane to to be customized.
     */
    public static void customizeJScrollPane(JScrollPane jScrollPane) {
        jScrollPane.getHorizontalScrollBar().setUI(createBasicScrollBarUI());
        jScrollPane.getVerticalScrollBar().setUI(createBasicScrollBarUI());
        jScrollPane.setBackground(new Color(23, 23, 23));
    }

    /**
     * This method create Basic Scroll Bar UI which sets color of JScroll bar.
     *
     * @return desired basic scroll bar UI.
     */
    private static BasicScrollBarUI createBasicScrollBarUI() {
        return new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(84, 84, 84);
                this.trackColor = new Color(23, 23, 23);
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return new BasicArrowButton(orientation,
                        new Color(23, 23, 23),
                        new Color(23, 23, 23),
                        new Color(84, 84, 84),
                        new Color(23, 23, 23));
            }

            protected JButton createDecreaseButton(int orientation) {
                return new BasicArrowButton(orientation,
                        new Color(23, 23, 23),
                        new Color(23, 23, 23),
                        new Color(84, 84, 84),
                        new Color(23, 23, 23));
            }

        };
    }

    /**
     * this private method creates a container which holds west panel and artwork panel
     *
     * @param westPanel    program west panel
     * @param artworkPanel artwork panel which shows artworks after playing song.
     * @return desired container
     */
    private JPanel createWestContainer(WestPanel westPanel, JPanel artworkPanel) {
        JPanel westContainer = new JPanel();
        westContainer.setLayout(new BoxLayout(westContainer, BoxLayout.PAGE_AXIS));
        JScrollPane leftJScrollPane = new JScrollPane(westPanel);
        leftJScrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        leftJScrollPane.setPreferredSize(new Dimension(150, 512));
        customizeJScrollPane(leftJScrollPane);
        westContainer.add(leftJScrollPane);
        westContainer.add(artworkPanel);
        westContainer.setPreferredSize(new Dimension(150, 100));
        return westContainer;
    }

    public static EastPanelServerThread getMainThread() {
        return mainServerThread;

    }

    public static String getUserName() {
        return username;
    }

    public static EastPanel getEastPanel() {
        return eastPanel;

    }

    public static GUIFrame getGUIFrame() {
        return guiFrame;
    }

    public static void setMainServerThread(EastPanelServerThread mainServerThread) {
        GUIFrame.mainServerThread = mainServerThread;
    }

    public static void setMainClientThread(EastPanelClientThread mainClientThread) {
        GUIFrame.mainClientThread = mainClientThread;
    }

    /**
     * This method works as a linker to add new user to north part JCombobox.
     *
     * @param newUser new user to add.
     */
    public static void addConnectedUserNameJCombobox(String newUser) {
        centerPanel.getNorthPart().addUser(newUser);
    }
    /**
     *
     * This method works as a linker to remove user from north part JCombobox.
     *
     * @param user new user to remove.
     */
    public static void removeUserNameExited(String user){
        centerPanel.getNorthPart().removeUser(user);
    }

    /**
     * this method works as a linker and set true value to hash map of showSharedSongs in mainThreads.
     * so in pear to pear socket threads, our client/server order to given parameter user to let us see user's shared songs.
     *
     * @param user given user
     */
    public static void setShowSharedSongs(String user) {
        if (mainServerThread != null) {
            mainServerThread.setShowSharedSongsRequest(user);
        }
        if (mainClientThread != null) {
            mainClientThread.setShowSharedSongsReqest(user);
        }
    }


    /**
     * it sets the sizes of different images used in different parts of the program.
     *
     * @param directory the directory of the image.
     * @param scale     the scale of the image.
     * @return a preferred size image.
     */
    public static ImageIcon setIconSize(String directory, int scale) {
        ImageIcon output = new ImageIcon(directory);
        Image newImage = output.getImage();
        Image newimg = newImage.getScaledInstance(scale, scale, Image.SCALE_SMOOTH);
        output = new ImageIcon(newimg);
        return output;
    }
}