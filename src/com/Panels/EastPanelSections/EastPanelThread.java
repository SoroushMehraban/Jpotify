package com.Panels.EastPanelSections;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class EastPanelThread extends Thread
{
    @Override
    public void run()  {
        try
        {
            ServerSocket mainSocket=new ServerSocket(2019);
            Socket connectedSocket=mainSocket.accept();
            System.out.println("someone connected.");
            OutputStream serverSocketOutputStream=connectedSocket.getOutputStream();
            InputStream serverSocketInputStream=connectedSocket.getInputStream();
            PrintWriter serverSocketWriter=new PrintWriter(serverSocketOutputStream,true);
            Scanner serverSocketReader=new Scanner(serverSocketInputStream);
            serverSocketWriter.write("Hi,you are connected to this server.");
            System.out.println(serverSocketReader.nextLine());
            //mainSocket.accept();

        }
        catch (Exception e)
        {
            //this.interrupt();
            System.err.println("(server socket)CAN NOT CONNECT.THERE IS A PROBLEM. ");

        }

    }
    private void startCummunicating()
    {



    }





}
