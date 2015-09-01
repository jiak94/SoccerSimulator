import java.util.Random;

/**
 * Created by Jiakuan on 8/24/2015.
 */
public class SoccerServer {
    private final double SQR2 = Math.sqrt(2);

    private final int MAX_X = 80;
    private final int MAX_Y = 23;
    private final int TIME_LIMIT = 20;
    private final int TIME_OUT = 3000;
    private final int KICK_DIST = 10;

    private final double PI = Math.PI;

    private final int EMPTY = 0;
    private final int GOAL = 1;
    private final int BALL = 2;
    private final int BOUNDARY = 3;
    private final int WEST_PLAYER = 6;
    private final int EAST_PLAYER = 7;
    private final int BIGGEST_SIT = 7;

    private final int NW = 0;
    private final int N = 1;
    private final int NE = 2;
    private final int W = 3;
    private final int PLAYER = 4;
    private final int E = 5;
    private final int SW = 6;
    private final int S = 7;
    private final int SE = 8;

    private final int KICK = 9;
    private final int DO_NOTHING = 10;
    private final int BIGGEST_ACTION = 10;


    private int ball_x;
    private int ball_y;
    private int[] player_x = new int[8];
    private int[] player_y = new int[8];

    private int[][] field = new int[MAX_X][MAX_Y];

    private boolean display = true;
    private int points = 7;

    private int opterr;
    private String optarg;

    private Team EAST;
    private Team WEST;

    public SoccerServer() {
        EAST = new Barcelona();
        WEST = new RealMadrid();

        Field.setEastTeam(EAST.teamName());
        Field.setWestTeam(WEST.teamName());
    }

    private void report_score(int west, int east) {

        //west_team.setText(westName);
        Field.setWestScore(Integer.toString(west));
        Field.setEastScore(Integer.toString(east));

    }

    private void replace_ball() {
        Random rand = new Random();
        if (display) {
            //TODO: erase old ball
        }

        do {
            field[ball_x][ball_y] = EMPTY;
            ball_y = rand.nextInt(1000) % 20 + 1;
            //System.out.println(ball_y);
        } while (field[ball_x][ball_y] != EMPTY);

        field[ball_x][ball_y] = BALL;

        if (display) {
            //TODO: paint the ball
        }
    }

    private void nudge_ball() {
        Random rand = new Random();
        int i = 1;
        int temp;

        if (display) {
            //TODO: erase ball
        }

        do {
            temp = ball_y + ((rand.nextInt() / 256) % i) - (i / 2);
            if (temp < 1) {
                temp = 1;
            }
            if (temp > 22) {
                temp = 22;
            }
            i++;
            if (i > 40) {
                i = 40;
            }
        } while ((field[ball_x][temp] != EMPTY) && (field[ball_x][temp] != GOAL));

        field[ball_x][ball_y] = EMPTY;
        ball_y = temp;
        field[ball_x][ball_y] = BALL;

        if (display) {
            //TODO: paint new ball
        }
    }

    private int swap_heading(int heading) {
        switch (heading) {
            case NW:
                return (SE);
            case N:
                return (S);
            case NE:
                return (SW);
            case E:
                return (W);
            case SE:
                return (NW);
            case S:
                return (N);
            case SW:
                return (NE);
            case W:
                return (E);
            case KICK:
                return (KICK);
            case DO_NOTHING:
                return (DO_NOTHING);
            default:
                return (DO_NOTHING);
        }
    }


    private void init() {
        char c;
        int i, j, val, r;

        if (display) {
            //TODO: clear Screen
        }

        /*
         * Clear out the field map.
         */
        for (i = 0; i < MAX_X; i++) {
            for (j = 0; j < MAX_Y; j++) {
                field[i][j] = EMPTY;
            }
        }

        /*
         * location of ball
         */
        try {
            ball_x = 38;
            ball_y = 20;
            replace_ball();
        } catch (Exception e) {

        }

        /*
         * locations of players
         */
        for (i = 0; i <= 6; i += 2) {
            player_x[i] = 46;
            player_y[i] = (i / 2 + 1) * 6 - 4;
            field[player_x[i]][player_y[i]] = EAST_PLAYER;
            //if (display) mvaddch(player_y[i], player_x[i], '<');

            player_x[i + 1] = 30;
            player_y[i + 1] = (i / 2 + 1) * 6 - 4;
            field[player_x[i + 1]][player_y[i + 1]] = WEST_PLAYER;
            //if (display) mvaddch(player_y[i+1], player_x[i+1], '>');
        }


        //TODO: refresh screen
        for (i = 0; i < MAX_X; i++) {
            for (j = 0; j < MAX_Y; j++) {
                if ((i == 0) || (i == (MAX_X - 1))) {
                    field[i][j] = GOAL;
                    //if (display) mvaddch(j, i, '|');
                }
                if ((j == 0) || (j == (MAX_Y - 1))) {
                    field[i][j] = BOUNDARY;
                    //if (display) mvaddch(j, i, '=');
                }
            }
        }


    }


