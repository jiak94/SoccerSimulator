import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jiakuan on 8/24/2015.
 */
public class Field extends JPanel{
    public static JFrame jframe = new JFrame();
    public static JLabel eastPlayer;
    public static JLabel westPlayer;
    public static JPanel ball = new JPanel();

    public static JLabel empty = new JLabel();

    private final int column = 80;
    private final int row = 24;

    public static JPanel bg = null;

    public static JPanel[][] field = new JPanel[23][80];

    public static JPanel infoPanel = null;
    public static JLabel east_team;
    public static JLabel west_team;
    public static JLabel east_score = new JLabel("0", SwingConstants.CENTER);
    public static JLabel west_score = new JLabel("0", SwingConstants.CENTER);
    public static JButton play;
    public static JButton pause;
    public static JLabel name = new JLabel("Soccer Server", SwingConstants.CENTER);
    //public SoccerServer ss;

    public static Timer timer;

    public Field() {
        //ss = new SoccerServer();
        initFrame();

        //ball.setBackground(Color.black);
    }

    private void initFrame() {

        final ImageIcon icon = new ImageIcon("field.gif");
        jframe.setLayout(new BoxLayout(jframe.getContentPane(), BoxLayout.Y_AXIS));
        jframe.setSize(icon.getIconWidth(), icon.getIconHeight() + 170);

        GridLayout infoLayout = new GridLayout(0, 3);
        //GridLayout bgLayout = new GridLayout(0, 80);


        //bg panel

        bg = new JPanel() {
            protected void paintComponent(Graphics g) {
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, icon.getIconWidth(),
                        icon.getIconHeight(), icon.getImageObserver());
            }
        };
        bg.setLayout(new GridLayout(8, 8));

        bg.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));

        bg.setLayout(new GridLayout(23, 80));

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                field[i][j] = new JPanel();
                //field[i][j].setBackground(null);
                field[i][j].setOpaque(false);
                bg.add(field[i][j]);
            }
        }
        //end bg Panel


        //bg.add(ball);


        // info Panel
        infoPanel = new JPanel();
        infoPanel.setLayout(infoLayout);

        infoPanel.setPreferredSize(new Dimension(icon.getIconWidth(), 150));

        play = new JButton();
        pause = new JButton();

        play.setText("Play");


        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //timer.start();
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SoccerServer ss = new SoccerServer();
                        ss.play();
                    }
                });

                t.start();
            }
        });

        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


        //pause.setText("Pause");

        east_team = new JLabel("East", SwingConstants.CENTER);
        west_team = new JLabel("WEST", SwingConstants.CENTER);

        //line 1
        infoPanel.add(new JLabel());
        infoPanel.add(name);
        infoPanel.add(new JLabel());

        //line 2
        infoPanel.add(west_team);
        infoPanel.add(play);
        infoPanel.add(east_team);

        //line 3
        infoPanel.add(west_score);
        infoPanel.add(new JLabel());
        //infoPanel.add(pause);
        infoPanel.add(east_score);
        //end info Panel


        jframe.setTitle("Soccer Simulator");
        jframe.add(bg);
        jframe.add(infoPanel);
        jframe.pack();
        jframe.setVisible(true);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void setWestTeam(final String westName) {

        west_team.setText(westName);
        //jframe.repaint();

    }

    public static void setEastTeam(String eastName) {
        east_team.setText(eastName);
    }

    public static void setEastScore(String score) {
        east_score.setText(score);
    }

    public static void setWestScore(String score) {
        west_score.setText(score);
    }

    public static void paintWestPlayer(int y, int x) {
        field[y][x].setOpaque(true);
        field[y][x].setBackground(Color.yellow);
        try {
            Thread.sleep(5);
        } catch (Exception e) {

        }
    }

    public static void paintEastPlayer(int y, int x) {
        field[y][x].setOpaque(true);
        field[y][x].setBackground(Color.blue);
        try {
            Thread.sleep(5);
        } catch (Exception e) {

        }
    }

    public static void removeComponent(int y, int x) {
        field[y][x].setBackground(null);
        field[y][x].setOpaque(false);
        try {
            Thread.sleep(5);
        } catch (Exception e) {

        }
    }

    public static void paintBall(int y, int x) {
        field[y][x].setOpaque(true);
        field[y][x].setBackground(Color.black);
        try {
            Thread.sleep(5);
        } catch (Exception e) {


        }
    }

    public static void cleanScreen() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                //field[i][j] = new JPanel();
                field[i][j].setBackground(null);
                field[i][j].setOpaque(false);
                //bg.add(field[i][j]);
            }
        }
    }
}
