import javax.swing.*;

public class SpaceInvaders {
    public SpaceInvaders(){

        JFrame f = new JFrame();
        Game p = new Game();

        f.add(p);
        f.pack();
        f.setSize(500,700);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setTitle("SpaceInvaders");
    }
}
