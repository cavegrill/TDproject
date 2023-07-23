package Towersdefences;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Random;
import java.util.Scanner;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.sound.sampled.*;

public class Main {
    public static void main(String[] args) {
    
        int screenWidth = 1280;
        int screenHeight = 720;
        Scanner scanner = new Scanner(System.in);
        
        

        Game game = Game.getInstance();
        
        int startingLives = 5;
        game.setLives(startingLives);
       
      
        
        TiledMap map = new TiledMap();
        Main.resetGame(map);
        String[] mapFiles = {
                "res/map1.png",
                "res/map2.png",
                "res/map3.png",
                "res/map4.png",
                "res/map5.png"
        };

        
    

        // Rastgele bir harita dosyası seçin
        Random random = new Random();
        String selectedMapFile = mapFiles[random.nextInt(mapFiles.length)];
        

        // Seçilen haritayı yükle
        map.load(selectedMapFile, screenWidth, screenHeight);
        game.setMap(map);
       
        
        
        Image enemyImage = ImageLoader.loadImage("res/enemy.png");
        Image towerImage = ImageLoader.loadImage("res/tower.png");
        Image projectileImage = ImageLoader.loadImage("res/bomb.png");

        
   
           
        
        // Read input from the user
        // Calculate enemy size
        int enemyWidth = screenWidth / 9;
        int enemyHeight = screenHeight / 6;

        // Calculate enemy position on the left side, vertically centered
        int enemyX = 0;
        int enemyY = screenHeight / 2 - enemyHeight / 2;

        // Create and add the enemy
        Vector2 enemyPosition = new Vector2(enemyX, enemyY);
        Vector2 enemyVelocity = new Vector2(1, 0); // Sağa doğru hareket etmek için hızı 1 olarak ayarladık
        int enemyHealth = 100;
        Vector2 enemyTarget = new Vector2(screenWidth, enemyY);
        Enemy enemy = new Enemy(enemyPosition, enemyVelocity, enemyHealth, 0, enemyWidth, enemyHeight, enemyImage, enemyTarget);
        game.addEnemy(enemy);

        
        int towerWidth = screenWidth / 8; 
        int towerHeight = screenHeight / 6; 
        int towerX = screenWidth / 2 - towerWidth / 2;
        int towerY = screenHeight - towerHeight;
        Vector2 towerPosition = new Vector2(towerX, towerY);

        int towerDamage = 34;
        int towerRange = map.getTileWidth();
        int projectileSpeed = 10; // Mermi hızı
        Tower tower = new Tower(towerPosition, 1, towerDamage, towerWidth, towerHeight, towerImage, projectileImage, projectileSpeed);
        game.addTower(tower);
        // Play the song
            String[] songFiles ={
                    "res/karayip.wav",
                    "res/MaidenVoyage.wav",
                    "res/Ride of the Valkyries.wav",
                    "res/Spongebob.wav",
                    "res/BosunBill.wav"
                    
            };
            
            Song song = new Song(songFiles);
            
            song.playSong();

        // Rastgele bir ses dosyası seçin
        Random randomSong = new Random();
        String selectedSongFile = songFiles[random.nextInt(songFiles.length)];


  

        //mouse and keyboard
        game.addMouseListener(new MouseAdapter() {
            private boolean dragging = false;
            private int initialX;
            private int initialY;

            @Override
            public void mousePressed(MouseEvent e) {
                if (tower.getBounds().contains(e.getX(), e.getY())) {
                    dragging = true;
                    initialX = e.getX();
                    initialY = e.getY();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (dragging) {
                    dragging = false;
                    int deltaX = e.getX() - initialX;
                    int deltaY = e.getY() - initialY;
                    Vector2 newTowerPosition = tower.getPosition().add(new Vector2(deltaX, deltaY));
                    Tower newTower = new Tower(newTowerPosition, towerRange, towerDamage, towerWidth, towerHeight, towerImage, projectileImage, projectileSpeed);
                    game.addTower(newTower);
                    newTower.startShooting();
                    
                    
                   
                }}
            
            
           
        });

        game.start();
    }
    private static void resetGame(Song song) {
		// TODO Auto-generated method stub
		
	}
	public static void resetGame(TiledMap map) {
        // Diğer sıfırlama işlemleri...

        // Harita nesnesini sıfırla
        map.reset();
    }
    

}