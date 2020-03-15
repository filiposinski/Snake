//import java.awt.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

//import java.awt.event.*;
//import java.util.Random;
//import javax.swing.*;

class Gameplay extends JPanel implements ActionListener, KeyListener {

    boolean play, start, eat, pause; //zmienne sterujące

    int score, tail, dir; //zmienne pamiętające wynik, długość ogona oraz
    //kierunek poruszania się

    int width = 300;        //szerokość ramki
    int height = 300;       //wysokość ramki

    Timer time;             //inicjacja obiektu klasy Timer
    int delay = 100;        //ustawienie opóźnienia

    int fruitX = 0;         //pozycja owoca
    int fruitY = 0;         //pozycja owoca

    int temp = 0;            //zmienna zapasowa do danych tymczasowych

    ArrayList<Integer> x,y;     //inicjacja list elementów

    // Image apple = Toolkit.getDefaultToolkit().getImage("apple.jpg");
    //ImageIcon ia = new ImageIcon(this.getClass().getResource("apple.jpg"));
    //Image apple = ia.getImage();

    Gameplay(){


        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(width,height+30));
        addKeyListener(this);
        setFocusable(true);

        time = new Timer(delay, this);

        start();

    }

    public void start(){

        play = false;
        start = false;
        eat = true;
        pause = false;

        score = 0;
        tail = 3; //długość startowa węża
        dir = 0;

        x = new ArrayList<Integer>();
        y = new ArrayList<Integer>();

        for(int i=0 ; i <=tail; i++){ //tworzenie pozycji początkowych węża
            x.add(100+i*10);
            y.add(100);
        }

        time.start(); //start odliczania czasu

    }

    public void paint(Graphics g){
        super.paint(g);
        //PLANSZA

        g.setColor(Color.ORANGE); //ustawienie koloru

        g.fillRect(5, 5, 5, height-10); //lewa krawedz
        g.fillRect(5, 5, width-10, 5); //gorna krawedz
        g.fillRect(width-10 ,5, 5, height-10); // prawa krawedz
        g.fillRect(5, height-10, width-10, 5); //dolna krawedz


        //SCORE
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString("Score: " + score, 5, height+25);

        g.drawString("Tail: " + tail, 100, height+25);


        //SNAKE
        for(int i = 0; i <=tail; i++){
            if(i == 0){   //HEAD
                g.setColor(Color.white);
                g.fillRect(x.get(i),y.get(i),10,10);
                g.setColor(Color.DARK_GRAY);
                g.drawRect(x.get(i),y.get(i),10,10);
            }
            else{           //TAIL
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(x.get(i),y.get(i),10,10);
                g.setColor(Color.DARK_GRAY);
                g.drawRect(x.get(i),y.get(i),10,10);
            }
        }

        //FRUIT
        fruit();
        g.setColor(Color.red);
        g.fillOval(fruitX,fruitY,10,10);
        // g.drawImage(apple, fruitX,fruitY,this);

        //START
        if(!play && !start){
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("SNAKE", width/5, height/2);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("press [ARROW] to play!", width/8, height/2+20);
        }

        //END
        if(!play && start){
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("GAME", width/4, height/2);
            g.drawString("OVER!", width/4, height/2+50);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("press [ENTER] to play again!", width/10,height/2+100);
        }
    }


    //        //PAUSE
//        if(pause){
//            g.setColor(Color.white);
//            g.setFont(new Font("Arial", Font.BOLD, 50));
//            g.drawString("PAUSE", width/4, height/2);
//        }

    public void fruit(){
        while(eat){
            eat = false; //kontroler zjedzenia, zmiana prawdy na falsz

            //losowanie pozycji owoca na plaszy
            fruitX = new Random().nextInt((width-20)/10)*10+10;
            fruitY = new Random().nextInt((height-20)/10)*10+10;

            for(int i=0; i<=tail; i++){
                if((x.get(i).equals(fruitX)) && (y.get(i).equals(fruitY)))
                    eat = true;
            }
        }
    }


    public void end(){
        play = false;
        time.stop();
        System.out.println("stop");

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        time.start();

        if(play){

            for (int i = tail; i > 0; i--) {
                x.set(i, x.get(i-1));
                y.set(i, y.get(i-1));
            }

            switch(dir){
                case 1:
                    if (y.get(0) == 10) end();
                    else
                        // playerY -= 10;
                        temp = y.get(0);
                    y.set(0, temp-10);
                    break;

                case 2:
                    if (y.get(0) == width-20) end();
                    else
                        //playerY += 10;
                        temp = y.get(0);
                    y.set(0, temp+10);
                    break;
                case 3:
                    if(x.get(0) == 10) end();
                    else
                        //playerX -= 10;
                        temp = x.get(0);
                    x.set(0, temp-10);

                    break;
                case 4:
                    if(x.get(0) == height-20) end();
                    else
                        // playerX += 10;
                        temp = x.get(0);
                    x.set(0, temp+10);
                    break;
                default:
                    break;
            }

            for(int i=1; i<=tail; i++){
                if((x.get(i).equals(x.get(0))) && (y.get(i).equals(y.get(0))))
                    end();
            }


            if((x.get(0)==fruitX) && (y.get(0)==fruitY)) {
                eat=true;
                score +=10;
                tail++;

                x.add(x.get(1));
                y.add(y.get(1));
            }

        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            play = true;
            start=true;
            dir = 4;
//                System.out.println("right");
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            play = true;
            start=true;
            dir = 3;
//                System.out.println("left");
        }
        if(e.getKeyCode() == KeyEvent.VK_UP){
            play = true;
            start=true;
            dir = 1;
//                System.out.println("up");
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            play = true;
            start=true;
            dir = 2;
//                System.out.println("down");
        }


        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            start();
        }

        if(e.getKeyCode() == KeyEvent.VK_P){
            if(!pause){ pause = true; time.stop(); }
            else { pause = false; time.start();}
        }

    }


    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}

}
