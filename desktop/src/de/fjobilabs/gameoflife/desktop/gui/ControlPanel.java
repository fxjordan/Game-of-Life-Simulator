package de.fjobilabs.gameoflife.desktop.gui;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import de.fjobilabs.gameoflife.desktop.gui.actions.ActionManager;
import de.fjobilabs.gameoflife.desktop.gui.actions.control.ChangeUPSAction;
import de.fjobilabs.gameoflife.desktop.gui.actions.control.PauseSimulationAction;
import de.fjobilabs.gameoflife.desktop.gui.actions.control.StartSimulationAction;
import de.fjobilabs.gameoflife.desktop.gui.actions.control.StepBackwardAction;
import de.fjobilabs.gameoflife.desktop.gui.actions.control.StepForwardAction;
import de.fjobilabs.gameoflife.desktop.simulator.Simulator;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 27.09.2017 - 15:31:18
 */
public class ControlPanel extends JPanel {
    
    private static final long serialVersionUID = 7878995773349421368L;
    
    private static final int ICON_WIDTH = 60;
    private static final int ICON_HEIGHT = 60;
    
    private ActionManager actionManager;
    
    public ControlPanel(Simulator simulator, ActionManager actionManager) {
        this.actionManager = actionManager;
        setBorder(BorderFactory.createTitledBorder("Control Panel"));
        
        initLayout(simulator);
    }
    
    private void initLayout(Simulator simulator) {
        /*
         * TODO Make image paths independent from environment, the paths should
         * work in eclipse and if the application is executed as a standalone
         * JAR.
         */
        JButton startButton = createActionButton(StartSimulationAction.ACTION_COMMAND, "images/start.png");
        JButton pauseButton = createActionButton(PauseSimulationAction.ACTION_COMMAND, "images/pause.png");
        JButton stepForwardButton = createActionButton(StepForwardAction.ACTION_COMMAND,
                "images/step-forward.png");
        JButton stepBackwardButton = createActionButton(StepBackwardAction.ACTION_COMMAND,
                "images/step-backward.png");
        
        // TODO Make separator visible
        JSeparator separator = new JSeparator();
        separator.setOrientation(SwingConstants.VERTICAL);
        
        UPSController upsController = new UPSController(
                this.actionManager.getAction(ChangeUPSAction.ACTION_COMMAND));
        
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
                .createSequentialGroup().addGap(5)
                .addComponent(startButton, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(pauseButton, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(stepBackwardButton, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(stepForwardButton, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.UNRELATED)
                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                        .addComponent(upsController, GroupLayout.DEFAULT_SIZE, 370, 370))
                .addGap(48)));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
                .createSequentialGroup().addGap(7)
                .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
                        .addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
                                .addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED).addComponent(upsController,
                                        GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
                        .addComponent(pauseButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 70,
                                Short.MAX_VALUE)
                        .addComponent(startButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 70,
                                Short.MAX_VALUE)
                        .addComponent(stepBackwardButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 70,
                                Short.MAX_VALUE)
                        .addComponent(stepForwardButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 70,
                                Short.MAX_VALUE))
                .addContainerGap(223, Short.MAX_VALUE)));
        setLayout(groupLayout);
    }
    
    private JButton createActionButton(String actionCommand, String iconImagePath) {
        JButton button = new JButton(this.actionManager.getAction(actionCommand));
        // We need to override the properties, set by the action
        button.setText(null);
        Image image = Toolkit.getDefaultToolkit().getImage(getResource(iconImagePath));
        image = image.getScaledInstance(ICON_WIDTH, ICON_HEIGHT, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(image));
        return button;
    }
    
    private URL getResource(String name) {
        return ControlPanel.class.getClassLoader().getResource(name);
    }
}
