package me.mjaroszewicz;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

class DataUtils {

    private DataUtils(){}

    /**
     * Method that adds screenshot reference to clipboard. Keep in mind that data might be unnaccessible on some Xorg-based systems.
     * @param bi image to be saves
     */
    public static void saveImageToClipboard(BufferedImage bi){

        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();

        c.setContents(new TransferableImage(bi), null);
    }


    public static void saveSequenceAsGif(List<BufferedImage> list){

        System.out.println("Saving GIF...");

        String filename = Main.getPreferences().get("filename");

        if(filename == null || filename.isEmpty())
            filename = "recording";

        try{
            ImageOutputStream ios = new FileImageOutputStream(new File(filename + ".gif"));

            GifSequenceWriter writer = new GifSequenceWriter(ios, list.get(0).getType(), 1000 / 30, true);

            for(BufferedImage i: list)
                writer.writeToSequence(i);

            writer.close();
            ios.close();

        }catch(Throwable t){
            System.err.println("Couldn't save GIF image.");
            t.printStackTrace();
        }

        System.out.println("GIF file saved.");


    }

    /**
     * Saves image to file, using name specified in preferences. If no name is specified, "screenshot" is used.
     * @param img image to be saved
     */
    public static void saveImageToFile(BufferedImage img){

        String filename = Main.getPreferences().get("filename");

        if(filename == null || filename.isEmpty())
            filename = "screenshot";

        try{
            File output = new File(filename + ".png");
            ImageIO.write(img, "png", output);

        }catch(Throwable t){
            t.printStackTrace();
        }

        System.out.println("Image successfully written to file " + filename + ".png");
    }
}


