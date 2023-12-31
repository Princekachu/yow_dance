package yoda;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class Main extends JFrame implements Runnable, KeyListener {

    // Timer
    private long rstart;
    private long rend;

    // Thread Object
    private Thread t;

    // Screen dimension variables
    private int screenWidth;
    private int screenHeight;

    // frame buffers
    FrameBuffer buffer;

    // Hiroshi
    Yoyo hero, hero2, flag;

    // Rectangular walls
    private Rectangle upperWall;
    private Rectangle lowerWall;

    // Margin for collision rectangles
    private int margin = 3; // Adjust this margin value as needed

    // Flag to check if the winner has already been declared
    private boolean winnerDeclared = false;

    private static Clip backgroundMusic;
    private static String backgroundMusicFilePath = "src//Sound_BG.wav";


    public Main(String title, int width, int height) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setResizable(false);

        screenWidth = width;
        screenHeight = height;

        // Instantiate character
        hero = new Yoyo("src//FinalImg2.png", 0, 295, 98, 98); // Try.png
        hero2 = new Yoyo("src//FinalImg.png", 530, 295, 98, 98); // Try2.png
        flag = new Yoyo("src//FlagFinal.png", 545, 15, 95, 95);

        // Instantiate thread using the class instance
        t = new Thread(this);

        // Initialize rectangular walls
        upperWall = new Rectangle(110, 100, 150, (height / 2) + 50);
        lowerWall = new Rectangle(width - 300, (height / 2) - 100, 300, height / 2);

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        setLocationRelativeTo(null);
        setVisible(true);
        startAnimation(); // we start the animation

    }

    public void paint(Graphics g) {
        // detect if the frame buffer is initialized
        if(buffer == null)
            buffer = new FrameBuffer(createImage(screenWidth, screenHeight),
                    createImage(screenWidth, screenHeight));

        Graphics2D g2d = (Graphics2D) g;

        // Clear (createImage(screenWidth, screenHeight))
        buffer.clear(createImage(getWidth(), getHeight()));

        buffer.drawCharacter(hero, 0);
        buffer.drawCharacter(hero2, 0);
        buffer.drawCharacter(flag, 0);

        // Draw
        g2d.drawImage(buffer.render(), 0, 0, this);

        // Draw rectangular walls
        g2d.setColor(new Color(92, 100, 139));  // Dark Blue
        g2d.fill(upperWall);
        g2d.fill(lowerWall);

        // Render

        g2d.dispose();
    }

    public void run() {
        while(true){
            try {

                // update frame
                hero.update();
                hero2.update();
                flag.update();

                // Check if hero1 touches the flag
                if (hero.getBounds().intersects(flag.getBounds()) && !winnerDeclared) {
                    winnerDeclared = true;  // Set the flag to true
                    new Won("Yow Race", 640, 400, "src//FinalImg2.png");

                    dispose();
                }

                // Check if hero2 touches the flag
                if (hero2.getBounds().intersects(flag.getBounds()) && !winnerDeclared) {
                    winnerDeclared = true;  // Set the flag to true
                    new Won("Yow Race", 640, 400, "src//FinalImg.png");

                    dispose();
                }

                // To maintain a 25 frame per second rendering,
                // we let the thread sleep for 40 milliseconds.
                // That is:
                // 1 second = 1000 milliseconds
                // so we divide
                // 1000 milliseconds / 25
                // that gives us 40 milliseconds window
                // for every rendering

                // we render after the thread sleep
                rstart = System.currentTimeMillis();
                repaint();
                rend = System.currentTimeMillis();

                // sleep for the remaining time
                Thread.sleep(40-(rend-rstart));

            } catch (InterruptedException ex) {
                // Print errors
                System.out.println(ex.getMessage());
            }
        }

    }

    public void startAnimation(){
        t.start(); // starts the animation
        hero.setLastUpdate(System.currentTimeMillis());
        hero2.setLastUpdate(System.currentTimeMillis());

    }

    public static void playBackgroundMusic() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(backgroundMusicFilePath));
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioInputStream);

            // Loop the background music
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);

            // Adjust the volume if needed (optional)
            FloatControl gainControl = (FloatControl) backgroundMusic.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-20.0f); // Adjust the volume level (negative values reduce volume)

            backgroundMusic.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int step = 5;

        int newX = hero.xpos;
        int newY = hero.ypos;

        int newX2 = hero2.xpos;
        int newY2 = hero2.ypos;

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                newY = Math.max(hero.ypos - step, 0);
                break;
            case KeyEvent.VK_A:
                newX = Math.max(hero.xpos - step, 0);
                break;
            case KeyEvent.VK_S:
                newY = Math.min(hero.ypos + step, screenHeight - hero.getHeight());
                break;
            case KeyEvent.VK_D:
                newX = Math.min(hero.xpos + step, screenWidth - hero.getWidth());
                break;
            case KeyEvent.VK_UP:
                newY2 = Math.max(hero2.ypos - step, 0);
                break;
            case KeyEvent.VK_LEFT:
                newX2 = Math.max(hero2.xpos - step, 0);
                break;
            case KeyEvent.VK_DOWN:
                newY2 = Math.min(hero2.ypos + step, screenHeight - hero2.getHeight());
                break;
            case KeyEvent.VK_RIGHT:
                newX2 = Math.min(hero2.xpos + step, screenWidth - hero2.getWidth());
                break;
        }

        int smallMargin = 10; // Adjust this smaller margin value as needed

        Rectangle proposedHeroRect = new Rectangle(newX + smallMargin, newY + smallMargin, hero.getWidth() - 2 * smallMargin, hero.getHeight() - 2 * smallMargin - 10);
        Rectangle proposedHero2Rect = new Rectangle(newX2 + smallMargin, newY2 + smallMargin, hero2.getWidth() - 2 * smallMargin, hero2.getHeight() - 2 * smallMargin - 10);

        if (!proposedHeroRect.intersects(upperWall) && !proposedHeroRect.intersects(lowerWall)) {
            hero.xpos = newX;
            hero.ypos = newY;
        }

        if (!proposedHero2Rect.intersects(upperWall) && !proposedHero2Rect.intersects(lowerWall)) {
            hero2.xpos = newX2;
            hero2.ypos = newY2;
        }

        // Repaint only after the position is updated
        repaint();

        // Debugging: Draw colored borders around the heroes' collision rectangles
        //debugDrawCollisionRectangles();
    }


    private void debugDrawCollisionRectangles() {
        Graphics2D g2d = (Graphics2D) getGraphics();

        g2d.setColor(Color.RED); // Color for hero
        g2d.draw(new Rectangle(hero.xpos + margin, hero.ypos, hero.getWidth() - 2 * margin, hero.getHeight()));

        g2d.setColor(Color.BLUE); // Color for hero2
        g2d.draw(new Rectangle(hero2.xpos + margin, hero2.ypos, hero2.getWidth() - 2 * margin, hero2.getHeight()));

        g2d.dispose();
    }


    @Override
    public void keyReleased(KeyEvent e) {

    }
}
