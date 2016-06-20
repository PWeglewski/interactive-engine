package pl.lodz.p.pag.objparser.file;

import java.io.File;

/**
 * Created by piotr on 14.05.2016.
 */
public class ObjFile {
    private File file;

    private String contextRoot;
    private String fileName;

    public ObjFile(File file) {
        this.file = file;
        dividePath(file);
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getContextRoot() {
        return contextRoot;
    }

    public void setContextRoot(String contextRoot) {
        this.contextRoot = contextRoot;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    private void dividePath(File file) {
        contextRoot = file.getParent();
        fileName = file.getName();
    }
}
