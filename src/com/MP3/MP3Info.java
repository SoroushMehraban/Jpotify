package com.MP3;

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

    /**
     * class constructor.
     * at first if file exists, it throws additional bytes away and then extract data at last 125 bytes of mp3 file.
     *
     * @param inputFileDirectory input file directory.
     * @throws IOException if can not read from mp3 file.
     * @throws NoSuchFieldException if there isn't any mp3 file in directory.
     */
    public MP3Info(String inputFileDirectory) throws IOException, NoSuchFieldException {
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
        ArrayList<Byte> infoBytes = new ArrayList<Byte>();
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
}
