package GameObjects;

import DefPack.*;
import EGraphics.*;
import Game.ELevel;
import Game.Input;
import Game.Multiplayer;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Enemy extends GameObject
{
    public static enum Heading
    {
        Up    (0, 1),
        Left  (2, 1),
        Down  (4, 1),
        Right (6, 1); 
        
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
    
    public static boolean IsAlive;
    public boolean Imortl;
    private long lastShotTime;
    private long SpawnTime;
    Heading heading;
    private Map<Heading, SpriteAnimation> SpriteMap;
    private float AnimationTime;
    private float CAT;
    int CurrentFrame;
    
    public Enemy(float x,float y, Heading heading)
    {
        super(GameObjectType.Player, x*ESettings.BlockSize, y*ESettings.BlockSize,ESettings.SpriteSize);
        
        lastShotTime = System.currentTimeMillis();
        
        this.heading = heading;
        SpriteMap = new HashMap<Heading,SpriteAnimation>();
        
        for(Heading h: Heading.values())
        {
            SpriteAnimation SA = new SpriteAnimation(h.Texture(ESettings.Atlas),2,ESettings.SpriteSize);
            Sprite sprite = new Sprite(SA,ESettings.BlockScale);
            SpriteMap.put(h, SA);
        }
        CurrentFrame = 0;
        SpawnTime = System.currentTimeMillis();
        IsAlive = true;
        Imortl = false;
        System.out.println("Enemy");
    }
    
    public void Update() 
    {
        EnemyNetUpdater ENU = new EnemyNetUpdater();
        ENU.start();
    }

    public void Render(Graphics2D g) 
    {
        if(IsAlive)
        {
            Sprite.render(g, x, y, Sprite.Resize(SpriteMap.get(heading).GetSprite(CurrentFrame),ESettings.BlockScale));
        }
    }
    
    public void Dead()
    {
        Thread TT = new Thread(new ETime(GameObjectType.Enemy));
        TT.start();
    }
}

class EnemyNetUpdater extends Thread
{
    public void run()
    {
        while(ESettings.IsRunning)
        {

                String Message = null;
                try {
                    Message = Multiplayer.in.readLine();
                } catch (IOException ex) {}

                if(Message == null)
                {
                    ESettings.IsRunning = false;
                }
                else
                {
                    String[] Tokens = Message.split(" ");
                    if(Tokens[0].equals("POS"))
                    {
                        ESettings.enemy.x = Integer.parseInt(Tokens[1]);
                        ESettings.enemy.y = Integer.parseInt(Tokens[2]);
                        ESettings.enemy.CurrentFrame = Integer.parseInt(Tokens[3]);
                        int NH = Integer.parseInt(Tokens[4]);
                        if(NH == 0)ESettings.enemy.heading = Enemy.Heading.Up;
                        else if(NH == 1)ESettings.enemy.heading = Enemy.Heading.Left;
                        else if(NH == 2)ESettings.enemy.heading = Enemy.Heading.Down;
                        else if(NH == 3)ESettings.enemy.heading = Enemy.Heading.Right;
                    }
                    if(Tokens[0].equals("SHOOT"))
                    {
                        ESettings.enemy.x = Integer.parseInt(Tokens[1]);
                        ESettings.enemy.y = Integer.parseInt(Tokens[2]);
                        ESettings.enemy.CurrentFrame = Integer.parseInt(Tokens[3]);
                        int NH = Integer.parseInt(Tokens[4]);
                        if(NH == 0)
                        {
                            ESettings.enemy.heading = Enemy.Heading.Up;
                            Bullet.Spawn(Bullet.Heading.Up, (int)ESettings.enemy.x+ESettings.BlockSize/2-3, (int)ESettings.enemy.y - 5);
                        }
                        else if(NH == 1)
                        {
                            ESettings.enemy.heading = Enemy.Heading.Left;
                            Bullet.Spawn(Bullet.Heading.Left, (int)ESettings.enemy.x - 5, (int)ESettings.enemy.y + ESettings.BlockSize/2-3);
                        }
                        else if(NH == 2)
                        {
                            ESettings.enemy.heading = Enemy.Heading.Down;
                            Bullet.Spawn(Bullet.Heading.Down, (int)ESettings.enemy.x + ESettings.BlockSize/2-3, ((int)ESettings.enemy.y + ESettings.BlockSize)+5);
                        }
                        else if(NH == 3)
                        {
                            ESettings.enemy.heading = Enemy.Heading.Right;
                            Bullet.Spawn(Bullet.Heading.Right, ((int)ESettings.enemy.x + ESettings.BlockSize) + 5, (int)ESettings.enemy.y + ESettings.BlockSize/2-3);
                        }
                    }
                }
            
        }
    }
}
