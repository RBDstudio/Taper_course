package EGraphics;

import DefPack.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Tile
{
    private BufferedImage Image;
    private TileType Type;
    
    public Tile(BufferedImage Image, TileType Type)
    {
        this.Type = Type;
        this.Image = Image;
        this.Image = Sprite.Resize(Image, ESettings.BlockScale);
    }
    
    public void Render(Graphics2D g, int x, int y)
    {
        g.drawImage(Image, x, y, null);
    }
    
    protected TileType Type()
    {
        return Type;
    }
}
