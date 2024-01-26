import java.util.Objects;
import java.util.Random;

public class GameClass { // 게임 클래스
    private final int width; // 가로
    private final int height; // 세로
    private final String type; // 타입
    private Random random = new Random();

    public DbClass start(){
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++)
                result.append("□");
            result.append("\n");
        }

        return new DbClass(this, null, "");
    }

    public DbClass process(DbClass dbClass) {
        GameClass gameClass = dbClass.gameClass();

        // 보드 생성
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < height; i++) {
            result.append("□".repeat(Math.max(0, width)));
            result.append("\n");
        }

        PlayerClass[] currentDB = dbClass.playerClasses();

        if (currentDB != null) {
            // 기존 플레이어 화면에 출력
            for (PlayerClass playerClass : currentDB) {
                playerClass.setTime(playerClass.getTime() - 1);

                // 시간이 0초 이하인 플레이어 삭제
                if (playerClass.getTime() <= 0)
                    continue;

                // 플레이어 이동 (1: 상, 2: 하, 3: 좌, 4: 우)
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
                    case 0 -> playerClass.setY(playerClass.getY() - 1);
                    case 1 -> playerClass.setY(playerClass.getY() + 1);
                    case 2 -> playerClass.setX(playerClass.getX() - 1);
                    case 3 -> playerClass.setX(playerClass.getX() + 1);
                    default -> {
                    }
                }

                result = new StringBuilder(result.substring(0, playerClass.getY() * (width + 1) + playerClass.getX()) + playerClass.getName() + result.substring(playerClass.getY() * (width + 1) + playerClass.getX() + 1));
            }
        }

        // 플레이어 추가
        PlayerClass playerClass = gameClass.add(result.toString(), currentDB);
        result = new StringBuilder(result.substring(0, playerClass.getY() * (width + 1) + playerClass.getX()) + playerClass.getName() + result.substring(playerClass.getY() * (width + 1) + playerClass.getX() + 1));

        // DB에 저장
        if (dbClass.playerClasses() == null)
            return new DbClass(this, new PlayerClass[]{playerClass}, result.toString());
        else {
            PlayerClass[] playerClasses = new PlayerClass[Objects.requireNonNull(currentDB).length + 1];

            for(int i = 0; i < dbClass.playerClasses().length; i++)
                playerClasses[i] = currentDB[i];

            playerClasses[dbClass.playerClasses().length] = playerClass;

            return new DbClass(this, playerClasses, result.toString());
        }
    }

    public PlayerClass add(String result, PlayerClass[] playerClasses) { // 플레이어 추가 (기존 방식)
        int[] xy = getXY(result);
        int[] currentPlayerName = randomName(playerClasses);
        char name = (char) (random.nextInt(26) + 65);

        for (int i = 0; i < currentPlayerName.length; i++) {
            if (name == currentPlayerName[i]){
                name = (char) (random.nextInt(26) + 65);
                i = 0;
            }
        }

        return new PlayerClass(name, 10, xy[0], xy[1]);
    }

    private int[] getXY(String result){
        int x; int y;

        do {
            x = random.nextInt(width);
            y = random.nextInt(height);

        } while (result.charAt(y * (width + 1) + x) != '□');

        return new int[]{x, y};
    }

    private int[] randomName(PlayerClass[] playerClasses){
        int[] currentPlayerName = new int[10 - 1];

        if (playerClasses != null){
            int count = 0;

            for (PlayerClass playerClass : playerClasses) {
                if (playerClass.getTime() > 0) {
                    currentPlayerName[count] = playerClass.getName();
                    count++;
                }
            }
        }

        return currentPlayerName;
    }

    public GameClass(int width, int height, String type) {
        this.width = width;
        this.height = height;
        this.type = type;
    }
}