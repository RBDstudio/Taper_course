package Game;

import DefPack.*;
import EGraphics.*;
import GameObjects.Bullet;
import java.awt.Graphics2D;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ELevel 
{    
    private Integer[][] TileMap;
    private Map<TileType,Tile> Tiles;
    
    public ELevel(TextureAtlas Atlas)
    { 
        Tiles = new HashMap<TileType,Tile>();
        Tiles.put(TileType.Empty, new Tile(Atlas.Cut(7,2,1),TileType.Empty));
        Tiles.put(TileType.Brick, new Tile(Atlas.Cut(0,2,1),TileType.Brick));
        Tiles.put(TileType.Metal, new Tile(Atlas.Cut(5,2,1),TileType.Metal));
        Tiles.put(TileType.Water, new Tile(Atlas.Cut(1,3,1),TileType.Water));
        Tiles.put(TileType.Grass, new Tile(Atlas.Cut(3,3,1),TileType.Grass));
        
        TileMap = new Integer[ESettings.LevelWidth][ESettings.LevelHeight];
        TileMap = ELevel.LoadLevel("C:/Level.lvl");
    }
    
    public void Update()
    {
        
    }
    
    public void Render(Graphics2D g)
    {
        int STS = (int)(ESettings.SpriteSize * ESettings.BlockScale); 
        for(int i = 0; i < ESettings.LevelHeight; i++)
        {
            for(int j = 0; j < ESettings.LevelWidth; j++)
            {
                if(TileMap[i][j] != 4)Tiles.get(TileType.FromIndex(TileMap[i][j])).Render(g,j * STS, i * STS);
            }
        }
    }
    
    public void RenderGrass(Graphics2D g)
    {
        int STS = (int)(ESettings.SpriteSize * ESettings.BlockScale); 
        for(int i = 0; i < ESettings.LevelHeight; i++)
        {
            for(int j = 0; j < ESettings.LevelWidth; j++)
            {
                if(TileMap[i][j] == 4)Tiles.get(TileType.FromIndex(TileMap[i][j])).Render(g,j * STS, i * STS);
            }
        }
    }
    
    public static Integer[][] LoadLevel(String Path)
    {
        Integer[][] Result = null;
        try(BufferedReader Reader = new BufferedReader(new FileReader(new File(Path))))
        {
            String Line = null;
            List<Integer[]> LvlLines = new ArrayList<Integer[]>();
            while((Line = Reader.readLine()) != null)
            {
                String[] Token = Line.split(" ");
                Integer[] NewInt = new Integer[Token.length];
                for(int i = 0; i < Token.length; i++)
                {
                    NewInt[i] = Integer.parseInt(Token[i]);
                }
                LvlLines.add(NewInt);
            }
                Result = new Integer[ESettings.LevelWidth][ESettings.LevelHeight];
        
                for(int i = 0; i < LvlLines.size(); i++)
                {
                    Result[i] = LvlLines.get(i);
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return Result;
    }
    
    public static boolean CheckMapCollision(GameObject Object, ELevel lvl)
    {
        if(Object.Type == GameObjectType.Player)
        {
            for(int i = 0 ; i < ESettings.LevelHeight; i++)
            {
                for(int j = 0 ; j < ESettings.LevelWidth; j++)
                {
                    if(lvl.TileMap[i][j] != 4 && lvl.TileMap[i][j] != 0)
                    {
                        if(GameObject.CheckCollision(Object, new GameObject(GameObjectType.Block,j * ESettings.BlockSize, i * ESettings.BlockSize,ESettings.SpriteSize)))
                        {
                            return true;
                        }
                    }
                }
            }
        }
        if(Object.Type == GameObjectType.Bullet)
        {
            for(int i = 0 ; i < ESettings.LevelHeight; i++)
            {
                for(int j = 0 ; j < ESettings.LevelWidth; j++)
                {
                    if(lvl.TileMap[i][j] != 4 && lvl.TileMap[i][j] != 0 && lvl.TileMap[i][j] != 3)
                    {
                        if(GameObject.CheckCollision(Object, new GameObject(GameObjectType.Block,j * ESettings.BlockSize, i * ESettings.BlockSize,ESettings.SpriteSize)))
                        {
                            if(lvl.TileMap[i][j] == 1)lvl.TileMap[i][j] = 0;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
   
}
