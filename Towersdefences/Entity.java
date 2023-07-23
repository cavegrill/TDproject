package Towersdefences;
import java.awt.*;

public abstract class Entity {
    protected Vector2 position;
    protected Image image;

    public Entity(Vector2 position) {
        this.position = position;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public abstract void update();

    public abstract void render(Graphics g);
}