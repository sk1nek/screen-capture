package me.mjaroszewicz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

class CapturePane extends JPanel {

    private Rectangle selection;
    private Point clickPoint;

    private ScreenCapturer capturer;
    private BufferedImage screenImage;
    private BufferedImage capturedImage;


    CapturePane(){

        this.capturer = new ScreenCapturer();
        screenImage = capturer.captureScreen();

        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2){
                    invalidate();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                clickPoint = e.getPoint();
                selection = null;
            }

            @Override
            public void mouseReleased(MouseEvent e) {

                BufferedImage bi = screenImage;
                Rectangle r = selection;
                capturedImage = bi.getSubimage(r.x, r.y, r.width, r.height);

                DataUtils.saveImageToClipboard(capturedImage);

                UI ancestor = (UI) getTopLevelAncestor();
                ancestor.destroyCapturePane(capturedImage);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                Point dragPoint = e.getPoint();
                int x = Math.min(clickPoint.x, dragPoint.x);
                int y = Math.min(clickPoint.y, dragPoint.y);
                int width = Math.max(clickPoint.x - dragPoint.x, dragPoint.x - clickPoint.x);
                int height = Math.max(clickPoint.y - dragPoint.y, dragPoint.y - clickPoint.y);
                selection = new Rectangle(x, y, width, height);
                repaint();
            }
        };

        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2d = (Graphics2D) graphics.create();
        g2d.setColor(new Color(255, 255, 255, 128));
        Area fill = new Area(new Rectangle(new Point(0, 0), getSize()));

        g2d.drawImage(screenImage, 0, 0, null);

        if(selection != null)
            fill.subtract(new Area(selection));

        g2d.fill(fill);

        if(selection != null){
            g2d.setColor(Color.BLACK);
            g2d.draw(selection);
        }

        g2d.dispose();


    }

    public BufferedImage getCapturedImage() {
        return capturedImage;
    }



}