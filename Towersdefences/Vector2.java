package Towersdefences;

public class Vector2 {
    private double x;
    private double y;

    public Vector2() {
        this.x = 0;
        this.y = 0;
    }

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public Vector2 add(Vector2 other) {
        return new Vector2(x + other.getX(), y + other.getY());
    }

    public Vector2 subtract(Vector2 other) {
        return new Vector2(x - other.getX(), y - other.getY());
    }

    public Vector2 multiply(double scalar) {
        return new Vector2(x * scalar, y * scalar);
    }

    public Vector2 divide(double scalar) {
        if (scalar != 0) {
            return new Vector2(x / scalar, y / scalar);
        }
        return new Vector2();
    }

    public Vector2 normalize() {
        double magnitude = magnitude();
        if (magnitude != 0) {
            return new Vector2(x / magnitude, y / magnitude);
        }
        return new Vector2();
    }

    public double dotProduct(Vector2 other) {
        return x * other.getX() + y * other.getY();
    }

    public double distanceTo(Vector2 other) {
        double dx = x - other.getX();
        double dy = y - other.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
}