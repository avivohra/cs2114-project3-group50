public class AppleDemo {
    public static void main(String[] args) {
        SnakeGame game = new SnakeGame(12345L);
        Position apple = game.getApplePosition();
        System.out.println(apple.getRow() + "," + apple.getColumn());
    }
}