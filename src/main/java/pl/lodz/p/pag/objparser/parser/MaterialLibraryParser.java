package pl.lodz.p.pag.objparser.parser;

import pl.lodz.p.pag.objparser.file.ObjFile;
import pl.lodz.p.pag.objparser.materials.Material;
import pl.lodz.p.pag.objparser.materials.MaterialLibrary;

import java.io.*;

/**
 * Created by piotr on 14.05.2016.
 */
public class MaterialLibraryParser {
    public static final String NEW_MATERIAL = "newmtl";
    public static final String TEXTURE = "map_Kd";

    public static MaterialLibrary parseMaterialLibrary(String materialLibraryLine, ObjFile objFile) {
        String[] splittedLine = materialLibraryLine.split(" ");
        if (!(splittedLine.length > 0)) {
            System.err.println("Invalid material library declaration");
            System.exit(-1);
        }
        String fileName = objFile.getContextRoot() + File.separator + splittedLine[1];
        File file = new File(fileName);
        MaterialLibrary materialLibrary = new MaterialLibrary(file);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith(NEW_MATERIAL)) {
                    String materialName = line.split(" ")[1];
                    Material material;
                    String texturePath;
                    do {
                        line = line.trim();
                        if (line.startsWith(TEXTURE)) {
                            String textureName = line.split(" ")[1];
                            texturePath = file.getParent();
                            texturePath += File.separator + textureName;
                            material = new Material(materialName, texturePath);
                            materialLibrary.getMaterialList().put(materialName, material);
                            break;
                        }
                    } while ((line = bufferedReader.readLine()) != null);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return materialLibrary;
    }
}
