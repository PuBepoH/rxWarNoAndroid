package presenter;

public class FieldState {
    private   int xSize, ySize;
    private int[][] field;
    private boolean[][] visible;

    public FieldState(int xSize, int maxY, int[][] field, boolean[][] visible) {
        this.xSize = xSize;
        this.ySize = maxY;
        this.field =field.clone();
        this.visible=visible.clone();
    }

    public int getXSize() {
        return xSize;
    }

    public int getYSize() {
        return ySize;
    }

    public int getColor(int x, int y) {
        return field[x][y];
    }

    public boolean isVisible(int x, int y) {
        return visible[x][y];
    }
}
