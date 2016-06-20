package pl.lodz.p.pag.objparser.models;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import pl.lodz.p.pag.objparser.materials.MaterialLibrary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by piotr on 06.05.2016.
 */
public class Model {
    MaterialLibrary materialLibrary;

    List<Vector3f> vertices = new ArrayList<>();

    List<Vector2f> textureCoordinates = new ArrayList<>();

    List<Vector3f> normals = new ArrayList<>();

    List<Group> groups = new ArrayList<>();

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<Vector3f> getNormals() {
        return normals;
    }

    public void setNormals(List<Vector3f> normals) {
        this.normals = normals;
    }

    public List<Vector2f> getTextureCoordinates() {
        return textureCoordinates;
    }

    public void setTextureCoordinates(List<Vector2f> textureCoordinates) {
        this.textureCoordinates = textureCoordinates;
    }

    public List<Vector3f> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vector3f> vertices) {
        this.vertices = vertices;
    }

    public MaterialLibrary getMaterialLibrary() {
        return materialLibrary;
    }

    public void setMaterialLibrary(MaterialLibrary materialLibrary) {
        this.materialLibrary = materialLibrary;
    }
}
