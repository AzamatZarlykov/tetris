package project.tetris.model.menu;

public class User {
    private final String username;
    private final int score;

    public User(String name, int score) {
        this.username = name;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "Username: " + username + " Score: " + score;
    }
}
