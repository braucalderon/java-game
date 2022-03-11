package BB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePlay extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private int score = 0;

    private int totalBricks = 21;
    private Timer timer;
//    controls the velocity of the ball
    private int delay = 6;

    //    position of the pedal "player" at start game
    private int playerX = 350;
    private boolean colorChange = true;

    private int ballposX = 120;
    private int ballposY = 350;
//    velocity of the bal
    private int ballXdir = -1; // program will know how to move the ball
    private int ballYdir = -2;

    private MapGenerator map;

    public GamePlay() {
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start(); // start timer with a delay of 10
    }

    public void paint(Graphics g) {
        g.setColor(Color.white); // set the background of the frame white
        g.fillRect(1, 1, 692, 592);

//        run the MapGenerator class to be visible to the GUI
        map.draw((Graphics2D) g);

//        setting the color borders
        g.setColor(Color.black);
        g.fillRect(0, 0, 5, 592);
        g.fillRect(0, 0, 695, 5);
        g.fillRect(695, 0, 5, 592);

//        setting the attributes for the pedal
        if (colorChange) {
            g.setColor(Color.blue);
        }
        if (!colorChange) {
            g.setColor(Color.red);
        }
        g.fillRect(playerX, 550, 100, 8);


//        setting the ball
        g.setColor(Color.CYAN);
        g.fillOval(ballposX, ballposY, 20, 20);

//        total points display
        g.setColor(Color.blue);
        g.setFont(new Font("serif", Font.BOLD, 25));
//        updates the score and set the location in the GUI
        g.drawString("" + score, 650, 30);

//        message if the player wins the game
//        totalBricks 21 / reserve count
        if (totalBricks <= 0) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.green);
            g.setFont(new Font("serif", Font.BOLD, 35));
            g.drawString("You Won the Game, Score: " + score, 100, 250);

            g.setFont(new Font("serif", Font.BOLD, 25));
            g.drawString("Press Enter to Restart.", 230, 360);
            g.setColor(Color.white);
            g.fillOval(ballposX, ballposY, 20, 20);
//           makes the display points drawing disappear
            g.setColor(Color.white);
            g.fillRect(640, 10, 55, 35);

        }

//        game over message
        if (ballposY > 570) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 35));
            g.drawString("Game Over, Score: " + score, 200, 350);

            g.setFont(new Font("serif", Font.BOLD, 25));
            g.drawString("Press Enter to Restart.", 230, 385);

//            makes the total points disappear
            g.setColor(Color.white);
            g.fillRect(640, 10, 55, 35);
        }

        g.dispose(); // release the resources used to play the game
    }

    //    change the color of the pedal
    public void setColorChange(boolean status) {
        colorChange = status;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (play) {
//      Ball - Pedal interaction
//      creates a new rectangle object one for the ball and the pedal as rectangles
//      uses the same values from the initialization from both objects
            if (new Rectangle(ballposX, ballposY, 20, 20)
                    .intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballYdir = -ballYdir; // the ball bounce back with each interaction with the pedal
                if (colorChange) {
                    setColorChange(false);
                } else {
                    setColorChange(true);
                }


            }

//            controls the interaction with the bricks and the ball
//            map is the object create in GamePlay and .map is the attribute from MapGenerator class
            for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
//                    create locations of the bricks greater than zero
//                    values 80 and 50 must be the same from MapGenerator class
                    if (map.map[i][j] > 0) {
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

//                        creates another set of bricks for the interaction
                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
//                        creates another ball to interact with the bricks
                        Rectangle ballReact = new Rectangle(ballposX, ballposY, 20, 20);
                        Rectangle brickRect = rect;

//                        logic for the interaction for the ball and each brick
                        if (ballReact.intersects(brickRect)) {
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 5;


//                            the ball bounce back for each interaction of each brick
//                            + 10 controls how the balls start the game and move thru the panel
                            if (ballposX + 10 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
                                ballXdir = -ballXdir;
                            } else {
                                ballposY = -ballYdir;
                            }

                        }
                    }
                }
            }

            ballposX += ballXdir;
            ballposY += ballYdir;
//         the ball bound back
//         ballposX left border
//         ballposY top border
            if (ballposX < 0) {
                ballXdir = -ballXdir;
            }
            if (ballposY < 0) {
                ballYdir = -ballYdir;
            }
//         ball bound back from on exes at 670 pixels
//         ballposX = 670 right border
            if (ballposX > 670) {
                ballXdir = -ballXdir;
            }
        }
        repaint(); // automatically updates gui

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
//          add boundary to 595 pixels, pedal does not overflow over 600 pixels
//          for playerX 700 width frame - 100 width pedal = 600 width
            if (playerX >= 595) {
                playerX = 590; // bound the player to position x 590 pixels
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;  // bound player to position 10 on exes
            } else {
                moveLeft();
            }
        }

//        accelerates the speed of the ball based on the count of bricks left
        if(totalBricks == 15){
            delay = 5;
        }
        if(totalBricks == 6) {
            delay = 4;
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                play = true;
                ballposX = 120;
                ballposY = 350;
                ballYdir = -2;
                ballXdir = -1;
                totalBricks = 21;
                score = 0;
//                generated a new map generator
                map = new MapGenerator(3, 7);

                repaint();

            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void moveRight() {
        play = true;
        playerX += 40; // move the pedal (speed)
    }

    public void moveLeft() {
        play = true;
        playerX -= 40;
    }
}
