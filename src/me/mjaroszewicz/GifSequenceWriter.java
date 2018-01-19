package me.mjaroszewicz;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.Iterator;

public class GifSequenceWriter {

    private IIOMetadata metadata;
    private ImageWriter imageWriter;
    private ImageWriteParam params;
    private ImageOutputStream ios;


    public GifSequenceWriter(BufferedImage bufferedImage, ImageOutputStream ios) throws IOException{

        this.ios = ios;

        imageWriter = getImageWriter();
        params = imageWriter.getDefaultWriteParam();

        ImageTypeSpecifier typeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(bufferedImage.getType());

        metadata = imageWriter.getDefaultImageMetadata(typeSpecifier, params);

        String metaFormatName = metadata.getNativeMetadataFormatName();

        IIOMetadataNode root = (IIOMetadataNode) metadata.getAsTree(metaFormatName);

        IIOMetadataNode graphicsControlExtensionNode = getNode(root, "GraphicControlExtension");

        graphicsControlExtensionNode.setAttribute("disposalMethod", "none");
        graphicsControlExtensionNode.setAttribute("userInputFlag", "FALSE");
        graphicsControlExtensionNode.setAttribute("transparentColorFlag", "FALSE");
        graphicsControlExtensionNode.setAttribute("delayTime", Integer.toString(1000 / 30));
        graphicsControlExtensionNode.setAttribute("transparentColorIndex", "0");

        IIOMetadataNode appExtensionNode = getNode(root, "ApplicationExtensions");

        IIOMetadataNode child = new IIOMetadataNode("ApplicationExtension");

        child.setAttribute("applicationID", "app");
        child.setAttribute("authenticationCode", "2.0");

        child.setUserObject(new byte[]{0x1, (byte) (0), (byte) (0)});

        appExtensionNode.appendChild(child);

        metadata.setFromTree(metaFormatName, root);

        imageWriter.setOutput(ios);
        imageWriter.prepareWriteSequence(null);


    }

    public void writeToSequence(RenderedImage img) throws IOException{
        imageWriter.writeToSequence(new IIOImage(img, null, metadata), params);
    }

    public void close() throws IOException{
        imageWriter.endWriteSequence();
    }

    private ImageWriter getImageWriter() throws IIOException {

        Iterator<ImageWriter> iter = ImageIO.getImageWritersBySuffix("gif");

        if (!iter.hasNext())
            throw new IIOException("No gif image writers available");

        return iter.next();

    }

    private static IIOMetadataNode getNode(IIOMetadataNode rootNode, String nodeName){

        int n = rootNode.getLength();

        for(int i = 0 ; i < n ; i++)
            if(rootNode.item(i).getNodeName().compareToIgnoreCase(nodeName) == 0)
                return ((IIOMetadataNode) rootNode.item(i));

        IIOMetadataNode node = new IIOMetadataNode(nodeName);
        rootNode.appendChild(node);

        return node;
    }


}
