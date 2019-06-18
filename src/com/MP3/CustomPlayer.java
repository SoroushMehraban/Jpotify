package com.MP3;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import javazoom.jl.player.Player;

import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * This class is program player created with help of Jlayer.
 * it has extra features compatible with our programs.
 * it can play,pause and resume selected music.
 *
 * @author Soroush Mehraban & Morteza Damghani
 * @version 1.0
 */
public class CustomPlayer {
    public static final int START_TIME = 0;
    private Player player;
    private FileInputStream fileInputStream;
    private boolean isPlaying;
    private String directory;
    private int total;
    private int stopped;

    /**
     * Class constructor, it only gives mp3 file directory from user.
     * @param directory directory of mp3 file.
     */
    public CustomPlayer(String directory){
        this.directory = directory;
    }

    /**
     * this method happens if user pause playing music.
     * it store remaining bytes to stopped variable so next time if it wants to resume, it start with total - stopped bytes.
     */
    public void pause() {
        if (isPlaying) {//if music is playing
            try {
                stopped = fileInputStream.available();//storing remaining bytes to play later.
                player.close();//close the entire player.
                isPlaying = false;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error pausing mp3 file");
            }
        }
    }

    /**
     * this method resume current music if user paused it before.
     */
    public void resume(){
        if(!isPlaying) {//if musing is not playing
            play(total - stopped);//play from remaining bytes.
            isPlaying = true;
        }
    }

    /**
     * overloaded method created to work with JProgressbar.
     * it changes stopped variable to where JProgressbar located.
     *
     * @param startingTimeDegree a number between 0 and 1 indicates how much time spends.
     */
    public void resume(double startingTimeDegree){
        stopped = total - (int) (total * startingTimeDegree);//generating remaining bytes amounts from point of start
        close();//closing current music(if we didn't close it plays beside previous one)
        play(total - stopped);//play from beginning point of JProgressbar
    }

    /**
     * this method plays music from wherever we want to.
     * @param pos point of start(CustomPlayer.START_TIME if we want to play from the beginning)
     */
    public void play(int pos){
        try{
            isPlaying = true;
            fileInputStream = new FileInputStream(directory);
            total = fileInputStream.available();
            if(pos > 0) fileInputStream.skip(pos);//skipping additional bytes
            player = new Player(new BufferedInputStream(fileInputStream));
            new Thread(//creating thread to play parallel with our program.
                    new Runnable(){
                        public void run(){
                            try{
                                player.play();
                            }catch(Exception e){
                                JOptionPane.showMessageDialog(null, "Error playing mp3 file");
                            }
                        }
                    }
            ).start();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error reading mp3 file");
        }
    }

    /**
     * @return total Time of music
     */
    public int getTotalSeconds() {
        int totalSeconds = 0;
        try {
            totalSeconds = (int) new Mp3File(directory).getLengthInSeconds();
        } catch (IOException | UnsupportedTagException | InvalidDataException e) {
            JOptionPane.showMessageDialog(null, "Error reading total seconds");
        }
        return totalSeconds;
    }

    /**
     * @return current time of music while playing(not necessary is playing,it can be paused)
     * @throws IOException if stream is closed.
     */
    public int getCurrentSeconds() throws IOException {
        int passedBytes = total - fileInputStream.available();//amount of bytes which played.
        return (int) ((passedBytes / (double) total) * getTotalSeconds()) - 3;
    }

    /**
     * closing player(helps us when another music is going to start in our program)
     */
    public void close(){
        player.close();
    }
}