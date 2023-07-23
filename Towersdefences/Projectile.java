package Towersdefences;
import java.awt.*;

public class Projectile extends Entity {
    private Vector2 velocity;
    private int damage;
    private int width;
    private int height;
    private Image image;
    private Enemy target;

    public Projectile(Vector2 position, Vector2 direction, int damage, int width, int height, Image image, int projectileSpeed) {
            super(position);
            this.velocity = direction.multiply(projectileSpeed);
            this.damage = damage;
            this.width = width;
            this.height = height;
            this.image = image;
            this.target = null;
    }

    public void setTarget(Enemy target) {
            this.target = target;
    }

    public void update() {
            position = position.add(velocity);

            // Projectile'ın sınırlarının dışında olup olmadığını kontrol et.
            if (position.getX() < 0 || position.getX() >= Game.getInstance().getWidth() ||
                    position.getY() < 0 || position.getY() >= Game.getInstance().getHeight()) {
                    remove();
            }

            // Hedefe çarptıysa hasar ver ve sil.
            if (target != null && getBounds().intersects(target.getBounds())) {
                    target.takeDamage(damage);
                    remove();
            } else {
                    // Hedef yoksa, geçerken başka bir düşmana çarptıysa hasar ver ve sil.
                    for (Enemy enemy : Game.getInstance().getEnemies()) {
                            if (enemy != target && getBounds().intersects(enemy.getBounds())) {
                                    enemy.takeDamage(damage);
                                    remove();
                                    break;
                            }
                    }
            }
    }

	    @Override
	    public void render(Graphics g) {
	            // Projectile görüntüsünü çiz.
	            int x = (int) position.getX() - width / 2;
	            int y = (int) position.getY() - height / 2;
	            g.drawImage(image, x, y, width, height, null);
	    }
	
	    public Rectangle getBounds() {
	            int x = (int) position.getX() - width / 2;
	            int y = (int) position.getY() - height / 2;
	            return new Rectangle(x, y, width, height);
	    }
	
	    public void remove() {
	            Game.getInstance().removeProjectile(this);
	    }
}