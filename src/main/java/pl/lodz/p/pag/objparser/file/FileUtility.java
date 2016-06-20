package pl.lodz.p.pag.objparser.file;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by piotr on 08.05.2016.
 */
public class FileUtility {
    List<ObjFile> objFiles = new ArrayList<>();

    public FileUtility(String[] args) {
        validateArguments(args);
        Arrays.stream(args).forEach(this::addNewFile);
    }

    public List<ObjFile> getObjFiles() {
        return objFiles;
    }

    public void setObjFiles(List<ObjFile> objFiles) {
        this.objFiles = objFiles;
    }

    private void validateArguments(String[] args) {
        if (args.length < 1) {
            System.err.println("At least one argument expected");
            System.exit(-1);
        }
    }

    private void addNewFile(String fileName) {
        File file = new File(fileName);
        ObjFile objFile = new ObjFile(file);
        objFiles.add(objFile);
    }
}
