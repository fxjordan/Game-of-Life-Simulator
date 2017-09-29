package de.fjobilabs.gameoflife.desktop.simulator;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import de.fjobilabs.gameoflife.desktop.simulator.io.WorldContentModel;
import de.fjobilabs.gameoflife.model.World;
import de.fjobilabs.gameoflife.model.simulation.ca.Pattern;
import de.fjobilabs.gameoflife.model.simulation.ca.RLEParser;

/**
 * Can load a {@link WorldContentModel} in a specific {@link World}.
 * 
 * @author Felix Jordan
 * @version 1.0
 * @since 29.09.2017 - 00:52:55
 */
public class WorldContentLoader {
    
    /**
     * The RLE pattern type. Currently the only supported pattern format.
     */
    public static final String RLE_PATTERN_TYPE = "RLE";
    
    /**
     * Loads the content described in the {@link WorldContentModel} in a given
     * {@link World}.
     * 
     * @param world
     * @param worldContent
     * @throws WorldContentLoaderException 
     */
    public void loadContent(World world, WorldContentModel worldContent) throws WorldContentLoaderException {
        Pattern pattern = parsePattern(worldContent.getPatternFormat(), worldContent.getPattern());
        pattern.apply(world, worldContent.getPatternX(), worldContent.getPatternY());
    }
    
    private Pattern parsePattern(String patternFormat, String pattern) throws WorldContentLoaderException {
        if (!RLE_PATTERN_TYPE.equals(patternFormat)) {
            throw new IllegalArgumentException("Unsupported pattern format: " + patternFormat);
        }
        RLEParser rleParser = new RLEParser(
                new ByteArrayInputStream(pattern.getBytes(Charset.forName("UTF-8"))));
        try {
            rleParser.parse();
        } catch (IOException e) {
            throw new WorldContentLoaderException("Failed to parse pattern", e);
        }
        return rleParser.createPattern();
    }
}
