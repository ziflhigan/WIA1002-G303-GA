import java.io.Serializable;

public class GameState implements Serializable{
    public char[][] board;
    public int numMoves;
    public int round;
    public int playerMark;
    public int engineMark;
    public PlayerAccount playerAccount;

    private static final long serialVersionUID = 1L;
    private String gameVersion;

    public GameState(String gameVersion, char[][] board, int numMoves, int round, int playerMark, int engineMark, PlayerAccount playerAccount) {
        this.gameVersion = gameVersion;
        this.board = board;
        this.numMoves = numMoves;
        this.round = round;
        this.playerMark = playerMark;
        this.engineMark = engineMark;
        this.playerAccount = playerAccount;
    }

    public String getGameVersion() {
        return gameVersion;
    }
}
