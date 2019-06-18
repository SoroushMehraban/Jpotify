package com.MP3;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

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
     * @throws IOException if can not read from mp3 file.
     * @throws NoSuchFieldException if there isn't any mp3 file in directory.
     */
    public MP3Info(String inputFileDirectory) throws IOException, NoSuchFieldException {
        this.inputFileDirectory = inputFileDirectory;
        File file = new File(inputFileDirectory);
        if(file.exists() && file.isFile()){
            FileInputStream in = new FileInputStream(inputFileDirectory);
            long additionalBytesLength = file.length() - 125;//we only need last 125 bytes(ID3v1 format)
            byte[] additionalBytes = new byte[(int) additionalBytesLength];
            in.read(additionalBytes);//throwing additional bytes away.
            title = findInfo(in);
            artist = findInfo(in);
            album = findInfo(in);
        }
        else throw new NoSuchFieldException();
    }

    /**
     * finding an info from mp3 metadata
     * @param in inputstream from file
     * @return string info
     * @throws IOException if can not read from file
     */
    private String findInfo(FileInputStream in) throws IOException {
        ArrayList<Byte> infoBytes = new ArrayList<>();
        int input;
        boolean firstInput = true;
        while((input = in.read()) != 0 || firstInput)
            if(input != 0) {
                infoBytes.add((byte) input);
                firstInput = false;
            }
        byte[] infoBytesArray = new byte[infoBytes.size()];
        for (int i = 0; i < infoBytes.size() ; i++) {
            infoBytesArray[i] = infoBytes.get(i);
        }
        return new String(infoBytesArray);
    }

    /**
     * @return title of mp3 file.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return artist of mp3 file.
     */
    public String getArtist() {
        return artist;
    }

    /**
     * @return album of mp3 file.
     */
    public String getAlbum() {
        return album;
    }

    /**
     *
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
}
