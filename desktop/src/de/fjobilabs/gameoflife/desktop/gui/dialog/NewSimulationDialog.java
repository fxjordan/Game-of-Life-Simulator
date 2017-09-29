package de.fjobilabs.gameoflife.desktop.gui.dialog;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.NumberFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fjobilabs.gameoflife.desktop.simulator.SimulationConfiguration;
import de.fjobilabs.gameoflife.model.simulation.ca.rules.GameOfLifeRuleSet;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 26.09.2017 - 18:15:50
 */
public class NewSimulationDialog extends JDialog implements ActionListener {
    
    private static final long serialVersionUID = 6662359509185742522L;
    
    private static final Logger logger = LoggerFactory.getLogger(NewSimulationDialog.class);
    
    public static final String CANCEL_ACTION = "cancel";
    public static final String FINISH_ACTION = "finish";
    
    private InputActionListener inputActionListener;
    private JSpinner worldWidthSpinner;
    private JSpinner worldHeightSpinner;
    private SimulationConfiguration result;
    
    private JComboBox<WorldTypeItem> worldTypeComboBox;
    private JTextField ruleStringTextField;
    
    private JCheckBox advancedSettingsCheckbox;
    
    private JLabel simulationTypeLabel;
    private JLabel ruleSetLabel;
    private JComboBox<RuleSetItem> ruleSetComboBox;
    private JLabel ruleStringLabel;
    private JComboBox<SimulationTypeItem> simulationTypeComboBox;
    private JLabel ruleStringFeedbackLabel;

    private JButton finishButton;
    
    public NewSimulationDialog() {
        this.inputActionListener = new InputActionListener();
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("New Simulation");
        setSize(500, 520);
        setModal(true);
        
        SpinnerModel worldWidthSpinnerModel = new SpinnerNumberModel(
                SimulationConfiguration.DEFAULT_WORLD_WIDTH, 1, Integer.MAX_VALUE, 1);
        this.worldWidthSpinner = new JSpinner(worldWidthSpinnerModel);
        disableInvalidSpinnerInput(this.worldWidthSpinner);
        
        SpinnerModel worldHeightSpinnerModel = new SpinnerNumberModel(
                SimulationConfiguration.DEFAULT_WORLD_HEIGHT, 1, Integer.MAX_VALUE, 1);
        this.worldHeightSpinner = new JSpinner(worldHeightSpinnerModel);
        disableInvalidSpinnerInput(this.worldHeightSpinner);
        
        initLayout();
        updateComponentStates();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        switch (actionCommand) {
        case CANCEL_ACTION:
            dispose();
            break;
        case FINISH_ACTION:
            this.result = createSimulationConfiguration();
            dispose();
            break;
        default:
            logger.warn("Unknown action command: " + actionCommand);
        }
    }
    
    public SimulationConfiguration getResult() {
        return result;
    }
    
    private SimulationConfiguration createSimulationConfiguration() {
        SimulationConfiguration config = new SimulationConfiguration();
        config.setWorldType(getWorldType());
        config.setWorldWidth((int) this.worldWidthSpinner.getValue());
        config.setWorldHeight((int) this.worldHeightSpinner.getValue());
        config.setSimulationType(getSimulationType());
        config.setRuleSet(getRuleSet());
        config.setRuleString(this.ruleStringTextField.getText());
        return config;
    }
    
