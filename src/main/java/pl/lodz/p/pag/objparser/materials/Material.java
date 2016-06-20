package pl.lodz.p.pag.objparser.materials;

/**
 * Created by piotr on 06.05.2016.
 */
public class Material {
    String materialName;

    String textureFileName;
    int textureVaoId;

    public Material(String materialName, String textureFileName) {
        this.materialName = materialName;
        this.textureFileName = textureFileName;
    }

    public int getTextureVaoId() {
        return textureVaoId;
    }

    public void setTextureVaoId(int textureVaoId) {
        this.textureVaoId = textureVaoId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getTextureFileName() {
        return textureFileName;
    }

    public void setTextureFileName(String textureFileName) {
        this.textureFileName = textureFileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Material)) return false;

        Material material = (Material) o;

        if (!getMaterialName().equals(material.getMaterialName())) return false;
        return getTextureFileName().equals(material.getTextureFileName());

    }

    @Override
    public int hashCode() {
        int result = getMaterialName().hashCode();
        result = 31 * result + getTextureFileName().hashCode();
        return result;
    }
}
