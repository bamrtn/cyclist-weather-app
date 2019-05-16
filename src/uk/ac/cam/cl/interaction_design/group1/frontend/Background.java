package uk.ac.cam. cl.interaction_design.group1.frontend;
import java.awt.Image;
import javax.swing.*;
import java.awt.*;
public class Background extends JPanel
{
    Image img;
    public Background()
    {
        // Read the image and place it in the variable img so it can be used in paintComponent
        img = Toolkit.getDefaultToolkit().createImage("images/AppBackground.png");

    }

    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this); // draw the image
    }
}
