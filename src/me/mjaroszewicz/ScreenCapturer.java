package me.mjaroszewicz;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

public class ScreenCapturer {

    private GraphicsEnvironment env;
    private Robot robot;
    private List<BufferedImage> frames;

    private Thread recordingThread;
    private volatile boolean recordingThreadRunFlag = true; //false if stopped
    private Rectangle selection;

    ScreenCapturer(){
        init();
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

        recordingThread = new Thread(() -> {

            //if no selection is provided, whole screen gets captured
            if(selection == null)
                selection = env.getMaximumWindowBounds();

            while(recordingThreadRunFlag){
                try{
                    Thread.sleep(1000L / 30);
                }catch (InterruptedException ex){
                    ex.printStackTrace();
                }

                BufferedImage frame = robot.createScreenCapture(selection);
                frames.add(frame);
            }

        });

    }

    public BufferedImage captureScreen(){

        Rectangle r = env.getMaximumWindowBounds();
        BufferedImage ret = robot.createScreenCapture(r);

        return ret;

    }


    public void startRecording(){
        recordingThreadRunFlag = true;
        frames = new LinkedList<>();
        recordingThread.start();
    }

    public void stopRecording(){
        recordingThreadRunFlag = false;
    }


    public List<BufferedImage> getFrames(){
        return frames;
    }


}