    private void initLayout() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.WHITE);
        
        JSeparator separator = new JSeparator();
        
        JLabel worldWidthLabel = new JLabel("World width:");
        JLabel worldHeightLabel = new JLabel("World height:");
        
        JSeparator separator1 = new JSeparator();
        
        this.finishButton = new JButton("Finish");
        this.finishButton.setActionCommand(FINISH_ACTION);
        this.finishButton.addActionListener(this);
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand(CANCEL_ACTION);
        cancelButton.addActionListener(this);
        
        JLabel worldTypeLabel = new JLabel("World type:");
        this.worldTypeComboBox = new JComboBox<>();
        this.worldTypeComboBox.setModel(new DefaultComboBoxModel<>(createWorldTypeItems()));
        
        JSeparator separator2 = new JSeparator();
        
        this.advancedSettingsCheckbox = new JCheckBox("Advanced settings");
        this.advancedSettingsCheckbox.addActionListener(this.inputActionListener);
        
        this.simulationTypeLabel = new JLabel("Simulation type:");
        this.simulationTypeLabel.setEnabled(false);
        
        this.simulationTypeComboBox = new JComboBox<>();
        this.simulationTypeComboBox.setModel(new DefaultComboBoxModel<>(createSimulationTypeItems()));
        this.simulationTypeComboBox.addActionListener(this.inputActionListener);
        
        this.ruleSetLabel = new JLabel("Rule set:");
        
        this.ruleSetComboBox = new JComboBox<>();
        this.ruleSetComboBox.setModel(new DefaultComboBoxModel<>(createRuleSetItems()));
        this.ruleSetComboBox.addActionListener(this.inputActionListener);
        
        this.ruleStringLabel = new JLabel("Life-like rule:");
        
        this.ruleStringTextField = new JTextField();
        this.ruleStringTextField.getDocument().addDocumentListener(new RuleStringDocumentListener());
        
        this.ruleStringFeedbackLabel = new JLabel("Invalid rule string");
        this.ruleStringFeedbackLabel.setForeground(Color.RED);
        
        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout
                .setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(headerPanel, GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
                        .addComponent(separator, GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
                        .addGroup(groupLayout.createSequentialGroup().addContainerGap(243, Short.MAX_VALUE)
                                .addComponent(finishButton, GroupLayout.PREFERRED_SIZE, 110,
                                        GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 110,
                                        GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                        .addComponent(separator1, GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
                        .addGroup(groupLayout.createSequentialGroup().addContainerGap()
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(worldWidthLabel).addComponent(worldHeightLabel)
                                        .addComponent(worldTypeLabel))
                                .addGap(36)
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(worldTypeComboBox, GroupLayout.PREFERRED_SIZE, 239,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                                                .addComponent(worldHeightSpinner, GroupLayout.PREFERRED_SIZE,
                                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(worldWidthSpinner, GroupLayout.PREFERRED_SIZE,
                                                        GroupLayout.DEFAULT_SIZE,
                                                        GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(117, Short.MAX_VALUE))
                        .addGroup(groupLayout.createSequentialGroup().addContainerGap()
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(advancedSettingsCheckbox).addComponent(separator2,
                                                GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE))
                                .addContainerGap())
                        .addGroup(groupLayout.createSequentialGroup().addContainerGap()
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(simulationTypeLabel).addComponent(ruleSetLabel)
                                        .addComponent(ruleStringLabel))
                                .addGap(22)
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(ruleStringFeedbackLabel)
                                        .addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
                                                .addComponent(simulationTypeComboBox, 0, 233, Short.MAX_VALUE)
                                                .addComponent(ruleSetComboBox, 0, GroupLayout.DEFAULT_SIZE,
                                                        Short.MAX_VALUE)
                                                .addComponent(ruleStringTextField, Alignment.TRAILING)))
                                .addContainerGap()));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
                .createSequentialGroup()
                .addComponent(headerPanel, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
                .addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.UNRELATED)
                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(worldTypeLabel)
                        .addComponent(worldTypeComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(ComponentPlacement.RELATED)
                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(worldWidthLabel)
                        .addComponent(worldWidthSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(ComponentPlacement.RELATED)
                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(worldHeightLabel)
                        .addComponent(worldHeightSpinner, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18)
                .addComponent(separator2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.UNRELATED).addComponent(advancedSettingsCheckbox)
                .addGap(18)
                .addGroup(
                        groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(simulationTypeLabel)
                                .addComponent(simulationTypeComboBox, GroupLayout.PREFERRED_SIZE,
                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(ComponentPlacement.RELATED)
                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(ruleSetLabel)
                        .addComponent(ruleSetComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(ComponentPlacement.RELATED)
                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(ruleStringTextField, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(ruleStringLabel))
                .addPreferredGap(ComponentPlacement.RELATED).addComponent(ruleStringFeedbackLabel)
                .addPreferredGap(ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addComponent(separator1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.UNRELATED)
                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(cancelButton)
                        .addComponent(finishButton))
                .addContainerGap()));
        
        JLabel titleLabel = new JLabel("Simulation");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        
        JLabel subtitleLabel = new JLabel("Create a new Simulation");
        subtitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        GroupLayout gl_headerPanel = new GroupLayout(headerPanel);
        gl_headerPanel.setHorizontalGroup(gl_headerPanel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_headerPanel.createSequentialGroup().addContainerGap()
                        .addGroup(gl_headerPanel.createParallelGroup(Alignment.LEADING)
                                .addGroup(gl_headerPanel.createSequentialGroup().addGap(10)
                                        .addComponent(subtitleLabel))
                                .addComponent(titleLabel))
                        .addContainerGap(264, Short.MAX_VALUE)));
        gl_headerPanel.setVerticalGroup(gl_headerPanel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_headerPanel.createSequentialGroup().addContainerGap().addComponent(titleLabel)
                        .addPreferredGap(ComponentPlacement.RELATED).addComponent(subtitleLabel)
                        .addContainerGap(16, Short.MAX_VALUE)));
        headerPanel.setLayout(gl_headerPanel);
        getContentPane().setLayout(groupLayout);
    }
    
    private void updateComponentStates() {
        if (!this.advancedSettingsCheckbox.isSelected()) {
            setSimulationTypeInputEnabled(false);
            setRuleSetInputEnabled(false);
            setRuleStringInputEnabled(false);
            this.simulationTypeComboBox.setSelectedIndex(0);
            this.ruleSetComboBox.setSelectedIndex(0);
            this.ruleStringTextField.setText(SimulationConfiguration.DEFAULT_RULE_STRING);
            return;
        }
        setSimulationTypeInputEnabled(true);
        if (SimulationConfiguration.CELLULAR_AUTOMATON_SIMULATION.equals(getSimulationType())) {
            setRuleSetInputEnabled(true);
            if (SimulationConfiguration.LIFE_LIKE_RULE_SET.equals(getRuleSet())) {
                setRuleStringInputEnabled(true);
            } else {
                this.ruleStringTextField.setText(SimulationConfiguration.DEFAULT_RULE_STRING);
                setRuleStringInputEnabled(false);
            }
        } else {
            setRuleSetInputEnabled(false);
            setRuleStringInputEnabled(false);
        }
    }
    
    private void setSimulationTypeInputEnabled(boolean enabled) {
        this.simulationTypeLabel.setEnabled(enabled);
        this.simulationTypeComboBox.setEnabled(enabled);
    }
    
    private void setRuleSetInputEnabled(boolean enabled) {
        this.ruleSetLabel.setEnabled(enabled);
        this.ruleSetComboBox.setEnabled(enabled);
    }
    
    private void setRuleStringInputEnabled(boolean enabled) {
        this.ruleStringLabel.setEnabled(enabled);
        this.ruleStringTextField.setEnabled(enabled);
    }
    
    private String getWorldType() {
        return ((WorldTypeItem) this.worldTypeComboBox.getSelectedItem()).getWorldType();
    }
    
    private String getSimulationType() {
        return ((SimulationTypeItem) this.simulationTypeComboBox.getSelectedItem()).getSimulationType();
    }
    
    private String getRuleSet() {
        return ((RuleSetItem) this.ruleSetComboBox.getSelectedItem()).getRuleSet();
    }
    
    private void updateRuleStringFeedback() {
        boolean valid = isRuleStringValid(this.ruleStringTextField.getText());
        this.ruleStringFeedbackLabel.setVisible(!valid);
        this.finishButton.setEnabled(valid);
    }
    
    public boolean isRuleStringValid(String ruleString) {
        // TODO BAD CODE! Implement isValid(String) method for parser.
        try {
            GameOfLifeRuleSet.parse(ruleString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    private void disableInvalidSpinnerInput(JSpinner spinner) {
        JFormattedTextField txt = ((JSpinner.NumberEditor) spinner.getEditor()).getTextField();
        ((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);
    }
    
    private WorldTypeItem[] createWorldTypeItems() {
        return new WorldTypeItem[] {
                new WorldTypeItem(SimulationConfiguration.TORUS_WORLD, "FixedSizeTorusWorld"),
                new WorldTypeItem(SimulationConfiguration.BORDERED_WORLD, "FixedSizeBorderedWorld")};
    }
    
    private SimulationTypeItem[] createSimulationTypeItems() {
        return new SimulationTypeItem[] {
                new SimulationTypeItem(SimulationConfiguration.CELLULAR_AUTOMATON_SIMULATION,
                        "Cellular Automaton"),
                new SimulationTypeItem(SimulationConfiguration.LANGTONS_ANT_SIMULATION,
                        "Langtion's Ant Simulation")};
    }
    
    private RuleSetItem[] createRuleSetItems() {
        return new RuleSetItem[] {
                new RuleSetItem(SimulationConfiguration.STANDARD_GAME_OF_LIFE_RULE_SET,
                        "Standard Game of Life rule set"),
                new RuleSetItem(SimulationConfiguration.LIFE_LIKE_RULE_SET, "Life-like rule set")};
    }
    
    private class InputActionListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            updateComponentStates();
        }
    }
    
    public class RuleStringDocumentListener implements DocumentListener {
        
        @Override
        public void insertUpdate(DocumentEvent e) {
            updateRuleStringFeedback();
        }
        
        @Override
        public void removeUpdate(DocumentEvent e) {
            updateRuleStringFeedback();
        }
        
        @Override
        public void changedUpdate(DocumentEvent e) {
            updateRuleStringFeedback();
        }
    }
}
