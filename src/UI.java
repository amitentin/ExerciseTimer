import javax.swing.*;
import java.awt.*;


public class UI extends JFrame {
    private JPanel pane;
    private JTextField[] A = new JTextField[9];


    public UI(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 800);
        setLocation(600, 400);
        pane = new JPanel();
        pane.setBackground(Color.DARK_GRAY);

    }

}
