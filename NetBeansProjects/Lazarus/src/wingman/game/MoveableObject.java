package wingman.game;

import java.awt.Image;
import java.awt.Point;

import wingman.modifiers.motions.MotionController;
import wingman.modifiers.motions.NullMotion;

/*MoveableObjects have movement behaviors*/

/**
 *
 * @author noslide
 */

public class MoveableObject extends GameObject {

    /**
     *
     */
    protected int strength;

    /**
     *
     */
    protected MotionController motion;
	
    /**
     *
     * @param location
     * @param speed
     * @param img
     */
    public MoveableObject(Point location, Point speed, Image img){
		super(location, speed, img);
		this.strength=0;
		this.motion = new NullMotion();
	}
	
    /**
     *
     * @return
     */
    public int getStrength()
    {
    	return strength;
    }
    
    /**
     *
     * @param strength
     */
    public void setStrength(int strength){
    	this.strength = strength;
    }
    
    /**
     *
     * @param w
     * @param h
     */
    public void update(int w, int h){
    	motion.read(this);
    }
    
    /**
     *
     */
    public void start(){
    	motion.addObserver(this);
    }
}
