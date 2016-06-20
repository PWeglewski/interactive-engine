package pl.lodz.p.pag.objparser.shaders;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

/**
 * Created by piotr on 16.04.2016.
 */
public abstract class ShaderProgram {
    private static FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);
    private int programId;
    private int vertexShaderId;
    private int fragmentShaderId;
    private int colorPickFragmentShaderId;

    public ShaderProgram(String vertexShader, String fragmentShader) {
        this.vertexShaderId = loadShader(vertexShader, GL20.GL_VERTEX_SHADER);
        this.fragmentShaderId = loadShader(fragmentShader, GL20.GL_FRAGMENT_SHADER);
        this.programId = GL20.glCreateProgram();
        GL20.glAttachShader(this.programId, this.vertexShaderId);
        GL20.glAttachShader(this.programId, this.fragmentShaderId);
        GL20.glAttachShader(this.programId, this.colorPickFragmentShaderId);
        bindAttributes();
        GL20.glLinkProgram(this.programId);
        GL20.glValidateProgram(programId);
        getAllUniformLocations();
    }

    private static int loadShader(String file, int type) {
        StringBuilder shaderSource = new StringBuilder();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("Shader file: " + file + " not found.");
            System.exit(-1);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Exception while reading file: " + file + ".");
            System.exit(-1);
        }
        int shaderId = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderId, shaderSource);
        GL20.glCompileShader(shaderId);
        if (GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.out.println(GL20.glGetShaderInfoLog(shaderId, 500));
            System.err.println("Could not compile shader.");
            System.exit(-1);
        }
        return shaderId;
    }

    protected abstract void bindAttributes();

    protected abstract void getAllUniformLocations();

    protected int getUniformLocation(String uniformName) {
        return GL20.glGetUniformLocation(programId, uniformName);
    }

    public void start() {
        GL20.glUseProgram(programId);
    }

    public void stop() {
        GL20.glUseProgram(0);
    }

    public void cleanUp() {
        stop();
        GL20.glDetachShader(programId, this.vertexShaderId);
        GL20.glDetachShader(programId, this.fragmentShaderId);
        GL20.glDeleteShader(this.vertexShaderId);
        GL20.glDeleteShader(this.fragmentShaderId);
        GL20.glDeleteProgram(programId);
    }

    protected void bindAttribute(int attribute, String variableName) {
        GL20.glBindAttribLocation(programId, attribute, variableName);
    }

    protected void loadFloat(int location, float value) {
        GL20.glUniform1f(location, value);
    }

    protected void loadVector(int location, Vector3f vector) {
        GL20.glUniform3f(location, vector.x, vector.y, vector.z);
    }

    protected void loadVector4f(int location, Vector4f vector) {
        GL20.glUniform4f(location, vector.getX(), vector.getY(), vector.getZ(), vector.getW());
    }

    protected void loadBoolean(int location, boolean value) {
        float toLoad = 0;
        if (value) {
            toLoad = 1;
        }
        GL20.glUniform1f(location, toLoad);
    }

    protected void loadMatrix(int location, Matrix4f matrix4f) {
        matrix4f.store(floatBuffer);
        floatBuffer.flip();
        GL20.glUniformMatrix4(location, false, floatBuffer);
    }
}
