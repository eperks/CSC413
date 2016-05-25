package wingman.game.enemy;

import java.awt.Point;

import wingman.WingmanWorld;
import wingman.game.Ship;
import wingman.modifiers.motions.CentralHorizontalHover;
import wingman.modifiers.weapons.SpreadWeapon;

/**
 *
 * @author noslide
 */
public class HoverEnemy extends Ship {

    /**
     *
     * @param location
     */
    public HoverEnemy(int location){
		this(location, new Point(3,3));
	}
	
    /**
     *
     * @param location
     * @param speed
     */
    public HoverEnemy(int location, Point speed){
		super(location, speed, 20, WingmanWorld.sprites.get("enemy3"));
		strength=10;
		health=10;
		this.weapon = new SpreadWeapon();
		
		motion = new CentralHorizontalHover();
	}
}
