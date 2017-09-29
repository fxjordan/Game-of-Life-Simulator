package de.fjobilabs.gameoflife.desktop.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.Timer;

import de.fjobilabs.gameoflife.desktop.simulator.SimulationRendererPanel;
import de.fjobilabs.gameoflife.desktop.simulator.SimulationState;
import de.fjobilabs.gameoflife.desktop.simulator.Simulator;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 27.09.2017 - 17:52:23
 */
public class RendererInfoPanel extends JPanel {
    
    private static final long serialVersionUID = -3588177332239506450L;
    
    private static final int REFRESH_DELAY = 10;
    private static final int CRITICAL_FPS = 30;
    
    private Simulator simulator;
    private SimulationRendererPanel rendererPanel;
    private JLabel fpsLabel;
    private JLabel fpsValueLabel;
    private JLabel upsLabel;
    private JLabel upsValueLabel;
    private JLabel generationValueLabel;
    private Timer refreshTimer;
    
    public RendererInfoPanel(Simulator simulator, SimulationRendererPanel rendererPanel) {
        this.simulator = simulator;
        this.rendererPanel = rendererPanel;
        initLayout();
        this.refreshTimer = new Timer(REFRESH_DELAY, new RefreshListener());
        this.refreshTimer.start();
    }
    
    public void dispose() {
        this.refreshTimer.stop();
    }
    
    private void initLayout() {
        this.fpsLabel = new JLabel("FPS:");
        this.fpsLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        
        this.fpsValueLabel = new JLabel("0");
        this.fpsValueLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        
        this.upsLabel = new JLabel("UPS:");
        this.upsLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        
        this.upsValueLabel = new JLabel("0");
        this.upsValueLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        
        JLabel generationLabel = new JLabel("Generation:");
        generationLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        
        this.generationValueLabel = new JLabel("0");
        this.generationValueLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(fpsLabel)
                        .addPreferredGap(ComponentPlacement.RELATED).addComponent(fpsValueLabel).addGap(75)
                        .addComponent(upsLabel).addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(upsValueLabel)
                        .addPreferredGap(ComponentPlacement.RELATED, 316, Short.MAX_VALUE)
                        .addComponent(generationLabel).addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(generationValueLabel).addContainerGap()));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
                .createSequentialGroup().addContainerGap()
                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(fpsValueLabel)
                        .addComponent(upsLabel).addComponent(upsValueLabel).addComponent(fpsLabel)
                        .addComponent(generationValueLabel).addComponent(generationLabel))
                .addContainerGap(15, Short.MAX_VALUE)));
        setLayout(groupLayout);
    }
    
    private class RefreshListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            updateFPS();
            updateUPS();
            updateGeneration();
        }
        
        private void updateFPS() {
            int fps = rendererPanel.getFPS();
            fpsValueLabel.setText(Integer.toString(fps));
            if (fps <= CRITICAL_FPS) {
                fpsLabel.setForeground(Color.RED);
                fpsValueLabel.setForeground(Color.RED);
            } else {
                fpsLabel.setForeground(Color.BLACK);
                fpsValueLabel.setForeground(Color.BLACK);
            }
        }
        
        private void updateUPS() {
            int ups = simulator.getMeasuredUPS();
            upsValueLabel.setText(Integer.toString(ups));
            if (simulator.getCurrentSimulationState() == SimulationState.Running
                    && ups < simulator.getUPS() / 2) {
                upsLabel.setForeground(Color.RED);
                upsValueLabel.setForeground(Color.RED);
            } else {
                upsLabel.setForeground(Color.BLACK);
                upsValueLabel.setForeground(Color.BLACK);
            }
        }
        
        private void updateGeneration() {
            int generation = 0;
            if (simulator.hasSimulation()) {
                generation = simulator.getCurrentGeneration();
            }
            generationValueLabel.setText(Integer.toString(generation));
        }
    }
}
