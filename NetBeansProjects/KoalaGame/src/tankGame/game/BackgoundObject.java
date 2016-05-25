package tankGame.game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class BackgoundObject {
    boolean passby;
    int x, y;
    BufferedImage img;
    boolean visible;

    public BackgoundObject(BufferedImage img, int x, int y){
        this.x = x;
        this.y = y;
        this.img = img;
        visible = true;
    }

    public void draw(Graphics g, ImageObserver obs){

            g.drawImage(img,x,y,obs);
    }

    public boolean getpass(){
        return passby;
    }
    public void setPassby(boolean value){
        passby = value;
    }
    public int getX(){return x;}
    public int getY(){return y;}
    public BufferedImage getImg(){return img;}
}
