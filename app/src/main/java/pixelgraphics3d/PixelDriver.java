package pixelgraphics3d;

import java.io.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

public class PixelDriver extends Application
{
    public static boolean play = true;
    final int width = 1024;
    final int height = 1024;
    Group root;
    Scene scene;
    PixelWriter pixelWriter;

    @Override
    public void init() throws Exception{
        Group root = new Group();
        WritableImage writableImage = new WritableImage(width, height);
        pixelWriter = writableImage.getPixelWriter();
        root.getChildren().add(new ImageView(writableImage));
        scene = new Scene(root, width, height);
        
    }

    @Override
    public void start(Stage stage) {
        stage.setScene(scene);
        stage.show();

        PixelGraphics3D pg = new PixelGraphics3D(width, height, pixelWriter);
        InputThread it = new InputThread(pg);
        it.start();
    
    }
    public static void main(String[] args) throws IOException, NullPointerException{    
        PixelDriver.launch(args);
    }
}