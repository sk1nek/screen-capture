package me.mjaroszewicz;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

class ScreenCapturer {

    private GraphicsEnvironment env;
    private Robot robot;
    private List<BufferedImage> frames;

    private Callable<List<BufferedImage>> recordingCallable;
    private Rectangle selection;

    private int frameLimit = 30;

    ScreenCapturer(){
        init();
    }

    ScreenCapturer(Rectangle selection){
        init();
        this.selection = selection;
    }

    private void init(){

        this.env = GraphicsEnvironment.getLocalGraphicsEnvironment();

        try{
            this.robot = new Robot(env.getDefaultScreenDevice());
        }catch (Throwable t){
            t.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error, look at stacktrace.");
            System.exit(-1);
        }

        recordingCallable = () -> {

            frames = new LinkedList<>();

            if (selection == null)
                selection = env.getMaximumWindowBounds();

            while (frames.size() < frameLimit) {

                BufferedImage frame = robot.createScreenCapture(selection);
                frames.add(frame);

                try {
                    Thread.sleep(1000L / 30);
                } catch (InterruptedException iex) {
                    iex.printStackTrace();
                }
            }

            return frames;
        };
    }

    public BufferedImage captureScreen(){

        Rectangle r = env.getMaximumWindowBounds();

        return robot.createScreenCapture(r);

    }

    public List<BufferedImage> recordFrames(int n){

        this.frameLimit = n;

        try{
            return recordingCallable.call();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return Collections.emptyList();
    }


}


