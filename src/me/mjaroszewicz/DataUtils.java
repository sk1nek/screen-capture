package me.mjaroszewicz;

import com.sun.imageio.plugins.jpeg.JPEGImageWriter;
import com.sun.imageio.plugins.png.PNGImageWriter;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.spi.ImageWriterSpi;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class DataUtils {

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

        String filename = Main.getPreferences().get("filename");

        if(filename == null || filename.isEmpty())
            filename = "recording";

        BufferedImage firstImage = list.get(0);

        try{
            ImageOutputStream os = new FileImageOutputStream(new File(filename + ".gif"));

            GifSequenceWriter writer = new GifSequenceWriter(firstImage, os);

            writer.writeToSequence(firstImage);

            for(BufferedImage bi: list)
                writer.writeToSequence(bi);

            writer.close();
            os.close();
        }catch(Throwable t){
            System.err.println("Couldn't save GIF image.");
            t.printStackTrace();
        }

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

        System.out.println("Image successfully written to file " + filename);
    }
}


