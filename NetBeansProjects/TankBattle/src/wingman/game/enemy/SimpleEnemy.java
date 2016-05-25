package wingman.game.enemy;

import java.awt.Point;

import wingman.*;
import wingman.game.Ship;
import wingman.modifiers.motions.SimpleFiringMotion;
import wingman.modifiers.motions.SimpleMotion;
import wingman.modifiers.weapons.NullWeapon;
import wingman.modifiers.weapons.SimpleWeapon;

/*A simple enemy that just moves vertically and possibly fires at fixed intervals*/

/**
 *
 * @author noslide
 */

public class SimpleEnemy extends Ship {

    /**
     *
     * @param location
     */
    public SimpleEnemy(int location){
		this(location, new Point(0,3), 5, 0);
	}
	
    /**
     *
     * @param location
     * @param speed
     * @param strength
     * @param fireInterval
     */
    public SimpleEnemy(int location, Point speed, int strength, int fireInterval){
		super(location, speed, strength, WingmanWorld.sprites.get("enemy1"));
		
		if(fireInterval==0){
			this.weapon = new NullWeapon();
			motion = new SimpleMotion();
		} else{
			this.weapon = new SimpleWeapon();
			motion = new SimpleFiringMotion(fireInterval);
		}
	}

}
