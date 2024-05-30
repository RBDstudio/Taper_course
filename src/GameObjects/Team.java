package GameObjects;

import DefPack.*;
import EGraphics.*;
import java.awt.Graphics2D;

public class Team 
{
    public int SpawnX;
    public int SpawnY;
    public boolean InGame;
    
    private Sprite FlagSprite;
    private Sprite DefFlagSprite;
    
    public int FlagX;
    public int FlagY;
    
    public boolean IsEnemy;
    
    
    public Team(int SpawnX,int SpawnY,int FlagX,int FlagY, TextureAtlas TA)
    {
        InGame = true;
        
        this.SpawnX = SpawnX;
        this.SpawnY = SpawnY;
        
        this.FlagX = FlagX;
        this.FlagY = FlagY;
        
        SpriteAnimation SA = new SpriteAnimation(TA.Cut(4, 3,1),1,ESettings.SpriteSize);
        FlagSprite = new Sprite(SA,ESettings.BlockScale);
        SA = new SpriteAnimation(TA.Cut(5, 3,1),1,ESettings.SpriteSize);
        DefFlagSprite = new Sprite(SA,ESettings.BlockScale);
    }
    
    public void Update()
    {
        
    }
    
    public void Render(Graphics2D g)
    {
        FlagSprite.render(g, FlagX * ESettings.BlockSize, FlagY*ESettings.BlockSize);
    }
    
    public void Def()
    {
        FlagSprite = DefFlagSprite;
        if(IsEnemy)
        {
            ESettings.EnemyScore++;
        }
        else
        {
            ESettings.YouScore++;
        }
        InGame = false;
        ESettings.IsRunning = false;
    }
}
