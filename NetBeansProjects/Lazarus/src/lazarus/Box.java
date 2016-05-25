package lazarus;

import java.awt.Image;
import java.awt.Point;
import wingman.game.Ship;

/**
 *
 * @author noslide
 */
public class Box extends Ship{

    /**
     *
     * @param position
     * @param speed
     * @param damage
     * @param img
     */
    public Box(Point position, Point speed, int damage, Image img){
        super(position, speed, damage, img);
    }

    /**
     *
     * @param boxLocation
     */
    public void setSpeed(Point boxLocation){
        speed = boxLocation;
    }

    /**
     *
     * @return
     */
    public int getStrength(){
        return strength;
    }

    /**
     *
     */
    @Override
    public void fire(){
    }
}
