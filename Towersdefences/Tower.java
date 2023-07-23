package Towersdefences;
import java.awt.*;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import javax.sound.sampled.*;

public class Tower extends Entity {
    private int range;
    private int damage;
    private int width;
    private int height;
    private boolean isDraggable;
    private Vector2 selectedTowerPosition;
    private Image projectileImage;
    private long lastShotTime;
    private long shootInterval;
    private int projectileSpeed; 
    private Clip fireSound;

    private int cost;
    private boolean active;

    
    
    
    
    public Tower(Vector2 position, int range, int damage, int width, int height, Image image, Image projectileImage, int projectileSpeed) {
        super(position);
        this.range = range;
        this.damage = damage;
        this.width = width;
        this.height = height;
        setImage(image);
        isDraggable = false;
        selectedTowerPosition = null;
        this.projectileImage = projectileImage;
        lastShotTime = System.currentTimeMillis();
        shootInterval = 2000; // 2 saniye
        this.projectileSpeed = projectileSpeed;

        // Ses dosyasını yükle
        try {
            File audioFile = new File("res\\Cannon.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat audioFormat = audioInputStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);
            fireSound = (Clip) AudioSystem.getLine(info);
            fireSound.open(audioInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        // Sürüklenebilir kule konumunu güncelle
        if (isDraggable && selectedTowerPosition != null) {
            setPosition(selectedTowerPosition);
        }

        // En yakın düşmana ateş et
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShotTime >= shootInterval) {
            findAndShootTarget();
            lastShotTime = currentTime;
        }
    }

    public void render(Graphics g) {
        // Kuleyi çiz
        g.drawImage(getImage(), (int) getPosition().getX(), (int) getPosition().getY(), width, height, null);
    }

    private void playFireSound() {
        if (fireSound != null) {
            if (fireSound.isRunning())
                fireSound.stop();
            fireSound.setFramePosition(0);
            fireSound.start();
        }
    }

    public void startShooting() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                update();
            }
        };

        int delay = 0; // Hemen başlat
        int period = 2000; // 2 saniyede bir ateş et
        timer.scheduleAtFixedRate(task, delay, period);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) getPosition().getX(), (int) getPosition().getY(), width, height);
    }

    public void findAndShootTarget() {
        Enemy closestEnemy = null;
        double closestDistance = Double.MAX_VALUE;

        for (Enemy enemy : Game.getInstance().getEnemies()) {
            double distance = enemy.getPosition().distanceTo(getPosition());
            if (distance <= range && distance < closestDistance) {
                closestEnemy = enemy;
                closestDistance = distance;
            }
        }

        if (closestEnemy != null) {
            // En yakın düşmana bir mermi ateşle
            Vector2 direction = closestEnemy.getPosition().subtract(getPosition()).normalize();
            Projectile projectile = new Projectile(getPosition(), direction, damage, width, height, projectileImage, projectileSpeed);
            projectile.setTarget(closestEnemy);
            Game.getInstance().addProjectile(projectile);

            // Ateş etmeden önce sesi çal
            playFireSound();
        }
        
        
    }
   
}