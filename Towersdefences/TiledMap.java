package Towersdefences;
import java.awt.*;
import java.util.Random;

public class TiledMap {
    private Image image;
    private int tileWidth;
    private int tileHeight;
    private int mapWidth; // Harita genişliği
    private int mapHeight; // Harita yüksekliği

    public void load(String imagePath, int tileWidth, int tileHeight) {
        image = ImageLoader.loadImage(imagePath);
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.mapWidth = image.getWidth(null);
        this.mapHeight = image.getHeight(null);
    }

    public int getWidth() {
        return mapWidth;
    }

    public int getHeight() {
        return mapHeight;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;	}
    public void render(Graphics g) {
        // Resmi tam boyutta çizmek için Graphics nesnesini kullanın
       
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }
    
		
	

	public void reset() {
        image = null;

    }
}