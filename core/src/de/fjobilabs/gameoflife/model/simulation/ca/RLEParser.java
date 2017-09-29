package de.fjobilabs.gameoflife.model.simulation.ca;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.utils.Logger;

import de.fjobilabs.gameoflife.model.Cell;
import de.fjobilabs.gameoflife.model.simulation.ca.RLEPattern.Token;
import de.fjobilabs.libgdx.util.LoggerFactory;

/**
 * Parser for the RLE pattern format.<br>
 * <br>
 * Format described at <a href=
 * "http://www.conwaylife.com/wiki/RLE">http://www.conwaylife.com/wiki/RLE</a>.
 * 
 * @author Felix Jordan
 * @version 1.0
 * @since 17.09.2017 - 23:42:04
 */
/*
 * TODO Create new implementation of this parser, that uses token streaming.
 * Implement similar to JsonParser from Jackson XML/JSON library.
 */
public class RLEParser {
    
    private static final char MEATA_HEADER_START = '#';
    private static final char META_HEADER_SEPARATOR = ' ';
    private static final char META_HEADER_TYPE_AUTHOR = 'O';
    private static final char META_HEADER_TYPE_COMMENT = 'C';
    private static final char PATTERN_HEADER_START = 'x';
    
    private static final Logger logger = LoggerFactory.getLogger(RLEParser.class, Logger.DEBUG);
    
    private InputStream data;
    private boolean patternHeaderParsed;
    private boolean parsingCellStates;
    private boolean parsingCellStatesFinished;
    private int currentTokenRow;
    
    // Meta headers
    private List<String> comments;
    private String author;
    private Map<String, String> unknownMetaHeaders;
    
    // Pattern header
    private int patternWidth;
    private int patternHeight;
    private String patternRule;
    
    // Pattern
    @SuppressWarnings("rawtypes")
    private List[] patternTokens;
    
    public RLEParser(InputStream data) {
        this.data = data;
        this.comments = new ArrayList<>();
    }
    
    /*
     * TODO BAD CODE
     */
    @SuppressWarnings("unchecked")
    public RLEPattern createPattern() {
        Token[][] tokens = new Token[this.patternHeight][];
        for (int i=0; i<this.patternHeight; i++) {
            @SuppressWarnings("rawtypes")
            List tokenRowList = this.patternTokens[i];
            Token[] tokenRow = new Token[tokenRowList.size()];
            tokens[i] = (Token[]) tokenRowList.toArray(tokenRow);
        }
        RLEPattern pattern = new RLEPattern(this.patternWidth, this.patternHeight, tokens);
        // TODO set meta headers of pattern
        return pattern;
    }
    
