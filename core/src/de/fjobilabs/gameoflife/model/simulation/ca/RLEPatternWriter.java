package de.fjobilabs.gameoflife.model.simulation.ca;

import java.io.OutputStream;

import de.fjobilabs.gameoflife.model.simulation.ca.RLEPattern.Token;

/**
 * Writes {@link RLEPattern}s to streams or creates a strign representation of
 * them. <br>
 * Format described at <a href=
 * "http://www.conwaylife.com/wiki/RLE">http://www.conwaylife.com/wiki/RLE</a>.
 * 
 * @author Felix Jordan
 * @version 1.0
 * @since 28.09.2017 - 21:15:05
 */
public class RLEPatternWriter {
    
    private static final int MAX_LINE_WIDTH = 70;
    
    public void writePattern(OutputStream out, RLEPattern pattern) {
        
    }
    
    public String writePatternAsString(RLEPattern pattern) {
        StringBuilder builder = new StringBuilder();
        writeMetaHeaders(builder, pattern);
        writeHeader(builder, pattern);
        writeCellStates(builder, pattern);
        return builder.toString();
    }
    
    private void writeMetaHeaders(StringBuilder builder, RLEPattern pattern) {
        // TODO Write meta headers to builder (name, author, comments,...)
    }
    
    private void writeHeader(StringBuilder builder, RLEPattern pattern) {
        builder.append("x = ");
        builder.append(pattern.getWidth());
        builder.append(", y = ");
        builder.append(pattern.getHeight());
        // TODO Store rule in RLEPattern
        // builder.append(", rule = ");
        // builder.append(pattern.getRule());
        builder.append('\n');
    }
    
    private void writeCellStates(StringBuilder builder, RLEPattern pattern) {
        TokenStringBuilder tokenStringBuilder = new TokenStringBuilder(MAX_LINE_WIDTH);
        Token[][] tokenRows = pattern.getTokens();
        for (int i = 0; i < tokenRows.length; i++) {
            Token[] tokens = tokenRows[i];
            for (int j = 0; j < tokens.length; j++) {
                tokenStringBuilder.append(tokens[j].toString());
            }
            if (i < tokenRows.length - 1) {
                tokenStringBuilder.append("$");
            }
        }
        tokenStringBuilder.append("!");
        builder.append(tokenStringBuilder.toString());
    }
    
    /**
     * String builder that can append tokens and ensures that all lines do not
     * exceed a maximum width.
     * 
     * @author Felix Jordan
     * @version 1.0
     * @since 28.09.2017 - 21:49:42
     */
    private static class TokenStringBuilder {
        
        private final int maxLineWidth;
        private StringBuilder stringBuilder;
        private int currentLineStart;
        
        public TokenStringBuilder(int maxLineWidth) {
            this.maxLineWidth = maxLineWidth;
            this.stringBuilder = new StringBuilder();
            this.currentLineStart = 0;
        }
        
        public void append(String token) {
            if (this.stringBuilder.length() - this.currentLineStart + token.length() > this.maxLineWidth) {
                this.stringBuilder.append('\n');
                this.currentLineStart = this.stringBuilder.length();
            }
            this.stringBuilder.append(token);
        }
        
        @Override
        public String toString() {
            return this.stringBuilder.toString();
        }
    }
}
