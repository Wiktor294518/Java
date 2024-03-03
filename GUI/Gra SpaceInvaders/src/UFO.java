
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class UFO extends JPanel  {
    int ux, uy, us;
    boolean direction;
    boolean isVisible=true;
    public BufferedImage enemy;

    public UFO(int ux, int uy, int us){
        this.ux=ux;
        this.uy=uy;
        this.us=us;


    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        File imageFile = new File("enemy.png");
        try {
            enemy = ImageIO.read(imageFile);
        } catch (IOException e) {
            System.err.println("Blad odczytu obrazka");
            e.printStackTrace();
        }
        g.drawImage(enemy, ux, uy, null);

    }



    public int getX()
    {
        return ux;
    }

    public int getY()
    {
        return uy;
    }

    public int getSpeed()
    {return us;
    }

    public void setX(int ux) {
        this.ux = ux;
    }

    public void setY(int uy)
    {
        this.uy=uy;

    }

}
