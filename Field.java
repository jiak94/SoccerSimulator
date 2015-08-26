import javax.swing.*;
import java.awt.*;

/**
 * Created by Jiakuan on 8/24/2015.
 */
public class Field {
    JFrame jframe = new JFrame();
    public static JPanel bg = null;
    public static JPanel infoPanel = null;
    public static JLabel east_team;
    public static JLabel west_team;
    public static JLabel east_score = new JLabel("0", SwingConstants.CENTER);
    public static JLabel west_score = new JLabel("0", SwingConstants.CENTER);
    public static JButton play;
    public static JButton pause;
    public static JLabel name = new JLabel("Soccer Server", SwingConstants.CENTER);

    public Field() {
        initFrame();
    }

    private void initFrame() {

        final ImageIcon icon = new ImageIcon("field.png");
        jframe.setLayout(new BoxLayout(jframe.getContentPane(), BoxLayout.Y_AXIS));

        GridLayout infoLayout = new GridLayout(0, 3);

        //bg panel
        bg = new JPanel() {
            protected void paintComponent(Graphics g) {
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, icon.getIconWidth(),
                        icon.getIconHeight(), icon.getImageObserver());
                jframe.setSize(icon.getIconWidth(), icon.getIconHeight() + 170);
            }
        };

        bg.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        //end bg Panel


        // info Panel
        infoPanel = new JPanel();
        infoPanel.setLayout(infoLayout);

        infoPanel.setPreferredSize(new Dimension(icon.getIconWidth(), 150));

        play = new JButton();
        pause = new JButton();

        play.setText("Play");
        pause.setText("Pause");

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
        infoPanel.add(pause);
        infoPanel.add(east_score);
        //end info Panel


        jframe.setTitle("Soccer Simulator");
        jframe.add(bg);
        jframe.add(infoPanel);
        jframe.pack();
        jframe.setVisible(true);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void setWestTeam(String westName) {
        west_team.setText(westName);
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

}
