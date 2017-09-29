package de.fjobilabs.gameoflife.desktop.gui.dialog;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.NumberFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fjobilabs.gameoflife.desktop.simulator.SimulationConfiguration;

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
    
    private JSpinner worldWidthSpinner;
    private JSpinner worldHeightSpinner;
    private SimulationConfiguration result;
    
    private JComboBox<WorldTypeComboItem> comboBox;
    
    public NewSimulationDialog() {
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
        config.setWorldType(((WorldTypeComboItem) this.comboBox.getSelectedItem()).getWorldType());
        config.setWorldWidth((int) this.worldWidthSpinner.getValue());
        config.setWorldHeight((int) this.worldHeightSpinner.getValue());
        return config;
    }
    
    private void initLayout() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.WHITE);
        
        JSeparator separator = new JSeparator();
        
        JLabel worldWidthLabel = new JLabel("World width:");
        JLabel worldHeightLabel = new JLabel("World height:");
        
        JSeparator separator1 = new JSeparator();
        
        JButton finishButton = new JButton("Finish");
        finishButton.setActionCommand(FINISH_ACTION);
        finishButton.addActionListener(this);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand(CANCEL_ACTION);
        cancelButton.addActionListener(this);
        
        JLabel worldTypeLabel = new JLabel("World type:");
        this.comboBox = new JComboBox<>(createWorldTypeItems());
        
        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout
                .setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(headerPanel, GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
                        .addComponent(separator, GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
                        .addGroup(groupLayout.createSequentialGroup().addContainerGap(243, Short.MAX_VALUE)
                                .addComponent(finishButton, GroupLayout.PREFERRED_SIZE, 110,
                                        GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(
                                        cancelButton, GroupLayout.PREFERRED_SIZE, 110,
                                        GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                        .addComponent(separator1, GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
                        .addGroup(groupLayout.createSequentialGroup().addContainerGap().addGroup(groupLayout
                                .createParallelGroup(Alignment.TRAILING)
                                .addGroup(Alignment.LEADING,
                                        groupLayout.createSequentialGroup().addComponent(worldTypeLabel)
                                                .addGap(29).addComponent(comboBox, GroupLayout.PREFERRED_SIZE,
                                                        239, GroupLayout.PREFERRED_SIZE))
                                .addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
                                        .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                                .addComponent(worldWidthLabel).addComponent(worldHeightLabel))
                                        .addGap(18)
                                        .addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
                                                .addComponent(worldWidthSpinner)
                                                .addComponent(worldHeightSpinner))))
                                .addContainerGap(135, Short.MAX_VALUE)));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
                .createSequentialGroup()
                .addComponent(headerPanel, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
                .addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.UNRELATED)
                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(worldTypeLabel)
                        .addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(ComponentPlacement.RELATED)
                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(worldWidthLabel)
                        .addComponent(worldWidthSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(ComponentPlacement.RELATED)
                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(worldHeightSpinner, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(worldHeightLabel))
                .addPreferredGap(ComponentPlacement.RELATED, 252, Short.MAX_VALUE)
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
    
    private void disableInvalidSpinnerInput(JSpinner spinner) {
        JFormattedTextField txt = ((JSpinner.NumberEditor) spinner.getEditor()).getTextField();
        ((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);
    }
    
    private WorldTypeComboItem[] createWorldTypeItems() {
        return new WorldTypeComboItem[] {
                new WorldTypeComboItem("fixed-size-torus-world", "FixedSizeTorusWorld"),
                new WorldTypeComboItem("fixed-size-bordered-world", "FixedSizeBorderedWorld")};
    }
}
