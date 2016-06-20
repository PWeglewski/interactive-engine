package pl.lodz.p.pag.objparser.entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by piotr on 16.04.2016.
 */
public class Camera {
    public static final float CAMERA_STEP = 0.1f;

    private Vector3f position = new Vector3f(5.0f, 0, 0);
    private float pitch;
    private float yaw;
    private float roll;

    public Camera() {
    }

    public void move() {
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            position.z -= CAMERA_STEP;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            position.x += CAMERA_STEP;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            position.x -= CAMERA_STEP;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            position.z += CAMERA_STEP;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
            position.y += CAMERA_STEP;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
            position.y -= CAMERA_STEP;
        }
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getRoll() {
        return roll;
    }

    public void setRoll(float roll) {
        this.roll = roll;
    }
}
