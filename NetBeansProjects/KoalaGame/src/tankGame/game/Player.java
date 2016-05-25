package tankGame.game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.Math;
import java.util.ArrayList;

public class Player extends Sprites {

    protected ArrayList<Bullet> bullets;
    private final int DEGREES = 6; //degree for each rotation. degree * sprite = angle 360/60sprites = 6
    boolean[] keys = new boolean[4]; //left, down, right, up,fire
    int rotation, initialX,initialY,initialSprite;
    int life = 30, score;
    boolean show = true, powerup = false;
    public ArrayList<Explosion> explosions;
    public Player(BufferedImage img, int initialX, int initialY, int sprite, int totalSprites){
        super(img, initialX, initialY, sprite, totalSprites, "player");
        for(int i=0; i<=3; i++)
            keys[i]=false;
        bullets = new ArrayList<>();
        explosions = new ArrayList<>();
        rotation = DEGREES*sprite;
        this.initialY = initialY;
        this.initialX = initialX;
        this.initialSprite = sprite;
    }

    public void move(){
        if(keys[0]) moveLeft();
        if(keys[1]) moveDown();
        if(keys[2]) moveRight();
        if(keys[3]) moveUp();
    }

    public void moveLeft(){
        sprite++;
        if(sprite > 59) sprite =0;
        rotation = DEGREES*sprite;
    }
    public void moveRight(){
        sprite--;
        if (sprite < 0) sprite = 59;
        rotation = DEGREES*sprite;
    }
    public void moveUp(){
        x += speed * Math.cos(Math.toRadians(rotation));
        y -= speed * Math.sin(Math.toRadians(rotation));
    }
    public void moveDown(){
        x -= speed * Math.cos(Math.toRadians(rotation));
        y += speed * Math.sin(Math.toRadians(rotation));
    }

    public void fire(){
        bullets.add(new Bullet(game.getSprites().get("bullet"), x+getImg().getWidth()/2, y+getImg().getHeight()/2, rotation));
        if(powerup)
            bullets.add(new Bullet(game.getSprites().get("bullet"), x+getImg().getWidth()/2+10, y+getImg().getHeight()/2+10, rotation));
    }

    public Rectangle returnBounds(){
        return new Rectangle(x,y,54,54);
    }

    public void checkCollision(Player p2){
        Rectangle p = returnBounds();
        Rectangle p2Rec = p2.returnBounds();
        //check bullets

        //check collision with power
        for(int i=0; i<game.sp.size(); i++){
            if(game.sp.get(i).type.equals("power")) {
                Rectangle powers = game.sp.get(i).getBounds();
                if (p.intersects(powers)) {
                    powerup = true;
                    game.sp.remove(i);
                }
            }
        }

        for(int i=0; i<bullets.size(); i++){
            boolean remove = false;
            Rectangle r1 = new Rectangle(bullets.get(i).getX(),bullets.get(i).getY(), bullets.get(i).getImg().getWidth(),bullets.get(i).getImg().getHeight());
            //bullets and player
            if(r1.intersects(p2Rec)) {
                explosions.add(new Explosion(game.getSprites().get("smallExplosion"),(int)r1.getX()-16,(int)r1.getY()-16,0,6));
                game.sound.play("resources/Explosion_small.wav");
                remove = true;
                p2.life-=1;
                if(p2.life == 0){
                    game.sound.play("resources/Explosion_large.wav");
                    game.gameOver=true;
                }
            }
            //check bullets and breakableWall
            for(int j=0; j<game.walls2.size();j++){
                Rectangle w2 = new Rectangle(game.walls2.get(j).getX(),game.walls2.get(j).getY(), game.walls2.get(j).getImg().getWidth(), game.walls2.get(j).getImg().getHeight());
                if(r1.intersects(w2) && game.walls2.get(j).visible){
                    explosions.add(new Explosion(game.getSprites().get("smallExplosion"),game.walls2.get(j).getX(),game.walls2.get(j).getY(), 0, 6));
                    game.sound.play("resources/Explosion_small.wav");
                    game.walls2.get(j).visible=false;
                    remove=true;
                }
            }
            //check bullets and unbreakableWall
            for(int j=0; j<game.sp.size();j++){
                if(game.sp.get(i).type.equals("wall")) {
                    Rectangle w2 = new Rectangle(game.sp.get(j).getX(), game.sp.get(j).getY(), game.sp.get(j).getImg().getWidth(), game.sp.get(j).getImg().getHeight());
                    if (r1.intersects(w2)) {
                        remove = true;
                    }
                }
            }
            if(remove) bullets.remove(i);
        }
    }

    public ArrayList getBullets(){
        return bullets;
    }


}
