package de.fjobilabs.gameoflife.desktop.gui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import de.fjobilabs.gameoflife.desktop.gui.actions.ActionManager;
import de.fjobilabs.gameoflife.desktop.gui.actions.control.PauseSimulationAction;
import de.fjobilabs.gameoflife.desktop.gui.actions.control.StartSimulationAction;
import de.fjobilabs.gameoflife.desktop.gui.actions.control.StepBackwardAction;
import de.fjobilabs.gameoflife.desktop.gui.actions.control.StepForwardAction;
import de.fjobilabs.gameoflife.desktop.gui.actions.file.CloseSimulationAction;
import de.fjobilabs.gameoflife.desktop.gui.actions.file.ExitAction;
import de.fjobilabs.gameoflife.desktop.gui.actions.file.NewSimulationAction;
import de.fjobilabs.gameoflife.desktop.gui.actions.file.OpenSimulationAction;
import de.fjobilabs.gameoflife.desktop.gui.actions.file.SaveSimulationAction;
import de.fjobilabs.gameoflife.desktop.gui.actions.file.SaveSimulationAsAction;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 25.09.2017 - 17:45:18
 */
public class SimulatorMenuBar {
    
    private ActionManager actionManager;
    private JMenuBar menuBar;
    
    public SimulatorMenuBar(ActionManager actionManager) {
        this.actionManager = actionManager;
        this.menuBar = new JMenuBar();
        this.menuBar.add(createFileMenu());
        this.menuBar.add(createEditMenu());
        this.menuBar.add(createViewMenu());
        this.menuBar.add(createSimulationMenu());
    }
    
    public JMenuBar getMenuBar() {
        return menuBar;
    }
    
    private JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');
        fileMenu.add(createItem(NewSimulationAction.ACTION_COMMAND));
        fileMenu.add(createItem(OpenSimulationAction.ACTION_COMMAND));
        fileMenu.addSeparator();
        fileMenu.add(createItem(CloseSimulationAction.ACTION_COMMAND));
        fileMenu.addSeparator();
        fileMenu.add(createItem(SaveSimulationAction.ACTION_COMMAND));
        fileMenu.add(createItem(SaveSimulationAsAction.ACTION_COMMAND));
        fileMenu.addSeparator();
        fileMenu.add(createItem(ExitAction.ACTION_COMMAND));
        return fileMenu;
    }
    
    private JMenu createEditMenu() {
        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic('E');
        return editMenu;
    }
    
    private JMenu createViewMenu() {
        JMenu viewMenu = new JMenu("View");
        viewMenu.setMnemonic('V');
        return viewMenu;
    }
    
    private JMenu createSimulationMenu() {
        JMenu simulationMenu = new JMenu("Simulation");
        simulationMenu.setMnemonic('S');
        simulationMenu.add(createItem(StartSimulationAction.ACTION_COMMAND));
        simulationMenu.add(createItem(PauseSimulationAction.ACTION_COMMAND));
        simulationMenu.addSeparator();
        simulationMenu.add(createItem(StepForwardAction.ACTION_COMMAND));
        simulationMenu.add(createItem(StepBackwardAction.ACTION_COMMAND));
        return simulationMenu;
    }
    
    private JMenuItem createItem(String actionCommand) {
        return new JMenuItem(this.actionManager.getAction(actionCommand));
    }
}
