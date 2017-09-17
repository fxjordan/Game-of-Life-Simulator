package de.fjobilabs.gameoflife.model.simulation;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 17.09.2017 - 21:55:20
 */
public class Ant {
    
    private int x;
    private int y;
    private Direction direction;
    
    public Ant(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
    
    public void turnLeft() {
        switch (this.direction) {
        case LEFT:
            this.direction = Direction.DOWN;
            break;
        case UP:
            this.direction = Direction.LEFT;
            break;
        case RIGHT:
            this.direction = Direction.UP;
            break;
        case DOWN:
            this.direction = Direction.RIGHT;
            break;
        default:
            throw new IllegalStateException("Invalid direction: " + this.direction);
        }
    }
    
    public void turnRight() {
        switch (this.direction) {
        case LEFT:
            this.direction = Direction.UP;
            break;
        case UP:
            this.direction = Direction.RIGHT;
            break;
        case RIGHT:
            this.direction = Direction.DOWN;
            break;
        case DOWN:
            this.direction = Direction.LEFT;
            break;
        default:
            throw new IllegalStateException("Invalid direction: " + this.direction);
        }
    }
    
    public void moveForward() {
        switch (this.direction) {
        case LEFT:
            this.x--;
            break;
        case UP:
            this.y++;
            break;
        case RIGHT:
            this.x++;
            break;
        case DOWN:
            this.y--;
            break;
        default:
            throw new IllegalStateException("Invalid direction: " + this.direction);
        }
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public Direction getDirection() {
        return direction;
    }
    
    public static enum Direction {
        LEFT, RIGHT, UP, DOWN
    }
}
