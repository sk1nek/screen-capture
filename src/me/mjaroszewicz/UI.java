package me.mjaroszewicz;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class UI extends JFrame{

    CapturePane capturePane = new CapturePane();
    private UIDestructionListener uiDestructionListener;

    public UI(){
        this.uiDestructionListener = new UIDestructionListener(this);
        setPreferredSize(new Dimension(0, 0));
        setUndecorated(true);

        add(capturePane);
        pack();
        setVisible(true);
    }

    void destroyCapturePane(BufferedImage image) {
        setVisible(false);
        remove(capturePane);
        pack();
        invalidate();
        dispose();
        uiDestructionListener.destructionHappened(image);
    }

}
