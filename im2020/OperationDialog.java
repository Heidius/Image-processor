/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oop.im2020;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 *
 * @author childm
 */
public class OperationDialog extends JDialog {

    private final JButton applyButton = new JButton("Apply");
    private final JButton cancelButton = new JButton("Cancel");
    
    private boolean wasCancelled = true;
    
    public OperationDialog(ImageProcessor frame, JPanel optionPanel) {
        super(frame, true);
      
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(this.applyButton);
        buttonPanel.add(this.cancelButton);

        add(optionPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        this.applyButton.addActionListener(ev -> doApplyButton());
        this.cancelButton.addActionListener(ev -> setVisible(false));

        pack();
    }

    public boolean wasCancelled() {
    	return this.wasCancelled;
    }
    
    private void doApplyButton() {
        setVisible(false);
        this.wasCancelled = false;
    }
}
