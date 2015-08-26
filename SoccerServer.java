/**
 * Created by Jiakuan on 8/24/2015.
 */
public class SoccerServer {
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

    public void reportScore(int west_score, int east_score) {
        System.out.println(westTeamName + " : " + west_score);
        System.out.println(eastTeamName + " : " + east_score);
    }

//    public int replaceBall() {
//        if (display) {
//
//        }
//    }
}
