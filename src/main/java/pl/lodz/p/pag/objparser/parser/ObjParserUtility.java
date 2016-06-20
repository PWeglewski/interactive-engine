package pl.lodz.p.pag.objparser.parser;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import pl.lodz.p.pag.objparser.file.ObjFile;
import pl.lodz.p.pag.objparser.models.Group;
import pl.lodz.p.pag.objparser.models.Model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by piotr on 14.05.2016.
 */
public class ObjParserUtility {
    public static final String MATERIAL_LIBRARY = "mtllib ";
    public static final String VERTEX = "v ";
    public static final String TEXTURE_COORDINATES = "vt ";
    public static final String NORMAL = "vn ";
    public static final String GROUP = "g ";
    public static final String FACE = "f ";
    public static final String USE_MATERIAL = "usemtl ";
    public static final String SMOOTH_SHADING = "s ";

    public static Model parseModel(ObjFile objFile) {
        Model model = new Model();

        try (BufferedReader bufferedReader = new BufferedReader(
                new FileReader(
                        objFile.getFile()
                )
        )) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith(GROUP)) {
                    Group group = new Group(line.split(" ")[1]);
                    model.getGroups().add(group);
                    while ((line = bufferedReader.readLine()) != null) {
                        if (line.startsWith(USE_MATERIAL)) {
                            String[] splittedLine = line.split(" ");
                            group.setMaterial(model.getMaterialLibrary().getMaterialList().get(splittedLine[1]));
                        } else if (line.startsWith(FACE)) {
                            String[] faces = getFaces(line);
                            List<int[]> indicesList = new ArrayList<>();
                            for (String vertex : faces) {
                                indicesList.add(getIndices(vertex));
                            }
                            group.getFaces().add(indicesList);
                        } else if (line.startsWith(SMOOTH_SHADING)) {
                            // not implemented yet
                        } else {
                            break;
                        }
                    }
                }
                if (line.startsWith(MATERIAL_LIBRARY)) {
                    model.setMaterialLibrary(
                            MaterialLibraryParser.parseMaterialLibrary(line, objFile)
                    );
                } else if (line.startsWith(VERTEX)) {
                    String[] splittedLine = line.split(" ");
                    model.getVertices().add(
                            new Vector3f(
                                    Float.parseFloat(splittedLine[1]),
                                    Float.parseFloat(splittedLine[2]),
                                    Float.parseFloat(splittedLine[3])
                            )
                    );
                } else if (line.startsWith(TEXTURE_COORDINATES)) {
                    String[] splittedLine = line.split(" ");
                    model.getTextureCoordinates().add(
                            new Vector2f(
                                    Float.parseFloat(splittedLine[1]),
                                    Float.parseFloat(splittedLine[2])
                            )
                    );
                } else if (line.startsWith(NORMAL)) {
                    String[] splittedLine = line.split(" ");
                    model.getNormals().add(
                            new Vector3f(
                                    Float.parseFloat(splittedLine[1]),
                                    Float.parseFloat(splittedLine[2]),
                                    Float.parseFloat(splittedLine[3])
                            )
                    );
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return model;
    }

    private static String[] getFaces(String line) {
        String[] splittedLine = line.split(" ");
        String[] faces = Arrays.copyOfRange(splittedLine, 1, splittedLine.length);
        return faces;
    }

    private static int[] getIndices(String vertex) {
        String[] stringIndices = vertex.split("/");
        int[] integerIndices = Arrays.stream(stringIndices)
                .mapToInt(Integer::parseInt)
                .toArray();
        return integerIndices;
    }
}
