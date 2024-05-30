package DefPack;

import DefPack.*;
import Game.ELevel;
import Game.Input;
import java.awt.Graphics2D;
import java.awt.List;
import java.util.ArrayList;

public class GameObject 
{
    public final GameObjectType Type;
    public float x;
    public float y;
    public int PSize;
    
    public GameObject(GameObjectType Type, float x, float y , int PSize)
    {
        this.Type = Type;
        this.x = x;
        this.y = y;
        this.PSize = PSize;
    }
    
    public float x(){return x;}
    public float y(){return y;}
   
    
    public static boolean CheckCollision(GameObject o1,GameObject o2)
    {
        float BufO1 = ESettings.BlockScale * o1.PSize;
        float BufO2 = ESettings.BlockScale * o2.PSize;
        if(o1.x + BufO1 > o2.x &&
           o1.x < o2.x + BufO2 &&
           o1.y + BufO1 > o2.y &&
           o1.y < o2.y + BufO2)
        {
            return true;
        }
        return false;
    }
    
    public void TP(int x, int y)
    {
        this.x = x * PSize * ESettings.BlockScale;
        this.y = y * PSize * ESettings.BlockScale;
    }
}
