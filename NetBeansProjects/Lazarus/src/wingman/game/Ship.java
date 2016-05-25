package wingman.game;

import java.awt.Image;
import java.awt.Point;

import wingman.WingmanWorld;
import wingman.modifiers.motions.MotionController;
import wingman.modifiers.weapons.AbstractWeapon;

/* Ships are things that have weapons and health */

/**
 *
 * @author noslide
 */

public class Ship extends MoveableObject {

    /**
     *
     */
    protected AbstractWeapon weapon;

    /**
     *
     */
    protected int health;

    /**
     *
     */
    protected Point gunLocation;
	
    /**
     *
     * @param location
     * @param speed
     * @param strength
     * @param img
     */
    public Ship(Point location, Point speed, int strength, Image img){
    	super(location,speed,img);
    	this.strength=strength;
    	this.health=strength;
    	this.gunLocation = new Point(15,20);
    }
    
    /**
     *
     * @param x
     * @param speed
     * @param strength
     * @param img
     */
    public Ship(int x, Point speed, int strength, Image img){
    	this(new Point(x,-90), speed, strength, img);
    }
    
    /**
     *
     * @param weapon
     */
    public void setWeapon(AbstractWeapon weapon){
    	this.weapon.remove();
    	this.weapon = weapon;
    }
    
    /**
     *
     * @return
     */
    public AbstractWeapon getWeapon(){
    	return this.weapon;
    }
    
    /**
     *
     * @param damageDone
     */
    public void damage(int damageDone){
    	this.health -= damageDone;
    	if(health<=0){
    		this.die();
    	}
    	return;
    }
    
    /**
     *
     */
    public void die(){
    	this.show=false;
    	SmallExplosion explosion = new SmallExplosion(new Point(location.x,location.y));
    	WingmanWorld.getInstance().addBackground(explosion);
    	weapon.deleteObserver(this);
    	motion.deleteObserver(this);
    	WingmanWorld.getInstance().removeClockObserver(motion);
    }
    
    /**
     *
     * @param otherObject
     */
    public void collide(GameObject otherObject){
    }
    
    /**
     *
     */
    public void fire()
    {
    	weapon.fireWeapon(this);
    }
    
    /* some setters and getters!*/

    /**
     *
     * @param health
     */
    
    public void setHealth(int health){
    	this.health = health;
    }
    
    /**
     *
     * @return
     */
    public int getHealth(){
    	return health;
    }
    
    /**
     *
     * @return
     */
    public MotionController getMotion(){
    	return this.motion;
    }
    
    /**
     *
     * @param motion
     */
    public void setMotion(MotionController motion){
    	this.motion = motion;
    }
    
    /**
     *
     * @return
     */
    public Point getGunLocation(){
    	return this.gunLocation;
    }
}