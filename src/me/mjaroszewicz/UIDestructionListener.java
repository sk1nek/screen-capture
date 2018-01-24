package me.mjaroszewicz;

import java.awt.image.BufferedImage;
import java.util.EventListener;
import java.util.List;

public class UIDestructionListener {

    UI ui;

    public UIDestructionListener(UI ui) {
        this.ui = ui;
    }

    void destructionHappened(BufferedImage image){
        onFrameDestruction(image);
    }

    void destructionHappened(List<BufferedImage> images){
        onFrameDestruction(images);
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

    private void onFrameDestruction(List<BufferedImage> images){

        DataUtils.saveSequenceAsGif(images);

    }




}
