package me.mjaroszewicz;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class UIDestructionListener {

    UI ui;

    public UIDestructionListener(UI ui) {
        this.ui = ui;
    }

    void destructionHappened(BufferedImage image, Rectangle rec){

        switch(Main.getPreference("type")){

            case "screenshot":
                onFrameDestruction(image);
                break;

            case "gif":
                onFrameDestruction(rec);
        }

        onFrameDestruction(image);
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
        List<BufferedImage> frames = new ArrayList<>();

        SwingUtilities.invokeLater(() -> {
            sc.startRecording();

            while (sc.getFrames().size() < 300){
                try{
                    Thread.sleep(1000L/30L);
                }catch(Throwable t){}
            }

            sc.stopRecording();

            frames.addAll(sc.getFrames());

            DataUtils.saveSequenceAsGif(frames);
        });

    }






}
