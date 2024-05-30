package DefPack;

import Game.Input;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.util.Arrays;

public abstract class EFrame 
{
    private static JFrame Window;
    private static Canvas FrameContent;
    
    private static BufferedImage RenderedFrame;
    private static int[] RenderedFrameData;
    private static Graphics RenderedFrameGraphics;
    private static int ClearColor;
    private static BufferStrategy RenderedFrameStrategy;
    

    
    
    public static void CreateWindow(String Name, int Wight, int Height, int NewClearColor, int BuffersCount)
    {
        ClearColor = NewClearColor;
        
        FrameContent = new Canvas();
        FrameContent.setPreferredSize(new Dimension(Wight,Height));
            
        Window = new JFrame(Name);
        Window.setResizable(false);
        Window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Window.getContentPane().add(FrameContent);
        Window.pack();
        Window.setVisible(true);
        
        RenderedFrame = new BufferedImage(Wight,Height,BufferedImage.TYPE_INT_ARGB);
        RenderedFrameData = ((DataBufferInt)RenderedFrame.getRaster().getDataBuffer()).getData();
        RenderedFrameGraphics = RenderedFrame.getGraphics();
        ((Graphics2D)RenderedFrameGraphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        FrameContent.createBufferStrategy(BuffersCount);
        RenderedFrameStrategy = FrameContent.getBufferStrategy();
    }
    
    public static void ClearFrame()
    {
        Arrays.fill(RenderedFrameData,ClearColor);
    }
    
    
    public static void SwapFrame()
    {
        Graphics g = RenderedFrameStrategy.getDrawGraphics();
        g.drawImage(RenderedFrame,0,0,null);
        RenderedFrameStrategy.show();
    }
    
    public static Graphics2D GetGraphics()
    {
        return (Graphics2D)RenderedFrameGraphics;
    }
    
    public static void DestroyWindow()
    {
        Window.dispose();
    }
    
    public static void ChangeName(String NewName)
    {
        Window.setTitle(NewName);
    }
    
    public static void AddInputListener(Input IL)
    {
        Window.add(IL);
    }
}
