package me.mjaroszewicz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

class UI {

    private static JFrame jframe;
    private static CapturePane capturePane;


    UI(){

        jframe = new JFrame();
        jframe.setVisible(true);
        capturePane = new CapturePane();
        jframe.add(capturePane);

        jframe.pack();
    }

    static void deconstructCapturePane(){
        capturePane.invalidate();
        jframe.remove(capturePane);
        jframe.revalidate();
        jframe.repaint();
        jframe.pack();

    }


}




