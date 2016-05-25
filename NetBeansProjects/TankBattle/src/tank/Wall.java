package tank;

import java.awt.Point;

import wingman.game.BackgroundObject;

/**
 *
 * @author noslide
 */
public class Wall extends BackgroundObject {

    /**
     *
     * @param x
     * @param y
     */
    public Wall(int x, int y){
		super(new Point(x*32, y*32), new Point(0,0), TankWorld.sprites.get("wall"));
	}
	
    /**
     *
     * @param damage
     */
    public void damage(int damage){
		return;
	}
}
