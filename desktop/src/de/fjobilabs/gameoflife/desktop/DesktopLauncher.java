package de.fjobilabs.gameoflife.desktop;

import org.lwjgl.opengl.GL32;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import de.fjobilabs.gameoflife.RenderingTestGame;

public class DesktopLauncher {
    
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//        new LwjglApplication(new GameOfLifeGame(), config);
        new LwjglApplication(new RenderingTestGame(), config);
    }
}
