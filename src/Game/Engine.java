package Game;

import java.awt.*;
import DefPack.*;
import EGraphics.Sprite;
import EGraphics.SpriteAnimation;
import EGraphics.TextureAtlas;
import GameObjects.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Engine extends Thread
{
    private Thread GameThread;
    private Input KeyInput;
    private TextureAtlas Atlas;
    private ELevel lvl;
    
    private Team TF;
    private Team TS;
    
    private Graphics2D Graphics;
    
    private Player player;
    private Enemy enemy;
    
    public Engine()
    {
        ESettings.IsRunning = false;
        //EFrame.CreateWindow(ESettings.Name, ESettings.Width, ESettings.Height, ESettings.ClearColor, ESettings.BuffersCount);
        Graphics = EFrame.GetGraphics();
        ESettings.MainGraphics = Graphics;
        KeyInput = new Input();
        EFrame.AddInputListener(KeyInput);
        Atlas = new TextureAtlas(ESettings.MainTextureName, ESettings.SpriteSize);
        ESettings.Atlas = Atlas;
        if(Multiplayer.IsHost)
        {
            TF = new Team(5,2,22,1,Atlas);
            ESettings.TF = TF;
            TF.IsEnemy = false;
            TS = new Team(18,15,1,16,Atlas);
            ESettings.TS = TS;
            TF.IsEnemy = true;
        }
        else
        {
            TS = new Team(5,2,22,1,Atlas);
            ESettings.TS = TS;
            TS.IsEnemy = false;
            TF = new Team(18,15,1,16,Atlas);
            ESettings.TF = TF;
            TF.IsEnemy = true;
        }
        lvl = new ELevel(Atlas);
        if(Multiplayer.IsHost) player = new Player(TF.SpawnX,TF.SpawnY, 10000f,Player.Heading.Down);
        else player = new Player(TF.SpawnX,TF.SpawnY, 10000f,Player.Heading.Up);
        ESettings.player = player;
        if(Multiplayer.IsHost) enemy = new Enemy(TS.SpawnX,TS.SpawnY, Enemy.Heading.Up);
        else enemy = new Enemy(TS.SpawnX,TS.SpawnY, Enemy.Heading.Down);
        ESettings.enemy = enemy;
        ESettings.lvl = lvl;
        
    }
    
    public synchronized void StartGame()
    {
        if(ESettings.IsRunning) return;
        
        ESettings.IsRunning = true;
        GameThread = new Thread(this);
        GameThread.start();
    }
    
    public synchronized void StopGame()
    {
        if(!ESettings.IsRunning) return;
        
        try
        {
        GameThread.join();
        }catch(InterruptedException e)
        {
            e.printStackTrace();
        }
        Clean();
    }
    
    private void Update()
    {
        player.Update(KeyInput);
        for(Bullet B: Bullet.BulletList)
        {
            B.Update();
        }
        for(Bullet B: Bullet.DeleteList)
        {
            Bullet.BulletList.remove(B);
        }
        Bullet.DeleteList.clear();
        if(KeyInput.GetKey(KeyEvent.VK_F3)) ESettings.ShowDebug = !ESettings.ShowDebug;
    }
    
    private void Render()
    {
        EFrame.ClearFrame();
        
        lvl.Render(Graphics);
        
        player.Render(Graphics);
        
        enemy.Render(Graphics);
        
        for(Bullet B: Bullet.BulletList)
        {
            B.Render(Graphics);
        }
        
        TF.Render(Graphics);
        TS.Render(Graphics);
        
        lvl.RenderGrass(Graphics);
        
        EFrame.SwapFrame();
    }
    
    private void Clean()
    {
        EFrame.DestroyWindow();
    }
    public void run()
    {
        
        int c = 0;
        int FPS = 0;
        int Upd = 0;
        int UpdL = 0;
        float UpdateDelta = 0;
        
        ESettings.enemy.Update();
        
        long Count = 0;
        ETime.LastTime = ETime.GetTime();
        while(ESettings.IsRunning)
        {
            c++;
            //Обновление времени
            ETime.SwapTime();
            Count += ETime.Diff;
            UpdateDelta += (ETime.Diff/ ESettings.FrameInterval);
            //расчёт физики
            boolean FrameIsChanged = false;
            while(UpdateDelta > 1)
            {
                Update();
                Upd++;
                UpdateDelta--;
                
                if(FrameIsChanged) UpdL++;
                else FrameIsChanged = true;
            }
            
            //рендер кадра
            if(FrameIsChanged)
            {
                Render();
                FPS++;
            }else
            {
                try 
                {
                    Thread.sleep(ESettings.ThreadSaveTime);
                } catch (InterruptedException e) 
                {
                    e.printStackTrace();
                }
            }
            if(Count >= ETime.Second) 
            {
                //EFrame.ChangeName(ESettings.Name + " | FPS: " + FPS + " | Upd: " + Upd + " | UpdL: " + UpdL);
                Upd = 0;
                FPS = 0;
                UpdL = 0;
                Count = 0;
            }
        }
    }
}
