/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oop.im2020;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

/**
 *
 * @author childm
 */
public class ImagePanel extends JComponent {

    private BufferedImage image;

    public void setImage(final BufferedImage image) {
        this.image = image;
        setPreferredSize(new Dimension(this.image.getWidth(), this.image.getHeight()));
        repaint();
    }

    @Override
    public void paintComponent(final Graphics g) {
        g.drawImage(this.image, 0, 0, this);
    }
}
