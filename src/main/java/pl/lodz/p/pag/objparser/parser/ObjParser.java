package pl.lodz.p.pag.objparser.parser;

import pl.lodz.p.pag.objparser.file.FileUtility;
import pl.lodz.p.pag.objparser.models.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by piotr on 14.05.2016.
 */
public class ObjParser {
    List<Model> models = new ArrayList<>();

    public ObjParser(FileUtility fileUtility) {
        fileUtility.getObjFiles().stream()
                .forEach(objFile -> addNewModel(ObjParserUtility.parseModel(objFile)));
    }

    private void addNewModel(Model model) {
        models.add(model);
    }

    public List<Model> getModels() {
        return models;
    }

    public void setModels(List<Model> models) {
        this.models = models;
    }
}
