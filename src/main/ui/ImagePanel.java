package ui;

import javax.swing.JPanel;
import java.awt.Image;
import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ImagePanel extends JPanel {
    private Image image;

    public ImagePanel(String imagePath) {
        try {
            image = ImageIO.read(new File(imagePath)); // Load the image during initialization
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception as appropriate for your application
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the image to fit the panel size
        g.drawImage(image, 100, 100, this.getWidth(), this.getHeight(), this);
    }
}