package DefPack;

public enum TileType 
{
    Empty(0), 
    Brick(1), 
    Metal(2), 
    Water(3), 
    Grass(4);
    
    private int Index;
    
    TileType(int Index)
    {
        this.Index = Index;
    }
    
    public int Index()
    {
        return Index;
    }
    
    public static TileType FromIndex(int Index)
    {
        switch(Index)
        {
            default: return Empty;
            case 1: return Brick;
            case 2: return Metal;
            case 3: return Water;
            case 4: return Grass;
        }
    }
}
