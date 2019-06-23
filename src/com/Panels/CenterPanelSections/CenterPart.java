package com.Panels.CenterPanelSections;

import com.Interfaces.PlaylistOptionLinker;
import com.Interfaces.LikeLinker;
import com.Interfaces.LyricsLinker;
import com.Interfaces.ShowSongsLinker;
import com.MP3.MP3Info;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Center part of CenterPanel.
 * this part shows user albums,playlist, or list of albumSongs based on user choices.
 *
 * @author Soroush Mehraban & Morteza Damghani
 * @version 1.0
 */
public class CenterPart extends JPanel implements ShowSongsLinker, LikeLinker, LyricsLinker, PlaylistOptionLinker {
    private HashMap<String,AlbumPanel> albumPanels;
    private HashMap<String,PlayListPanel> playListPanels;
    private PlayListPanel currentPlaylistPanel;//helps for adding, removing and swapping in playlist song.
    private ArrayList<SongPanel> addingSongPanels; //helps for adding song to playlist.
    private ArrayList<SongPanel> removingSongPanels; //helps for removing song from playlist.
    private SongPanel firstSelectedSwaping;//helps for swapping
    private SongPanel secondSelectedSwaping;//helps for swaping
    private ArrayList<SongPanel> currentPlaying;
    private GridBagConstraints constraints;
    private BufferedImage emptyPlayListImage;
    private BufferedImage plusImage;
    private BufferedImage tickImage;
    private BufferedImage minusImage;
    private BufferedImage swapImage;
    private JLabel plusLabel;
    private JLabel tickLabel;
    private JLabel minusLabel;
    private JLabel doneLabel;
    private JLabel swapLabel;
    private JLabel removeSongFromPlaylistLabel;
    private JLabel addSongToPlayListLabel;
    private JLabel swapTextLabel;
    private boolean addingSongToPlaylist;//helps for adding song to playlist
    private boolean removeSongFromPlaylist;//helps for removing song from playlist.
    private boolean isSwaping;//helps for swap between playlist songs.

