package flappyBird;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class FlappyBird implements ActionListener ,MouseListener ,KeyListener {

    public static FlappyBird flappyBird;
    public final int WIDTH = 800, HEIGHT = 700;
    public Renderer renderer;
    public int ticks, yMotion, score;
    public Rectangle bird;
    public Random random;
    public  boolean gameOver, started;
    public ArrayList<Rectangle> columns;

    public FlappyBird(){

        JFrame jFrame = new JFrame();
        renderer = new Renderer();
        Timer timer = new Timer(20, this);
        random  = new Random();

        jFrame.add(renderer);
        jFrame.addKeyListener(this);
        jFrame.addMouseListener(this);
        jFrame.setTitle("Flappy Bird");
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.setSize(WIDTH,HEIGHT);
        jFrame.setVisible(true);
        jFrame.setResizable(false);

        bird =new Rectangle(WIDTH / 2 -10 ,HEIGHT / 2 - 10 , 20, 20 );

        columns = new ArrayList<Rectangle>();

        addColumn(true);
        addColumn(true);
        addColumn(true);
        addColumn(true);
        addColumn(true);
        timer.start();
    }

    public void jump(){
        if(gameOver){
            bird =new Rectangle(WIDTH / 2 -10 ,HEIGHT / 2 - 10 , 20, 20 );
            columns.clear();
            yMotion = 0;
            score = 0;

            addColumn(true);
            addColumn(true);
            addColumn(true);
            addColumn(true);
            addColumn(true);
            gameOver = false;
        }
        if(!started){
            started = true;

        }
        else if ( !gameOver ){
            if (yMotion > 0){
                yMotion = 0;
            }
            yMotion -= 13;
        }
    }

    public void addColumn(boolean start){

        int space = 300;
        int width = 100;

        int height  = 50 + random.nextInt(300);
        if (start){

            columns.add(new Rectangle(WIDTH + width + columns.size() * 300, HEIGHT - height -120, width, height));
            columns.add(new Rectangle(WIDTH + width + (columns.size() - 1 ) * 300, 0, width, HEIGHT - height - space));

        }
        else {

            columns.add(new Rectangle(columns.get(columns.size() - 1 ).x + 600, HEIGHT - height -120, width, height));
            columns.add(new Rectangle(columns.get(columns.size() - 1).x , 0, width, HEIGHT - height - space));
        }

    }

    public void paintColumn(Graphics g, Rectangle column){
        g.setColor(Color.gray.darker());
        g.fillRect(column.x,column.y,column.width,column.height);
    }

    public void repaint(Graphics g) {

        g.setColor(Color.GREEN);
        g.fillRect(0,0,WIDTH,HEIGHT);

        g.setColor(Color.orange);
        g.fillRect(0, HEIGHT - 120 , WIDTH, 120);

        g.setColor(Color.gray);
        g.fillRect(0, HEIGHT - 120 , WIDTH, 20);

        g.setColor(Color.RED);
        g.fillRect(bird.x,bird.y,bird.width,bird.height);

        for (Rectangle column : columns){
            paintColumn(g,column);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial" ,1 , 100));

        if( !started){
            g.drawString("Click to Start!" , 60, HEIGHT / 2 - 50);
        }

        if(gameOver){
            g.drawString("Game Over!" , 100, HEIGHT / 2 - 50);
        }

        if (!gameOver && started){
            g.drawString(String.valueOf(score), WIDTH / 2 - 25, 100);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int speed = 12;
        ticks ++;
        if(started) {
            for (int i = 0; i < columns.size(); i++) {

                Rectangle column = columns.get(i);
                column.x -= speed;
            }

            if (ticks % 2 == 0 && yMotion < 5) {
                yMotion += 3;
            }

            for (int i = 0; i < columns.size(); i++) {

                Rectangle column = columns.get(i);

                if (column.x + column.width < 0) {
                    columns.remove(column);

                    if (column.y == 0) {
                        addColumn(false);
                    }
                }

            }

            bird.y += yMotion;

            for (Rectangle column : columns) {

                if (column.y == 0 && bird .x + bird.width / 2 > column.x + column.width / 2 - 10 && bird.x + bird.width / 2 < column.x + column.width / 2 + 10 ){
                    score++;
                }
                if (column.intersects(bird)) {
                    gameOver = true;
                        if ( bird.x <= column.x) {
                            bird.x = column.x - bird.width;
                    }
                    else{
                            if(column.y != 0){
                                bird.y = column.x -bird.height;
                            }
                            else if (bird.y < column.height){
                                bird.y = column.height;
                            }

                        }
                }
            }

            if (bird.y > HEIGHT - 120 || bird.y < 0) {

                gameOver = true;
            }

            if (bird.y + yMotion >= HEIGHT - 120 ){

                bird.y = HEIGHT - 120 - bird.height;
            }
        }
        renderer.repaint();
    }

    public static void main(String[] args){

        flappyBird = new FlappyBird();

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        jump();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        jump();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}



