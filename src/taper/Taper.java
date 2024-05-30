package taper;
import DefPack.EFrame;
import DefPack.ESettings;
import Game.Engine;
import Game.Multiplayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Taper{

    public static void main(String[] args) throws InterruptedException {
        try {
                System.out.println("start");
                Multiplayer.socket = new Socket("localhost",9643);
                Multiplayer.IsHost = false;
                Multiplayer.in = new BufferedReader(new InputStreamReader(Multiplayer.socket.getInputStream()));
                Multiplayer.out = new PrintWriter(Multiplayer.socket.getOutputStream(), true);
            } catch (IOException ex) 
            {
                Multiplayer.IsHost = true;
                try {
                    Multiplayer.serverSocket = new ServerSocket(9643);
                    Multiplayer.socket = Multiplayer.serverSocket.accept();
                    Multiplayer.in = new BufferedReader(new InputStreamReader(Multiplayer.socket.getInputStream()));
                    Multiplayer.out = new PrintWriter(Multiplayer.socket.getOutputStream(), true);
                } catch (IOException ex1) {
                    Logger.getLogger(Taper.class.getName()).log(Level.SEVERE, null, ex1);
                    return;
                }
            }
        EFrame.CreateWindow(ESettings.Name, ESettings.Width, ESettings.Height, ESettings.ClearColor, ESettings.BuffersCount);
        while(true)
        {
            EFrame.ChangeName("You: " + ESettings.YouScore + " | Enemy: " +  ESettings.EnemyScore);
            Engine GM = new Engine();
            GM.start();
            ESettings.IsRunning = true;
            GM.join();
            EFrame.ChangeName("END");
            EFrame.ClearFrame();
        }
    }
}
