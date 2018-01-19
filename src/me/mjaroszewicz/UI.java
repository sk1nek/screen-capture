package me.mjaroszewicz;

import javax.swing.*;
import java.awt.*;

class UI {

    private static JFrame jframe;
    private static CapturePane capturePane;
    private static JButton screenshotButton;


    UI(){
        prepareGUI();
    }

    private void prepareGUI(){
        jframe = new JFrame();

        screenshotButton = new JButton("Screenshot");
        screenshotButton.addActionListener(e -> {
            jframe.remove(screenshotButton);
            buildCapturePane();

        });

        jframe.setLocation(0, 0);
        jframe.add(screenshotButton);
        jframe.setUndecorated(true);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setVisible(true);
        jframe.pack();
    }

    private void buildCapturePane(){

        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle r = env.getMaximumWindowBounds();
        Dimension d = new Dimension(r.width, r.height);

        capturePane = new CapturePane();
        capturePane.setPreferredSize(d);
        capturePane.setLocation(0,0);
        jframe.add(capturePane);
        jframe.pack();
        jframe.repaint();
    }

    static void deconstructCapturePane(){
        capturePane.invalidate();
        jframe.remove(capturePane);
        jframe.revalidate();
        jframe.repaint();
        jframe.pack();

    }


}




