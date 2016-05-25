package wingman.modifiers.motions;

import wingman.WingmanWorld;
import wingman.game.Ship;

/**
 *
 * @author noslide
 */
public class SimpleFiringMotion extends SimpleMotion {

    /**
     *
     * @param interval
     */
    public SimpleFiringMotion(int interval){
		super();
		this.fireInterval = interval;
	}
	
    /**
     *
     * @param theObject
     */
    public void read(Object theObject){
		super.read(theObject);
		
		Ship ship = (Ship) theObject;
		
		if(WingmanWorld.getInstance().getFrameNumber()%fireInterval==0){
			ship.fire();
		}
	}

}
