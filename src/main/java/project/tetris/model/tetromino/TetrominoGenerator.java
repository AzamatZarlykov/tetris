package project.tetris.model.tetromino;

import project.tetris.model.helper.Position;

import java.util.*;

/**
 * <code>TetrominoGenerator</code> is responsible for generating random tetromino.
 * <p>
 *  It is used to generate current and next tetrominos
 *
 * @author Azamat Zarlykov
 */
public class TetrominoGenerator {
    /**
     * Random used to get random tetromino from the list
     */
    private final Random rand;
    /**
     * Container for all types of tetrominos
     */
    private final List<Tetromino> tetrominoList;
    /**
     * Container that stores random tetrominos
     */
    private Deque<Tetromino> nextTetromino;
    /**
     * Object that is used to carry information about current and next tetrominos
     */
    private TetrominoInformation currentTetrominoInfo;

    /**
     * Instantiates this class by adding all types of tetrominos to <code>tetrominoList</code>, selects 2 random
     * tetrominos and returns information about current tetromino
     */
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

    /**
     * Generates current tetromino information: pos, shape and if passed the saved tetromino
     *
     * @param s saved tetromino or null
     */
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

    /**
     * @return next tetromino
     */
    public Tetromino getNextTetromino() {
        return nextTetromino.peek();
    }

    /**
     * This method exchanges the saved with next tetromino
     *
     * @param saved saved tetromino to substitute with next tetromino
     */
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

    /**
     * Gets the tetromino from the random list and returns back to a called
     *
     * Whenever the random contained is less than or equal to 1, we generate a next random tetromino
     *
     * @return next falling tetromino from the top of the board
     */
    private Tetromino getTetromino() {
        if (nextTetromino.size() <= 1) {
            nextTetromino.add(tetrominoList.get(
                    rand.nextInt(tetrominoList.size())
            ));
        }
        return nextTetromino.poll();
    }

    /**
     * @return current tetromino information
     */
    public TetrominoInformation getCurrentTetrominoInfo() {
        return currentTetrominoInfo;
    }
}
