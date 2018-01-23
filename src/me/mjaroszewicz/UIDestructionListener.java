package me.mjaroszewicz;

import java.awt.image.BufferedImage;
import java.util.EventListener;

public class UIDestructionListener {

    UI ui;

    public UIDestructionListener(UI ui) {
        this.ui = ui;
        System.out.println(ui.toString());
    }

    void destructionHappened(BufferedImage image){
        onFrameDestruction(image);
    }

    protected void onFrameDestruction(BufferedImage image){
        DataUtils.saveImageToClipboard(image);
    }

}
