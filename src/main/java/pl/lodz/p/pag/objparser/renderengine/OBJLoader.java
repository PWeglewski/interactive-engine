package pl.lodz.p.pag.objparser.renderengine;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import pl.lodz.p.pag.objparser.models.Group;
import pl.lodz.p.pag.objparser.models.Model;
import pl.lodz.p.pag.objparser.parser.ObjParser;

import java.util.List;

/**
 * Created by piotr on 16.04.2016.
 */
public class OBJLoader {
    public static void loadObj(ObjParser objParser, Loader loader) {
        for (Model model : objParser.getModels()) {
            for (Group group : model.getGroups()) {
                group.getMaterial().setTextureVaoId(loader.loadTexture(group.getMaterial().getTextureFileName()));

                int verticesNumber = findMaxVertexIndex(group.getFaces());
                int[] indicesArray = new int[group.getFaces().size() * 3];
                float[] verticesArray = new float[verticesNumber * 3];
                float[] normalsArray = new float[verticesNumber * 3];
                float[] textureArray = new float[verticesNumber * 2];

                group.setVertexCount(indicesArray.length);

                List<Vector3f> vertices = model.getVertices();
                int verticesArrayIterator = 0;
                for (int i = 0; i < verticesNumber; i++) {
                    Vector3f vertex = vertices.get(i);
                    verticesArray[i * 3] = vertex.getX();
                    verticesArrayIterator++;
                    verticesArray[i * 3 + 1] = vertex.getY();
                    verticesArrayIterator++;
                    verticesArray[i * 3 + 2] = vertex.getZ();
                    verticesArrayIterator++;
                }

                int indicesArrayIterator = 0;
                for (List<int[]> face : group.getFaces()) {
                    for (int[] vertexIndex : face) {
                        int currentVertexIndex = vertexIndex[0] - 1;
                        indicesArray[indicesArrayIterator] = currentVertexIndex;

                        Vector2f textureCoordinates = model.getTextureCoordinates().get(vertexIndex[1] - 1);
                        textureArray[currentVertexIndex * 2] = textureCoordinates.getX();
                        textureArray[currentVertexIndex * 2 + 1] = textureCoordinates.getY();

                        Vector3f normal = model.getNormals().get(vertexIndex[2] - 1);
                        normalsArray[currentVertexIndex * 3] = normal.getX();
                        normalsArray[currentVertexIndex * 3 + 1] = normal.getY();
                        normalsArray[currentVertexIndex * 3 + 2] = normal.getZ();

                        indicesArrayIterator++;
                    }
                }
                group.setVaoId(loader.loadToVAO(verticesArray, textureArray, normalsArray, indicesArray));
            }
        }
    }


    private static int findMaxVertexIndex(List<List<int[]>> faces) {
        int max = 0;
        for (List<int[]> face : faces) {
            for (int[] indices : face) {
                for (int index : indices) {
                    if (index > max) {
                        max = index;
                    }
                }
            }
        }
        return max;
    }
}
