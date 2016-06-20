package pl.lodz.p.pag.objparser.materials;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by piotr on 06.05.2016.
 */
public class MaterialLibrary {
    File file;
    Map<String, Material> materialList = new HashMap<>();

    public MaterialLibrary(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Map<String, Material> getMaterialList() {
        return materialList;
    }

    public void setMaterialList(Map<String, Material> materialList) {
        this.materialList = materialList;
    }
}
