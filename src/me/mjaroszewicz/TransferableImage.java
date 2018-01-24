package me.mjaroszewicz;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;

class TransferableImage implements Transferable {

    private BufferedImage bufferedImage;

    TransferableImage(BufferedImage bufferedImage){
        this.bufferedImage = bufferedImage;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{DataFlavor.imageFlavor};
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor dataFlavor) {
        return DataFlavor.imageFlavor.equals(dataFlavor);
    }

    @Override
    public Object getTransferData(DataFlavor dataFlavor) throws UnsupportedFlavorException {

        if(!DataFlavor.imageFlavor.equals(dataFlavor))
            throw new UnsupportedFlavorException(dataFlavor);

        return bufferedImage;

    }
}