package Backend.ArchiveSystem;

public class GameSettingStorage {

    private int gameMode;
    private final int gameDifficulty;
    private final int mapSize;

    public GameSettingStorage(int gameMode, int gameDifficulty, int mapSize) {
        this.gameMode = gameMode;
        this.gameDifficulty = gameDifficulty;
        this.mapSize = mapSize;
    }

    public void setGameMode(int gameMode) {
        this.gameMode = gameMode;
    }

    public String inputForFile() {
        return gameMode + "\n" + gameDifficulty + "\n" + mapSize;
    }
}
