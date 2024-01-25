public class DbClass {
    private GameClass gameClass;
    private PlayerClass[] playerClasses;

    public DbClass(GameClass gameClass, PlayerClass[] playerClasses) {
        this.gameClass = gameClass;
        this.playerClasses = playerClasses;
    }

    public GameClass getGameClass() {
        return gameClass;
    }
    public PlayerClass[] getPlayerClasses() {
        return playerClasses;
    }

    public void setGameClass(GameClass gameClass) {
        this.gameClass = gameClass;
    }

    public void setPlayerClasses(PlayerClass[] playerClasses) {
        this.playerClasses = playerClasses;
    }
}
