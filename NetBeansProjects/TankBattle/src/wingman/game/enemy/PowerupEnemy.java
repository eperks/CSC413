package wingman.game.enemy;

import java.awt.Point;

import wingman.WingmanWorld;
import wingman.game.PowerUp;
import wingman.game.Ship;
import wingman.modifiers.motions.SimpleMotion;
import wingman.modifiers.weapons.AbstractWeapon;
import wingman.modifiers.weapons.SpreadWeapon;

/**
 *
 * @author noslide
 */
public class PowerupEnemy extends Ship {

    /**
     *
     * @param location
     */
    public PowerupEnemy(int location){
		this(location, new Point(3,3));
	}
	
    /**
     *
     * @param location
     * @param speed
     */
    public PowerupEnemy(int location, Point speed){
		this(location, speed, new SpreadWeapon());
	}
	
    /**
     *
     * @param location
     * @param speed
     * @param weapon
     */
    public PowerupEnemy(int location, Point speed, AbstractWeapon weapon){
		super(location, speed, 20, WingmanWorld.sprites.get("enemy2"));
		this.weapon = weapon;
		
		motion = new SimpleMotion();
	}
	
    /**
     *
     */
    public void die(){
		WingmanWorld.getInstance().addPowerUp(new PowerUp(this));
		super.die();
	}
}