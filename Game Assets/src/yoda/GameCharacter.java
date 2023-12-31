package yoda;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GameCharacter {
    // Position of the Character
    public int xpos;
    public int ypos;

    // Asset Sprite Sheet
    private BufferedImage spriteSheet;
    private BufferedImage[] sprites;

    // Timing property
    private long lastUpddate;

    // The speed of animation
    private long speed;

    // Current Frame Index
    private int index;

    // Constructor
    public GameCharacter(int xpos, int ypos, String asset,
                         int nrows, int ncols, int swidth, int sheight, long speed) {
        this.xpos = xpos;
        this.ypos = ypos;

        // load images
        try {
            spriteSheet = ImageIO.read(new File(asset));
            sprites = new BufferedImage[nrows * ncols];
            for (int i = 0; i < nrows; i++){
                for (int j = 0; j < ncols; j++){
                    sprites[(i * ncols) + j] = spriteSheet.getSubimage(
                            j * swidth,
                            i * sheight,
                            swidth,
                            sheight);
                }
            }
            index = 0; // set current index
            this.speed = speed; // set speed
            this.lastUpddate = 0L; // set last update time
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

    }

    // New methods added
    public int getWidth() {
        return sprites[0].getWidth();
    }

    public int getHeight() {
        return sprites[0].getHeight();
    }

    // Setters and Getters
    public long getLastUpdate() {
        return this.lastUpddate;
    }


    public void setLastUpdate(long lastUpdate){
        this.lastUpddate = lastUpdate;
    }

    public long getSpeed() {
        return speed;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }


    public int getXPos() {
        return this.xpos;
    }

    public int getYPos() {
        return this.ypos;
    }

    public BufferedImage getCurrentFrame() {
        return sprites[index];
    }

    public void update() {
        return;
    }


}

