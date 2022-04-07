package project.tetris.model.helper;

public record Position(int x, int y) {

    public int getXPos() {
        return x;
    }

    public int getYPos() {
        return y;
    }

    @Override
    public String toString() {
        return "X: " + x + " Y: " + y;
    }
}
