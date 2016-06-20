package pl.lodz.p.pag.objparser.renderengine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

/**
 * Created by piotr on 16.04.2016.
 */
public class DisplayManager {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int FPS_CAP = 120;
    public static final String WINDOW_TITLE = "OBJ Parser";

    public static void createDisplay() {

        ContextAttribs contextAttribs = new ContextAttribs(3, 2)
                .withForwardCompatible(true)
                .withProfileCore(true);

        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create(new PixelFormat(), contextAttribs);
            Display.setTitle(WINDOW_TITLE);
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        GL11.glViewport(0, 0, WIDTH, HEIGHT);
    }

    public static void updateDisplay() {
        Display.sync(FPS_CAP);
        Display.update();
    }

    public static void closeDisplay() {
        Display.destroy();
    }

}
