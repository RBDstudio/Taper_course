package Game;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.*;

public class Multiplayer 
{
    public static Socket socket;
    public static ServerSocket serverSocket;
    public static boolean IsHost;
    public static BufferedReader in;
    public static PrintWriter out;
    
    public static void Send(String message)
    {
        out.println(message);
    }
}
