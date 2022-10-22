/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oop.im2020;

import java.awt.BorderLayout;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;

/**
 *
 * @author childm
 */
public class TintUI extends JPanel {

    private final JRadioButton rButton = new JRadioButton("Red");
    private final JRadioButton gButton = new JRadioButton("Green");
    private final JRadioButton bButton = new JRadioButton("Blue");
    private final JSlider alphaSlider = new JSlider(0, 100);

    private ColourComponent selectedColor = ColourComponent.RED;

    public TintUI() {
        super(new BorderLayout());

        final JPanel radioPanel = new JPanel();
        radioPanel.add(this.rButton);
        radioPanel.add(this.gButton);
        radioPanel.add(this.bButton);

        // make radio buttons mutually exclusive
        final ButtonGroup bg = new ButtonGroup();
        bg.add(this.rButton);
        bg.add(this.gButton);
        bg.add(this.bButton);

        this.rButton.setSelected(true);

        this.rButton.addActionListener((ev) -> colourChosen(ColourComponent.RED));
        this.gButton.addActionListener((ev) -> colourChosen(ColourComponent.GREEN));
        this.bButton.addActionListener((ev) -> colourChosen(ColourComponent.BLUE));

        add(radioPanel, BorderLayout.CENTER);
        add(this.alphaSlider, BorderLayout.SOUTH);
    }

    private void colourChosen(final ColourComponent colour) {
        this.selectedColor = colour;
    }

    public ColourComponent getChosenColor() {
        return this.selectedColor;
    }

    public int getAlpha() {
        return this.alphaSlider.getValue();
    }
}
