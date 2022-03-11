package BB;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame obj = new JFrame();
        GamePlay gamePlay = new GamePlay();
        // create the frame of the game and specifications of the frame
        obj.setTitle("Brick Breaker");
        obj.setBounds(10, 10, 700, 600);
        obj.add(gamePlay); // game play must be added before setVisible()
        obj.setResizable(false); // cannot resize frame
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }
}
