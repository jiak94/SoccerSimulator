/**
 * Created by Jiakuan on 8/24/2015.
 */
public interface Information {
    public void determineOffensive(int myId, int ball_direction);
    public int player1(int[] local_area, int ball_direction, int x, int y);
    public int player2(int[] local_area, int ball_direction, int x, int y);
    public int player3(int[] local_area, int ball_direction, int x, int y);
    public int player4(int[] local_area, int ball_direction, int x, int y);
    public String teamName();
    public void initializePoint();
    public void wonPoint();
    public void lostPoint();
    public void initializeGame();
    public void gameOver();
}
