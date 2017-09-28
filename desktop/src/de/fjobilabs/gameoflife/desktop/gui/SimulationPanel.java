package de.fjobilabs.gameoflife.desktop.gui;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;

import de.fjobilabs.gameoflife.desktop.simulator.SimulationRendererPanel;
import de.fjobilabs.gameoflife.desktop.simulator.Simulator;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 27.09.2017 - 17:45:43
 */
public class SimulationPanel extends JPanel {
    
    private static final long serialVersionUID = 5203546416914082578L;
    
    private SimulationRendererPanel rendererPanel;
    private RendererInfoPanel rendererInfoPanel;
    
    public SimulationPanel(Simulator simulator) {
        this.rendererPanel = new SimulationRendererPanel(simulator);
        this.rendererInfoPanel = new RendererInfoPanel(simulator, this.rendererPanel);
        setBorder(BorderFactory.createTitledBorder("Simulation Renderer"));
        
        initLayout();
    }
    
    private void initLayout() {
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
            groupLayout.createParallelGroup(Alignment.TRAILING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                        .addComponent(rendererInfoPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
                        .addComponent(rendererPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap())
        );
        groupLayout.setVerticalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(rendererInfoPanel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                    .addGap(18)
                    .addComponent(rendererPanel, GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                    .addContainerGap())
        );
        setLayout(groupLayout);
    }
    
    public void dispose() {
        this.rendererPanel.dispose();
        this.rendererInfoPanel.dispose();
    }
}
