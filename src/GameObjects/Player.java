 package GameObjects;

import DefPack.*;
import EGraphics.*;
import Game.ELevel;
import Game.Input;
import Game.Multiplayer;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Player extends GameObject 
{
    public static enum Heading{
        Up    (0, 0),
        Left  (2, 0),
        Down  (4, 0),
        Right (6, 0); 
        
        private int x,y;
        
        Heading(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
        
        protected BufferedImage Texture(TextureAtlas Atlas)
        {
            return Atlas.Cut(x, y,2);
        }
    }
    
    private long lastShotTime;
    private Heading heading;
    private Map<Heading, SpriteAnimation> SpriteMap;
    private float AnimationTime;
    private float CAT;
    private int CurrentFrame;
    private int MH;
    
    public boolean Imortl;
    public static boolean IsAlive;
    
    public Player(float x,float y, float AnimationTime, Heading heading)
    {
        super(GameObjectType.Player, x*ESettings.BlockSize, y*ESettings.BlockSize,ESettings.SpriteSize);
        
        Imortl = false;
        IsAlive = true;
        
        lastShotTime = System.currentTimeMillis();
        
        this.heading = heading;
        
        if(this.heading == Player.Heading.Up)MH = 0;
        if(this.heading == Player.Heading.Left)MH = 1;
        if(this.heading == Player.Heading.Down)MH = 2;
        if(this.heading == Player.Heading.Right)MH = 3;
        
        SpriteMap = new HashMap<Heading,SpriteAnimation>();
        
        for(Heading h: Heading.values())
        {
            SpriteAnimation SA = new SpriteAnimation(h.Texture(ESettings.Atlas),2,ESettings.SpriteSize);
            Sprite sprite = new Sprite(SA,ESettings.BlockScale);
            SpriteMap.put(h, SA);
        }
        this.AnimationTime = AnimationTime;
        CurrentFrame = 0;
        System.out.println("Player ready");
    }
    
    public void Update(Input input) 
    {
        if(IsAlive)
        {
            float NewX = x;
            float NewY = y;
            if(input.GetKey(KeyEvent.VK_SPACE))
            {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastShotTime >= 1400) 
                {
                    if(heading == Heading.Up)Bullet.Spawn(Bullet.Heading.Up, (int)x+ESettings.BlockSize/2-3, (int)y - 5);
                    if(heading == Heading.Down)Bullet.Spawn(Bullet.Heading.Down, (int)x + ESettings.BlockSize/2-3, ((int)y + ESettings.BlockSize) + 5);
                    if(heading == Heading.Left)Bullet.Spawn(Bullet.Heading.Left, (int)x - 5, (int)y + ESettings.BlockSize/2-3);
                    if(heading == Heading.Right)Bullet.Spawn(Bullet.Heading.Right, ((int)x + ESettings.BlockSize)+5, (int)y + ESettings.BlockSize/2-3);
                    
                    Multiplayer.out.println("SHOOT " + (int)x + " " + (int)y + " " + CurrentFrame + " " + MH);
                    lastShotTime = currentTime;
                }
            }
            if(input.GetKey(KeyEvent.VK_UP))
            {
                NewY -= ESettings.PlayerSpeed;
                CAT -= ETime.Diff;
                heading = Heading.Up;
                MH = 0;
            }else if(input.GetKey(KeyEvent.VK_LEFT))
            {
                NewX -= ESettings.PlayerSpeed;
                CAT -= ETime.Diff;
                heading = Heading.Left;
                MH = 1;
            }else if(input.GetKey(KeyEvent.VK_DOWN))
            {
                NewY += ESettings.PlayerSpeed;
                CAT -= ETime.Diff;
                heading = Heading.Down;
                MH = 2;
            }else if(input.GetKey(KeyEvent.VK_RIGHT))
            {
                NewX += ESettings.PlayerSpeed;
                CAT -= ETime.Diff;
                heading = Heading.Right;
                MH = 3;
            }

            if(CAT < 0)
            {
                CAT = AnimationTime;
                if(CurrentFrame > 1)
                {
                    CurrentFrame = 0;
                }
                else
                {
                    CurrentFrame++;
                }
            }

            if(ELevel.CheckMapCollision(new GameObject(this.Type,NewX,NewY,ESettings.SpriteSize), ESettings.lvl)) return;
            if(GameObject.CheckCollision(new GameObject(this.Type,NewX,NewY,ESettings.SpriteSize), ESettings.enemy)) return;
            if(GameObject.CheckCollision(new GameObject(this.Type,NewX,NewY,ESettings.SpriteSize), new GameObject(GameObjectType.Flag,ESettings.TF.FlagX *ESettings.BlockSize ,ESettings.TF.FlagY * ESettings.BlockSize,ESettings.SpriteSize))) return;
            if(GameObject.CheckCollision(new GameObject(this.Type,NewX,NewY,ESettings.SpriteSize), new GameObject(GameObjectType.Flag,ESettings.TS.FlagX *ESettings.BlockSize ,ESettings.TS.FlagY * ESettings.BlockSize,ESettings.SpriteSize))) return;
            x = NewX;
            y = NewY;
            if(IsAlive)Multiplayer.out.println("POS " + (int)x + " " + (int)y + " " + CurrentFrame + " " + MH);
        }

    }

    public void Render(Graphics2D g) 
    {
        Sprite.render(g, x, y, Sprite.Resize(SpriteMap.get(heading).GetSprite(CurrentFrame),ESettings.BlockScale));
    }
    
    public void Dead()
    {
        Thread TT = new Thread(new ETime(GameObjectType.Player));
        TT.start();
    }
}
