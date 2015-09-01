/**
 * Created by Jiakuan on 8/24/2015.
 */
public class RealMadrid extends Team {
    private final int NW = 0;
    private final int N = 1;
    private final int NE = 2;
    private final int W = 3;
    private final int PLAYER = 4;
    private final int E = 5;
    private final int SW = 6;
    private final int S = 7;
    private final int SE = 8;
    private int BALL = 2;
    private int GOAL = 1;
    private int EMPTY = 0;
    private int BOUNDARY = 3;
    private int WEST_PLAYER = 6;
    private int EAST_PLAYER = 7;
    private int BIGGEST_SIT = 7;

    private int KICK = 9;
    private int DO_NOTHING = 10;
    private int BIGGEST_ACTION = 10;

    private static int[] player_x = new int[5];
    private static int[] player_y = new int[5];
    private static boolean firstTime = true;
    private static int[] haveBall = new int[5];
    private static boolean offensive = false;



    @Override
    public int player1(int[] local_area, int ball_direction, int x, int y) {
        if (local_area[N] == BALL) {
            return NE;
        }
        if (local_area[NE] == BALL) {
            return(E);
        }
        if (local_area[E] == BALL) {
            return(SE);
        }
        if (local_area[SE] == BALL) {
            return(E);
        }
        if (local_area[S] == BALL) {
            return(SE);
        }

        if (local_area[SW] == BALL) {
            return(KICK);
        }
        if (local_area[W] == BALL) {
            return(KICK);
        }
        if (local_area[NW] == BALL) {
            return(KICK);
        }

        return ball_direction;

    }

    @Override
    public int player2(int[] local_area, int ball_direction, int x, int y) {
        return player1(local_area, ball_direction, x, y);
    }

    @Override
    public int player3(int[] local_area, int ball_direction, int x, int y) {
        return player1(local_area, ball_direction, x, y);
    }

    @Override
    public int player4(int[] local_area, int ball_direction, int x, int y) {
        return player1(local_area, ball_direction, x, y);
    }

    @Override
    public String teamName() {
        return "Real Madrid FC.";
    }

    @Override
    public void initializePoint() {

    }

    @Override
    public void wonPoint() {

    }

    @Override
    public void lostPoint() {

    }

    @Override
    public void initializeGame() {

    }

    @Override
    public void gameOver() {

    }
}
