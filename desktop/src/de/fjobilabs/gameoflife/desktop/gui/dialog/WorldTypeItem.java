package de.fjobilabs.gameoflife.desktop.gui.dialog;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 29.09.2017 - 22:02:09
 */
public class WorldTypeItem {
    
    private String worldType;
    private String label;
    
    public WorldTypeItem(String worldType, String label) {
        this.worldType = worldType;
        this.label = label;
    }
    
    public String getWorldType() {
        return worldType;
    }
    
    public String getLabel() {
        return label;
    }
    
    @Override
    public String toString() {
        return label;
    }
}
