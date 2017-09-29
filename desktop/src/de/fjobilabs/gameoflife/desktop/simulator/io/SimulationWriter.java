package de.fjobilabs.gameoflife.desktop.simulator.io;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import de.fjobilabs.gameoflife.desktop.simulator.SimulationConfiguration;
import de.fjobilabs.gameoflife.model.Cell;
import de.fjobilabs.gameoflife.model.Simulation;
import de.fjobilabs.gameoflife.model.World;
import de.fjobilabs.gameoflife.model.simulation.ca.RLEPattern;
import de.fjobilabs.gameoflife.model.simulation.ca.RLEPattern.Token;
import de.fjobilabs.gameoflife.model.simulation.ca.RLEPatternWriter;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 28.09.2017 - 19:51:42
 */
public class SimulationWriter {
    
    /**
     * The RLE pattern type. Currently the only supported pattern format.
     */
    public static final String RLE_PATTERN_FORMAT = "RLE";
    
    private final ObjectMapper objectMapper;
    private final RLEPatternWriter rlePatternWriter;
    
    public SimulationWriter() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.rlePatternWriter = new RLEPatternWriter();
    }
    
    public void writeSimulation(OutputStream out, SimulationConfiguration config, Simulation simulation)
            throws IOException {
        SimulationModel model = createSimulationModel(config, simulation);
        this.objectMapper.writeValue(out, model);
    }
    
    public SimulationModel createSimulationModel(SimulationConfiguration config, Simulation simulation) {
        checkSimulationPaused(simulation);
        SimulationModel model = new SimulationModel();
        model.setConfig(config);
        model.setWorldContent(createWorldContentModel(simulation.getWorld()));
        model.setGeneration(simulation.getGeneration());
        return model;
    }
    
    /**
     * Creates a snapshot of the worlds current state.<br>
     * Returns <code>null</code> if the world is empty.
     * 
     * @param world
     * @return
     */
    public WorldContentModel createWorldContentModel(World world) {
        WorldContentModel model = new WorldContentModel();
        model.setPatternFormat(RLE_PATTERN_FORMAT);
        int startX = findStartX(world);
        if (startX == -1) {
            return null;
        }
        int startY = findStartY(world);
        if (startY == -1) {
            return null;
        }
        model.setPatternX(startX);
        model.setPatternY(startY);
        RLEPattern rlePattern = createRLEPattern(startX, startY, world);
        model.setPattern(this.rlePatternWriter.writePatternAsString(rlePattern));
        return model;
    }
    
    /**
     * Finds the lowest x position of an alive cell.<br>
     * Returns <code>-1</code> if the world is empty.
     * 
     * @param world
     * @return
     */
    private int findStartX(World world) {
        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                if (world.getCellState(x, y) == Cell.ALIVE) {
                    return x;
                }
            }
        }
        return -1;
    }
    
    /**
     * Finds the highest y position of an alive cell.<br>
     * Returns <code>-1</code> if the world is empty.
     * 
     * @param world
     * @return
     */
    private int findStartY(World world) {
        for (int y = world.getHeight() - 1; y >= 0; y--) {
            for (int x = 0; x < world.getWidth(); x++) {
                if (world.getCellState(x, y) == Cell.ALIVE) {
                    return y;
                }
            }
        }
        return -1;
    }
    
    /**
     * Creates an {@link RLEPattern} as a snapshot from the given world. The
     * snapshot starts with its top-left corner at the given position in the
     * world.
     * 
     * @param startX
     * @param startY
     * @param world
     * @return
     */
    private RLEPattern createRLEPattern(int startX, int startY, World world) {
        List<List<Token>> tokenRows = new ArrayList<>();
        int width = findEndX(world) - startX + 1;
        int height = startY - findEndY(world) + 1;
        System.out.println("pattern x: " + startX);
        System.out.println("pattern y: " + startY);
        System.out.println("pattern width: " + width);
        System.out.println("pattern height: " + height);
        
        for (int i = 0; i < height; i++) {
            tokenRows.add(createTokenRow(world, startX, startY - i, width));
        }
        return new RLEPattern(width, height, createTokenArray(tokenRows, height));
    }
    
    private List<Token> createTokenRow(World world, int startX, int y, int width) {
        List<Token> tokens = new ArrayList<>();
        int runCount = 0;
        int tokenState = -1;
        for (int x = 0; x < width; x++) {
            int state = world.getCellState(startX + x, y);
            if (tokenState == -1) {
                tokenState = state;
                runCount = 1;
            } else if (tokenState == state) {
                runCount++;
            } else {
                tokens.add(new Token(runCount, tokenState));
                tokenState = state;
                runCount = 1;
            }
        }
        if (runCount > 0) {
            tokens.add(new Token(runCount, tokenState));
        }
        return tokens;
    }
    
    private Token[][] createTokenArray(List<List<Token>> tokenRows, int height) {
        Token[][] tokens = new Token[height][];
        for (int i=0; i<height; i++) {
            List<Token> tokenList = tokenRows.get(i);
            Token[] row = new Token[tokenList.size()];
            tokens[i] = tokenList.toArray(row);
        }
        return tokens;
    }
    
    /**
     * Finds the highest x position of an alive cell.<br>
     * Returns <code>-1</code> if the world is empty.
     * 
     * @param world
     * @return
     */
    private int findEndX(World world) {
        for (int x = world.getWidth() - 1; x >= 0; x--) {
            for (int y = 0; y < world.getHeight(); y++) {
                if (world.getCellState(x, y) == Cell.ALIVE) {
                    return x;
                }
            }
        }
        return -1;
    }
    
    /**
     * Finds the lowest y position of an alive cell.<br>
     * Returns <code>-1</code> if the world is empty.
     * 
     * @param world
     * @return
     */
    private int findEndY(World world) {
        for (int y = 0; y < world.getHeight(); y++) {
            for (int x = 0; x < world.getWidth(); x++) {
                if (world.getCellState(x, y) == Cell.ALIVE) {
                    return y;
                }
            }
        }
        return -1;
    }
    
    private void checkSimulationPaused(Simulation simulation) {
        if (simulation.isRunning()) {
            throw new IllegalStateException("Simulation must be paused to create a model from it");
        }
    }
}
