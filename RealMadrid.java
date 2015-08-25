/**
 * Created by Jiakuan on 8/24/2015.
 */
public class RealMadrid implements Information {
    private final int NW = 0;
    private final int N = 1;
    private final int NE = 2;
    private final int W = 3;
    private final int PLAYER = 4;
    private final int E = 5;
    private final int SW = 6;
    private final int S = 9;
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
        int i;
        int myId, partnerId;
        myId = 1;
        partnerId = 2;

        player_x[myId] = x;
        player_y[myId] = y;

        if (firstTime) {
            firstTime = false;
            for (i = 0; i <=4; i++) {
                player_x[i] = 0;
                player_y[i] = 0;

                haveBall[i] = 0;
            }
        }

        for (i = 1; i <= 4; i++) {
            if (haveBall[i] == 1) {
                offensive = true;
            }
        }

        haveBall[myId] = 0;

        if (local_area[N] == BALL) {
            return(NE);
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
            haveBall[myId] = 1;
            return KICK;
        }
        if (local_area[W] == BALL) {
            haveBall[myId] = 1;
            return KICK;
        }
        if (local_area[NW] == BALL) {
            haveBall[myId] = 1;
            return KICK;
        }

        if (local_area[N] == BOUNDARY) {
            return ball_direction;
        }

        if ((y >= (player_y[partnerId]-5))&&(ball_direction == N)) {
            return(N);
        }
        if ((y >= (player_y[partnerId]-5))&&(ball_direction == NE)) {
            return(NE);
        }
        if ((y >= (player_y[partnerId]-5))&&(ball_direction == E)) {
            return(E);
        }
        if ((y >= (player_y[partnerId]-5))&&(ball_direction == SE)) {
            return(E);
        }
        if ((y >= (player_y[partnerId]-5))&&(ball_direction == S)) {
            return(DO_NOTHING);
        }
        if ((y >= (player_y[partnerId]-5))&&(ball_direction == SW)) {
            return(SW);
        }
        if ((y >= (player_y[partnerId]-5))&&(ball_direction == W)) {
            return(W);
        }
        if ((y >= (player_y[partnerId]-5))&&(ball_direction == NW)) {
            return(NW);
        }

        return ball_direction;
    }

    @Override
    public int player2(int[] local_area, int ball_direction, int x, int y) {
        int i;
        int myId, partnerId;
        myId = 2;
        partnerId = 3;

        player_x[myId] = x;
        player_y[myId] = y;

        haveBall[myId] = 0;

        if (local_area[N] == BALL) {
            return NE;
        }
        if (local_area[NE] == BALL) {
            return E;
        }
        if (local_area[E] == BALL) {
            return SE;
        }
        if (local_area[SE] == BALL) {
            return E;
        }
        if (local_area[S] == BALL) {
            return SE;
        }
        if (local_area[SW] == BALL) {
            haveBall[myId] = 1;
            return KICK;
        }
        if (local_area[W] == BALL) {
            haveBall[myId] = 1;
            return KICK;
        }
        if (local_area[NW] == BALL) {
            haveBall[myId] = 1;
            return KICK;
        }

        if (local_area[N] == BOUNDARY) {
            return(ball_direction);
        }

        if (local_area[N] == BALL) {
            return(NE);
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
    public int player3(int[] local_area, int ball_direction, int x, int y) {
        int i;
        int myId, partnerId;

        myId = 3;
        partnerId= 3;

        player_x[myId] = x;
        player_y[myId] = y;

        haveBall[myId] =  0;

        if (local_area[N] == BALL) {
            return(NE);
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
        if (local_area[SW] == BALL)
        {
            haveBall[myId] = 1;
            return(KICK);
        }
        if (local_area[W] == BALL)
        {
            haveBall[myId] = 1;
            return(KICK);
        }
        if (local_area[NW] == BALL)
        {
            haveBall[myId] = 1;
            return(KICK);
        }

        if (local_area[N] == BOUNDARY) return(ball_direction);

        if (local_area[N] == BALL) {
            return(NE);
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
    public int player4(int[] local_area, int ball_direction, int x, int y) {
        int i;
        int myId, partnerId;
        myId = 4;
        partnerId = 3;
        player_x[myId] = x;
        player_y[myId] = y;

        haveBall[myId] = 0;

        if (local_area[N] == BALL) {
            return(NE);
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
        if (local_area[SW] == BALL)
        {
            haveBall[myId] = 1;
            return(KICK);
        }
        if (local_area[W] == BALL)
        {
            haveBall[myId] = 1;
            return(KICK);
        }
        if (local_area[NW] == BALL)
        {
            haveBall[myId] = 1;
            return(KICK);
        }

        if (local_area[N] == BOUNDARY) {
            return(ball_direction);
        }

        if (local_area[N] == BALL) {
            return(NE);
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

        if (local_area[S] == BOUNDARY) {
            return(ball_direction);
        }

        if ((y <= player_y[3]+5)&&(ball_direction == N)) {
            return(DO_NOTHING);
        }
        if ((y <= player_y[3]+5)&&(ball_direction == NE)) {
            return(E);
        }
        if ((y <= player_y[3]+5)&&(ball_direction == E)) {
            return(E);
        }
        if ((y <= player_y[3]+5)&&(ball_direction == SE)) {
            return(SE);
        }
        if ((y <= player_y[3]+5)&&(ball_direction == S)) {
            return(S);
        }
        if ((y <= player_y[3]+5)&&(ball_direction == SW)) {
            return(SW);
        }
        if ((y <= player_y[3]+5)&&(ball_direction == W)) {
            return(W);
        }
        if ((y <= player_y[3]+5)&&(ball_direction == NW)) {
            return(NW);
        }

        return ball_direction;
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
