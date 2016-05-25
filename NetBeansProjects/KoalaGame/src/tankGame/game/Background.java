package tankGame.game;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Background extends JPanel{
    private BufferedImage ground;
    private int w, h; //width and height

    public Background(int w, int h){
        this.w = w;
        this.h = h;
    }

    public void initBackground(BufferedImage ii){
        ground = ii;
    }

    public void drawBackground(Graphics g){
        int TileWidth = ground.getWidth(this);
        int TileHeight = ground.getHeight(this);
        int NumberX = w / TileWidth;
        int NumberY = h / TileHeight;
        for (int i = -1; i <= NumberY; i++) {
            for (int j = 0; j <= NumberX; j++) {
                g.drawImage(ground, j * TileWidth, i * TileHeight, TileWidth, TileHeight, this);
            }
        }
    }
}
