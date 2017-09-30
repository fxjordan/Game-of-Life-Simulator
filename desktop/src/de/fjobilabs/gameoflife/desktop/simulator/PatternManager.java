package de.fjobilabs.gameoflife.desktop.simulator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fjobilabs.gameoflife.model.simulation.ca.Pattern;
import de.fjobilabs.gameoflife.model.simulation.ca.RLEParser;
import de.fjobilabs.gameoflife.model.simulation.ca.RLEParserException;
import de.fjobilabs.gameoflife.model.simulation.ca.RLEPattern;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 30.09.2017 - 13:20:31
 */
public class PatternManager {
    
    private static final Logger logger = LoggerFactory.getLogger(PatternManager.class);
    
    private static final String RLE_PATTERN_FORMAT = "rle";
    
    private Map<String, LoadedPattern> loadedPatterns;
    
    public PatternManager() {
        this.loadedPatterns = new HashMap<>();
        loadInternalPatterns();
    }
    
    /**
     * Loads a pattern from a file.
     * 
     * @param file
     * @throws IOException
     * @throws WorldEditorException
     */
    public LoadedPattern loadPattern(File file) throws IOException, WorldEditorException {
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            return loadPattern(in, file.getName());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    logger.warn("Failed to close InputStream to file: " + file, e);
                }
            }
        }
    }
    
    public LoadedPattern getLoadedPattern(String patternId) {
        return this.loadedPatterns.get(patternId);
    }
    
    public LoadedPattern[] getLoadedPatterns() {
        LoadedPattern[] patterns = new LoadedPattern[this.loadedPatterns.size()];
        return this.loadedPatterns.values().toArray(patterns);
    }
    
    private LoadedPattern loadPattern(InputStream in, String filename) throws WorldEditorException {
        String format = getPatternFormat(filename);
        Pattern pattern;
        String patternName = createDefaultPatternName(filename);
        switch (format) {
        case RLE_PATTERN_FORMAT:
            RLEPattern rlePattern = loadRLEPattern(in);
            String rlePatternName = rlePattern.getName();
            if (rlePatternName != null && !rlePatternName.isEmpty()) {
                patternName = rlePatternName;
            }
            pattern = rlePattern;
            break;
        default:
            throw new WorldEditorException("Unsupported pattern format: " + format);
        }
        return addLoadedPattern(pattern, filename, patternName);
    }
    
    private String createDefaultPatternName(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex == -1) {
            return filename;
        }
        return filename.substring(0, dotIndex);
    }
    
    private RLEPattern loadRLEPattern(InputStream in) throws WorldEditorException {
        RLEParser rleParser = new RLEParser(in);
        try {
            rleParser.parse();
            return rleParser.createPattern();
        } catch (RLEParserException e) {
            throw new WorldEditorException("Failed to parse pattern", e);
        } catch (IOException e) {
            throw new WorldEditorException("Failed to load pattern", e);
        }
    }
    
    private LoadedPattern addLoadedPattern(Pattern pattern, String id, String name) {
        LoadedPattern loadedPattern = new LoadedPattern(id, pattern, name);
        this.loadedPatterns.put(id, loadedPattern);
        return loadedPattern;
    }
    
    private String getPatternFormat(String filename) {
        if (filename.endsWith(".rle")) {
            return RLE_PATTERN_FORMAT;
        }
        return null;
    }
    
    private void loadInternalPatterns() {
        try {
            loadInternalPattern("patterns/puffer1.rle", "Puffer 1");
            loadInternalPattern("patterns/turingmachine.rle", "Turingmachine");
            loadInternalPattern("patterns/gosperglidergun.rle", "Gosperglidergun");
            loadInternalPattern("patterns/wingextended.rle", "Wing extended");
            loadInternalPattern("patterns/wingspaceship.rle", "Wingspaceship");
        } catch (WorldEditorException e) {
            logger.warn("Failed to load internal patterns", e);
        }
    }
    
    private void loadInternalPattern(String path, String name) throws WorldEditorException {
        Pattern pattern = loadRLEPattern(WorldEditor.class.getClassLoader().getResourceAsStream(path));
        addLoadedPattern(pattern, path, name);
    }
}
