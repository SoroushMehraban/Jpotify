package com.Panels.EastPanelSections;

import java.net.ServerSocket;

public class EastPanelThread implements Runnable
{
    @Override
    public void run()  {
       try
       {
           ServerSocket mainSocket=new ServerSocket(2019);
           mainSocket.accept();
           System.out.println("someone connected.");
           mainSocket.accept();

       }
        catch (Exception e)
        {
            System.err.println("CAN NOT CONNECT");

        }

    }




}
