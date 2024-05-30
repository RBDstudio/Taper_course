package Game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Resurses 
{
    public static final String Path ="Resurses/";
    
    public static BufferedImage LoadImage(String FileName)
    {
        BufferedImage Image = null;
        
        try
        {
            Image = ImageIO.read(new File("C:/" + FileName));
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        
        return Image;
    }
}
