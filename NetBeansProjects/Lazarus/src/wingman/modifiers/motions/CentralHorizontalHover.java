package wingman.modifiers.motions;

import java.awt.Point;
import java.util.Observable;

import wingman.WingmanWorld;
import wingman.game.Ship;

/**
 *
 * @author noslide
 */
public class CentralHorizontalHover extends MotionController {
	int right, left, top;

    /**
     *
     */
    public CentralHorizontalHover() {
		this(30, 150);
	}
	
    /**
     *
     * @param interval
     * @param y
     */
    public CentralHorizontalHover(int interval, int y) {
		super();
		int width = WingmanWorld.getInstance().getSize().width;
		this.right = width-50;
		this.left = 50;
		this.top = y;
		this.fireInterval = interval;
	}

	public void update(Observable o, Object arg){
		this.setChanged();
		this.notifyObservers();
	}
	
    /**
     *
     * @param theObject
     */
    public void read(Object theObject){
		Ship ship = (Ship) theObject;
		Point location = ship.getLocationPoint();
		Point speed = ship.getSpeed();
		
		if(location.y<this.top){
			ship.move(0, speed.y);
		}
		else{
			if(location.x<left || location.x>right)
				speed.x = -speed.x;
			ship.move(1*speed.x, 0);
		}
		
		if(WingmanWorld.getInstance().getFrameNumber()%fireInterval==0){
			ship.fire();
		}
	}
}
