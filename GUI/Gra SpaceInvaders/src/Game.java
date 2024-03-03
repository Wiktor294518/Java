import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Game extends JPanel implements ActionListener, KeyListener {

    int x=225, y=600, velX=0; // położenie x i y gracza, prędkość gracza
    final int PANEL_WIDTH = 500;
    final int PANEL_HEIGHT = 700;
    boolean shot, reload;
    int posX, posY; // położenie x i y pocisku
    Rectangle bullet;
    public BufferedImage playerImage;
    UFO[][] aliens = new UFO[3][5]; // tablica dla obiektów UFO
    int alienX = 10, alienY = 10; // położenie x i y dla UFO
    int score; // wynik, im niższy tym lepszy
    Timer timer = new Timer(10, this);

    public Game(){
        timer.start();
        addKeyListener(this);
        setFocusable(true);
        this.setBackground(Color.black);

        for(int r = 0; r < aliens.length; r++){
            for (int c = 0; c < aliens[0].length; c++){
                aliens[r][c] = new UFO(alienX, alienY, 2); // tworzenie obiektów UFO
                alienX += 52;
            }
            alienX = 10;
            alienY += 52;
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        File imageFile = new File("player.png"); // rysowanie gracza
        try {
            playerImage = ImageIO.read(imageFile);
        } catch (IOException e) {
            System.err.println("Błąd odczytu obrazka");
            e.printStackTrace();
        }
        g.drawImage(playerImage, x, y, null);

        collisionDetection();
        for(int r = 0; r < aliens.length; r++){
            for (int c = 0; c < aliens[0].length; c++){  // rysowanie UFO
                if(aliens[r][c].isVisible)
                    aliens[r][c].paintComponent(g);
            }
        }
        if(shot){
            g.setColor(Color.GREEN);
            g.fillRect(bullet.x, bullet.y, bullet.width, bullet.height);
        }

        g.setColor(Color.white);
        g.drawString("Score: " + score, 10, 10);  // rysowanie wyniku
        try {
            g.drawString("HiScore " + HighScore(), 400, 10);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if(GameOver()){
            g.drawString("game over", (PANEL_WIDTH/2)-30,PANEL_HEIGHT/2);
        }
        if(lost()){
            g.drawString("przegrana",(PANEL_WIDTH/2)-30,PANEL_HEIGHT/2 );
        }
    }

    public void shooting(){
        if(shot){
            bullet.y -= 5;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!lost()){
            if (x < 10) {           // nie pozwala graczowi wyjsc poza mape
                velX = 0;
                x = 10;
            }
            if (x > 430) {
                velX = 0;
                x = 430;
            }
            x += velX;

            shooting();

            moveAlien();
            if (!GameOver()) {
                score += 10;
            }
            if (GameOver()) {
                try {
                    save();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }

            repaint();}
    }

    public void left(){
        velX = -4;
    }

    public void right(){
        velX = 4;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        int c = e.getKeyCode();
        if(c == KeyEvent.VK_RIGHT)
            right();
        if(c == KeyEvent.VK_LEFT)
            left();
        if(c == KeyEvent.VK_SPACE){
            if(bullet == null)
                reload = true;   // jeśli pocisk wyjdzie poza mape pojawia się możliwość strzelenia ponownie
            if(reload){
                posY = y;
                posX = x + 21;
                bullet = new Rectangle(posX, posY, 8, 8);
                shot = true;
            }
        }
    }
    public void restart(){
        try {
            Runtime.getRuntime().exec("java -jar main.jar");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int c = e.getKeyCode();
        if(c == KeyEvent.VK_R) {
            restart();
        }
        if(c == KeyEvent.VK_RIGHT)
            right();
        if(c == KeyEvent.VK_LEFT)
            left();
        if(c == KeyEvent.VK_SPACE){
            if(bullet == null)
                reload = true;
            if(reload){
                posY = y;
                posX = x + 25;
                bullet = new Rectangle(posX, posY, 8, 8);
                shot = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int c = e.getKeyCode();
        if(c == KeyEvent.VK_LEFT){
            velX = 0;
        }
        if(c == KeyEvent.VK_RIGHT){
            velX = 0;
        }
        if(c == KeyEvent.VK_SPACE) {
            reload = false;
            if (bullet.y <= -5) {
                bullet = new Rectangle(0, 0, 0, 0);
                shot = false;
                reload = true;
            }
        }
    }
    public void moveAlien(){
        for(int r = 0; r < aliens.length; r++){
            for (int c = 0; c < aliens[0].length; c++){
                if(!aliens[r][c].direction) // ruch w lewo
                    aliens[r][c].setX(aliens[r][c].getX()-aliens[r][c].getSpeed());

                if(aliens[r][c].direction){  // wuch w prawo
                    aliens[r][c].setX(aliens[r][c].getX()+aliens[r][c].getSpeed());
                }
            }}

        for(int r = 0; r < aliens.length; r++){
            for (int c = 0; c < aliens[0].length; c++){

                if(aliens[r][c].getX()>430){
                    moveLeftRight(1);
                    break;
                }

                if(aliens[r][c].getX()<0){
                    moveLeftRight(2);
                    break;
                }
            }
        }

    }
    public void moveLeftRight(int d){          // zmienia y ufo
        for(int r = 0; r < aliens.length; r++){
            for (int c = 0; c < aliens[0].length; c++){
                if(d == 1){
                    aliens[r][c].direction = false;
                }else{
                    aliens[r][c].direction = true;
                }

                aliens[r][c].setY(aliens[r][c].getY()+10);

            }
        }
    }
    public void collisionDetection(){
        for(int r = 0; r < aliens.length; r++) {
            for (int c = 0; c < aliens[0].length; c++) {
                if (shot) {
                    if (aliens[r][c].isVisible && bullet.getX() + bullet.getWidth() >= aliens[r][c].getX() &&
                            bullet.getX() <= aliens[r][c].getX() + 59 &&
                            bullet.getY() + bullet.getHeight() >= (aliens[r][c].getY()) &&
                            bullet.getY() <= aliens[r][c].getY() + 40) {

                        aliens[r][c].isVisible = false;
                        bullet.y = -30;
                    }
                }
            }
        }
    }
    public boolean GameOver() {
        int g = 0;
        for (int r = 0; r < aliens.length; r++) {
            for (int c = 0; c < aliens[0].length; c++) {
                if(aliens[r][c].isVisible){
                    g++;
                }
            }
        }
        if(g > 0)
            return false;
        else
            return true;
    }
    public boolean lost() {
        int p = 0;
        for (int r = 0; r < aliens.length; r++) {
            for (int c = 0; c < aliens[0].length; c++) {
                if(aliens[r][c].isVisible && aliens[r][c].getY() + 40 > y){
                    p++;
                }
            }
        }
        if(p > 0)
            return true;
        else
            return false;
    }

    public int HighScore() throws FileNotFoundException {
        Scanner reader = new Scanner(new File("best score.txt"));
        int s = reader.nextInt();
        return s;
    }
    public void save() throws FileNotFoundException {
        if(score < HighScore()){
            PrintWriter writer = new PrintWriter("best score.txt");
            writer.println(score);
            writer.close();
        }
    }
}
