package wingman.game;

import java.awt.Image;
import java.awt.Point;

import wingman.GameWorld;

/*BackgroundObjects move at speed of 1 and are not collidable*/

/**
 *
 * @author noslide
 */

public class BackgroundObject extends GameObject {

    /**
     *
     * @param location
     * @param img
     */
    public BackgroundObject(Point location, Image img){
		super(location, GameWorld.getSpeed(), img);
	}
	
    /**
     *
     * @param location
     * @param speed
     * @param img
     */
    public BackgroundObject(Point location, Point speed, Image img){
		super(location, speed, img);
	}

}
