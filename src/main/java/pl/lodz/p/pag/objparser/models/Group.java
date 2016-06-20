package pl.lodz.p.pag.objparser.models;

import pl.lodz.p.pag.objparser.materials.Material;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by piotr on 15.05.2016.
 */
public class Group {
    String groupName;
    Material material;
    List<List<int[]>> faces = new ArrayList<>();
    int vaoId;
    int vertexCount;

    public Group(String groupName) {
        this.groupName = groupName;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public void setVertexCount(int vertexCount) {
        this.vertexCount = vertexCount;
    }

    public int getVaoId() {
        return vaoId;
    }

    public void setVaoId(int vaoId) {
        this.vaoId = vaoId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public List<List<int[]>> getFaces() {
        return faces;
    }

    public void setFaces(List<List<int[]>> faces) {
        this.faces = faces;
    }
}
