package de.fjobilabs.gameoflife.desktop.gui.dialog;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 26.09.2017 - 21:30:13
 */
public class SaveConfirmationDialog extends JDialog implements ActionListener {
    
    private static final long serialVersionUID = 4677488853349000596L;
    
    private static final Logger logger = LoggerFactory.getLogger(SaveConfirmationDialog.class);
    
    public static final String SAVE_ACTION = "save";
    public static final String DONT_SAVE_ACTION = "dont_save";
    public static final String CANCEL_ACTION = "cancel";
    
    public static final int SAVE = 1;
    public static final int DONT_SAVE = 0;
    public static final int CANCEL = -1;
    public static final int NO_RESULT = -2;
    
    private int result;
    
    public SaveConfirmationDialog(String filename) {
        this.result = NO_RESULT;
        
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setTitle("Save Simulation");
        setSize(500, 150);
        setModal(true);
        setResizable(false);
        
        initLayout(filename);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                result = CANCEL;
                dispose();
            }
        });
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        switch (actionCommand) {
        case CANCEL_ACTION:
            this.result = CANCEL;
            dispose();
            break;
        case SAVE_ACTION:
            this.result = SAVE;
            dispose();
            break;
        case DONT_SAVE_ACTION:
            this.result = DONT_SAVE;
            dispose();
            break;
        default:
            logger.warn("Unknown action command: " + actionCommand);
        }
    }
    
    /**
     * Returns the result of the dialog. This method blocks until the user made
     * a decision.
     * 
     * @return The dialog result.
     */
    public int getResult() {
        return this.result;
    }
    
    private void initLayout(String filename) {
        JLabel iconLabel = new JLabel(UIManager.getIcon("OptionPane.questionIcon"));
        
        JLabel messageLabel = new JLabel("'" + filename + "' has been modified. Save changes?");
        messageLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand(CANCEL_ACTION);
        cancelButton.addActionListener(this);
        
        JButton dontSaveButton = new JButton("Don't Save");
        dontSaveButton.setActionCommand(DONT_SAVE_ACTION);
        dontSaveButton.addActionListener(this);
        
        JButton saveButton = new JButton("Save");
        saveButton.setActionCommand(SAVE_ACTION);
        saveButton.addActionListener(this);
        
        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                .addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(iconLabel)
                        .addGap(18).addComponent(messageLabel).addContainerGap(202, Short.MAX_VALUE))
                .addGroup(groupLayout.createSequentialGroup().addContainerGap(278, Short.MAX_VALUE)
                        .addComponent(saveButton, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(dontSaveButton, GroupLayout.PREFERRED_SIZE, 110,
                                GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(ComponentPlacement.RELATED).addComponent(cancelButton,
                                GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap()));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup().addContainerGap()
                        .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                .addComponent(messageLabel).addComponent(iconLabel))
                        .addPreferredGap(ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                        .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                .addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 30,
                                        GroupLayout.PREFERRED_SIZE)
                                .addComponent(dontSaveButton, GroupLayout.PREFERRED_SIZE, 30,
                                        GroupLayout.PREFERRED_SIZE)
                                .addComponent(saveButton, GroupLayout.PREFERRED_SIZE, 30,
                                        GroupLayout.PREFERRED_SIZE))
                        .addContainerGap()));
        getContentPane().setLayout(groupLayout);
    }
}
