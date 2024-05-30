package GameObjects;

import DefPack.*;
import EGraphics.Sprite;
import EGraphics.SpriteAnimation;
import EGraphics.TextureAtlas;
import Game.ELevel;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Bullet extends GameObject 
{
    public static ArrayList<Bullet> BulletList = new ArrayList<Bullet>();
    public static ArrayList<Bullet> DeleteList = new ArrayList<Bullet>();
    
    public static enum Heading{
        Up    (0, 4),
        Left  (1, 4),
        Down  (2, 4),
        Right (3, 4); 
        
        private int x,y;
        
        Heading(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
        
        protected BufferedImage Texture(TextureAtlas Atlas)
        {
            return Atlas.CutFP(x*ESettings.SpriteSize, y*ESettings.SpriteSize,1,ESettings.BulletSize);
        }
    }
    
    private Heading heading;
    private Map<Heading, Sprite> SpriteMap;
    
    public void Update()
    {
        float NewX = x;
        float NewY = y;
        if(heading == Heading.Up)
        {
            NewY-=ESettings.BulletSpeed;
        }
        if(heading == Heading.Down)
        {
            NewY+=ESettings.BulletSpeed;
        }
        if(heading == Heading.Left)
        {
            NewX-=ESettings.BulletSpeed;
        }
        if(heading == Heading.Right)
        {
            NewX+=ESettings.BulletSpeed;
        }
        
        if(GameObject.CheckCollision(new GameObject(GameObjectType.Bullet,NewX,NewY,ESettings.BulletSize), ESettings.enemy) && !ESettings.enemy.Imortl)
        {
            ESettings.enemy.Dead();
            DeleteList.add(this);
            return;
        }
        
        if(GameObject.CheckCollision(new GameObject(GameObjectType.Bullet,NewX,NewY,ESettings.BulletSize), ESettings.player) && !ESettings.player.Imortl)
        {
            ESettings.player.Dead();
            DeleteList.add(this);
            return;
        }
        
        if(ELevel.CheckMapCollision(new GameObject(GameObjectType.Bullet,NewX,NewY,ESettings.BulletSize), ESettings.lvl))
        {
            DeleteList.add(this);
            return;
        }
        
        if(GameObject.CheckCollision(new GameObject(this.Type,NewX,NewY,ESettings.SpriteSize), new GameObject(GameObjectType.Flag,ESettings.TF.FlagX *ESettings.BlockSize ,ESettings.TF.FlagY * ESettings.BlockSize,ESettings.SpriteSize)) && ESettings.TF.InGame)
        {
            ESettings.TF.Def();
            DeleteList.add(this);
            return;
        }
        if(GameObject.CheckCollision(new GameObject(this.Type,NewX,NewY,ESettings.SpriteSize), new GameObject(GameObjectType.Flag,ESettings.TS.FlagX *ESettings.BlockSize ,ESettings.TS.FlagY * ESettings.BlockSize,ESettings.SpriteSize))&& ESettings.TS.InGame)
        {
            ESettings.TS.Def();
            DeleteList.add(this);
            return;
        }
        
        x = NewX;
        y = NewY;
    }
    
    public void Render(Graphics2D g)
    {
        SpriteMap.get(heading).render(g, x, y);
    }
    

    
    public Bullet(Heading heading ,int x, int y)
    {
        super(GameObjectType.Bullet, x,y, ESettings.BulletSize);
        this.heading = heading;
        SpriteMap = new HashMap<Heading,Sprite>();
        for(Heading h: Heading.values())
        {
            SpriteAnimation SA = new SpriteAnimation(h.Texture(ESettings.Atlas),2,ESettings.BulletSize);
            Sprite sprite = new Sprite(SA,ESettings.BlockScale);
            SpriteMap.put(h, sprite);
        }
    }
    
    public static void Spawn(Heading heading,int x, int y)
    {
        Bullet NB = new Bullet(heading,x,y);
        BulletList.add(NB);
    }
}
