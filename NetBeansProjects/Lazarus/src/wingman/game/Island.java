/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wingman.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.ImageObserver;
import java.util.Random;

/**
 *
 * @author noslide
 */
public class Island extends BackgroundObject {
    Random gen;

    /**
     *
     * @param location
     * @param speed
     * @param img
     * @param gen
     */
    public Island(Point location, Point speed, Image img, Random gen) {
    	super(location, speed, img);
        this.gen = gen;
    }

    /**
     *
     * @param w
     * @param h
     */
    public void update(int w, int h) {
        location.translate(speed.x, speed.y);;
        if (location.y >= h) {
            location.y = -100;
            location.x = Math.abs(gen.nextInt() % (w - 30));
        }
    }

    /**
     *
     * @param g
     * @param obs
     */
    public void draw(Graphics g, ImageObserver obs) {
        g.drawImage(img, location.x, location.y, obs);
    }
}