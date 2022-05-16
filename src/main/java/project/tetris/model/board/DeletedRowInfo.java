package project.tetris.model.board;

/**
 * This record holds the information about the deleted row
 *
 * @author Azamat Zarlykov
 */
public record DeletedRowInfo(int rowCount, int totalScore) {

    /**
     * @return positive number that represents the rows deleted
     */
    public int getRowCount() {
        return rowCount;
    }

    /**
     * @return positive number that represents the score earned from removed lines
     */
    public int getTotalScore() {
        return totalScore;
    }
}
