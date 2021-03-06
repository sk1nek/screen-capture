package me.mjaroszewicz;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

class UI extends JFrame{

    private CapturePane capturePane = new CapturePane();
    private UIDestructionListener uiDestructionListener;

    public UI(){
        this.uiDestructionListener = new UIDestructionListener();
        setPreferredSize(new Dimension(0, 0));
        setUndecorated(true);

        add(capturePane);
        pack();
        setVisible(true);
    }

    void destroyCapturePane(BufferedImage image, Rectangle r) {
        setVisible(false);
        remove(capturePane);
        pack();
        invalidate();
        uiDestructionListener.destructionHappened(image, r);
        dispose();
    }

}
