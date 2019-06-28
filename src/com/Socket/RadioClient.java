package com.Socket;

import com.GUIFrame.GUIFrame;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class RadioClient {
    private InputStream socketIn;
    private OutputStream socketOut;
    private PrintWriter socketWriter;
    private Scanner socketReader;
    private HashMap<String, String> titles;
    private FileOutputStream fos;

    public RadioClient() {
        Thread receivingThread = createReceivingThread();
        receivingThread.start();
    }

    private Thread createReceivingThread() {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                //trying to connect to server
                //connectToServer();
                System.out.println("trying to coonect...");
                try {
                    Socket mySocket = new Socket("95.216.52.203", 1999);
                    System.out.println("connected");
                    socketIn = mySocket.getInputStream();
                    socketOut = mySocket.getOutputStream();
                    socketWriter = new PrintWriter(socketOut, true);
                    socketReader = new Scanner(socketIn);
                    while (true) {
                       /* try {
                            Thread.sleep(5000);
                        } catch (InterruptedException ignored) {
                        }*/
                        socketWriter.println("Update");//telling server we want to update.
                        System.out.println("Trying to receive..");
                        while (socketReader.hasNext()) {
                            System.out.println("Adding to Radio songs...");
                            String title = socketReader.nextLine();
                            String artist = socketReader.nextLine();
                            GUIFrame.addRadioSong(title, artist);
                            System.out.println("added!");
                        }
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Error connecting to server", "Socket Error", JOptionPane.ERROR_MESSAGE);
                }
                /*try {
                    FileOutputStream fos;
                    System.out.println("trying to coonect...");
                    Socket mySocket = new Socket("95.216.52.203", 1999);
                    System.out.println("connected");
                    InputStream socketIn = mySocket.getInputStream();
                    System.out.println("downloading...");
                    fos = new FileOutputStream("test.mp3");
                    int count;
                    while ((count = socketIn.read()) != -1) {
                        fos.write(count);
                    }
                    fos.close();
                    socketIn.close();
                    mySocket.close();
                    System.out.println("downloaded");
                } catch (IOException e) {
                }*/
            }
        });
    }
}