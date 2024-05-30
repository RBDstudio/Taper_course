package EGraphics;

import java.awt.image.BufferedImage;

public class SpriteAnimation 
{
    private BufferedImage Image;
    private int SpriteCount;
    private int Scale;
    private int SptireInRow;
    
    public SpriteAnimation(BufferedImage Image, int SpriteCount, int Scale)
    {
        this.Image = Image;
        this.SpriteCount = SpriteCount;
        this.Scale = Scale;
        this.SptireInRow = Image.getWidth()/ Scale;
    }
    
    public BufferedImage GetSprite(int Index)
    {
        Index = Index % SpriteCount;
        
        int x = Index % SptireInRow * Scale;
        int y = Index / SptireInRow * Scale;
        
        return Image.getSubimage(x, y, Scale, Scale);
    }
}
