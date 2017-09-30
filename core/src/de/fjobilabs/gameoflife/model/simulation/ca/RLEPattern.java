package de.fjobilabs.gameoflife.model.simulation.ca;

import java.util.Map;

import de.fjobilabs.gameoflife.model.Cell;
import de.fjobilabs.gameoflife.model.World;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 17.09.2017 - 23:35:50
 */
public class RLEPattern implements Pattern {
    
    private int width;
    private int height;
    private String name;
    private String author;
    private String[] comments;
    private Map<String, String> unknownHeaders;
    private Token[][] tokens;
    
    public RLEPattern(int width, int height, Token[][] tokens) {
        this.width = width;
        this.height = height;
        if (tokens.length != height) {
            throw new IllegalArgumentException(
                    "Invalid number of tokenRows: height=" + height + ", rows=" + tokens.length);
        }
        setTokens(tokens);
    }
    
    @Override
    public int getWidth() {
        return this.width;
    }
    
    @Override
    public int getHeight() {
        return this.height;
    }
    
    @Override
    public void apply(World world, int x, int y) {
        int currentY = y;
        for (int i = 0; i < this.height; i++) {
            applyTokenRow(world, this.tokens[i], x, currentY);
            currentY--;
        }
    }
    
    Token[][] getTokens() {
        return this.tokens;
    }
    
    public String getName() {
        return name;
    }
    
    private void applyTokenRow(World world, Token[] tokens, int startX, int startY) {
        int currentX = startX;
        for (int i = 0; i < tokens.length; i++) {
            Token token = tokens[i];
            applyToken(world, token, currentX, startY);
            currentX += token.getRunCount();
        }
    }
    
    private void applyToken(World world, Token token, int startX, int startY) {
        int runCount = token.getRunCount();
        int cellState = token.getCellState();
        for (int i = 0; i < runCount; i++) {
            int x = startX + i;
            world.setCellState(x, startY, cellState);
        }
    }
    
    private void setTokens(Token[][] tokens) {
        this.tokens = new Token[this.height][];
        for (int i = 0; i < height; i++) {
            this.tokens[i] = createTokenRow(i, tokens[i]);
        }
    }
    
    private Token[] createTokenRow(int rowIndex, Token[] row) {
        int numTokens = row.length;
        Token[] newRow = new Token[numTokens];
        int rowWidth = 0;
        for (int i = 0; i < numTokens; i++) {
            Token token = row[i];
            rowWidth += token.getRunCount();
            validateRowWidth(rowIndex, rowWidth);
            newRow[i] = new Token(token.getRunCount(), token.getCellState());
        }
        return newRow;
    }
    
    private void validateRowWidth(int rowIndex, int rowWidth) {
        if (rowWidth > this.width) {
            throw new IllegalArgumentException("Tokens in row " + rowIndex
                    + " exceed pattern width! (patternWidth=" + this.width + ", rowWidth=" + rowWidth + ")");
        }
    }
    
    public static class Token {
        
        private final int runCount;
        private final int cellState;
        
        public Token(int cellState) {
            this(1, cellState);
        }
        
        public Token(int runCount, int cellState) {
            validateRunCount(runCount);
            this.runCount = runCount;
            Cell.validateCellState(cellState);
            this.cellState = cellState;
        }
        
        public int getRunCount() {
            return runCount;
        }
        
        public int getCellState() {
            return cellState;
        }
        
        private void validateRunCount(int runCount) {
            if (runCount < 0) {
                throw new IllegalArgumentException("Invalid runCount: " + runCount + "(must be >= 0)");
            }
        }
        
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            if (this.runCount > 1) {
                builder.append(this.runCount);
            }
            if (this.cellState == Cell.ALIVE) {
                builder.append('o');
            } else {
                builder.append('b');
            }
            return builder.toString();
        }
    }
}
