package me.mjaroszewicz;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

class UIDestructionListener {

    void destructionHappened(BufferedImage image, Rectangle rec){

        switch(Main.getPreference("type")){

            case "screenshot":
                onFrameDestruction(image);
                break;

            case "gif":
                onFrameDestruction(rec);
        }
    }


    private void onFrameDestruction(BufferedImage image){

        switch(Main.getPreference("storage")){

            case "file":
                DataUtils.saveImageToFile(image);

            case "clipboard":
                DataUtils.saveImageToClipboard(image);
                break;

            default:
                DataUtils.saveImageToClipboard(image);
                break;
        }

    }

    private void onFrameDestruction(Rectangle rec){

        ScreenCapturer sc = new ScreenCapturer(rec);


        SwingUtilities.invokeLater(() -> {

            List<BufferedImage> frames;

            String secondsString = Main.getPreference("seconds");
            int seconds = Integer.parseInt(secondsString);

            System.out.println("Capture starting in: ");

            for(int i = 3 ; i > 0 ; i--){

                System.out.println(i);

                try{
                    Thread.sleep(1000L);
                }catch(Throwable t){
                    t.printStackTrace();
                }
            }


            frames = sc.recordFrames(seconds * 30);

            System.out.println(frames.size() + " frames recorded.");
            DataUtils.saveSequenceAsGif(frames);
        });

    }






}
