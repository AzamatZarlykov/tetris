package project.tetris.model.tetromino;

import project.tetris.model.helper.Position;

import java.util.*;

public class TetrominoGenerator {
    private final Random rand;
    private final List<Tetromino> tetrominoList;
    private Deque<Tetromino> nextTetromino;

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

        generateNewTetromino(null);
    }

    public void generateNewTetromino(Tetromino s) {
        int tetrominoShape = 0;
        Tetromino newT = getTetromino();
        Position spawn = new Position(5, 0);
        Tetromino saved;

        if (s == null) {
            saved = tetrominoList.get(rand.nextInt(tetrominoList.size()));
        } else {
            saved = s;
        }

        currentTetrominoInfo = new TetrominoInformation(
                newT, tetrominoShape,
                spawn.getXPos(), spawn.getYPos(),
                getNextTetromino(),
                saved
        );
    }

    public Tetromino getNextTetromino() {
        return nextTetromino.peek();
    }

    public void changeNext(Tetromino saved) {
        // remove the next tetromino
        nextTetromino.poll();
        // add the saved tetromino and the remaining
        Deque<Tetromino> tempNextTet = new ArrayDeque<>();
        tempNextTet.add(saved);
        while (!nextTetromino.isEmpty()) {
            tempNextTet.add(nextTetromino.poll());
        }
        nextTetromino = tempNextTet;
    }

    private Tetromino getTetromino() {
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
