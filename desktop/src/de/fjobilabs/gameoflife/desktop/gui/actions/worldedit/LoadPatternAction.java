package de.fjobilabs.gameoflife.desktop.gui.actions.worldedit;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fjobilabs.gameoflife.desktop.SimulatorFrame;
import de.fjobilabs.gameoflife.desktop.gui.actions.AbstractWorldAction;
import de.fjobilabs.gameoflife.desktop.gui.actions.ActionManager;
import de.fjobilabs.gameoflife.desktop.simulator.WorldEditorException;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 30.09.2017 - 14:46:35
 */
public class LoadPatternAction extends AbstractWorldAction {
    
    private static final long serialVersionUID = 5793033584923943756L;
    
    private static final Logger logger = LoggerFactory.getLogger(LoadPatternAction.class);
    
    public static final String ACTION_COMMAND = "load_pattern";
    
    public LoadPatternAction(ActionManager actionManager, SimulatorFrame simulatorFrame) {
        super(actionManager, "Load pattern", ACTION_COMMAND, simulatorFrame);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        File patternFile = getPatternFile();
        if (patternFile != null) {
            loadPattern(patternFile);
            this.simulatorFrame.getWorldEditorPanel().refreshPatterns();
        }
    }
    
    private void loadPattern(File patternFile) {
        try {
            this.worldEditor.getPatternManager().loadPattern(patternFile);
        } catch (IOException e) {
            logger.error("Failed to load pattern file: " + patternFile, e);
            JOptionPane.showMessageDialog(this.simulatorFrame, "Cannot load pattern file",
                    "Pattern loading error", JOptionPane.ERROR_MESSAGE);
        } catch (WorldEditorException e) {
            logger.error("Failed to parse pattern: " + patternFile, e);
            JOptionPane.showMessageDialog(this.simulatorFrame, "Failed to parse pattern file",
                    "Pattern parse error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private File getPatternFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open Pattern");
        fileChooser.setFileFilter(new FileNameExtensionFilter("RLE Pattern (RLE) (*.rle)", "rle"));
        int state = fileChooser.showOpenDialog(this.simulatorFrame);
        if (state == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }
}
