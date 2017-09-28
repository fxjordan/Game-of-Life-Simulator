package de.fjobilabs.gameoflife.desktop;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

import de.fjobilabs.gameoflife.desktop.gui.ControlPanel;
import de.fjobilabs.gameoflife.desktop.gui.SimulationPanel;
import de.fjobilabs.gameoflife.desktop.gui.SimulatorMenuBar;
import de.fjobilabs.gameoflife.desktop.gui.actions.ActionManager;
import de.fjobilabs.gameoflife.desktop.gui.actions.control.ChangeUPSAction;
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
import de.fjobilabs.gameoflife.desktop.simulator.Simulator;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 24.09.2017 - 13:05:09
 */
public class SimulatorFrame extends JFrame {
    
    private static final long serialVersionUID = -2112042941614744619L;
    
    private Simulator simulator;
    private ActionManager actionManager;
    private SimulationPanel simulationRendererPanel;
    
    public SimulatorFrame() {
        setTitle("Game of Life Simulator");
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(800, 500));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        this.simulator = new Simulator();
        
        this.actionManager = new ActionManager();
        registerActions();
        
        initLayout();
        setJMenuBar(new SimulatorMenuBar(this.actionManager).getMenuBar());
    }
    
    private void registerActions() {
        // File actions
        this.actionManager.registerAction(new NewSimulationAction(this.actionManager, this, this.simulator));
        this.actionManager.registerAction(new OpenSimulationAction(this.actionManager, this, this.simulator));
        this.actionManager
                .registerAction(new CloseSimulationAction(this.actionManager, this, this.simulator));
        this.actionManager.registerAction(new SaveSimulationAction(this.actionManager, this, this.simulator));
        this.actionManager
                .registerAction(new SaveSimulationAsAction(this.actionManager, this, this.simulator));
        this.actionManager.registerAction(new ExitAction(this.actionManager, this, this.simulator));
        
        // Control actions
        this.actionManager.registerAction(new StartSimulationAction(this.actionManager, this.simulator));
        this.actionManager.registerAction(new PauseSimulationAction(this.actionManager, this.simulator));
        this.actionManager.registerAction(new StepForwardAction(this.actionManager, this.simulator));
        this.actionManager.registerAction(new StepBackwardAction(this.actionManager, this.simulator));
        this.actionManager.registerAction(new ChangeUPSAction(this.actionManager, this.simulator));
    }
    
    private void initLayout() {
        JPanel worldEditorPanel = new JPanel();
        worldEditorPanel.setBorder(BorderFactory.createTitledBorder("World Editor"));
        
        this.simulationRendererPanel = new SimulationPanel(this.simulator);
        
        JPanel controlPanel = new ControlPanel(this.simulator, this.actionManager);
        controlPanel.setBorder(BorderFactory.createTitledBorder("Simulation Control"));
        
        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                .addGroup(groupLayout.createSequentialGroup().addContainerGap()
                        .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                .addComponent(controlPanel, GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE)
                                .addGroup(groupLayout.createSequentialGroup()
                                        .addComponent(worldEditorPanel, GroupLayout.PREFERRED_SIZE, 100, 300)
                                        .addPreferredGap(ComponentPlacement.RELATED)
                                        .addComponent(this.simulationRendererPanel, GroupLayout.DEFAULT_SIZE,
                                                492, Short.MAX_VALUE)))
                        .addContainerGap()));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                .addGroup(groupLayout.createSequentialGroup().addContainerGap()
                        .addComponent(controlPanel, 50, 75, 110).addPreferredGap(ComponentPlacement.RELATED)
                        .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                                .addComponent(worldEditorPanel, GroupLayout.DEFAULT_SIZE, 214,
                                        Short.MAX_VALUE)
                                .addComponent(this.simulationRendererPanel, GroupLayout.DEFAULT_SIZE, 214,
                                        Short.MAX_VALUE))
                        .addContainerGap()));
        getContentPane().setLayout(groupLayout);
    }
    
    @Override
    public void dispose() {
        this.simulationRendererPanel.dispose();
        super.dispose();
    }
}
