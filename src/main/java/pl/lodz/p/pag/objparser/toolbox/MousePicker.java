package pl.lodz.p.pag.objparser.toolbox;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import pl.lodz.p.pag.objparser.control.HierarchyControl;
import pl.lodz.p.pag.objparser.entities.Entity;

import java.nio.ByteBuffer;

/**
 * Created by piotr on 23.05.16.
 */
public class MousePicker {
    public static final int MOUSE_RIGHT_BUTTON = 1;
    public static final int MOUSE_LEFT_BUTTON = 0;

    Entity currentSelection;
    HierarchyControl hierarchyControl;
    private int mouseX;
    private int mouseY;

    public MousePicker(HierarchyControl hierarchyControl) {
        this.hierarchyControl = hierarchyControl;
    }

    public void update() {
        while (Mouse.next()) {
            if (Mouse.getEventButtonState()) {
                if (Mouse.isButtonDown(MOUSE_LEFT_BUTTON)) {
                    mouseX = Mouse.getX();
                    mouseY = Mouse.getY();

                    int bpp = 4; // Assuming a 32-bit display with a byte each for red, green, blue, and alpha.
                    ByteBuffer buffer = BufferUtils.createByteBuffer(bpp);
                    GL11.glReadPixels(mouseX, mouseY, 1, 1, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
                    int pickedID = buffer.get() + buffer.get() * 256 + buffer.get() * 256 * 256;
                    hierarchyControl.updateSelection(pickedID);
                }
            }

        }

    }
}
