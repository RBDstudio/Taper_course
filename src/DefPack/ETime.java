package DefPack;

import GameObjects.*;

public class ETime implements Runnable
{
    public static final long Second = 1000000000l;
    
    public static long LastTime;
    public static long NowTime;
    public static long Diff;
    
    private GameObjectType Type;
    
    public ETime(GameObjectType Type)
    {
        this.Type = Type;
    }
    
    public static void UpdateDiff()
    {
        Diff = NowTime - LastTime;
    }
    
    public static void UpdateNowTime()
    {
        NowTime = GetTime();
    }
    
    public static void SwapTime()
    {
        UpdateNowTime();
        UpdateDiff();
        LastTime = NowTime;
    }
    
    public static long GetTime()
    {
        return System.nanoTime();
    }
    
    public void run()
    {
        if(Type == GameObjectType.Enemy)
        {
            ESettings.enemy.TP(-1, -1);
            ESettings.enemy.Imortl = true;
            Enemy.IsAlive = false;
            long DeadTime = System.currentTimeMillis();
            while(System.currentTimeMillis() - DeadTime < ESettings.SpawnTime){}
            if(!GameObject.CheckCollision(new GameObject(this.Type,ESettings.TS.SpawnX*ESettings.BlockSize,ESettings.TS.SpawnY*ESettings.BlockSize,ESettings.SpriteSize),ESettings.player)){ESettings.enemy.TP(ESettings.TS.SpawnX, ESettings.TS.SpawnY);}
            else if(!GameObject.CheckCollision(new GameObject(this.Type,(ESettings.TS.SpawnX+1)*ESettings.BlockSize,ESettings.TS.SpawnY*ESettings.BlockSize,ESettings.SpriteSize),ESettings.player)){ESettings.enemy.TP(ESettings.TS.SpawnX+1, ESettings.TS.SpawnY);}
            else if(!GameObject.CheckCollision(new GameObject(this.Type,(ESettings.TS.SpawnX-1)*ESettings.BlockSize,ESettings.TS.SpawnY*ESettings.BlockSize,ESettings.SpriteSize),ESettings.player)){ESettings.enemy.TP(ESettings.TS.SpawnX-1, ESettings.TS.SpawnY);}
            Enemy.IsAlive = true;
            DeadTime = System.currentTimeMillis();
            while(System.currentTimeMillis() - DeadTime < ESettings.SpawnTime){}
            ESettings.enemy.Imortl = false;
        }
        
        if(Type == GameObjectType.Player)
        {
            ESettings.player.TP(-2, -1);
            ESettings.player.Imortl = true;
            Player.IsAlive = false;
            long DeadTime = System.currentTimeMillis();
            while(System.currentTimeMillis() - DeadTime < ESettings.SpawnTime){}
            if(!GameObject.CheckCollision(new GameObject(this.Type,ESettings.TF.SpawnX*ESettings.BlockSize,ESettings.TF.SpawnY*ESettings.BlockSize,ESettings.SpriteSize),ESettings.player)){ESettings.player.TP(ESettings.TF.SpawnX, ESettings.TF.SpawnY);}
            else if(!GameObject.CheckCollision(new GameObject(this.Type,(ESettings.TF.SpawnX+1)*ESettings.BlockSize,ESettings.TF.SpawnY*ESettings.BlockSize,ESettings.SpriteSize),ESettings.player)){ESettings.player.TP(ESettings.TF.SpawnX+1, ESettings.TF.SpawnY);}
            else if(!GameObject.CheckCollision(new GameObject(this.Type,(ESettings.TF.SpawnX-1)*ESettings.BlockSize,ESettings.TF.SpawnY*ESettings.BlockSize,ESettings.SpriteSize),ESettings.player)){ESettings.player.TP(ESettings.TF.SpawnX-1, ESettings.TF.SpawnY);}
            Player.IsAlive = true;
            DeadTime = System.currentTimeMillis();
            while(System.currentTimeMillis() - DeadTime < ESettings.SpawnTime){}
            ESettings.player.Imortl = false;
        }
    }
}
