package de.fjobilabs.gameoflife.desktop.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.Timer;

import de.fjobilabs.gameoflife.SimulatorApplication;
import de.fjobilabs.gameoflife.desktop.simulator.SimulationRendererPanel;
import de.fjobilabs.gameoflife.desktop.simulator.SimulationState;
import de.fjobilabs.gameoflife.desktop.simulator.Simulator;
import javax.swing.JComboBox;

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
    private JComboBox<CellRendererItem> cellRendererComboBox;
    
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
        
        this.cellRendererComboBox = new JComboBox<>();
        this.cellRendererComboBox.setModel(new DefaultComboBoxModel<>(createCellRendererItems()));
        this.cellRendererComboBox.addItemListener(new ItemListener() {
            
            @Override
            public void itemStateChanged(ItemEvent e) {
                rendererPanel.setCellRenderer(getSelectedRendererType());
            }
        });
        
        JLabel cellRendererLabel = new JLabel("Cell Renderer:");
        cellRendererLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(fpsLabel)
                        .addPreferredGap(ComponentPlacement.RELATED).addComponent(fpsValueLabel).addGap(43)
                        .addComponent(upsLabel).addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(upsValueLabel).addGap(32).addComponent(generationLabel)
                        .addPreferredGap(ComponentPlacement.RELATED).addComponent(generationValueLabel)
                        .addPreferredGap(ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                        .addComponent(cellRendererLabel).addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(cellRendererComboBox, GroupLayout.PREFERRED_SIZE, 204,
                                GroupLayout.PREFERRED_SIZE)
                        .addContainerGap()));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
                .createSequentialGroup().addContainerGap()
                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(fpsValueLabel)
                        .addComponent(upsLabel).addComponent(upsValueLabel).addComponent(generationValueLabel)
                        .addComponent(generationLabel)
                        .addComponent(cellRendererComboBox, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(cellRendererLabel).addComponent(fpsLabel))
                .addContainerGap(26, Short.MAX_VALUE)));
        setLayout(groupLayout);
    }
    
    private CellRendererItem[] createCellRendererItems() {
        return new CellRendererItem[] {
                new CellRendererItem(SimulatorApplication.TEXTURE_CELL_RENDERER, "TextureCellRenderer"),
                new CellRendererItem(SimulatorApplication.SHAPE_CELL_RENDERER, "ShapeCellRenderer"),
                new CellRendererItem(SimulatorApplication.HIGH_PERFORMANCE_CELL_RENDERER,
                        "HighPerformanceCellRenderer")};
    }
    
    private int getSelectedRendererType() {
        return ((CellRendererItem) this.cellRendererComboBox.getSelectedItem()).getRendererId();
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
