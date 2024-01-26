public class PlayerClass implements Cloneable { // 플레이어 클래스
    private final char name; // 이름
    private int time; // 시간
    private int x; // x좌표
    private int y; // y좌표

    @Override
    public PlayerClass clone()
            throws CloneNotSupportedException { // 프로토타입 패턴
        return (PlayerClass) super.clone();
    }

    public PlayerClass(char name, int time, int x, int y) {
        this.name = name;
        this.time = time;
        this.x = x;
        this.y = y;
    }

    public char getName() {
        return name;
    }

    public int getTime() {
        return time;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
