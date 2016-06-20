package pl.lodz.p.pag.objparser.shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;
import pl.lodz.p.pag.objparser.entities.Camera;
import pl.lodz.p.pag.objparser.entities.Light;
import pl.lodz.p.pag.objparser.toolbox.Maths;

/**
 * Created by piotr on 16.04.2016.
 */
public class StaticShader extends ShaderProgram {

    private static final String VERTEX_FILE = "src/main/java/pl/lodz/p/pag/objparser/shaders/vertexShader.glsl";
    private static final String FRAGMENT_FILE = "src/main/java/pl/lodz/p/pag/objparser/shaders/fragmentShader.glsl";

    private int location_isSelected;
    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_lightPosition;
    private int location_lightColour;
    private int location_pickingColor;
    private int location_renderPickColor;

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_lightPosition = super.getUniformLocation("lightPosition");
        location_lightColour = super.getUniformLocation("lightColour");
        location_isSelected = super.getUniformLocation("isSelected");
        location_pickingColor = super.getUniformLocation("pickingColor");
        location_renderPickColor = super.getUniformLocation("renderPickColor");
    }

    public void loadTransformationMatrix(Matrix4f matrix4f) {
        super.loadMatrix(location_transformationMatrix, matrix4f);
    }

    public void loadPickingColor(int id) {
        Vector4f pickingColor = new Vector4f();
        int r = (id & 0x000000FF) >> 0;
        int g = (id & 0x0000FF00) >> 8;
        int b = (id & 0x00FF0000) >> 16;
        pickingColor.set(r / 255.0f, g / 255.0f, b / 255.0f, 1.0f);
        super.loadVector4f(location_pickingColor, pickingColor);
    }

    public void loadProjectionMatrix(Matrix4f matrix4f) {
        super.loadMatrix(location_projectionMatrix, matrix4f);
    }

    public void loadIsSelected(boolean value) {
        float isSelected;
        if (value)
            isSelected = 2.5f;
        else
            isSelected = 0.5f;
        super.loadFloat(location_isSelected, isSelected);
    }

    public void loadRenderPickColor(boolean value) {
        float f = 0.0f;
        if (value) f = 1.0f;
        super.loadFloat(location_renderPickColor, f);
    }

    public void loadViewMatrix(Camera camera) {
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        super.loadMatrix(location_viewMatrix, viewMatrix);
    }

    public void loadLight(Light light) {
        super.loadVector(location_lightPosition, light.getPosition());
        super.loadVector(location_lightColour, light.getColour());
    }
}
