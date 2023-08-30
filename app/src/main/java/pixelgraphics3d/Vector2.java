package pixelgraphics3d;

public class Vector2 {
    
    public int x;
    public int y;
    
    public Vector2(int xS, int yS) {
        this.x = xS;
        this.y = yS;
    }

    //method that finds the midpoint of two Vectors
    public Vector2 midpointOf(Vector2 p2) {
        int x = (this.x + p2.x) / 2;
        int y = (this.y + p2.y) / 2;
        return new Vector2(x, y);
    }
}
