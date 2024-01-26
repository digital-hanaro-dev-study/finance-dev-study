import java.util.logging.Logger;

public class ApiClass {
    public static void main(String[] args) throws InterruptedException {
        GameClass gameClass = new GameClass(10, 10, "normal");

        DbClass dbClass = gameClass.start();

        Logger.getGlobal().info(dbClass.result());

        while (true){
            Thread.sleep(1000);
            dbClass = gameClass.process(dbClass);
            Logger.getGlobal().info("\n" + dbClass.result());
        }
    }
}