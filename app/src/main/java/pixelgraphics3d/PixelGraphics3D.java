package pixelgraphics3d;

import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class PixelGraphics3D {

    public PixelGraphics3D(int width,int height,PixelWriter pixelWriter) {
        this.width = width;
        this.height = height;
        this.pixelWriter = pixelWriter;
    }

    private final int width;
    private final int height;

    double xE = 6;
    double yE = 8;
    double zE = 7.5;

    double screenS = 30;
    double screenD = 60;

    double coordinateSystem = 1024;

    final private PixelWriter pixelWriter;

    public void deleteCube(Vector3[] cords){
        Vector2[] screenCords = new Vector2[8];
        for(int i = 0; i < cords.length; i++){
            screenCords[i] = convertToScreen(cords[i]);
        }
        drawLine(screenCords[0], screenCords[1], Color.TRANSPARENT);
        drawLine(screenCords[1], screenCords[2], Color.TRANSPARENT);
        drawLine(screenCords[2], screenCords[3], Color.TRANSPARENT);
        drawLine(screenCords[3], screenCords[0], Color.TRANSPARENT);
        drawLine(screenCords[4], screenCords[5], Color.TRANSPARENT);
        drawLine(screenCords[5], screenCords[6], Color.TRANSPARENT);
        drawLine(screenCords[6], screenCords[7], Color.TRANSPARENT);
        drawLine(screenCords[7], screenCords[4], Color.TRANSPARENT);
        drawLine(screenCords[0], screenCords[4], Color.TRANSPARENT);
        drawLine(screenCords[1], screenCords[5], Color.TRANSPARENT);
        drawLine(screenCords[2], screenCords[6], Color.TRANSPARENT);
        drawLine(screenCords[3], screenCords[7], Color.TRANSPARENT);
    }

    //method that computes the [V] and [N] matrix for the 3D space and concatenates them
    public Vector3 computeVNmatrix(Vector3 wPoint){
        double yMag = magCalc(xE, yE, zE, "y");
        double xMag = magCalc(xE, yE, zE, "x");
        double zMag = magCalc(xE, yE, zE, "z");
        double tMag = magCalc(xE, yE, zE, "t");
        
        Vector3 eCords; 
        
        double[][] p = { { wPoint.x, wPoint.y, wPoint.z, 1 }};
        Matrix point = new Matrix(p);

        double[][] m ={{ 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 },{ 0, 0, 0, 1 }};
        Matrix VN = new Matrix(m);

        double[][] d ={{ 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 },{ -xE, -yE, -zE, 1 }};
        Matrix transformerD = new Matrix(d);
        VN = VN.times(transformerD);

        double[][] e = {{ 1, 0, 0, 0 }, { 0, 0, -1, 0 }, { 0, 1, 0, 0 },{ 0, 0, 0, 1 }};
        Matrix transformerE = new Matrix(e);
        VN = VN.times(transformerE);

        double[][] f = {{ -yMag, 0, xMag, 0 }, { 0, 1, 0, 0 }, { -xMag, 0, -yMag, 0 },{ 0, 0, 0, 1 }};
        Matrix transformerF = new Matrix(f);
        VN = VN.times(transformerF);

        double[][] g = {{ 1, 0, 0, 0 }, { 0, tMag, zMag, 0 }, { 0, -zMag, tMag, 0 },{ 0, 0, 0, 1 }};
        Matrix transformerG = new Matrix(g);
        VN = VN.times(transformerG);

        double[][] h = {{ 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, -1, 0 },{ 0, 0, 0, 1 }};
        Matrix transformerH = new Matrix(h);
        VN = VN.times(transformerH);

        double[][] i ={{ (screenD/(screenS/2)), 0, 0, 0 }, { 0,(screenD/(screenS/2)), 0, 0 }, { 0, 0, 1, 0 },{ 0, 0, 0, 1 }};
        Matrix transformerI = new Matrix(i);
        VN = VN.times(transformerI);

        point = point.times(VN);

        eCords = new Vector3(point.get(0, 0), point.get(0, 1), point.get(0, 2));

        return eCords;
    }

    //method that converts a point in the 3D space to a point on the 2D screen(N)
    public Vector2 convertToScreen(Vector3 wPoint){
        Vector3 cPoint = computeVNmatrix(wPoint);
        double xC = cPoint.x;
        double yC = cPoint.y;
        double zC = cPoint.z;
        int xS = (int) ((xC/zC)*(coordinateSystem/2)+(coordinateSystem/2));
        int yS = (int) ((yC/zC)*(coordinateSystem/2)+(coordinateSystem/2));
        return new Vector2(xS, yS);
    }

    public void drawCube(Vector3[] cords, Color color){
        Vector2[] screenCords = new Vector2[8];
        
        for(int i = 0; i < cords.length; i++){
            screenCords[i] = convertToScreen(cords[i]);
        }
        drawLine(screenCords[0], screenCords[1], color);
        drawLine(screenCords[1], screenCords[2], color);
        drawLine(screenCords[2], screenCords[3], color);
        drawLine(screenCords[3], screenCords[0], color);
        drawLine(screenCords[4], screenCords[5], color);
        drawLine(screenCords[5], screenCords[6], color);
        drawLine(screenCords[6], screenCords[7], color);
        drawLine(screenCords[7], screenCords[4], color);
        drawLine(screenCords[0], screenCords[4], color);
        drawLine(screenCords[1], screenCords[5], color);
        drawLine(screenCords[2], screenCords[6], color);
        drawLine(screenCords[3], screenCords[7], color);
    }

    public void drawPyramid(Vector3[] cords, Color color){
        Vector2[] screenCords = new Vector2[5];
        
        for(int i = 0; i < cords.length; i++){
            screenCords[i] = convertToScreen(cords[i]);
        }
        drawLine(screenCords[1], screenCords[2], color);
        drawLine(screenCords[2], screenCords[3], color);
        drawLine(screenCords[3], screenCords[4], color);
        drawLine(screenCords[4], screenCords[1], color);
        drawLine(screenCords[0], screenCords[1], color);
        drawLine(screenCords[0], screenCords[2], color);
        drawLine(screenCords[0], screenCords[3], color);
        drawLine(screenCords[0], screenCords[4], color);
    }


    public void translate(Vector3[] cords, int xT, int yT, int zT){
        double[][] d = {{ 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 },{ xT, yT, zT, 1 }};
        Matrix transformer = new Matrix(d);
        for(int i = 0; i < cords.length; i++){
            double[][] p = { { cords[i].x, cords[i].y, cords[i].z, 1 }};
            Matrix point = new Matrix(p);
            Matrix result = point.times(transformer);
            cords[i].x = (int)result.get(0, 0);
            cords[i].y = (int)result.get(0, 1);
            cords[i].z = (int)result.get(0, 2);
        }
    }

    public void scale(Vector3[] cords, int xBS, int yBS, int zBS){
        double[][] d = {{ xBS, 0, 0, 0 }, { yBS, 0, 0, 0 }, { 0, 0, zBS, 0 },{ 0, 0, 0, 1 }};
        Matrix transformer = new Matrix(d);
        for(int i = 0; i < cords.length; i++){
            double[][] p = { { cords[i].x, cords[i].y, cords[i].z, 1 }};
            Matrix point = new Matrix(p);
            Matrix result = point.times(transformer);
            cords[i].x = (int)result.get(0, 0);
            cords[i].y = (int)result.get(0, 1);
            cords[i].z = (int)result.get(0, 2);
        }
    }

    public void basicRotateX(Vector3[] cords, double cos, double sin){
        double[][] d = {{ 1, 0, 0, 0 }, { 0, cos, sin, 0 }, { 0, -sin, cos, 0 },{ 0, 0, 0, 1 }};
        Matrix transformer = new Matrix(d);
        for(int i = 0; i < cords.length; i++){
            double[][] p = { { cords[i].x, cords[i].y, cords[i].z, 1 }};
            Matrix point = new Matrix(p);
            Matrix result = point.times(transformer);
            cords[i].x = (int)result.get(0, 0);
            cords[i].y = (int)result.get(0, 1);
            cords[i].z = (int)result.get(0, 2);
        }
    }

    public void basicRotateY(Vector3[] cords, double cos, double sin){
        double[][] d = {{ cos, 0, -sin, 0 }, { 0, 1, 0, 0 }, { sin, 0, cos, 0 },{ 0, 0, 0, 1 }};
        Matrix transformer = new Matrix(d);
        for(int i = 0; i < cords.length; i++){
            double[][] p = { { cords[i].x, cords[i].y, cords[i].z, 1 }};
            Matrix point = new Matrix(p);
            Matrix result = point.times(transformer);
            cords[i].x = (int)result.get(0, 0);
            cords[i].y = (int)result.get(0, 1);
            cords[i].z = (int)result.get(0, 2);
        }
    }

    public void basicRotateZ(Vector3[] cords, double cos, double sin){
        double[][] d = {{ cos, sin, 0, 0 }, { -sin, cos, 0, 0 }, { 0, 0, 1, 0 },{ 0, 0, 0, 1 }};
        Matrix transformer = new Matrix(d);
        for(int i = 0; i < cords.length; i++){
            double[][] p = { { cords[i].x, cords[i].y, cords[i].z, 1 }};
            Matrix point = new Matrix(p);
            Matrix result = point.times(transformer);
            cords[i].x = (int)result.get(0, 0);
            cords[i].y = (int)result.get(0, 1);
            cords[i].z = (int)result.get(0, 2);
        }
    }

    public void rotate(Vector3[] cords, double cos, double sin, String axis){
        if(axis.equals("x")){
            basicRotateX(cords, cos, sin);
        }
        else if(axis.equals("y")){
            basicRotateY(cords, cos, sin);
        }
        else if(axis.equals("z")){
            basicRotateZ(cords, cos, sin);
        }
    }

    public double magCalc(double xE, double yE, double zE, String q){
        if(q.equals("x")){ 
            return xE/(Math.sqrt(Math.pow(xE, 2) + Math.pow(yE, 2)));
        } else if(q.equals("y")) {
            return yE/(Math.sqrt(Math.pow(xE, 2) + Math.pow(yE, 2)));
        } else if(q.equals("z")) {
            return zE/(Math.sqrt(Math.pow(zE, 2) + Math.pow(Math.sqrt(Math.pow(xE,2) + Math.pow(yE,2)),2)));
        } else if (q.equals("t")) {
            return (Math.sqrt(Math.pow(xE, 2) + Math.pow(yE, 2)))/(Math.sqrt(Math.pow(zE, 2) + Math.pow(Math.sqrt(Math.pow(xE,2) + Math.pow(yE,2)),2)));
        } else {
            return 0;
        }
    }

    public void drawLineBresenham(PixelWriter writer, Vector2 p1, Vector2 p2, Color color){
        int x1 = (int)p1.x;
        int y1 = (int)p1.y;
        int x2 = (int)p2.x;
        int y2 = (int)p2.y;
        int x, y;
        int dx, dy;
        int incx, incy;
        int balance;

        if (x2 >= x1){
            dx = x2 - x1;
            incx = 1;
        }
        else
        {
            dx = x1 - x2;
            incx = -1;
        }

        if (y2 >= y1)
        {
            dy = y2 - y1;
            incy = 1;
        }
        else
        {
            dy = y1 - y2;
            incy = -1;
        }

        x = x1;
        y = y1;

        if (dx >= dy)
        {
            dy <<= 1;
            balance = dy - dx;
            dx <<= 1;

            while (x != x2)
            {
                writer.setColor(x, y, color);
                if (balance >= 0)
                {
                    y += incy;
                    balance -= dx;
                }

                balance += dy;
                x += incx;
            }

            writer.setColor(x, y, color);
        }
        else
        {
            dx <<= 1;
            balance = dx - dy;
            dy <<= 1;
            
            while (y != y2)
            {
                writer.setColor(x, y, color);
                if (balance >= 0)
                {
                    x += incx;
                    balance -= dy;
                }
                
                balance += dx;
                y += incy;
            }

            writer.setColor(x, y, color);
        }
    }

    public void drawLine(Vector2 p1, Vector2 p2, Color color){
                drawLineBresenham(pixelWriter, p1, p2, color);
    }
}
