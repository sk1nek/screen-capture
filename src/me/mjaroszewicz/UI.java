package me.mjaroszewicz;

import javax.swing.*;
import java.awt.*;

class UI {

    private static JFrame jframe;
    private static CapturePane capturePane;
    private static JButton screenshotButton;

    private static JButton startButton;
    private static JButton stopButton;
    private static JButton saveButton;

    private ScreenCapturer screenCapturer;


    UI(){
        prepareGUI();
    }

    private void prepareGUI(){
        jframe = new JFrame();
        jframe.setLayout(new GridLayout(3, 3));

        screenCapturer = new ScreenCapturer();

        screenshotButton = new JButton("Screenshot");
        screenshotButton.addActionListener(e -> {
            jframe.remove(screenshotButton);
            buildCapturePane();

        });

        startButton = new JButton("start");
        startButton.addActionListener((e) -> screenCapturer.startRecording());
        stopButton = new JButton("stop");
        stopButton.addActionListener(e -> screenCapturer.stopRecording());
        saveButton = new JButton("save");
        saveButton.addActionListener(e -> {
            try{
                DataUtils.saveSequenceAsGif(screenCapturer.getFrames(), "filename");
            }catch (Throwable t){
                t.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error has occured during gif processing");
            }
        });

        jframe.add(screenshotButton);
        jframe.add(startButton);
        jframe.add(stopButton);
        jframe.add(saveButton);


        jframe.setLocation(0, 0);

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

        jframe.remove(startButton);
        jframe.remove(saveButton);
        jframe.remove(stopButton);
        jframe.setLayout(new BorderLayout());

        jframe.add(capturePane, BorderLayout.CENTER);
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




