package com.Socket;

import com.MP3.MP3Info;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class RadioServer {
    private static Socket serverSocket;
    private static InputStream socketIn;
    private static OutputStream socketOut;
    private static Scanner socketReader;
    private static PrintWriter socketWriter;
    private static FileInputStream fis;

    public static void main(String[] args) {
        try {
            ServerSocket mainServerSocket = new ServerSocket(1999);
            System.out.println("waiting for someone to connect...");
            serverSocket = mainServerSocket.accept();
            System.out.println("someone is connected.");
            {

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            socketIn = serverSocket.getInputStream();
                            socketOut = serverSocket.getOutputStream();
                            socketReader = new Scanner(socketIn);
                            socketWriter = new PrintWriter(socketOut, true);
                            if (socketReader.nextLine().equals("Update")) {
                                sendSongsInfo();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                t.start();
            }
            /*if (mp3File.exists()) {
                FileInputStream fis = new FileInputStream(mp3File.getAbsolutePath());
                OutputStream socketOut = serverSocket.getOutputStream();
                System.out.println("sending mp3 file...");

                int count;
                while ((count = fis.read()) != -1) {
                    socketOut.write(count);
                }
                socketOut.close();
                fis.close();
                serverSocket.close();
                System.out.println("Sent!");
            }
            else System.out.println("couldn't find file");*/
        } catch (IOException e) {
            System.out.println("can not connect to the network.");
        }
    }

    private static void sendSongsInfo() {
        try {
            File mp3Folder = new File("test");
            File[] mp3files = mp3Folder.listFiles();
            if (mp3files != null) {
                System.out.println("Sending mp3 titles...");
                for (File mp3file : mp3files) {
                    System.out.println("Creating mp3 info...");
                    System.out.println(mp3file.getAbsolutePath());
                    MP3Info mp3Info = new MP3Info(mp3file.getAbsolutePath());
                    System.out.println("Created");
                    String songTitle = mp3Info.getTitle();
                    System.out.println(songTitle);
                    String songArtist = mp3Info.getArtist();

                    System.out.println(songArtist);
                    socketWriter.println(songTitle);
                    //socketWriter.flush();
                    System.out.println("Title sent");
                    socketWriter.println(songArtist);
                    //socketWriter.flush();
                    System.out.println("Artist sent");

                    /*byte[] imageBytes = mp3Info.getImage();
                    System.out.println("Sending image...");
                    socketOut.write(imageBytes);
                    System.out.println("Image sent");
                    socketOut.flush();*/
                }
            }
        } catch (Exception e) {
            System.out.println("can not find mp3 file info");
        }
    }
}
