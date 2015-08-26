/**
 * Created by Jiakuan on 8/24/2015.
 */
public class Barcelona extends Team {
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
    private static int[] ballInFront = new int[5];
    private static boolean offensive = false;



    public void determineOffensive(int myId, int ball_direction) {
        int i;
        ballInFront[myId] = 0;
        if ((ball_direction == W) || (ball_direction == NW)||
                (ball_direction == SW) || (ball_direction == N) || (ball_direction == S)) {
            ballInFront[myId] = 1;
        }
        else {
            ballInFront[myId] = 0;
        }

        offensive = true;

        for (i = 1; i <= 4; i++) {
            if (ballInFront[i] == 0) {
                offensive = false;
            }
        }
    }

    @Override
    public int player1(int[] localArea, int ball_direction, int x, int y) {
        int i;
        int myId, partnerId;

        myId = 1;
        partnerId = 2;

        player_x[myId] = x;
        player_y[myId] = y;

        if (firstTime) {
            firstTime = false;
            for(i = 0; i <= 4; i++) {
                player_x[i] = 0;
                player_y[i] = 0;
                ballInFront[i] = 0;
            }
        }

        determineOffensive(myId, ball_direction);

        if (localArea[N] == BALL) {
            return NE;
        }
        if (localArea[NE] == BALL) {
            return E;
        }
        if (localArea[E] == BALL) {
            return SE;
        }
        if (localArea[SE] == BALL) {
            return E;
        }
        if (localArea[S] == BALL) {
            return SE;
        }
        if (localArea[SW] == BALL) {
            return KICK;
        }
        if (localArea[W] == BALL) {
            return KICK;
        }
        if (localArea[NW] == BALL) {
            return KICK;
        }

        if ((ball_direction == S) && (!offensive)) {
            return SE;
        }
        if ((ball_direction == N) && (!offensive)) {
            return NE;
        }
        if (!offensive) {
            return ball_direction;
        }

        if (localArea[N] == BOUNDARY) {
            return ball_direction;
        }
        if (localArea[S] == BOUNDARY) {
            return ball_direction;
        }

        if ((y >= (player_y[partnerId] - 7)) && (ball_direction == N)) {
            return N;
        }
        if ((y >= (player_y[partnerId] - 7)) && (ball_direction == NE)) {
            return N;
        }
        if ((y >= (player_y[partnerId] - 7)) && (ball_direction == E)) {
            return N;
        }
        if ((y >= (player_y[partnerId] - 7)) && (ball_direction == E)) {
            return N;
        }
        if ((y >= (player_y[partnerId] - 7)) && (ball_direction == E)) {
            return N;
        }
        if ((y >= (player_y[partnerId] - 7)) && (ball_direction == W)) {
            return N;
        }
        if ((y >= (player_y[partnerId] - 7)) && (ball_direction == W)) {
            return N;
        }
        if ((y >= (player_y[partnerId] - 7)) && (ball_direction == NW)) {
            return N;
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

        determineOffensive(myId, ball_direction);

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
            return KICK;
        }
        if (local_area[W] == BALL) {
            return KICK;
        }
        if (local_area[NW] == BALL) {
            return KICK;
        }

        if ((ball_direction == W) && (offensive) && (local_area[NW] == EMPTY)) {
            return NW;
        }

        if ((ball_direction == S) && (!offensive)) {
            return SE;
        }
        if ((ball_direction == N) && (!offensive)) {
            return NE;
        }

        return ball_direction;
    }

    @Override
    public int player3(int[] local_area, int ball_direction, int x, int y) {
        int i;
        int myId, partnerId;
        myId = 3;
        partnerId = 2;

        player_x[myId] = x;
        player_y[myId] = y;

        determineOffensive(myId, ball_direction);

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
            return KICK;
        }
        if (local_area[W] == BALL) {
            return KICK;
        }
        if (local_area[NW] == BALL) {
            return KICK;
        }

        if ((ball_direction == W) && (offensive) && (local_area[SW] == EMPTY)) {
            return SW;
        }
        if ((ball_direction == S) && (!offensive)) {
            return SE;
        }
        if ((ball_direction == N) && (!offensive)) {
            return NE;
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

        determineOffensive(myId, ball_direction);

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
            return KICK;
        }
        if (local_area[W] == BALL) {
            return KICK;
        }
        if (local_area[NW] == BALL) {
            return KICK;
        }

        if (local_area[N] == BOUNDARY) {
            return ball_direction;
        }
        if (local_area[S] == BOUNDARY) {
            return ball_direction;
        }

        if ((ball_direction == S) && (!offensive)) {
            return SE;
        }
        if ((ball_direction == N) && (!offensive)) {
            return NE;
        }
        if (!offensive) {
            return ball_direction;
        }

        if ((y <= player_y[3]+7)&&(ball_direction == N)) {
            return(E);
        }
        if ((y <= player_y[3]+7)&&(ball_direction == NE)) {
            return(E);
        }
        if ((y <= player_y[3]+7)&&(ball_direction == E)) {
            return(E);
        }
        if ((y <= player_y[3]+7)&&(ball_direction == SE)) {
            return(SE);
        }
        if ((y <= player_y[3]+7)&&(ball_direction == S)) {
            return(S);
        }
        if ((y <= player_y[3]+7)&&(ball_direction == SW)) {
            return(W);
        }
        if ((y <= player_y[3]+7)&&(ball_direction == W)) {
            return(W);
        }
        if ((y <= player_y[3]+7)&&(ball_direction == NW)) {
            return(W);
        }

        return ball_direction;
    }

    @Override
    public String teamName() {
        return "FC. Barcelona";
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
