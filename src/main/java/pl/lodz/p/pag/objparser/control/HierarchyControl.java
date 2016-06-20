package pl.lodz.p.pag.objparser.control;

import org.lwjgl.input.Keyboard;
import pl.lodz.p.pag.objparser.entities.Entity;
import pl.lodz.p.pag.objparser.scene.Scene;

/**
 * Created by piotr on 22.05.16.
 */
public class HierarchyControl {
    Scene scene;

    Entity selection;
    int selectionIndex;

    public HierarchyControl(Scene scene) {
        this.scene = scene;
        selectionIndex = 0;
        updateSelection();
    }

    public void update() {
        if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD4)) {
            selection.increaseRotation(0, 0.4f, 0);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD6)) {
            selection.increaseRotation(0, -0.4f, 0);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD8)) {
            selection.increaseRotation(0.4f, 0, 0);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD2)) {
            selection.increaseRotation(-0.4f, 0, 0);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD1)) {
            selection.increaseRotation(0, 0, 0.4f);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD3)) {
            selection.increaseRotation(0, 0, -0.4f);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD7)) {
            selection.setScale(selection.getScale() * 1.02f);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD9)) {
            selection.setScale(selection.getScale() / 1.02f);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_T)) {
            selection.increasePosition(0, 0, 0.1f);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_G)) {
            selection.increasePosition(0, 0, -0.1f);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
            selection.increasePosition(-0.1f, 0, 0);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_H)) {
            selection.increasePosition(0.1f, 0, 0);
        }
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                if (Keyboard.getEventKey() == Keyboard.KEY_LEFT) {
                    deselect();
                    selectionIndex--;
                    if (selectionIndex < 0) {
                        selectionIndex = scene.getEntities().size() - 1;
                    }
                    updateSelection();
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT) {
                    deselect();
                    selectionIndex++;
                    if (selectionIndex == scene.getEntities().size()) {
                        selectionIndex = 0;
                    }
                    updateSelection();
                }
            }
        }
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    private void updateSelection() {
        selection = scene.getEntities().get(selectionIndex);
        selection.setSelected(true);
        String name = selection.getModel().getMaterialLibrary().getFile().getName().replace(".mtl", "");
        System.out.println("Current selection:\t" + name);
    }

    public void updateSelection(int index) {
        if (index < 0 || index > scene.getEntities().size()) return;
        if (scene.getEntities().get(index) != null) {
            deselect();
            selectionIndex = index;
            updateSelection();
        }
    }

    private void deselect() {
        selection.setSelected(false);
    }
}
