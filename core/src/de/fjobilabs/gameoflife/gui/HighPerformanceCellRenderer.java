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
    
    private int worldWidth;
    private int worldHeight;
    private int numVertices;
    private float[] cellStates;
    private VertexBufferObject positionVertexBufferObject;
    private VertexBufferObject cellStateVertexBufferObject;
    private ShaderProgram shaderProgram;
    private int u_projViewTransLocation;
    private int u_deadCellColorLocation;
    private int u_aliveCellColorLocation;
    private Matrix4 projectionViewMatrix;
    
    public HighPerformanceCellRenderer(int worldWidth, int worldHeight) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.numVertices = worldWidth * worldHeight;
        this.cellStates = new float[this.numVertices];
        
        VertexAttribute positionAttribute = new VertexAttribute(Usage.Position, 2, "a_position");
        this.positionVertexBufferObject = new VertexBufferObject(true, this.numVertices, positionAttribute);
        
        VertexAttribute cellStateAttribute = new VertexAttribute(Usage.Generic, 1, GL20.GL_FLOAT, true,
                "a_cellState");
        this.cellStateVertexBufferObject = new VertexBufferObject(false, this.numVertices,
                cellStateAttribute);
        
        this.shaderProgram = new ExtendedShaderProgram(Gdx.files.internal(VERTEX_SHADER_PATH),
                Gdx.files.internal(FRAGMENT_SHADER_PATH), Gdx.files.internal(GEOMETRY_SHADER_PATH));
        this.u_projViewTransLocation = this.shaderProgram.getUniformLocation("u_projViewTrans");
        this.u_deadCellColorLocation = this.shaderProgram.getUniformLocation("u_deadCellColor");
        this.u_aliveCellColorLocation = this.shaderProgram.getUniformLocation("u_aliveCellColor");
        
        this.shaderProgram.begin();
        this.shaderProgram.setUniformf(this.u_deadCellColorLocation, DEAD_CELL_COLOR);
        this.shaderProgram.setUniformf(this.u_aliveCellColorLocation, ALIVE_CELL_COLOR);
        this.shaderProgram.end();
        
        setVertexPositions();
        setVertexCellStates();
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
    public void dispose() {
        this.positionVertexBufferObject.dispose();
        this.cellStateVertexBufferObject.dispose();
        this.shaderProgram.dispose();
    }
    
    private void setVertexPositions() {
        float[] vertexPositions = createVertexPositions();
        this.positionVertexBufferObject.bind(this.shaderProgram);
        this.positionVertexBufferObject.setVertices(vertexPositions, 0, vertexPositions.length);
    }
    
    private void setVertexCellStates() {
        this.cellStateVertexBufferObject.bind(this.shaderProgram);
        this.cellStateVertexBufferObject.setVertices(this.cellStates, 0, this.numVertices);
    }
    
    private float[] createVertexPositions() {
        float[] vertices = new float[this.numVertices * 2];
        float x = 0;
        float y = 0;
        for (int i = 0; i < this.numVertices; i++) {
            vertices[i * 2] = x;
            vertices[i * 2 + 1] = y;
            x++;
            if (x >= this.worldWidth) {
                x = 0;
                y++;
            }
        }
        return vertices;
    }
}
