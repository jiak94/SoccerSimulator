import com.sun.tools.javac.comp.Todo;
import com.sun.xml.internal.bind.v2.TODO;

/**
 * Created by Jiakuan on 8/24/2015.
 */
public class SoccerServer {
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

    private final double SQR2 = Math.sqrt(2);

    private int KICK = 9;
    private int DO_NOTHING = 10;
    private int BIGGEST_ACTION = 10;

    public static final int MAX_X = 80;
    public static final int MAX_Y = 23;
    public static final int TIME_LIMIT = 20;
    public static final int TIME_OUT = 3000;
    public static final int KICK_DIST = 10;

    public String eastTeamName;
    public String westTeamName;

    private int ball_x;
    private int ball_y;
    private int[] player_x = new int[8];
    private int[] player_y = new int[8];

    private int[][] field = new int[MAX_X][MAX_Y];

    private boolean display = true;
    private int points = 7;


    public void play() {
        String ch, c;
        int i, j, k, cur_x, cur_y, backwards_cur_x, backwards_cur_y;
        int point_over = 0;
        int game_over = 0;
        int count;
        int player_nearby = 0;
        int overall_count = 0;
        int[] local_field = new int[9];
        int[] local_backwards_field = new int[9];
        int[] local_ball_field = new int[9];
        int ball_direction = N;
        int backwards_ball_direction = N;
        int player_move = N;
        int kick_direction = -1;
        int kick_steps = 0;
        double temp_angle = 0;
        double path = 0;
        int cur = 0;
        int east_score = 0;
        int west_score = 0;

        int slow = 2;
        int last_ball_x;
        int last_ball_y = 0;

        while (game_over != -1) {
            init();

            reportScore(west_score, east_score);
            point_over = 0;
            count = 0;
            overall_count = 0;
            kick_direction = -1;
            kick_steps = 0;

            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                continue;
            }

            Eastinitialize_point();
            WESTinitialize_point();

            while (point_over != 1) {

                //Player position
                cur_x = player_x[cur];
                cur_y = player_y[cur];

                /*
	 	         * Note the local field around the player
	 	         */
                k = 0;
                for (j = 0; j < 3; j++) {
                    for (i = 0; i < 3; i++) {
                        local_field[k++] = field[cur_x + i - 1][cur_y + j - 1];
                    }
                }

                /*
                 * Note the local field around the ball
                 */
                k = 0;
                for (j = 0; j < 3; j++) {
                    for (i = 0; i < 3; i++) {
                        local_ball_field[k++] = field[ball_x + i - 1][ball_y + j - 1];
                    }
                }

                /*
                 * Figure out heading to ball
                 */
                if ((ball_x == cur_x) && (ball_y == cur_y)) {
                    temp_angle = 0;
                }
                else {
                    temp_angle = Math.atan2((ball_x - cur_x), (ball_y - cur_y));
                }

                temp_angle += Math.PI;

                temp_angle = 360.0 * temp_angle / (2.0 * Math.PI);
                ball_direction = N;

                if (temp_angle > 22.5+0*45) {
                    ball_direction = NW;
                }
                if (temp_angle > 22.5+1*45) {
                    ball_direction = W;
                }
                if (temp_angle > 22.5+2*45) {
                    ball_direction = SW;
                }
                if (temp_angle > 22.5+3*45) {
                    ball_direction = S;
                }
                if (temp_angle > 22.5+4*45) {
                    ball_direction = SE;
                }
                if (temp_angle > 22.5+5*45) {
                    ball_direction = E;
                }
                if (temp_angle > 22.5+6*45) {
                    ball_direction = NE;
                }
                if (temp_angle > 22.5+7*45) {
                    ball_direction = N;
                }

                /*
                 * Construct backwards sensing for western players.
                 */
                for (i = 0; i <= 8; i++) {
                    local_backwards_field[i] = local_field[8 - i];
                }
                backwards_cur_x = (MAX_X - 1) - cur_x;
                backwards_cur_y = (MAX_Y - 1) - cur_y;


                /*
                 * Find out which way to go. The variable cur
                 * indicates which player we are moving for now.
                 * East players are 0 2 4 6. The local_field and
                 * ball_direction are reversed for west players
                 * so they can think they are going east! Then
                 * their output is reversed again.
                 */
                switch (cur) {
                    case 0:
                        player_move = EASTplayer1(local_field, ball_direction, cur_x, cur_y);
                        break;
                    case 1:
                        player_move = swap_heading(
                                WESTplayer1(local_backwards_field, backwards_ball_direction,
                                        backwards_cur_x, backwards_cur_y));
                        break;
                    case 2:
                        player_move = EASTplayer2(local_field, ball_direction, cur_x, cur_y);
                        break;
                    case 3:
                        player_move = swap_heading(
                                WESTplayer2(local_backwards_field, backwards_ball_direction,
                                        backwards_cur_x, backwards_cur_y));
                        break;
                    case 4:
                        player_move = EASTplayer3(local_field, ball_direction, cur_x, cur_y);
                        break;
                    case 5:
                        player_move = swap_heading(
                                WESTplayer3(local_backwards_field, backwards_ball_direction,
                                        backwards_cur_x, backwards_cur_y));
                        break;
                    case 6:
                        player_move = EASTplayer4(local_field, ball_direction, cur_x, cur_y);
                        break;
                    case 7:
                        player_move = swap_heading(
                                WESTplayer4(local_backwards_field, backwards_ball_direction,
                                        backwards_cur_x, backwards_cur_y));
                        break;
                }

                /*
                 * Check for move = PlAYER
                 */
                if (player_move == PLAYER) {
                    player_move = DO_NOTHING;
                }

                /*
                 * cHECK FOR kick. If the player wants to kick
                 * set up the kick, then set player_move to be heading in the
                 * direction of the ball
                 */
                if (player_move == KICK) {
                    for (i = 0; i <= 8; i++) {
                        if (local_field[i] == BALL) {
                            kick_direction = i;
                            kick_steps = KICK_DIST;
                            player_move = i;
                            break;
                        }
                    }
                    if (player_move == KICK) {
                        player_move = N;
                    }
                }

                /*
                 * Make sure heading is legal.
                 */
                if ((player_move < 0) || (player_move > 8)) {
                    player_move = DO_NOTHING;
                }

                /*
                 * Move the player in the world
                 */
                //TODO: move the player;

                /*
                 * If the cell we are going to is empty. or
                 * the ball is there and the next cell after that is empty, then...
                 */
                if ((local_field[player_move] == EMPTY) ||
                        ((local_field[player_move] == BALL)&&
                                ((local_ball_field[player_move] == EMPTY)||
                                        (local_ball_field[player_move] == GOAL)))) {
                    switch(player_move)
                    {
                        case N:
                            player_y[cur]--;
                            path++;
                            break;
                        case S:
                            player_y[cur]++;
                            path++;
                            break;
                        case E:
                            player_x[cur]++;
                            path++;
                            break;
                        case W:
                            player_x[cur]--;
                            path++;
                            break;
                        case NE:
                            player_y[cur]--;
                            player_x[cur]++;
                            path += SQR2;
                            break;
                        case NW:
                            player_y[cur]--;
                            player_x[cur]--;
                            path += SQR2;
                            break;
                        case SE:
                            player_y[cur]++;
                            player_x[cur]++;
                            path += SQR2;
                            break;
                        case SW:
                            player_y[cur]++;
                            player_x[cur]--;
                            path += SQR2;
                            break;
                    }
                }

                /*
                 * Mark the field with the player's new position
                 */
                if ((cur % 2) == 0) {
                    field[player_x[cur]][player_y[cur]] = EAST_PLAYER;
                }
                else {
                    field[player_x[cur]][player_y[cur]] = WEST_PLAYER;
                }

                /*
                 * Now move the ball
                 */
                if ((local_field[player_move] == BALL)&&
                        ((local_ball_field[player_move] == EMPTY)||
                                (local_ball_field[player_move] == GOAL))) {

                    field[ball_x][ball_y] = EMPTY;
                    //TODO: MOVE the ball

                    switch (player_move) {
                        case N:
                            ball_y--;
                            break;
                        case S:
                            ball_y++;
                            break;
                        case E:
                            ball_x++;
                            break;
                        case W:
                            ball_x--;
                            break;
                        case NE:
                            ball_y--;
                            ball_x++;
                            break;
                        case NW:
                            ball_y--;
                            ball_x--;
                            break;
                        case SE:
                            ball_y++;
                            ball_x++;
                            break;
                        case SW:
                            ball_y++;
                            ball_x--;
                            break;
                    }
                }

                /*
                 * Place the ball on the field
                 */
                field[ball_x][ball_y] = BALL;

                /*
                 * Now handle the case of KICK
                 */
                if (kick_steps > 0) {
                    k = 0;
                    for (j = 0; j < 3; j++) {
                        for (i = 0; i < 3; i++) {
                            local_ball_field[k++] = field[ball_x + i - 1][ball_y + j - 1];
                        }
                    }
                }
            }

        }
    }
}
