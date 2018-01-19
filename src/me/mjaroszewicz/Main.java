package me.mjaroszewicz;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(UI::new);

        ScreenCapturer sc = new ScreenCapturer();
        sc.captureScreen();

    }
}
