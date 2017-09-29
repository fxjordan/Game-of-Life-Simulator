package de.fjobilabs.gameoflife.desktop.gui;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 30.09.2017 - 01:06:52
 */
public class CellRendererItem {
    
    private int rendererId;
    private String label;
    
    public CellRendererItem(int rendererId, String label) {
        this.rendererId = rendererId;
        this.label = label;
    }
    
    public int getRendererId() {
        return rendererId;
    }
    
    public String getLabel() {
        return label;
    }
    
    @Override
    public String toString() {
        return label;
    }
}
