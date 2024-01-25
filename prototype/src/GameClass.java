import java.awt.*;
import java.util.Random;
import java.util.logging.Logger;

public class GameClass { // 게임 클래스
    private final int width; // 가로
    private final int height; // 세로
    private final String type; // 타입

    public DbClass start(){
        String result = "";

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++)
                result += "□";
            result += "\n";
        }

        Logger.getGlobal().info("\n" + result);

        return new DbClass(this, null);
    }

    public DbClass process(DbClass dbClass){
        GameClass gameClass = dbClass.getGameClass();

        // 보드 생성
        String result = "";

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++)
                result += "□";
            result += "\n";
        }

        PlayerClass[] currentDB = dbClass.getPlayerClasses();

        if (currentDB != null) {
            // 기존 플레이어 화면에 출력
            for(int i = 0; i < currentDB.length; i++) {
                PlayerClass playerClass = currentDB[i];

                playerClass.setTime(playerClass.getTime() - 1);

                // 시간이 0초 이하인 플레이어 삭제
                if (playerClass.getTime() <= 0)
                    continue;

                // 플레이어 이동 (1: 상, 2: 하, 3: 좌, 4: 우)
                Random random = new Random();

                boolean[] direction = new boolean[4];

                if (playerClass.getY() - 1 >= 0 && result.charAt((playerClass.getY() - 1) * (width + 1) + playerClass.getX()) == '□')
                    direction[0] = true;
                if (playerClass.getY() + 1 < height && result.charAt((playerClass.getY() + 1) * (width + 1) + playerClass.getX()) == '□')
                    direction[1] = true;
                if (playerClass.getX() - 1 >= 0 && result.charAt(playerClass.getY() * (width + 1) + playerClass.getX() - 1) == '□')
                    direction[2] = true;
                if (playerClass.getX() + 1 < width && result.charAt(playerClass.getY() * (width + 1) + playerClass.getX() + 1) == '□')
                    direction[3] = true;

                int moveDirection = -1;
                do {
                    moveDirection = random.nextInt(4);
                } while (!direction[moveDirection]);

                switch (moveDirection) {
                    case 0:
                        playerClass.setY(playerClass.getY() - 1);
                        break;
                    case 1:
                        playerClass.setY(playerClass.getY() + 1);
                        break;
                    case 2:
                        playerClass.setX(playerClass.getX() - 1);
                        break;
                    case 3:
                        playerClass.setX(playerClass.getX() + 1);
                        break;
                    default:
                        break;
                }

                result = result.substring(0, playerClass.getY() * (width + 1) + playerClass.getX()) + playerClass.getName() + result.substring(playerClass.getY() * (width + 1) + playerClass.getX() + 1);
            }
        }

        // 플레이어 추가
        PlayerClass playerClass = gameClass.add(result);
        result = result.substring(0, playerClass.getY() * (width + 1) + playerClass.getX()) + playerClass.getName() + result.substring(playerClass.getY() * (width + 1) + playerClass.getX() + 1);

        // 화면에 출력
        Logger.getGlobal().info("\n" + result);


        // DB에 저장
        if (dbClass.getPlayerClasses() == null)
            return new DbClass(this, new PlayerClass[]{playerClass});
        else {
            PlayerClass[] playerClasses = new PlayerClass[currentDB.length + 1];

            for(int i = 0; i < dbClass.getPlayerClasses().length; i++)
                playerClasses[i] = currentDB[i];

            playerClasses[dbClass.getPlayerClasses().length] = playerClass;

            return new DbClass(this, playerClasses);
        }
    }

    public PlayerClass add(String result) {
        Random random = new Random();
        int x; int y;

        while(true) {
            x = random.nextInt(width);
            y = random.nextInt(height);

            if (result.charAt(y * (width + 1) + x) == '□')
                break;
        }

        return new PlayerClass((char) (random.nextInt(26) + 65), 10, x, y);
    }

    public GameClass(int width, int height, String type) {
        this.width = width;
        this.height = height;
        this.type = type;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getType() {
        return type;
    }
}
