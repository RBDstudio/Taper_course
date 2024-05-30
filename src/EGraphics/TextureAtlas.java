package EGraphics;

import DefPack.ESettings;
import Game.Resurses;
import java.awt.image.BufferedImage;


public class TextureAtlas 
{
    private BufferedImage Image;
    private int SpriteSize;
    
    public TextureAtlas(String Name,int SpriteSize)
    {
        this.SpriteSize = SpriteSize;
        Image = Resurses.LoadImage(Name);
    }
    
    public BufferedImage Cut(int x, int y,int FrameCount)
    {
        return Image.getSubimage(x*SpriteSize, y*SpriteSize, FrameCount * ESettings.SpriteSize,ESettings.SpriteSize);
    }
    
    public BufferedImage CutFP(int x, int y,int FrameCount, int Size)
    {
        return Image.getSubimage(x, y, FrameCount * Size,Size);
    }
}
