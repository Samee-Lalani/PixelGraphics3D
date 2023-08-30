package pixelgraphics3d;

import java.util.Scanner;
import javafx.scene.paint.Color;
import java.lang.Math;

public class InputThread extends Thread{
    PixelGraphics3D pg;

    public InputThread(PixelGraphics3D pg){
        this.pg = pg;
    }

    public void run() {
        Vector3[] cubeCords = new Vector3[8];
        cubeCords[0] = new Vector3(-1, 1, -1);
        cubeCords[1] = new Vector3(1, 1, -1);
        cubeCords[2] = new Vector3(1, -1, -1);
        cubeCords[3] = new Vector3(-1, -1, -1);
        cubeCords[4] = new Vector3(-1, 1, 1);
        cubeCords[5] = new Vector3(1, 1, 1);
        cubeCords[6] = new Vector3(1, -1, 1);
        cubeCords[7] = new Vector3(-1, -1, 1);

        Vector3[] pyramidCords = new Vector3[5];
        pyramidCords[0] = new Vector3(0, 1, 0);
        pyramidCords[1] = new Vector3(1, -1, 1);
        pyramidCords[2] = new Vector3(1, -1, -1);
        pyramidCords[3] = new Vector3(-1, -1, -1);
        pyramidCords[4] = new Vector3(-1, -1, 1);

        int xT=0;
        int yT=0;
        int zT=0;
        int angle = 0;
        int xBS;
        int yBS;
        int zBS;

        Scanner commands = new Scanner(System.in);
        System.out.println("Welcome to the Pixel Graphics 3D Program!");

        while(PixelDriver.play){
            System.out.println("Enter a command: Commands:");
            System.out.println("(o) - Output 3D Shape");
            System.out.println("(t) - Translate Cube");
            System.out.println("(s) - Scale Cube");
            System.out.println("(r) - Rotate Cube");
            System.out.println("(q) - Quit program");
            String cmd = commands.next();
            if(cmd.equals("o")){
                System.out.println("what shape would you like to output?{cube, pyramid}");
                String shape = commands.next();
                if(shape.equals("c")){
                    pg.drawCube(cubeCords,Color.RED);
                } else if(shape.equals("p")){
                    pg.drawPyramid(pyramidCords,Color.RED);
                }
            } else if(cmd.equals("t")) {
                pg.deleteCube(cubeCords);
                System.out.println("How much do you want the x to be translated?");
                xT = commands.nextInt();
                System.out.println("How much do you want the y to be translated?");
                yT = commands.nextInt();
                System.out.println("How much do you want the z to be translated?");
                zT = commands.nextInt();
                pg.translate(cubeCords, xT, yT, zT);
            } else if(cmd.equals("s")) {
                pg.deleteCube(cubeCords);
                System.out.println("How much do you want the x to be basic scaled");
                xBS = commands.nextInt();
                System.out.println("How much do you want the y to be basic scaled?");
                yBS = commands.nextInt();
                System.out.println("How much do you want the z to be basic scaled?");
                zBS = commands.nextInt();
                pg.scale(cubeCords, xBS, yBS, zBS);
            } else if(cmd.equals("r")) {
                pg.deleteCube(cubeCords);
                System.out.println("what axis would you like to rotate the cube on?{x, y ,or z}");
                String axis = commands.next();
                System.out.println("At what angle would you like to rotate the cube?");
                angle = commands.nextInt();
                double radians = Math.toRadians(angle);
                System.out.println(radians);
                double cos = Math.cos(radians);
                System.out.println(cos);
                double sin = Math.sin(radians);
                System.out.println(sin);
                pg.rotate(cubeCords, cos, sin, axis);
            } else if(cmd.equals("q")) {
                commands.close();
                PixelDriver.play = false;
                break;
            } else {
                System.out.println("This is an invalid command");
            }
        }
    }
}
