package Towersdefences;
import javax.swing.*;

import Towersdefences.Enemy;
import Towersdefences.ImageLoader;
import Towersdefences.Projectile;
import Towersdefences.TiledMap;
import Towersdefences.Tower;
import Towersdefences.Vector2;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends JFrame {
    private static Game instance;

    private TiledMap map;
    private List<Enemy> enemies;
    private List<Tower> towers;
    private List<Projectile> projectiles;
    private BufferedImage buffer;
    private int screenWidth;
    private int screenHeight;
    private Random random;
    private long lastSpawnTime;
    private long spawnInterval;
    
  
    private Game(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        buffer = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        setTitle("Tower Defense Game");
        setSize(screenWidth, screenHeight);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        enemies = new ArrayList<>();
        towers = new ArrayList<>();
        projectiles = new ArrayList<>();
        random = new Random();
        lastSpawnTime = System.currentTimeMillis();
        spawnInterval = 2000;
    }
    
    private int lives;
    public void decreaseLives() {
        lives--;
        if (lives <= 0) {
            gameOver();
        }
    }

    public void setLives(int initialLives) {
        lives = initialLives;
    }
    
    private void gameOver() {
        // Game over logic
        // Display game over message, stop the game, etc.
        // You can add your specific implementation here.
        System.out.println("Game Over!");
        ImageIcon imageIcon = new ImageIcon("res/GameOver.png");
        JFrame frame = new JFrame("res/GameOver.png");
                JLabel label = new JLabel(imageIcon);
                frame.getContentPane().add(label);
                frame.setSize(1280, 720);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
        try {
    Thread.sleep(3000); // 3000 milliseconds = 3 seconds
    } catch (InterruptedException e) {
    e.printStackTrace();
}
        // Stop the game, display game over
        System.exit(0);}
    

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game(1280, 720);
        }
        return instance;
    }

    public void setMap(TiledMap map) {
        this.map = map;
    }

    public TiledMap getMap() {
        return map;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<Tower> getTowers() {
        return towers;
    }

    public List<Projectile> getProjectiles() {
        return projectiles;
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
    }

    public void addTower(Tower tower) {
        towers.add(tower);
    }

    public void removeTower(Tower tower) {
        towers.remove(tower);
    }

    public void addProjectile(Projectile projectile) {
        projectiles.add(projectile);
    }

    public void removeProjectile(Projectile projectile) {
        projectiles.remove(projectile);
    }

    public void start() {
        setVisible(true);
        gameLoop();
    }

    private void gameLoop() {
        while (true) {
            update();
            render();
            try {
                Thread.sleep(16); // Delay to control the game's FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
    	//fps
    	
        List<Enemy> enemiesCopy = new ArrayList<>(enemies);
        for (Enemy enemy : enemiesCopy) {
            enemy.update();
        }

        // Update the towers
        List<Tower> towersCopy = new ArrayList<>(towers);
        for (Tower tower : towersCopy) {
            tower.update();
            tower.startShooting(); // Kulelerin otomatik ateş etmesini sağlar
        }

        // Update the projectiles
        List<Projectile> projectilesCopy = new ArrayList<>(projectiles);
        for (Projectile projectile : projectilesCopy) {
            projectile.update();
        }

        // Spawn new enemies
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastSpawnTime > spawnInterval) {
            spawnEnemy(); 
            lastSpawnTime = currentTime;
        }
    }


    private void render() {
        // Draw on the buffer
        Graphics2D g2d = (Graphics2D) buffer.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Render the game on the buffer
        renderGame(g2d);

        // Draw the buffer to the screen
        Graphics g = getGraphics();
        g.drawImage(buffer, 0, 0, null);

        // Clear the memory
        g.dispose();
    }

    private void renderGame(Graphics g) {
        // Draw the background
        if (map != null) {
            map.render(g);
        }

        // Render the enemies
       
        for (Enemy enemy : enemies) {
            enemy.render(g);
        }

       // Render the towers
        for (Tower tower : towers) {
            tower.render(g);
        }


        // Render the projectiles
        for (Projectile projectile : projectiles) {
            projectile.render(g);
        }
    }
    
    private void spawnEnemy() {
        int enemyWidth = screenWidth / 9;
        int enemyHeight = screenHeight / 6;
        int enemyX = 0;
        int enemyY = screenHeight / 2 - enemyHeight / 2; // Ekranın ortasında spawn konumu
        Vector2 enemyPosition = new Vector2(enemyX, enemyY);
        Vector2 enemyVelocity = new Vector2(1, 0);
        int enemyHealth = 100;
        Vector2 enemyTarget = new Vector2(screenWidth, enemyY);
        Image enemyImage = ImageLoader.loadImage("res/enemy.png");
        Enemy enemy = new Enemy(enemyPosition, enemyVelocity, enemyHealth, 0, enemyWidth, enemyHeight, enemyImage, enemyTarget);
        addEnemy(enemy);}


		
	}