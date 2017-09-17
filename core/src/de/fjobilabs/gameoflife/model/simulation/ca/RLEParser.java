package de.fjobilabs.gameoflife.model.simulation.ca;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.utils.Logger;

import de.fjobilabs.libgdx.util.LoggerFactory;

/**
 * Parser for the RLE pattern format.<br>
 * <br>
 * Format described at <a href="http://www.conwaylife.com/wiki/RLE">http://www.conwaylife.com/wiki/RLE</a>.
 * 
 * @author Felix Jordan
 * @version 1.0
 * @since 17.09.2017 - 23:42:04
 */
public class RLEParser {
    
    private static final char MEATA_HEADER_START = '#';
    private static final char META_HEADER_SEPARATOR = ' ';
    private static final char META_HEADER_TYPE_AUTHOR = 'O';
    private static final char META_HEADER_TYPE_COMMENT = 'C';
    private static final char PATTERN_HEADER_START = 'x';
    
    private static final Logger logger = LoggerFactory.getLogger(RLEParser.class, Logger.DEBUG);
    
    private InputStream data;
    private boolean parsingCellStates;
    
    // Parsed data
    private List<String> comments;
    private String author;
    private Map<String, String> unknownMetaHeaders;
    
    public RLEParser(InputStream data) {
        this.data = data;
        this.comments = new ArrayList<>();
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
            } else {
                // TODO Check if parsing cell states
                // TODO If not: Check if header was parsed
                // TODO Error if header was not parsed
                // TODO STart parsing if begin of cell states
                // TODO Check end of cell states
            }
        }
    }
    
    private void parsePatternHeader(String header) {
        // TODO Parse pattern header
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
