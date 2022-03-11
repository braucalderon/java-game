package BB;

import java.awt.*;

public class MapGenerator {

    public int map[][];
    public int brickWidth;
    public int brickHeight;
    private boolean colorBrick = true;

    //    for loop to create the map matrix /  bricks
    public MapGenerator(int row, int col) {
        map = new int[row][col];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = 1;

            }
        }
        brickWidth = 540 / col;
        brickHeight = 150 / row;
    }

    //    to draw the matrix or bricks into the GUI
    public void draw(Graphics2D g) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] > 0) {
                    g.setColor(Color.black);
//                    create the bricks dimensions and + 80 move the bricks to x exes and + 50 move
//                    the bricks to the y exes
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                    g.setStroke(new BasicStroke(3)); // stroke is the line between each brick
                    g.setColor(Color.white);
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }
    }

    public void setColorBrick(boolean status) {
        colorBrick = status;
    }

    //    set the value of each brick
    public void setBrickValue(int value, int row, int col) {
        map[row][col] = value;


    }
}
