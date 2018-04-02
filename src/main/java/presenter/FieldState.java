package presenter;

import io.reactivex.subjects.BehaviorSubject;

public class FieldState {
    private   byte x,y;
    private int[][] field;

    public FieldState(byte x, byte y, int[][] field) {
        this.x = x;
        this.y = y;
        this.field = field;
    }

    public byte getX() {
        return x;
    }

    public byte getY() {
        return y;
    }

    public int[][] getField() {
        return field;
    }
}
