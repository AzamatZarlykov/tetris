package project.tetris.model.menu;

public class Highscore {
    private String username;
    private int score;
    private int id;

    public Highscore(String username, int score, int id) {
        this.username = username;
        this.score = score;
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String value) {
        this.username = value;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int value) {
        this.score = value;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int value) {
        this.id = value;
    }
}
