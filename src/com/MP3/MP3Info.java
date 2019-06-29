package com.MP3;

import com.GUIFrame.GUIFrame;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * This class gives information which is given from meta data of MP3 file(ID3v1 format)
 *
 * @author Soroush Mehraban @ Morteza Damghani
 * @version 1.0
 */
public class MP3Info {
    private String title;
    private String artist;
    private String album;
    private String inputFileDirectory;

    /**
     * class constructor.
     * at first if file exists, it throws additional bytes away and then extract data at last 125 bytes of mp3 file.
     *
     * @param inputFileDirectory input file directory.
     * @throws IOException          if can not read from mp3 file.
     * @throws NoSuchFieldException if there isn't any mp3 file in directory.
     */
    public MP3Info(String inputFileDirectory) throws IOException, NoSuchFieldException {
        this.inputFileDirectory = inputFileDirectory;
        File file = new File(inputFileDirectory);
        if (file.exists() && file.isFile()) {
            FileInputStream in = new FileInputStream(inputFileDirectory);
            long additionalBytesLength = file.length() - 125;//we only need last 125 bytes(ID3v1 format)
            byte[] additionalBytes = new byte[(int) additionalBytesLength];
            in.read(additionalBytes);//throwing additional bytes away.
            title = findInfo(in);
            artist = findInfo(in);
            album = findInfo(in);
        } else throw new NoSuchFieldException();
    }

    /**
     * finding an info from mp3 metadata
     *
     * @param in inputstream from file
     * @return string info
     * @throws IOException if can not read from file
     */
    private String findInfo(FileInputStream in) throws IOException {
        ArrayList<Byte> infoBytes = new ArrayList<>();
        int input;
        boolean firstInput = true;
        while ((input = in.read()) != 0 || firstInput)
            if (input != 0) {
                infoBytes.add((byte) input);
                firstInput = false;
            }
        byte[] infoBytesArray = new byte[infoBytes.size()];
        for (int i = 0; i < infoBytes.size(); i++) {
            infoBytesArray[i] = infoBytes.get(i);
        }
        return new String(infoBytesArray);
    }

    /**
     * @return title of mp3 file. if any [tag] exists. it doesn't consider it.
     */
    public String getTitle() {
        if (title.split("[\\[]")[0].length() > 1)
            return title.split("[\\[]")[0].trim();
        else return title;
    }

    /**
     * @return artist of mp3 file. if any [tag] exists. it doesn't consider it.
     */
    public String getArtist() {
        if (artist.split("[\\[]")[0].length() > 1)
            return artist.split("[\\[]")[0];
        else return artist;
    }

    /**
     * @return album of mp3 file.
     */
    public String getAlbum() {
        return album;
    }

    /**
     * @return directory of mp3 file.
     */
    public String getInputFileDirectory() {
        return inputFileDirectory;
    }

    /**
     * @return image of music.
     * @see Mp3File
     * @see ID3v2
     */
    public BufferedImage getImage() throws InvalidDataException, IOException, UnsupportedTagException {
        Mp3File mp3File = new Mp3File(inputFileDirectory);//creating a MP3file of object to give us desired image
        ID3v2 id3v2Tag = mp3File.getId3v2Tag();
        byte[] imageData = id3v2Tag.getAlbumImage();//getting bytes of our image.
        ByteArrayInputStream bis = new ByteArrayInputStream(imageData);//converting bytes to ByteArrayInputStream so it can be read by ImageIO
        return ImageIO.read(bis);//desired image
    }

    /**
     * This method searches given song in google and retrieves lyrics of that if no issues happens at the middle of that.
     * For doing this, a URL which is a default search pattern of google is created and search message is given to it.
     * Due to the google search pattern, every spaces in search message replaced with '+' character.
     * after running the page up with helps of JSoup library, we looking for div.Oh5wg in that page(which is found by google chrome inspect element)
     * then we search every span tag and extract the text out of tag.
     * google puts every line in a pair of span tag. so we made a firstSpan boolean to extract only one of those pairs.
     * due to additional words between and at the end of lines. we use 2 if condition to remove additional words.
     *
     * @return ArrayList of lyrics lines.
     * @see Element
     * @see Document
     */
    public ArrayList<String> getLyrics() {
        JDialog dialog = createLoadingDialog();
        SwingWorker<ArrayList<String>, Void> logicDoer = new SwingWorker<>() {
            @Override
            protected ArrayList<String> doInBackground() {
                ArrayList<String> lyricsLines = new ArrayList<>();
                boolean firstSpan = true;
                String searchMessage = (getArtist() + getTitle() + "+lyrics").replace(" ", "+");
                String URL = "https://www.google.com/search?ei=6d0NXareDdKegQaEqJjgDg&q=" + searchMessage + "&oq=" + searchMessage + "&gs_l=psy-ab.3..0.2699.2699..2999...0.0..0.217.217.2-1......0....1..gws-wiz.wFnbg5G6yho";
                try {
                    Document doc = Jsoup.connect(URL).get();
                    Element lyrics = doc.select("div.Oh5wg").get(0);
                    for (Element line : lyrics.getElementsByTag("span")) {
                        if (line.text().contains("Lyrics weren't translated."))//removing additional text at the end of search query.
                            break;
                        if (line.text().equals("…")) {//removing additional words between lines.
                            lyricsLines.remove(lyricsLines.size() - 1);
                            continue;
                        }
                        if (firstSpan) {
                            lyricsLines.add(line.text());
                        }
                        firstSpan = !firstSpan;
                    }
                    dialog.dispose();
                    return lyricsLines;
                } catch (IOException | IndexOutOfBoundsException e) {//if process didn't succeed:
                    lyricsLines.add("Sorry, We couldn't find any lyrics for this music.");
                    lyricsLines.add("Reason(s)");
                    lyricsLines.add("1- Internet connection");
                    lyricsLines.add("2- There is a additional tag besides title or artist name");
                    lyricsLines.add("3- Your song is not famous enough");
                    lyricsLines.add("3- Your artist does not sing anything at all");
                    dialog.dispose();
                    return lyricsLines;
                }
            }

            @Override
            protected void done() {
                dialog.dispose();
            }
        };
        logicDoer.execute();//execute logicDoer to find lyrics in background and dialog doesn't interrupt this.
        dialog.setVisible(true);//visible dialog until logicDoer task finish.
        try {
            return logicDoer.get();
        } catch (InterruptedException | ExecutionException e) {//if logicDoer doesn't succeed.
            ArrayList<String> errorOccured = new ArrayList<>();
            errorOccured.add("Sorry, an error occurred");
            return errorOccured;
        }
    }

    /**
     * @return JDialog to show Lyrics is loading.
     */
    private JDialog createLoadingDialog() {
        Icon icon = null;
        try {
            BufferedImage loadingImage = ImageIO.read(new File("Icons/Loading.png"));
            icon = new ImageIcon(loadingImage);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "error reading loading image icon.");
        }
        //creating a dialog without any button:
        JOptionPane optionPane = new JOptionPane("Loading lyrics..", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, icon, new Object[]{}, null);
        JDialog dialog = new JDialog();
        //setting dialog location:
        Point guiPoint = GUIFrame.getInstance().getLocation();
        Dimension guiSize = GUIFrame.getInstance().getSize();
        dialog.setLocation(guiPoint.x + guiSize.width / 3, guiPoint.y + guiSize.height / 2);

        dialog.setUndecorated(true);//removing title bar
        dialog.setModal(true);//forces to show dialog and it's content
        dialog.setContentPane(optionPane);//setting messages and icons.
        dialog.pack();//packing to remove additional spaces.
        return dialog;
    }
}
