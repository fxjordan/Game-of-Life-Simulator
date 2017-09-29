package de.fjobilabs.gameoflife.desktop.gui.dialog;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 29.09.2017 - 22:32:25
 */
public class SimulationTypeItem {
    
    private String simulationType;
    private String label;
    
    public SimulationTypeItem(String simulationType, String label) {
        this.simulationType = simulationType;
        this.label = label;
    }
    
    public String getSimulationType() {
        return simulationType;
    }
    
    public String getLabel() {
        return label;
    }
    
    @Override
    public String toString() {
        return label;
    }
}
