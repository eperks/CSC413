package wingman.game.enemy;

import java.awt.Point;

import wingman.WingmanWorld;
import wingman.game.Ship;
import wingman.modifiers.motions.SimpleFiringMotion;
import wingman.modifiers.weapons.SpreadBomb;

/**
 *
 * @author noslide
 */
public class Bomber extends Ship {

    /**
     *
     * @param location
     */
    public Bomber(int location){
		this(location, 30, 6);
	}
	
    /**
     *
     * @param location
     * @param speed
     * @param interval
     */
    public Bomber(int location, int speed, int interval){
		super(location, new Point(0,speed), 100, WingmanWorld.sprites.get("enemy3"));
		this.weapon = new SpreadBomb();
		
		motion = new SimpleFiringMotion(interval);
	}

}
