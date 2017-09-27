package de.fjobilabs.gameoflife.desktop;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class DesktopLauncher {
    
    public static void main(String[] arg) {
        // LwjglApplicationConfiguration config = new
        // LwjglApplicationConfiguration();
        // new LwjglApplication(new GameOfLifeGame(), config);
        // new LwjglApplication(new HighPerformanceCellRendererTest(), config);
        runGameOfLifeFrame();
    }
    
    private static void runGameOfLifeFrame() {
        setLookAndFell();
        SwingUtilities.invokeLater(() -> {
            SimulatorFrame frame = new SimulatorFrame();
            frame.setVisible(true);
        });
    }

    private static void setLookAndFell() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            System.err.println("Failed to set LookAndFell");
            e.printStackTrace();
        }
    }
}