    public void play() {
        char ch, c;
        int i, j, k, cur_x, cur_y, backwards_cur_x, backwards_cur_y;
        int point_over = 0;
        int game_over = 0;
        int count, player_nearby = 0;
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
        int last_ball_x = 0, last_ball_y = 0;


        opterr = 0;

        EAST.initializeGame();
        WEST.initializeGame();


        while (game_over != 1) {
            init();

            if (display) {
                report_score(west_score, east_score);
            }

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

            EAST.initializePoint();
            WEST.initializePoint();

            while (point_over != 1) {

                /*
                 * Player's posit
		         */
                cur_x = player_x[cur];
                cur_y = player_y[cur];

		/*
          * Note the local field around the player
	   	 */
                k = 0;
                for (j = 0; j < 3; j++)
                    for (i = 0; i < 3; i++) {
                        local_field[k++] =
                                field[cur_x + i - 1][cur_y + j - 1];
                    }

                /*
                 * Note the local field around the ball
                 */
                k = 0;
                for (j = 0; j < 3; j++)
                    for (i = 0; i < 3; i++) {
                        local_ball_field[k++] =
                                field[ball_x + i - 1][ball_y + j - 1];
                    }

                /*
                 * Figure out heading to ball
                 */
                if ((ball_x == cur_x) && (ball_y == cur_y))
                    temp_angle = 0;
                else
                    temp_angle = Math.atan2((ball_x - cur_x), (ball_y - cur_y));

                temp_angle += PI;
                temp_angle = 360.0 * temp_angle / (2.0 * PI);
                ball_direction = N;
                if (temp_angle > 22.5 + 0 * 45)
                    ball_direction = NW;
                if (temp_angle > 22.5 + 1 * 45)
                    ball_direction = W;
                if (temp_angle > 22.5 + 2 * 45)
                    ball_direction = SW;
                if (temp_angle > 22.5 + 3 * 45)
                    ball_direction = S;
                if (temp_angle > 22.5 + 4 * 45)
                    ball_direction = SE;
                if (temp_angle > 22.5 + 5 * 45)
                    ball_direction = E;
                if (temp_angle > 22.5 + 6 * 45)
                    ball_direction = NE;
                if (temp_angle > 22.5 + 7 * 45)
                    ball_direction = N;

                /*
                 * Construct backwards sensing for western players.
                 */
                for (i = 0; i <= 8; i++)
                    local_backwards_field[i] = local_field[8 - i];
                backwards_ball_direction = swap_heading(ball_direction);
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
                        player_move =
                                EAST.player1(local_field, ball_direction,
                                        cur_x, cur_y);
                        break;
                    case 1:
                        player_move =
                                swap_heading(
                                        WEST.player1(local_backwards_field,
                                                backwards_ball_direction,
                                                backwards_cur_x, backwards_cur_y));
                        break;
                    case 2:
                        player_move =
                                EAST.player2(local_field, ball_direction,
                                        cur_x, cur_y);
                        break;
                    case 3:
                        player_move =
                                swap_heading(
                                        WEST.player2(local_backwards_field,
                                                backwards_ball_direction,
                                                backwards_cur_x, backwards_cur_y));
                        break;
                    case 4:
                        player_move =
                                EAST.player3(local_field, ball_direction,
                                        cur_x, cur_y);
                        break;
                    case 5:
                        player_move =
                                swap_heading(
                                        WEST.player3(local_backwards_field,
                                                backwards_ball_direction,
                                                backwards_cur_x, backwards_cur_y));
                        break;
                    case 6:
                        player_move =
                                EAST.player4(local_field, ball_direction,
                                        cur_x, cur_y);
                        break;
                    case 7:
                        player_move =
                                swap_heading(
                                        WEST.player4(local_backwards_field,
                                                backwards_ball_direction,
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
                /*--- Where is the ball ?---*/
                        if (local_field[i] == BALL) {
                            kick_direction = i;
                            kick_steps = KICK_DIST;
                            player_move = i;
                            break;
                        }
                    }
                    if (player_move == KICK) {
                /*--- Ball was NOT nearby ---*/
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
                field[cur_x][cur_y] = EMPTY;

                /*
                 * If the cell we are going to is empty. or
                 * the ball is there and the next cell after that is empty, then...
                 */
                if ((local_field[player_move] == EMPTY) ||
                        ((local_field[player_move] == BALL) &&
                                ((local_ball_field[player_move] == EMPTY) ||
                                        (local_ball_field[player_move] == GOAL)))) {
                    switch (player_move) {
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
                if ((cur % 2) == 0)
                    field[player_x[cur]][player_y[cur]] = EAST_PLAYER;
                else
                    field[player_x[cur]][player_y[cur]] = WEST_PLAYER;

                /*
                 * Now move the ball
                 */
                if ((local_field[player_move] == BALL) &&
                        ((local_ball_field[player_move] == EMPTY) ||
                                (local_ball_field[player_move] == GOAL))) {
                    field[ball_x][ball_y] = EMPTY;
                    //TODO: if (display) mvaddch(ball_y, ball_x, ' '); /* new position */
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
            /*--- Revise the local ball field ---*/
                    k = 0;
                    for (j = 0; j < 3; j++)
                        for (i = 0; i < 3; i++) {
                            if (ball_x + i - 1 < 0) {
                                local_ball_field[k++] = field[0][ball_y + j - 1];
                            } else if (ball_x + i - 1 > 79) {
                                local_ball_field[k++] = field[79][ball_y + j - 1];
                            } else {
                                local_ball_field[k++] = field[ball_x + i - 1][ball_y + j - 1];
                            }
                        }

			/*--- Erase old position ---*/
                    field[ball_x][ball_y] = EMPTY;
                    //TODO: if (display) mvaddch(ball_y, ball_x, ' ');

			/*--- Propel the ball if the space is empty ---*/
                    if ((local_ball_field[kick_direction] == EMPTY) ||
                            (local_ball_field[kick_direction] == GOAL))
                        switch (kick_direction) {
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
                            default:
                                break;
                        }
                    else {
                        kick_direction = -1;
                        kick_steps = 0;
                    }
                    kick_steps--;
                    if (ball_x < 0) {
                        field[0][ball_y] = BALL;
                    } else if (ball_x > 79) {
                        field[79][ball_y] = BALL;
                    } else {
                        field[ball_x][ball_y] = BALL;
                    }
                }

                /*
                 * Mark the new locations of the ball and
                 * the player on the field.
                 */
                //TODO: if (display) mvaddch(ball_y, ball_x, 'O');
                if ((cur % 2) == 0) {
                    //TODO: if (display) mvaddch(player_y[cur], player_x[cur], '<');
                } else {
                    //TODO: if (display) mvaddch(player_y[cur], player_x[cur], '>');
                }
                //TODO: if (display) wrefresh(game_win);


                /*
                 * Check for a score.
                 */
                if (ball_x <= 0) {
                    east_score++;
                    point_over = 1;
//                    if (!display) {
//                        printf("%s vs %s: %d to %d \n", WESTteam_name(), EASTteam_name(),
//                                west_score, east_score);
//                        fflush(stdout);
//                    }
                    EAST.wonPoint(); /* Advise the teams of the point */
                    WEST.lostPoint();
                }


                if (ball_x >= (MAX_X - 1)) {
                    west_score++;
                    point_over = 1;
//                    if (!display)
//                    {
//                        printf("%s vs %s: %d to %d \n",WESTteam_name(),EASTteam_name(),
//                                west_score,east_score);
//                        fflush(stdout);
//                    }
                    WEST.wonPoint(); /* Advise the teams of the point */
                    EAST.lostPoint();
                    report_score(west_score, east_score);
                }

                player_nearby = 0;
                for (i = 0; i <= 8; i++) {
                    if ((local_ball_field[i] == WEST_PLAYER) ||
                            (local_ball_field[i] == EAST_PLAYER))
                        player_nearby = 1;
                }
                if (player_nearby == 1 &&
                        ((last_ball_x == ball_x) && (last_ball_y == ball_y)))
                    count++;
                if (count > TIME_LIMIT) {
                    nudge_ball();
                    count = 0;
                }
                if ((last_ball_x != ball_x) || (last_ball_y != ball_y))
                    count = 0;
                last_ball_x = ball_x;
                last_ball_y = ball_y;


                /*
                 * go on to the next player
                 */
                cur++;
                if (cur == 8)
                    cur = 0;

                /*
                 * check for big timeout.
                 */
                overall_count++;
                overall_count++;
                if (overall_count >= TIME_OUT) {
                    //printf("TIME_OUT\n");
            /* erase old spot */
                    //if (display) mvaddch(ball_x, ball_y, ' ');
                    field[ball_x][ball_y] = EMPTY;
                    ball_x = 38;
                    ball_y = 20;
                    replace_ball();
                    overall_count = 0;
                    EAST.lostPoint(); /* punish teams */
                    WEST.lostPoint();
                }
            }
            //Field.timer.stop();
            report_score(west_score, east_score);

            System.out.println("west: " + west_score + " east: " + east_score);

            /*
             * Check for first to 7 wins.
             */
            if ((west_score == points) || (east_score == points)) {
                game_over = 1;
            }
        }
        //Field.timer.stop();
        EAST.gameOver();
        WEST.gameOver();
        //Field.timer.stop();
        report_score(west_score, east_score);
        //System.exit(0);
    }
}
