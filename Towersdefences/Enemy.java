package Towersdefences;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Enemy extends Entity {
    private Vector2 velocity;
    private int maxHealth;
    private int health;
    private int damage;
    private int width;
    private int height;
    private Vector2 target;
    private Vector2 startPosition;
    private BufferedImage healthBarImage;
    


  

    public Enemy(Vector2 position, Vector2 velocity, int health, int damage, int width, int height, Image image, Vector2 target) {
        super(position);
        this.velocity = velocity;
        this.maxHealth = health;
        this.health = health;
        this.damage = damage;
        this.width = width;
        this.height = height;
        setImage(image);
        this.target = target;
        this.startPosition = position;
        this.healthBarImage = createHealthBarImage();
    }

    public void update() {
        Vector2 direction = target.subtract(position).normalize();
        Vector2 velocity = direction.multiply(3); 
        position = position.add(velocity);

        // Check if the enemy has reached the target position
        if (position.getX() >= target.getX()) {
            position = startPosition; // Başlangıç konumuna geri dön
            Game.getInstance().decreaseLives();
            Game.getInstance().removeEnemy(this);
            return;
            
        }
    }

    @Override
    public void render(Graphics g) {
        // Draw the enemy image
        g.drawImage(getImage(), (int) position.getX(), (int) position.getY(), width, height, null);

        // Draw the health bar
        int healthBarWidth = width;
        int healthBarHeight = 10;
        int healthBarX = (int) position.getX();
        int healthBarY = (int) position.getY() - healthBarHeight - 5;
        g.setColor(Color.RED);
        g.fillRect(healthBarX, healthBarY, healthBarWidth, healthBarHeight);

        // Calculate the current health bar width based on the enemy's health
        int currentHealthBarWidth = (int) ((double) health / maxHealth * healthBarWidth);
        g.setColor(Color.GREEN);
        g.fillRect(healthBarX, healthBarY, currentHealthBarWidth, healthBarHeight);
    }

    public void remove() {
        Game game = Game.getInstance();
        if (game != null) {
            game.removeEnemy(this);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle((int) position.getX(), (int) position.getY(), width, height);
    }



    private BufferedImage createHealthBarImage() {
        int healthBarWidth = width;
        int healthBarHeight = 10;
        BufferedImage healthBarImage = new BufferedImage(healthBarWidth, healthBarHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = healthBarImage.createGraphics();
        g2d.setColor(Color.RED);
        g2d.fillRect(0, 0, healthBarWidth, healthBarHeight);
        return healthBarImage;
    }
    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            // Düşmanın canı sıfıra veya daha azına düştüğünde düşmanı kaldır
            remove();
        }
    }
    
   
}