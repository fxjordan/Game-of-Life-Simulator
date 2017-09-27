package de.fjobilabs.gameoflife.desktop.gui;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

import de.fjobilabs.gameoflife.desktop.gui.actions.ActionManager;
import de.fjobilabs.gameoflife.desktop.gui.actions.control.PauseSimulationAction;
import de.fjobilabs.gameoflife.desktop.gui.actions.control.StartSimulationAction;
import de.fjobilabs.gameoflife.desktop.gui.actions.control.StepBackwardAction;
import de.fjobilabs.gameoflife.desktop.gui.actions.control.StepForwardAction;

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
    
    public ControlPanel(ActionManager actionManager) {
        this.actionManager = actionManager;
        setBorder(BorderFactory.createTitledBorder("Control Panel"));
        
        initLayout();
    }
    
    private void initLayout() {
        JButton startButton = createActionButton(StartSimulationAction.ACTION_COMMAND, "images/start.png");
        JButton pauseButton = createActionButton(PauseSimulationAction.ACTION_COMMAND, "images/pause.png");
        JButton stepForwardButton = createActionButton(StepForwardAction.ACTION_COMMAND,
                "images/step-forward.png");
        JButton stepBackwardButton = createActionButton(StepBackwardAction.ACTION_COMMAND,
                "images/step-backward.png");
        
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
                .addContainerGap(100, Short.MAX_VALUE)));
        groupLayout.setVerticalGroup(groupLayout
                .createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addGap(7)
                        .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
                                .addGroup(Alignment.LEADING,
                                        groupLayout.createParallelGroup(Alignment.BASELINE, false)
                                                .addComponent(pauseButton, GroupLayout.PREFERRED_SIZE, 70,
                                                        GroupLayout.PREFERRED_SIZE)
                                                .addComponent(startButton, GroupLayout.PREFERRED_SIZE, 70,
                                                        GroupLayout.PREFERRED_SIZE))
                                .addComponent(stepBackwardButton, Alignment.LEADING,
                                        GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                                .addComponent(stepForwardButton, Alignment.LEADING,
                                        GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(225, Short.MAX_VALUE)));
        setLayout(groupLayout);
    }
    
    private JButton createActionButton(String actionCommand, String iconImagePath) {
        JButton button = new JButton(this.actionManager.getAction(actionCommand));
        // We need to override the properties, set by the action
        button.setText(null);
        Image image = Toolkit.getDefaultToolkit().getImage(iconImagePath);
        image = image.getScaledInstance(ICON_WIDTH, ICON_HEIGHT, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(image));
        return button;
    }
}