    public void parse() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(this.data));
        String line = null;
        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) {
                // We simply skip empty lines
                continue;
            }
            char firstChar = line.charAt(0);
            if (firstChar == MEATA_HEADER_START) {
                parseMetaHeader(line);
            } else if (firstChar == PATTERN_HEADER_START) {
                parsePatternHeader(line);
            } else if (!this.patternHeaderParsed) {
                throw new RLEParserException("Invalid format: No pattern header before pattern!");
            } else if (this.parsingCellStates) {
                parseCellStates(line);
            } else if (!this.parsingCellStatesFinished) {
                this.parsingCellStates = true;
                parseCellStates(line);
            }
            /*
             * If parsingCellStatesFinished is true we silently ignore all other
             * lines, that are no meta headers.
             */
        }
    }
    
    @SuppressWarnings("unchecked")
    private void parseCellStates(String line) {
        int index = 0;
        int runCount = -1;
        while (index < line.length()) {
            char character = line.charAt(index);
            if (Character.isDigit(character)) {
                if (runCount == -1) {
                    runCount = parseRunCount(line, index);
                }
                // We skip every digit from the current run count
            } else if(character == '$') {
                if (runCount !=-1) {
                    this.currentTokenRow += runCount;
                    runCount = -1;
                } else {
                    this.currentTokenRow++;
                }
            } else if (character == '!') {
                this.parsingCellStates = false;
                this.parsingCellStatesFinished = true;
                // We ignore the rest of the line
                return;
            }
            else if (character == 'o') {
                // Alive cell
                Token token;
                if (runCount != -1) {
                    token = new Token(runCount, Cell.ALIVE);
                    runCount = -1;
                } else {
                    token = new Token(Cell.ALIVE);
                }
                this.patternTokens[this.currentTokenRow].add(token);
            } else if (character == 'b') {
                // Dead cell
                Token token;
                if (runCount != -1) {
                    token = new Token(runCount, Cell.DEAD);
                    runCount = -1;
                } else {
                    token = new Token(Cell.DEAD);
                }
                this.patternTokens[this.currentTokenRow].add(token);
            } else {
                // All other unknown characters are treated as dead cells
                logger.info("WARN: Unknown cell state: " + character + ". Treating as dead cell!");
                Token token;
                if (runCount != -1) {
                    token = new Token(runCount, Cell.DEAD);
                    runCount = -1;
                } else {
                    token = new Token(Cell.DEAD);
                }
                this.patternTokens[this.currentTokenRow].add(token);
            }
            index++;
        }
    }
    
    private int parseRunCount(String line, int index) {
        for (int i=index; i<line.length(); i++) {
            if (!Character.isDigit(line.charAt(i))) {
                return Integer.parseInt(line.substring(index, i));
            }
        }
        throw new RLEParserException("Failed to parse run count");
    }
    
    /*
     * TODO Refactor header parsing code.
     */
    private void parsePatternHeader(String header) {
        String[] elements = header.split(",");
        if (elements.length < 2) {
            throw new RLEParserException("Invalid pattern header: " + header);
        }
        // Parse width element
        parsePatternWidthHeaderElement(elements[0].trim());
        parsePatternHeighthHeaderElement(elements[1].trim());
        
        this.patternTokens = new List[this.patternHeight];
        for (int i=0; i<this.patternTokens.length; i++) {
            this.patternTokens[i] = new ArrayList<>();
        }
        
        if (elements.length == 3) {
            parsePatternRuleHeaderElement(elements[2].trim());
        } else if (elements.length > 3) {
            throw new RLEParserException("Invalid pattern header: " + header);
        }
        this.patternHeaderParsed = true;
    }
    
    /*
     * TODO Refactor header parsing code.
     */
    private void parsePatternWidthHeaderElement(String element) {
        if (element.length() < 3 || !element.startsWith("x")) {
            throw new RLEParserException("Invalid pattern width header element: " + element);
        }
        String value = parsePatternHeaderElementValue(element);
        int width;
        try {
            width = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new RLEParserException("Invalid pattern width: " + value);
        }
        if (width <= 0) {
            throw new RLEParserException("Invalid pattern width: " + value);
        }
        this.patternWidth = width;
    }
    
    /*
     * TODO Refactor header parsing code.
     */
    private void parsePatternHeighthHeaderElement(String element) {
        if (element.length() < 3 || !element.startsWith("y")) {
            throw new RLEParserException("Invalid pattern height header element: " + element);
        }
        String value = parsePatternHeaderElementValue(element);
        int height;
        try {
            height = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new RLEParserException("Invalid pattern height: " + value);
        }
        if (height <= 0) {
            throw new RLEParserException("Invalid pattern height: " + value);
        }
        this.patternHeight = height;
    }
    
    /*
     * TODO Refactor header parsing code.
     */
    private void parsePatternRuleHeaderElement(String element) {
        if (element.length() < 3 || !element.startsWith("rule")) {
            throw new RLEParserException("Invalid pattern rule header element: " + element);
        }
        // TODO Should we validate this here?
        this.patternRule = parsePatternHeaderElementValue(element);
    }
    
    /*
     * TODO Refactor header parsing code.
     */
    private String parsePatternHeaderElementValue(String element) {
        int equalsSignIndex = element.indexOf('=');
        if (equalsSignIndex == -1) {
            throw new RLEParserException("Invalid pattern header element: " + element);
        }
        return element.substring(equalsSignIndex + 1).trim();
    }
    
    private void parseMetaHeader(String header) {
        if (header.length() == 1) {
            logger.info("WARN: Undefined, empty meta header");
            return;
        }
        char type = header.charAt(1);
        if (header.length() == 2) {
            logger.info("WARN: Empty meta header of type '" + type + "'");
            return;
        }
        if (header.charAt(2) != META_HEADER_SEPARATOR) {
            logger.info("WARN: Invalid meta header: '" + header + "'");
            return;
        }
        
        String value = header.substring(3);
        if (type == META_HEADER_TYPE_AUTHOR) {
            parseAuthorHeader(value);
        } else if (type == META_HEADER_TYPE_COMMENT) {
            parseCommentHeader(value);
        } else {
            parseUnknownHeader(type, value);
        }
    }
    
    private void parseAuthorHeader(String value) {
        if (author == null) {
            if (value.isEmpty()) {
                logger.info("WARN: Empty author header");
            }
            this.author = value;
        } else {
            throw new RLEParserException("Invalid header: Duplicate author definition");
        }
    }
    
    private void parseCommentHeader(String value) {
        // TODO Parse comment header
    }
    
    private void parseUnknownHeader(char type, String value) {
        if (value.isEmpty()) {
            logger.info("WARN: Empty unknown header of type '" + type + "'");
        }
        // TODO parse unknown header
    }
}
