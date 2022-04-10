package project.tetris.model.tetromino;

import project.tetris.model.helper.Position;
import project.tetris.model.helper.TetrominoInformation;

import java.util.*;

public class TetrominoGenerator {
    private final Random rand;
    private final List<Tetromino> tetrominoList;
    private final Deque<Tetromino> nextTetromino;

    private TetrominoInformation currentTetrominoInfo;

    public TetrominoGenerator() {
        rand = new Random();

        this.tetrominoList = new ArrayList<>();
        this.nextTetromino = new ArrayDeque<>();

        this.tetrominoList.add(new ITetromino());
        this.tetrominoList.add(new JTetromino());
        this.tetrominoList.add(new LTetromino());
        this.tetrominoList.add(new OTetromino());
        this.tetrominoList.add(new STetromino());
        this.tetrominoList.add(new TTetromino());
        this.tetrominoList.add(new ZTetromino());

        nextTetromino.add(tetrominoList.get(
                rand.nextInt(tetrominoList.size())
        ));

        nextTetromino.add(tetrominoList.get(
                rand.nextInt(tetrominoList.size())
        ));

        generateNewTetromino();
    }

    public void generateNewTetromino() {
        int tetrominoShape = 0;
        Tetromino newT = getTetromino();
        Position spawn = new Position(5, 0);

        currentTetrominoInfo = new TetrominoInformation(
                newT, tetrominoShape,
                spawn.getXPos(), spawn.getYPos(),
                getNextTetromino().getStructure()
        );
    }

    public Tetromino getNextTetromino() {
        return nextTetromino.peek();
    }

    public Tetromino getTetromino() {
        if (nextTetromino.size() <= 1) {
            nextTetromino.add(tetrominoList.get(
                    rand.nextInt(tetrominoList.size())
            ));
        }
        return nextTetromino.poll();
    }

    public TetrominoInformation getCurrentTetrominoInfo() {
        return currentTetrominoInfo;
    }
}
