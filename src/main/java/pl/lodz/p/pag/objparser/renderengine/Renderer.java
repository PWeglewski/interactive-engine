package pl.lodz.p.pag.objparser.renderengine;

import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import pl.lodz.p.pag.objparser.entities.Entity;
import pl.lodz.p.pag.objparser.models.Group;
import pl.lodz.p.pag.objparser.scene.Scene;
import pl.lodz.p.pag.objparser.shaders.StaticShader;
import pl.lodz.p.pag.objparser.toolbox.Maths;
import pl.lodz.p.pag.objparser.toolbox.MousePicker;

/**
 * Created by piotr on 16.04.2016.
 */
public class Renderer {

    private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000;
    private MousePicker mousePicker;
    private Matrix4f projectionMatrix;

    public Renderer(StaticShader staticShader, MousePicker mousePicker) {
        createProjectionMatrix();
        staticShader.start();
        staticShader.loadProjectionMatrix(projectionMatrix);
        staticShader.stop();
        this.mousePicker = mousePicker;
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public void setProjectionMatrix(Matrix4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }

    public void prepare() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(0, 0, 0.7f, 1);
    }

    public void render(Scene scene, StaticShader staticShader) {
        for (Entity entity : scene.getEntities()) {
            for (Group group : entity.getModel().getGroups()) {
                render(entity, group, staticShader, scene.getEntities().indexOf(entity), true);
            }
        }
        GL11.glFlush();
        GL11.glFinish();

        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);

        mousePicker.update();

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        for (Entity entity : scene.getEntities()) {
            for (Group group : entity.getModel().getGroups()) {
                render(entity, group, staticShader, scene.getEntities().indexOf(entity), false);
            }
        }
    }

    public void render(Entity entity, Group group, StaticShader staticShader, int entityId, boolean renderPickColor) {

        GL30.glBindVertexArray(group.getVaoId());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);

        Matrix4f parentTransformationMatrix;
        Matrix4f resultMatrix4f = new Matrix4f();

        staticShader.loadIsSelected(entity.isSelected());

        staticShader.loadPickingColor(entityId);

        staticShader.loadRenderPickColor(renderPickColor);

        Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
        if (entity.getParent() != null) {
            parentTransformationMatrix = entity.getParent().getTransformationMatrix();
        } else {
            parentTransformationMatrix = Maths.createTransformationMatrix(new Vector3f(0f, 0f, 0f),
                    0f, 0f, 0f, 1);
        }
        Matrix4f.mul(parentTransformationMatrix, transformationMatrix, resultMatrix4f);
        entity.setTransformationMatrix(resultMatrix4f);
        staticShader.loadTransformationMatrix(resultMatrix4f);

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, group.getMaterial().getTextureVaoId());
        GL11.glDrawElements(GL11.GL_TRIANGLES, group.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    private void createProjectionMatrix() {
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;

        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;
    }
}
