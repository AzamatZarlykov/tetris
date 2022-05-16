package project.tetris.model.menu;

/**
 * <code>Highscore</code> class stores the information about the highest player's score to be displayed
 *
 * @author Azamat Zarlykov
 */
public class Highscore {
    /**
     * username of the player
     */
    private String username;

    /**
     * score of the player
     */
    private int score;
    /**
     * id of the player
     */
    private int id;

    /**
     * <code>Highscore</code> constructor that stores the id and the score
     * @param score int score to be passed
     * @param id int id to be passed
     */
    public Highscore(String username, int score, int id) {
        this.username = username;
        this.score = score;
        this.id = id;
    }

    /**
     * @return the username of the player
     */
    public String getUsername() {
        return this.username;
    }


    /**
     * @param value username of the player
     */
    public void setUsername(String value) {
        this.username = value;
    }


    /**
     * @return the score of the player that is greater or equal to 0
     */
    public int getScore() {
        return this.score;
    }


    /**
     * @param value score to set (>= 0)
     */
    public void setScore(int value) {
        this.score = value;
    }

    /**
     * @return id of the player
     */
    public int getId() {
        return this.id;
    }


    /**
     * @param value sets the given id
     */
    public void setId(int value) {
        this.id = value;
    }
}
