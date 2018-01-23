package me.mjaroszewicz;

import javax.swing.*;
import java.awt.*;

public class UI extends JFrame{

    CapturePane capturePane = new CapturePane();

    public UI(){
        setPreferredSize(new Dimension(0, 0));
        setUndecorated(true);

        add(capturePane);
        pack();
        setVisible(true);
    }

    void destroyCapturePane() {
        setVisible(false);
        remove(capturePane);
        pack();
        invalidate();

    }

}
