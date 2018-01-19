package me.mjaroszewicz;

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

    public static void saveImageToClipboard(BufferedImage bi){

        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();

        c.setContents(new TransferableImage(bi), null);
    }

    public static void saveSequenceAsGif(List<BufferedImage> list, String filename)throws IOException{

        BufferedImage firstImage = list.get(0);

        ImageOutputStream os = new FileImageOutputStream(new File(filename));

        GifSequenceWriter writer = new GifSequenceWriter(firstImage, os);

        writer.writeToSequence(firstImage);

        for(int i = 1; i < list.size() - 1; i++){
            BufferedImage next = list.get(i);
            writer.writeToSequence(next);
        }

        writer.close();
        os.close();

    }
}


