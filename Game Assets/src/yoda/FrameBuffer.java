package yoda;

import java.awt.Graphics2D;
import java.awt.Image;

public class FrameBuffer {

    private Image currentFrame;
    private Image nextFrame;


    public FrameBuffer(Image currentFrame, Image nextFrame) {
        this.currentFrame = currentFrame;
        this.nextFrame = nextFrame;
    }

    public Image getCurrentFrame(){
        return currentFrame;
    }

    public Image getNextFrame(){
        return nextFrame;
    }

    public void drawCharacter(GameCharacter c, int margin) {
        Graphics2D g = (Graphics2D) nextFrame.getGraphics();
        int adjustedX = c.getXPos() - margin;
        int adjustedY = c.getYPos() - margin;
        int adjustedWidth = c.getWidth() - 2 * margin;
        int adjustedHeight = c.getHeight() - 2 * margin;

        g.drawImage(c.getCurrentFrame(), adjustedX, adjustedY, adjustedWidth, adjustedHeight, null);
        g.dispose();
    }



    public Image render() {
        // swap
        currentFrame = nextFrame;
        return currentFrame;
    }

    public void clear(Image m) {
        nextFrame = m;
    }
}

