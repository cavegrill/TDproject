package Towersdefences;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class ImageLoader {
    public static Image loadImage(String imagePath) {
        try {
            Image image = ImageIO.read(new File(imagePath));
            
            return image.getScaledInstance(1280, 720, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }} 