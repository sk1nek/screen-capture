package me.mjaroszewicz;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class DataUtils {

    private DataUtils(){}

    public static void saveImageToClipboard(BufferedImage bi){

        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();


        c.setContents(new TransferableImage(bi), null);
    }
}


