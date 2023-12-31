package yoda;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Yoyo extends GameCharacter {

    // Asset Constant
    private final static int NROWS = 2;
    private final static int NCOLS = 5;
    private final static int SPEED = 50;

    // Constructor
    public Yoyo(String filePath, int initialX, int initialY, int SWIDTH, int SHEIGHT) {
        super(initialX, initialY, filePath, NROWS, NCOLS, SWIDTH, SHEIGHT, SPEED);
    }

    public void update() {
        long ctime = System.currentTimeMillis();

        if ((ctime - this.getLastUpdate()) >= this.getSpeed()) {
            this.setLastUpdate(ctime);
            this.setIndex(this.getIndex() + 1);
            // check if on the last frame
            if (this.getIndex() > (NROWS * NCOLS) - 1) this.setIndex(0);
        }
    }

    // New method to check for intersections
    public boolean intersects(int x, int y, int width, int height) {
        Rectangle characterBounds = new Rectangle(this.xpos, this.ypos, this.getWidth(), this.getHeight());
        Rectangle otherBounds = new Rectangle(x, y, width, height);
        return characterBounds.intersects(otherBounds);
    }

    public Rectangle getBounds() {
        // Assuming x, y, width, and height are your character's properties
        return new Rectangle(xpos, ypos, getWidth(), getHeight());
    }

    // Added methods for Won class
    public int getXPos() {
        return super.getXPos();
    }

    public int getYPos() {
        return super.getYPos();
    }

    // Override the getCurrentFrame method with the correct return type
    @Override
    public BufferedImage getCurrentFrame() {
        return super.getCurrentFrame(); // You may need to adjust this based on the actual implementation in the GameCharacter class
    }
}
