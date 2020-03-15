import javax.swing.JFrame;

public class Panel extends JFrame {

    public Panel()
    {
        super("SNAKE");
        setDefaultCloseOperation(3);
        add(gamePlay);

        pack();
        setResizable(false);
        setLocationRelativeTo(null);

    }

    Gameplay gamePlay = new Gameplay();
    public static void main(String[] args) {
        new Panel().setVisible(true);
    }

}
