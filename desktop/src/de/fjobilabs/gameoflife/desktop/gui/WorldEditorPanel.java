package de.fjobilabs.gameoflife.desktop.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle.ComponentPlacement;

import de.fjobilabs.gameoflife.desktop.gui.actions.ActionManager;
import de.fjobilabs.gameoflife.desktop.gui.actions.worldedit.AddPatternAction;
import de.fjobilabs.gameoflife.desktop.gui.actions.worldedit.CancelAddPatternAction;
import de.fjobilabs.gameoflife.desktop.gui.actions.worldedit.ClearWorldAction;
import de.fjobilabs.gameoflife.desktop.gui.actions.worldedit.LoadPatternAction;
import de.fjobilabs.gameoflife.desktop.simulator.LoadedPattern;
import de.fjobilabs.gameoflife.desktop.simulator.WorldEditor;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 30.09.2017 - 01:30:50
 */
public class WorldEditorPanel extends JPanel {
    
    private static final long serialVersionUID = -5497946512930538989L;
    
    private ActionManager actionManager;
    private WorldEditor worldEditor;
    private JComboBox<LoadedPattern> loadedPatternsComboBox;
    
    public WorldEditorPanel(ActionManager actionManager, WorldEditor worldEditor) {
        this.actionManager = actionManager;
        this.worldEditor = worldEditor;
        
        initLayout();
    }
    
    public void refreshPatterns() {
        this.loadedPatternsComboBox.setModel(createLoadedPatternsModel());
    }
    
    private void initLayout() {
        JButton clearButton = new JButton(this.actionManager.getAction(ClearWorldAction.ACTION_COMMAND));
        
        JLabel loadedPatternsLabel = new JLabel("Loaded patterns:");
        
        this.loadedPatternsComboBox = new JComboBox<>();
        this.loadedPatternsComboBox.setModel(createLoadedPatternsModel());
        this.loadedPatternsComboBox.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                worldEditor.setCurrentPattern(getSelectedPatternId());
            }
        });
        this.worldEditor.setCurrentPattern(getSelectedPatternId());
        
        JButton loadPatternButton = new JButton(
                this.actionManager.getAction(LoadPatternAction.ACTION_COMMAND));
        JButton addPatternButton = new JButton(this.actionManager.getAction(AddPatternAction.ACTION_COMMAND));
        JButton cancelPatternButton = new JButton(
                this.actionManager.getAction(CancelAddPatternAction.ACTION_COMMAND));
        
        JSeparator separator = new JSeparator();
        
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                .addGroup(groupLayout.createSequentialGroup().addContainerGap().addGroup(groupLayout
                        .createParallelGroup(Alignment.LEADING)
                        .addComponent(separator, GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
                        .addComponent(clearButton, GroupLayout.PREFERRED_SIZE, 110,
                                GroupLayout.PREFERRED_SIZE)
                        .addGroup(groupLayout.createSequentialGroup().addComponent(loadedPatternsLabel)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(loadedPatternsComboBox, 0, 220, Short.MAX_VALUE))
                        .addGroup(groupLayout.createSequentialGroup()
                                .addComponent(loadPatternButton, GroupLayout.PREFERRED_SIZE, 110,
                                        GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                                .addComponent(addPatternButton, GroupLayout.PREFERRED_SIZE, 110,
                                        GroupLayout.PREFERRED_SIZE))
                        .addComponent(cancelPatternButton, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE,
                                110, GroupLayout.PREFERRED_SIZE))
                        .addContainerGap()));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                .addGroup(groupLayout.createSequentialGroup().addContainerGap()
                        .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                .addComponent(loadedPatternsLabel)
                                .addComponent(loadedPatternsComboBox, GroupLayout.PREFERRED_SIZE,
                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(ComponentPlacement.UNRELATED)
                        .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                .addComponent(loadPatternButton).addComponent(addPatternButton))
                        .addGap(2).addComponent(cancelPatternButton).addGap(18)
                        .addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(ComponentPlacement.RELATED, 330, Short.MAX_VALUE)
                        .addComponent(clearButton, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap()));
        setLayout(groupLayout);
    }
    
    private String getSelectedPatternId() {
        LoadedPattern loadedPattern = (LoadedPattern) this.loadedPatternsComboBox.getSelectedItem();
        if (loadedPattern == null) {
            return null;
        }
        return loadedPattern.getPatternId();
    }
    
    private ComboBoxModel<LoadedPattern> createLoadedPatternsModel() {
        return new DefaultComboBoxModel<>(this.worldEditor.getPatternManager().getLoadedPatterns());
    }
}
