package tankGame.game;

import java.awt.image.BufferedImage;

public class Explosion extends Sprites {
    int delay, now;

    public Explosion(BufferedImage img, int initialX, int initialY, int sprite, int totalSprites) {
        super(img, initialX, initialY, sprite, totalSprites, "explosion");
//        drawSmallExplosion();
        delay = 2;
        now = 0;
    }
    public void drawSmallExplosion(){
            draw(game.g2, game);
            now++;
            if(now>delay){
                now=0;
                sprite++;
            }
    }
    public boolean finish(){
        return sprite >= totalSprites;
    }
}
