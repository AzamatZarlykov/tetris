package project.tetris.model.menu;

/**
 * This class is used to store information of players: username and score
 *
 * @author  Azamat Zarlykov
 */
public class User {
    /**
     * Username of the player
     */
    private final String username;

    /**
     * score of the player
     */
    private final int score;

    /**
     * @param name name to be passed
     * @param score score to be passed
     */
    public User(String name, int score) {
        this.username = name;
        this.score = score;
    }

    /**
     * @return score of the player (>= 0)
     */
    public int getScore() {
        return score;
    }

    /**
     * @return non-empty username of the player
     */
    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "Username: " + username + " Score: " + score;
    }
}
