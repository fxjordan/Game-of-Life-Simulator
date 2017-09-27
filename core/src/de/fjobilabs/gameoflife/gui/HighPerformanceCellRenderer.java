package de.fjobilabs.gameoflife.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.VertexBufferObject;
import com.badlogic.gdx.math.Matrix4;

import de.fjobilabs.libgdx.graphics.ExtendedShaderProgram;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 23.09.2017 - 14:38:11
 */
public class HighPerformanceCellRenderer implements CellRenderer {
    
    private static final String VERTEX_SHADER_PATH = "shaders/high-performance-cell-renderer/vertexShader.glsl";
    private static final String FRAGMENT_SHADER_PATH = "shaders/high-performance-cell-renderer/fragmentShader.glsl";
    private static final String GEOMETRY_SHADER_PATH = "shaders/high-performance-cell-renderer/geometryShader.glsl";
    
    private static final Color DEAD_CELL_COLOR = new Color(117f / 255f, 111f / 255f, 85f / 255f, 1f);
    private static final Color ALIVE_CELL_COLOR = new Color(255f / 255f, 216f / 255f, 0f / 255f, 1f);
    
    private static final VertexAttribute CELL_POSITION_VERTEX_ATTRIBUTE = new VertexAttribute(Usage.Position,
            2, "a_position");
    private static final VertexAttribute CELL_STATE_VERTEX_ATTRIBUTE = new VertexAttribute(Usage.Generic, 1,
            GL20.GL_FLOAT, true, "a_cellState");
    
    private int worldWidth;
    private int worldHeight;
    private int numVertices;
    private float[] cellPositions;
    private float[] cellStates;
    private VertexBufferObject cellPositionsVertexBufferObject;
    private VertexBufferObject cellStatesVertexBufferObject;
    private ShaderProgram shaderProgram;
    private int u_projViewTransLocation;
    private int u_deadCellColorLocation;
    private int u_aliveCellColorLocation;
    private Matrix4 projectionViewMatrix;
    
    public HighPerformanceCellRenderer() {
        this.shaderProgram = new ExtendedShaderProgram(Gdx.files.internal(VERTEX_SHADER_PATH),
                Gdx.files.internal(FRAGMENT_SHADER_PATH), Gdx.files.internal(GEOMETRY_SHADER_PATH));
        this.u_projViewTransLocation = this.shaderProgram.getUniformLocation("u_projViewTrans");
        this.u_deadCellColorLocation = this.shaderProgram.getUniformLocation("u_deadCellColor");
        this.u_aliveCellColorLocation = this.shaderProgram.getUniformLocation("u_aliveCellColor");
        
        this.shaderProgram.begin();
        this.shaderProgram.setUniformf(this.u_deadCellColorLocation, DEAD_CELL_COLOR);
        this.shaderProgram.setUniformf(this.u_aliveCellColorLocation, ALIVE_CELL_COLOR);
        this.shaderProgram.end();
    }
    
    @Override
    public void setBorderEnabled(boolean enabled) {
        // This renderer currently does not support border rendering
    }
    
    @Override
    public boolean isBorderEnabled() {
        // This renderer currently does not support border rendering
        return false;
    }
    
    @Override
    public void configure(int worldWidth, int worldHeight) {
        updateCellPositions(worldWidth, worldHeight);
        this.cellPositionsVertexBufferObject = getCellPositionsVertexBufferObject();
        setCellPositionVertices();
        
        updateCellStates();
        this.cellStatesVertexBufferObject = getCellStatesVertexBufferObject();
        setCellPositionVertices();
    }
    
    @Override
    public void begin(Camera camera) {
        this.projectionViewMatrix = camera.combined;
    }
    
    @Override
    public void drawCell(int x, int y, int state) {
        this.cellStates[this.worldWidth * y + x] = state;
    }
    
    @Override
    public void end() {
        setVertexCellStates();
        
        this.shaderProgram.begin();
        this.shaderProgram.setUniformMatrix(this.u_projViewTransLocation, this.projectionViewMatrix);
        
        Gdx.gl.glDrawArrays(GL20.GL_POINTS, 0, this.numVertices);
        this.shaderProgram.end();
    }
    
    @Override
    public void hide() {
        if (this.cellPositionsVertexBufferObject != null) {
            this.cellPositionsVertexBufferObject.unbind(this.shaderProgram);
        }
        if (this.cellStatesVertexBufferObject != null) {
            this.cellStatesVertexBufferObject.unbind(this.shaderProgram);
        }
        this.shaderProgram.end();
    }
    
    @Override
    public void dispose() {
        if (this.cellPositionsVertexBufferObject != null) {
            this.cellPositionsVertexBufferObject.dispose();
        }
        if (this.cellStatesVertexBufferObject != null) {
            this.cellStatesVertexBufferObject.dispose();
        }
        this.shaderProgram.dispose();
    }
    
    private void setCellPositionVertices() {
        this.cellPositionsVertexBufferObject.bind(this.shaderProgram);
        this.cellPositionsVertexBufferObject.setVertices(this.cellPositions, 0, this.cellPositions.length);
    }
    
    private void setVertexCellStates() {
        this.cellStatesVertexBufferObject.bind(this.shaderProgram);
        this.cellStatesVertexBufferObject.setVertices(this.cellStates, 0, this.numVertices);
    }
    
    private void updateCellPositions(int worldWidth, int worldHeight) {
        int newNumVertices = worldWidth * worldHeight;
        float[] newCellPositions = this.cellPositions;
        if (this.cellPositions == null || this.cellPositions.length != newNumVertices * 2) {
            newCellPositions = new float[newNumVertices * 2];
        } else if (this.worldWidth == worldWidth && this.worldHeight == worldHeight) {
            return;
        }
        
        float x = 0;
        float y = 0;
        for (int i = 0; i < newNumVertices; i++) {
            newCellPositions[i * 2] = x;
            newCellPositions[i * 2 + 1] = y;
            x++;
            if (x >= worldWidth) {
                x = 0;
                y++;
            }
        }
        
        this.cellPositions = newCellPositions;
        this.numVertices = newNumVertices;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
    }
    
    private VertexBufferObject getCellPositionsVertexBufferObject() {
        if (this.cellPositionsVertexBufferObject != null) {
            if (this.cellPositionsVertexBufferObject.getNumMaxVertices() >= this.numVertices) {
                return this.cellPositionsVertexBufferObject;
            }
            this.cellPositionsVertexBufferObject.dispose();
        }
        return new VertexBufferObject(false, numVertices, CELL_POSITION_VERTEX_ATTRIBUTE);
    }
    
    private void updateCellStates() {
        if (this.cellStates != null && this.cellStates.length == this.numVertices) {
            return;
        }
        this.cellStates = new float[this.numVertices];
    }
    
    private VertexBufferObject getCellStatesVertexBufferObject() {
        if (this.cellStatesVertexBufferObject != null) {
            if (this.cellStatesVertexBufferObject.getNumMaxVertices() >= this.numVertices) {
                return this.cellStatesVertexBufferObject;
            }
            this.cellStatesVertexBufferObject.dispose();
        }
        return new VertexBufferObject(false, this.numVertices, CELL_STATE_VERTEX_ATTRIBUTE);
    }
}
