package yoda;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Start extends JFrame {

    public Start(String title, int width, int height) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(false);


        Main.playBackgroundMusic();

        // Create and add your custom JPanel with resized image
        MyPanel myPanel = new MyPanel();
        add(myPanel);

        // Create and add the "Start" button
        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the button click event
                // You can navigate to another file or perform other actions here

                new Main("Yow Race", 650, 400);
            }
        });
        add(startButton, BorderLayout.SOUTH);  // Add the button to the bottom of the frame
    }

    private class MyPanel extends JPanel {

        private Image resizedImage;  // Resized image variable

        public MyPanel() {
            // Load the original image
            ImageIcon originalImageIcon = new ImageIcon("src//FrontPage.png"); // Replace with the actual path to your image
            Image originalImage = originalImageIcon.getImage();

            setBackground(Color.WHITE);

            // Resize the image
            int newWidth = 350; // Set your desired width
            int newHeight = 300; // Set your desired height
            resizedImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Draw the resized image at the top-left corner of the panel
            g.drawImage(resizedImage, 145, 15, this);

            // Your additional custom painting code goes here
        }
    }

    public static void main(String[] args) {

        Start s = new Start("Yow Race", 640, 400);
        s.setVisible(true);
    }
}