    /**
     * Class Constructor.
     */
    public CenterPart() {
        this.setLayout(new GridBagLayout());//setting panel layout
        constraints = new GridBagConstraints();//creating panel constraints to denote where components should located on.
        constraints.insets = new Insets(0,0,15,15);//denoting spaces between components.
        this.setBackground(new Color(23,23,23));//setting panel background

        albumPanels = new HashMap<>();//list of albumPanels.
        playListPanels = new HashMap<>();

        //creating remove song from playlist label:
        try {
            minusImage = ImageIO.read(new File("Icons/Minus-no-select.png"));
            minusLabel = new JLabel(new ImageIcon(minusImage));
            removeSongFromPlaylistLabel = new JLabel("Remove song from playlist");
            removeSongFromPlaylistLabel.setForeground(new Color(120,120,120));
            createRemoveSongFromPlaylistListener();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading minus image","An Error Occurred",JOptionPane.ERROR_MESSAGE);
        }
        //creating add song to playlist label:
        try {
            plusImage = ImageIO.read(new File("Icons/PlusSong-no-select.png"));
            plusLabel = new JLabel(new ImageIcon(plusImage));
            addSongToPlayListLabel = new JLabel("Add Song to Playlist");
            addSongToPlayListLabel.setForeground(new Color(120,120,120));
            createAddSongToPlayListListener();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading plus image","An Error Occurred",JOptionPane.ERROR_MESSAGE);
        }
        //creating done label:
        try {
            tickImage =ImageIO.read(new File("Icons/Tick-no-select.png"));
            tickLabel = new JLabel(new ImageIcon(tickImage));
            doneLabel = new JLabel("Done");
            doneLabel.setForeground(new Color(120,120,120));
            createDoneListener();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading tick image","An Error Occurred",JOptionPane.ERROR_MESSAGE);
        }
        //creating swap label:
        try {
            swapImage =ImageIO.read(new File("Icons/Swap-no-select.png"));
            swapLabel = new JLabel(new ImageIcon(swapImage));
            swapTextLabel = new JLabel("Swap");
            swapTextLabel.setForeground(new Color(120,120,120));
            createSwapListener();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading swap image","An Error Occurred",JOptionPane.ERROR_MESSAGE);
        }
        //creating default playLists(Shared Songs and Favorite Songs):
        createDefaultPlayLists();
        //creating Empty play list picture:
        try {
            emptyPlayListImage = ImageIO.read(new File("Images/EmptyPlayList.jpg"));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading empty playlist image","An Error Occurred",JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public boolean isAddingSongToPlaylist() {
        return addingSongToPlaylist;
    }

    @Override
    public boolean isRemoveSongFromPlaylist() {
        return removeSongFromPlaylist;
    }

    @Override
    public ArrayList<SongPanel> getAddingSongPanel(){
        return addingSongPanels;
    }

    @Override
    public ArrayList<SongPanel> getRemovingSongPanels() {
        return removingSongPanels;
    }

    @Override
    public boolean isSwaping() {
        return isSwaping;
    }

    @Override
    public SongPanel getFirstSelectedSwaping() {
        return firstSelectedSwaping;
    }

    @Override
    public SongPanel getSecondSelectedSwaping() {
        return secondSelectedSwaping;
    }

    @Override
    public void setFirstSelectedSwaping(SongPanel firstSelectedSwaping) {
        this.firstSelectedSwaping = firstSelectedSwaping;
    }

    @Override
    public void setSecondSelectedSwaping(SongPanel secondSelectedSwaping) {
        this.secondSelectedSwaping = secondSelectedSwaping;
    }

    public void swapPlayList(){
        if(firstSelectedSwaping != null && secondSelectedSwaping != null){
            int firstIndex = currentPlaylistPanel.getPlayListSongs().indexOf(firstSelectedSwaping);//first index to swap
            int secondIndex = currentPlaylistPanel.getPlayListSongs().indexOf(secondSelectedSwaping);//second index to swap
            Collections.swap(currentPlaylistPanel.getPlayListSongs(),firstIndex,secondIndex);//swapping!
            //changing changes to default:
            firstSelectedSwaping = null;
            secondSelectedSwaping = null;
            isSwaping = false;

            //making swap button look unselected:
            swapTextLabel.setForeground(new Color(120, 120, 120));
            try {
                swapImage = ImageIO.read(new File("Icons/Swap-no-select.png"));
                swapLabel.setIcon(new ImageIcon(swapImage));
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(null, "Error reloading swap image", "An Error Occurred", JOptionPane.ERROR_MESSAGE);
            }

            showPlayListSongs(currentPlaylistPanel.getTitle());//reloading playlist songs to user see they swapped
        }
    }

    /**
     * this method help us to control musics in south panel.
     * @return an ArrayList of song panels.
     */
    public ArrayList<SongPanel> getCurrentPlaying() {
        return currentPlaying;
    }

    /**
     * when this method calls, it shows albums in center part of Center panel.
     */
    public void showHome(){
        this.removeAll();//removing all components in center part
        addingSongToPlaylist = false;
        removeSongFromPlaylist = false;
        //initializing grids:
        int gridx = 0;
        int gridy = 0;
        //creating album label to show at top of albums:
        JLabel albumLabel = new JLabel("Albums:");
        albumLabel.setForeground(new Color(219,219,219));
        constraints.gridy = gridy;
        constraints.gridx = gridx;
        this.add(albumLabel, constraints);
        gridy++;//going to next line
        //showing album panels:
        for(AlbumPanel albumPanel: albumPanels.values()){
            constraints.gridx = gridx;
            constraints.gridy = gridy;
            this.add(albumPanel, constraints);
            if(gridx < 3)
                gridx++;
            else{
                gridy++;
                gridx = 0;
            }
        }
        //creating playList label to show at top of playLists:
        gridx--;
        gridy++;
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        JLabel playListLabel = new JLabel("PlayLists:");
        playListLabel.setForeground(new Color(219,219,219));
        this.add(playListLabel, constraints);
        gridy++;
        for(PlayListPanel playListPanel: playListPanels.values()){
            constraints.gridy = gridy;
            constraints.gridx = gridx;
            this.add(playListPanel, constraints);
            if(gridx < 3)
                gridx++;
            else{
                gridy++;
                gridx = 0;
            }
        }
        //updating center part of center panel:
        this.repaint();
        this.revalidate();
    }

    /**
     * this method shows given songPanels in center part of center panel.
     *
     * @param songPanels desired song panel to show.
     */
    @Override
    public void showSongs(ArrayList<SongPanel> songPanels){
        this.currentPlaying = songPanels;
        addingSongToPlaylist = false;
        this.removeAll();//removing all components.
        //initializing grids:
        int gridx = 0;
        int gridy = 0;
        //showing music panels:
        for(SongPanel songPanel: songPanels){
            songPanel.setBackground(new Color(23, 23, 23));//setting default background in case it doesn't
            songPanel.unSelect();//unSelecting if it's selected on previous adding panel.
            constraints.gridx = gridx;
            constraints.gridy = gridy;
            this.add(songPanel, constraints);
            if(gridx < 3) {
                gridx++;
            }
            else{
                gridx = 0;
                gridy++;
            }
        }
        if(removeSongFromPlaylist){//if we show this songs to remove, we need done button at the end.
            JPanel doneContainer = createOptionContainer(tickLabel,doneLabel);
            constraints.gridx = 0;
            constraints.gridy++;
            this.add(doneContainer,constraints);
        }
        //updating center part of center panel:
        this.repaint();
        this.revalidate();
    }

    /**
     * This method only shows song panels related to an album.
     * @param albumTitle title of album as a key
     */
    public void showAlbumSongs(String albumTitle){
        showSongs(albumPanels.get(albumTitle).getSongPanels());
    }


    @Override
    public void showPlayListSongs(String playListTitle){
        currentPlaylistPanel = playListPanels.get(playListTitle);//getting current song where user see.
        showSongs(playListPanels.get(playListTitle).getPlayListSongs());//show all songs related to playlist
        //creating playlist options containers:
        JPanel swapContainer = createOptionContainer(swapLabel,swapTextLabel);
        JPanel addSongContainer = createOptionContainer(plusLabel,addSongToPlayListLabel);
        JPanel removeSongContainer = createOptionContainer(minusLabel,removeSongFromPlaylistLabel);
        //adding container at the end:
        constraints.gridx = 0;
        if(currentPlaylistPanel.getPlayListSongs().size() >= 2) {
            constraints.gridy++;
            this.add(swapContainer, constraints);
        }
        constraints.gridy++;
        this.add(addSongContainer,constraints);
        if(currentPlaylistPanel.getPlayListSongs().size() > 0) {//if song exists in playlist
            constraints.gridy++;
            this.add(removeSongContainer,constraints);
        }

    }
    /**
     * This method is called when user press Songs in West panel, it shows all songs which exists in library.
     */
    public void showAllSongs(){
        this.removeAll();//removing all components.
        removeSongFromPlaylist = false;
        ArrayList<SongPanel> allSongs = new ArrayList<>();//this helps us to play ordered song in south panel.
        //initializing grids:
        int gridx = 0;
        int gridy = 0;
        //showing all songs:
        for (AlbumPanel albumPanel : albumPanels.values())
            for(SongPanel songPanel : albumPanel.getSongPanels()){
                //this boolean check in case if we show all songs for adding to playlist, it doesn't show song that playlist already has:
                boolean canAdd = !addingSongToPlaylist || !currentPlaylistPanel.getPlayListSongs().contains(songPanel);
                if(canAdd) {
                    allSongs.add(songPanel);
                    songPanel.setBackground(new Color(23, 23, 23));//setting default background in case it doesn't
                    songPanel.unSelect();//unSelecting if it's selected on previous adding panel.
                    constraints.gridy = gridy;
                    constraints.gridx = gridx;
                    this.add(songPanel, constraints);
                    if (gridx < 3) {
                        gridx++;
                    } else {
                        gridx = 0;
                        gridy++;
                    }
                }
            }
        if(addingSongToPlaylist){//if we show this songs to add, we need done button at the end.
            JPanel doneContainer = createOptionContainer(tickLabel,doneLabel);
            constraints.gridx = 0;
            constraints.gridy++;
            this.add(doneContainer,constraints);
        }
        currentPlaying = allSongs;
        //updating center part of center panel:
        this.repaint();
        this.revalidate();
    }

    /**
     * this method add an album to albumSongs HashMap if it's not exist
     * or add new songs to existing album if given songs are new.
     *
     * @param albumTitle title of album which is a key of HashMap
     * @param albumMusicsInfo list of albumSongs info which has similar albums.
     */
    public void addAlbum(String albumTitle, ArrayList<MP3Info> albumMusicsInfo){
        if(!albumPanels.containsKey(albumTitle)) {//if album is a new album
            AlbumPanel albumPanel = createAlbumPanel(albumMusicsInfo);
            albumPanels.put(albumTitle, albumPanel);
            showHome();//showing home after created new album to show it's added.
        }
        else//if album added before we just add new songs
            albumPanels.get(albumTitle).addNewSongs(albumMusicsInfo,this);

    }

    /**
     *this method create an album panel which if user press it, it shows music panels related to this album.
     * it gives image and title from first mp3info in given ArrayList.
     *
     * @param albumMusicsInfo list of music infos has similar album name
     * @return an album panel
     */
    private AlbumPanel createAlbumPanel(ArrayList<MP3Info> albumMusicsInfo){
        MP3Info firstMP3Info = albumMusicsInfo.get(0);
        AlbumPanel album = null;
        String description = "Album contains "+albumMusicsInfo.size()+" songs";
        try {//creating an album panel with its listener
            album = new AlbumPanel(firstMP3Info.getImage(),firstMP3Info.getTitle(),description,albumMusicsInfo,this,this);
        } catch (InvalidDataException | IOException | UnsupportedTagException e) {
            JOptionPane.showMessageDialog(null, "Error reading mp3 file image","An Error Occurred",JOptionPane.ERROR_MESSAGE);
        }
        return album;
    }

    /**
     * this method creates default play lists which must to exist at first playing of program.(only be called at class constructor).
     */
    private void createDefaultPlayLists(){
        try {
            BufferedImage favoriteSongsImage = ImageIO.read(new File("Images/FavoriteSong.png"));
            PlayListPanel favoriteSongs = new PlayListPanel(favoriteSongsImage,"Favorite Songs","Favorite albumSongs chosen by user",this);
            playListPanels.put("Favorite Songs",favoriteSongs);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading favorite albumSongs image","An Error Occurred",JOptionPane.ERROR_MESSAGE);
        }
        try {
            BufferedImage sharedSongImage = ImageIO.read(new File("Images/SharedSongs.jpg"));
            PlayListPanel sharedSongs = new PlayListPanel(sharedSongImage,"Shared Songs","Shared albumSongs between users",this);
            playListPanels.put("Shared Songs",sharedSongs);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading shared albumSongs image","An Error Occurred",JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void addToFavoritePlayList(String directory) {
        try {
            MP3Info mp3Info = new MP3Info(directory);
            if(albumPanels.get(mp3Info.getAlbum()) != null){
                for( SongPanel songPanel : albumPanels.get(mp3Info.getAlbum()).getSongPanels())
                    if(songPanel.getSongTitle().equals(mp3Info.getTitle())) {
                        playListPanels.get("Favorite Songs").addSong(songPanel);
                        break;
                    }
            }
            else
                JOptionPane.showMessageDialog(null, "This song doesn't belong to any album!","An Error Occurred",JOptionPane.ERROR_MESSAGE);
        } catch (IOException | NoSuchFieldException e) {
            JOptionPane.showMessageDialog(null, "Error reading mp3 file","An Error Occurred",JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void removeFromFavoritePlayList(String directory) {
        try {
            MP3Info mp3Info = new MP3Info(directory);
            playListPanels.get("Favorite Songs").removeSong(mp3Info.getTitle());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading mp3 file","An Error Occurred",JOptionPane.ERROR_MESSAGE);
        } catch (NoSuchFieldException e) {
            JOptionPane.showMessageDialog(null, "Error finding mp3 file","An Error Occurred",JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public boolean isSongLiked(String directory) {
        PlayListPanel favoriteSongsPlayLists = playListPanels.get("Favorite Songs");
        ArrayList<SongPanel> favoriteSongPanels = favoriteSongsPlayLists.getPlayListSongs();
        try {
            MP3Info mp3Info = new MP3Info(directory);
            for(SongPanel songPanel : favoriteSongPanels){
                if(songPanel.getSongTitle().equals(mp3Info.getTitle()))
                 return true;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading mp3 file","An Error Occurred",JOptionPane.ERROR_MESSAGE);
        } catch (NoSuchFieldException e) {
            JOptionPane.showMessageDialog(null, "Error finding mp3 file","An Error Occurred",JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    /**
     * Creating playlist which shows it in center part with an image which said : Empty PlayList
     * @param title title of play list.
     * @param description description to show under the title.
     */
    public void createPlayList(String title, String description){
        if(!playListPanels.containsKey(title)){//if this playlist doesn't exist.
            PlayListPanel newPlayListPanel = new PlayListPanel(emptyPlayListImage,title, description,this);
            playListPanels.put(title,newPlayListPanel);
        }
    }

    /**
     * this method adds a song to given playlist.
     * @param playListTitle  title of playlist as a key of HashMap.
     * @param songDirectory directory of music to add.
     */
    public void addSongToPlayList(String playListTitle, String songDirectory){
        //should be implement later
    }

    /**
     * this method remove a song from given playlist.
     * @param playListTitle title of playlist as a key of HashMap
     * @param songTitle title of song we want to remove.
     */
    public void removeSongFromPlayList(String playListTitle, String songTitle){
        if(playListPanels.containsKey(playListTitle))//if playlist exist
            playListPanels.get(playListTitle).removeSong(songTitle);
    }

    @Override
    public void showLyrics(ArrayList<String> lyricsLines) {
        this.removeAll();//removing all components in center part
        //initializing grids:
        constraints.gridy = 0;
        constraints.gridx = 0;
        for(String lyricsLine : lyricsLines){
            JLabel lineLabel = new JLabel(lyricsLine);
            lineLabel.setForeground(new Color(219,219,219));//setting label color
            this.add(lineLabel,constraints);
            constraints.gridy++;
        }
        //updating page:
        this.repaint();
        this.revalidate();
    }

    /**
     * @return title of play lists, to show as a JLabel in west panel.
     */
    public ArrayList<String> getPlayistTitles(){
        return new ArrayList<>(playListPanels.keySet());
    }

    /**
     * @return title of albums to show as a JLabel in west panel.
     */
    public ArrayList<String> getAlbumTitles(){
        return new ArrayList<>(albumPanels.keySet());
    }

    /**
     * this method adds a listener to add song to playlist option:
     * when mouse entered: it become brighter.
     * when mouse exited: it turn to previous form.
     * when mouse clicked: it show all songs which are not in playlist to add.
     */
    private void createAddSongToPlayListListener(){
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addingSongToPlaylist = true;//this cause showAllSong method doesn't consider existing song and add a option at the end.
                addingSongPanels = new ArrayList<>();//this creates a temporary memory space which hold adding song panels.
                showAllSongs();//show all songs without songs that playlist already has.
                mouseExited(e);//turn its gui like mouse exited.
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                try {
                    plusImage = ImageIO.read(new File("Icons/PlusSong.png"));
                    plusLabel.setIcon(new ImageIcon(plusImage));
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(null, "Error reading plus song image","An Error Occurred",JOptionPane.ERROR_MESSAGE);
                }
                addSongToPlayListLabel.setForeground(new Color(179,179,179));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                try {
                    plusImage = ImageIO.read(new File("Icons/PlusSong-no-select.png"));
                    plusLabel.setIcon(new ImageIcon(plusImage));
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(null, "Error reading plus song image","An Error Occurred",JOptionPane.ERROR_MESSAGE);
                }
                addSongToPlayListLabel.setForeground(new Color(120,120,120));
            }
        };
        plusLabel.addMouseListener(mouseAdapter);
        addSongToPlayListLabel.addMouseListener(mouseAdapter);
    }

    /**
     * this method adds a listener to done option:
     * when mouse entered: it become brighter.
     * when mouse exited: it turn to previous form.
     * when mouse clicked: it add all selected music to playlist and return back.
     */
    private void createDoneListener() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(addingSongToPlaylist) {
                    currentPlaylistPanel.getPlayListSongs().addAll(addingSongPanels);//adding selected songs to playlist.
                    addingSongPanels = null;//we don't need to this anymore, let garbage collector delete that!
                }
                else if(removeSongFromPlaylist){
                    currentPlaylistPanel.getPlayListSongs().removeAll(removingSongPanels);//removing selected songs from playlist
                    removingSongPanels = null;//we don't need to this anymore, let garbage collector delete that!
                    removeSongFromPlaylist = false;
                }
                showPlayListSongs(currentPlaylistPanel.getTitle());//coming back to current playlist.
                mouseExited(e);//turn its gui like mouse exited.
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                try {
                    tickImage = ImageIO.read(new File("Icons/Tick.png"));
                    tickLabel.setIcon(new ImageIcon(tickImage));
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(null, "Error reading tick image", "An Error Occurred", JOptionPane.ERROR_MESSAGE);
                }
                doneLabel.setForeground(new Color(179, 179, 179));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                try {
                    tickImage = ImageIO.read(new File("Icons/Tick-no-select.png"));
                    tickLabel.setIcon(new ImageIcon(tickImage));
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(null, "Error reading tick image", "An Error Occurred", JOptionPane.ERROR_MESSAGE);
                }
                doneLabel.setForeground(new Color(120, 120, 120));
            }
        };
        tickLabel.addMouseListener(mouseAdapter);
        doneLabel.addMouseListener(mouseAdapter);
    }

    /**
     * this method adds a listener to remove song from playlist option:
     * when mouse entered: it become brighter and red!
     * when mouse exited: it turn to previous form.
     * when mouse clicked: it show songs in playlist to remove.
     */
    private void createRemoveSongFromPlaylistListener(){
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                removeSongFromPlaylist = true;//this cause showSong method show done at the end
                removingSongPanels = new ArrayList<>();//this creates a temporary memory space which hold removing song panels.
                showSongs(currentPlaylistPanel.getPlayListSongs());//show existing song in playlist to remove.
                mouseExited(e);//turn its gui like mouse exited.
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                try {
                    minusImage = ImageIO.read(new File("Icons/Minus.png"));
                    minusLabel.setIcon(new ImageIcon(minusImage));
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(null, "Error reading minus image","An Error Occurred",JOptionPane.ERROR_MESSAGE);
                }
                removeSongFromPlaylistLabel.setForeground(new Color(179,179,179));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                try {
                    minusImage = ImageIO.read(new File("Icons/Minus-no-select.png"));
                    minusLabel.setIcon(new ImageIcon(minusImage));
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(null, "Error reading plus song image","An Error Occurred",JOptionPane.ERROR_MESSAGE);
                }
               removeSongFromPlaylistLabel.setForeground(new Color(120,120,120));
            }
        };
        minusLabel.addMouseListener(mouseAdapter);
        removeSongFromPlaylistLabel.addMouseListener(mouseAdapter);
    }

    /**
     * this method adds a listener to swap option:
     * when mouse entered: it become brighter and blue!
     * when mouse exited: it turn to previous form.
     * when mouse clicked: it select song panel to swap. after second time it swap!
     */
    private void createSwapListener(){
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                isSwaping = !isSwaping;
                try {
                    swapImage = ImageIO.read(new File("Icons/Swap-selected.png"));
                    swapLabel.setIcon(new ImageIcon(swapImage));
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(null, "Error reading swap image","An Error Occurred",JOptionPane.ERROR_MESSAGE);
                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if(!isSwaping) {
                    try {
                        swapImage = ImageIO.read(new File("Icons/Swap.png"));
                        swapLabel.setIcon(new ImageIcon(swapImage));
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(null, "Error reading swap image", "An Error Occurred", JOptionPane.ERROR_MESSAGE);
                    }
                    swapTextLabel.setForeground(new Color(179, 179, 179));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!isSwaping) {
                    try {
                        swapImage = ImageIO.read(new File("Icons/Swap-no-select.png"));
                        swapLabel.setIcon(new ImageIcon(swapImage));
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(null, "Error reading swap image", "An Error Occurred", JOptionPane.ERROR_MESSAGE);
                    }
                    swapTextLabel.setForeground(new Color(120, 120, 120));
                }
            }
        };
        swapLabel.addMouseListener(mouseAdapter);
        swapTextLabel.addMouseListener(mouseAdapter);
    }
    /**
     * this method creates a container which holds 2 labels: image label and description label.
     *
     * @return desired JPanel.
     */
    private JPanel createOptionContainer(JLabel imageLabel, JLabel descriptionLabel){
        //creating a container to cover given labels:
        JPanel coveringContainer = new JPanel();
        coveringContainer.setOpaque(false);//removing its background
        coveringContainer.setLayout(new BoxLayout(coveringContainer,BoxLayout.LINE_AXIS));
        //adding features:
        coveringContainer.add(imageLabel);
        coveringContainer.add(Box.createHorizontalStrut(5));//adding spaces between components.
        coveringContainer.add(descriptionLabel);
        return coveringContainer;
    }
}
