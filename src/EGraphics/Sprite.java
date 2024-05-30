package EGraphics;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprite 
{
    private SpriteAnimation Image;
    private BufferedImage ImageBuf;

    public Sprite(SpriteAnimation SA, float Scale)
    {
        Image = SA;
        ImageBuf = Image.GetSprite(0);
        ImageBuf = Sprite.Resize(ImageBuf, Scale);
    }
    
    public void render(Graphics2D g, float x, float y)
    {
        g.drawImage(ImageBuf,(int)(x),(int)(y),null);
    }
    
    public static void render(Graphics2D g, float x, float y, BufferedImage ImageR)
    {
        g.drawImage(ImageR,(int)(x),(int)(y),null);
    }
    
    public static BufferedImage Resize(BufferedImage image, float Scale)
    {
        int Width = (int)(image.getWidth() * Scale);
        int Height = (int)(image.getHeight() * Scale);
        BufferedImage Buffer = new BufferedImage(Width,Height,BufferedImage.TYPE_INT_ARGB);
        Buffer.getGraphics().drawImage(image,0,0,Width,Height,null);
        return Buffer;
    }
}
