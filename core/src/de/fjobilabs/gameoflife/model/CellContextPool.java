package de.fjobilabs.gameoflife.model;

import com.badlogic.gdx.utils.Pool;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 18.09.2017 - 21:41:44
 */
public class CellContextPool extends Pool<CellContext> {
    
    @Override
    protected CellContext newObject() {
        return new CellContext();
    }
}
