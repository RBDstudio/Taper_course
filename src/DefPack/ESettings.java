package DefPack;

import EGraphics.TextureAtlas;
import Game.ELevel;
import GameObjects.*;
import java.awt.Graphics2D;


public class ESettings 
{
    public static final int LevelHeight = 18;
    public static final int LevelWidth = 24;
    public static final String Name = "Tank Carnage";
    public static final int ClearColor = 0xff000000;
    public static final int BuffersCount = 3;
    public static final float FrameRate = 75.0f;
    public static final long ThreadSaveTime = 1;
    public static final String MainTextureName = "TCTexture.png";
    public static final int SpriteSize = 16;
    public static final int BulletSize = 3;
    public static final float BlockScale = 2f;
    public static final long SpawnTime = 2000;
    
    
    public static final float PlayerSpeed = 2;
    public static final float BulletSpeed = 6;
    
    public static Graphics2D MainGraphics;
    public static Enemy enemy;
    public static Player player;
    public static Team TS;
    public static Team TF;
    public static ELevel lvl;
    public static TextureAtlas Atlas;   
    public static boolean ShowDebug = false;
    public static boolean IsRunning;
    public static int YouScore = 0;
    public static int EnemyScore = 0;
    
    public static final int BlockSize = (int)(BlockScale * SpriteSize);
    public static final float FrameInterval = ETime.Second / FrameRate;
    public static final int Width = (int)(LevelWidth * SpriteSize * BlockScale);
    public static final int Height = (int)(LevelHeight * SpriteSize * BlockScale);
    
}
