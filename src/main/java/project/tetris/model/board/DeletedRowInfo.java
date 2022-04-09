package project.tetris.model.board;

public record DeletedRowInfo(int rowCount, int totalScore) {

    public int getRowCount() {
        return rowCount;
    }

    public int getTotalScore() {
        return totalScore;
    }
}
