package yoda;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Won extends JFrame {

    private Yoyo winner;
    private FrameBuffer buffer;

    public Won(String title, int width, int height, String filePath) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(false);

        // Instantiate character
        winner = new Yoyo(filePath, 255, 55, 98, 98);

        // Create and add your custom JPanel with resized image
        MyPanel myPanel = new MyPanel();
        add(myPanel);

        // Start animation timer
        Timer timer = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update frame
                winner.update();

                // Repaint the panel
                myPanel.repaint();
            }
        });
        timer.start();

        setVisible(true);
    }

    private class MyPanel extends JPanel {

        private Image resizedImage;

        public MyPanel() {
            // Load the original image using getResource
            ImageIcon originalImageIcon = new ImageIcon(getClass().getResource("/Winner.png"));

            if (originalImageIcon.getImage() == null) {
                throw new RuntimeException("Unable to load the image.");
            }

            Image originalImage = originalImageIcon.getImage();

            setBackground(Color.WHITE);

            // Resize the image
            int newWidth = 330;
            int newHeight = 280;
            resizedImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Draw the resized image at the top-left corner of the panel
            g.drawImage(resizedImage, 135, 70, this);

            Graphics2D g2d = (Graphics2D) g.create();  // Create a new Graphics2D object
            g2d.drawImage(winner.getCurrentFrame(), winner.getXPos(), winner.getYPos(), this);
            g2d.dispose();  // Dispose the Graphics2D object to release resources
        }
    }
}
