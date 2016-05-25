package tankGame.game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GameEvents {
    int[] p1,p2;
    GameWorld game = GameWorld.getInstance();

    public void process(ArrayList<Player> players, ArrayList<Sprites> w1, ArrayList<BreakableWall> w2){
        move(players);
        checkColisionPlayers(players, w1, w2);
    }

    public void pressed(KeyEvent e, ArrayList<Player> players) {
        if(e.getKeyCode() == KeyEvent.VK_A)
            players.get(0).keys[0] = true;
        if(e.getKeyCode() == KeyEvent.VK_LEFT)
            players.get(1).keys[0] = true;
        if(e.getKeyCode() == KeyEvent.VK_S)
            players.get(0).keys[1] = true;
        if(e.getKeyCode() == KeyEvent.VK_DOWN)
            players.get(1).keys[1] = true;
        if(e.getKeyCode() == KeyEvent.VK_D)
            players.get(0).keys[2] = true;
        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
            players.get(1).keys[2] = true;
        if(e.getKeyCode() == KeyEvent.VK_W)
            players.get(0).keys[3] = true;
        if(e.getKeyCode() == KeyEvent.VK_UP)
            players.get(1).keys[3] = true;
    }

    public void released(KeyEvent e, ArrayList<Player> players){
        if(e.getKeyCode() == KeyEvent.VK_A)
            players.get(0).keys[0] = false;
        if(e.getKeyCode() == KeyEvent.VK_LEFT)
            players.get(1).keys[0] = false;
        if(e.getKeyCode() == KeyEvent.VK_S)
            players.get(0).keys[1] = false;
        if(e.getKeyCode() == KeyEvent.VK_DOWN)
            players.get(1).keys[1] = false;
        if(e.getKeyCode() == KeyEvent.VK_D)
            players.get(0).keys[2] = false;
        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
            players.get(1).keys[2] = false;
        if(e.getKeyCode() == KeyEvent.VK_W)
            players.get(0).keys[3] = false;
        if(e.getKeyCode() == KeyEvent.VK_UP)
            players.get(1).keys[3] = false;
        if(e.getKeyCode() == KeyEvent.VK_SPACE)
            players.get(0).fire();
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
            players.get(1).fire();
    }

    public void move(ArrayList<Player> players){
        p1 = new int[2];
        p2 = new int[2];
        p1[0] = players.get(0).getX();
        p1[1] = players.get(0).getY();
        p2[0] = players.get(1).getX();
        p2[1] = players.get(1).getY();
        players.get(0).move();
        players.get(1).move();
    }

    public void checkColisionPlayers(ArrayList<Player> players, ArrayList<Sprites> w1, ArrayList<BreakableWall> w2){
        //check colision between two players
        Rectangle boxP1 = new Rectangle(players.get(0).getX(), players.get(0).getY(),54,54);
        Rectangle boxP2 = new Rectangle(players.get(1).getX(), players.get(1).getY(),54,54);
        if(boxP1.intersects(boxP2)) {
            players.get(0).setX(p1[0]);
            players.get(0).setY(p1[1]);
            players.get(1).setX(p2[0]);
            players.get(1).setY(p2[1]);
        }
        //check colision with sprites
        for(int i=0; i<w1.size(); i++){
            Rectangle wall = new Rectangle(w1.get(i).getX(), w1.get(i).getY(),
                    w1.get(i).getImg().getWidth()/2,w1.get(i).getImg().getHeight()/2);
            if(boxP1.intersects(wall)){
                players.get(0).setX(p1[0]);
                players.get(0).setY(p1[1]);
            }
            if(boxP2.intersects(wall)){
                players.get(1).setX(p2[0]);
                players.get(1).setY(p2[1]);
            }
        }
        //check colision with breakable wall
        for(int i=0; i<w2.size(); i++){
            Rectangle wall = new Rectangle(w2.get(i).getX(), w2.get(i).getY(),
                    w2.get(i).getImg().getWidth()/2,w2.get(i).getImg().getHeight()/2);
            if(boxP1.intersects(wall)&&game.walls2.get(i).visible){
                players.get(0).setX(p1[0]);
                players.get(0).setY(p1[1]);
            }
            if(boxP2.intersects(wall)&&game.walls2.get(i).visible){
                players.get(1).setX(p2[0]);
                players.get(1).setY(p2[1]);
            }
        }
        players.get(0).checkCollision(players.get(1));
        if(game.gameOver) {
            game.scoreP1++;
            game.gameOver();
        }
        players.get(1).checkCollision(players.get(0));
        if(game.gameOver) {
            game.scoreP2++;
            game.gameOver();
        }
    }


}
