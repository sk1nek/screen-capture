package me.mjaroszewicz;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(UI::new);

    }
}
