package de.fjobilabs.gameoflife.desktop;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class DesktopLauncher {
    
    public static void main(String[] arg) {
        System.out.println("Starting Game of Life Simulator...");
        runGameOfLifeFrame();
    }
    
    private static void runGameOfLifeFrame() {
        setLookAndFeel();
        SwingUtilities.invokeLater(() -> {
            SimulatorFrame frame = new SimulatorFrame();
            frame.setVisible(true);
            System.out.println("Game of Life Simulator started");
        });
    }

    private static void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            System.err.println("Failed to set LookAndFeel");
            e.printStackTrace();
        }
    }
}
