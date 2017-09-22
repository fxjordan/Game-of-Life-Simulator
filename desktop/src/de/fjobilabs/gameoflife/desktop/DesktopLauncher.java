package de.fjobilabs.gameoflife.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import de.fjobilabs.gameoflife.GameOfLifeGame;
import de.fjobilabs.gameoflife.GeometryShaderTestGame;

public class DesktopLauncher {
    
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//        new LwjglApplication(new GameOfLifeGame(), config);
        new LwjglApplication(new GeometryShaderTestGame(), config);
    }
}
