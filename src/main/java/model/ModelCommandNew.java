package model;

public class ModelCommandNew extends ModelCommand {
    private ModelConfiguration modelConfiguration;
    private int xSize,ySize;

    public ModelCommandNew(ModelConfiguration modelConfiguration , int x, int y) {
        this.modelConfiguration = modelConfiguration;
        this.xSize=x;
        this.ySize=y;
    }

    public ModelConfiguration getModelConfiguration() {
        return modelConfiguration;
    }

    public int getXSize() {
        return xSize; }

    public int getYSize() {
        return ySize;
    }
}
