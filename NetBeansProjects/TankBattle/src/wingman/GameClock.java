package wingman;

import java.util.Observable;

/*Game clock ticks on every frame and notifies observers to update*/

/**
 *
 * @author noslide
 */

public class GameClock extends Observable {
	private int startTime;
	private int frame;
	
    /**
     *
     */
    public GameClock(){
		startTime = (int) System.currentTimeMillis();
		frame = 0;
	}
		
    /**
     *
     */
    public void tick(){
		frame++;
		setChanged();
		this.notifyObservers();
	}
	
    /**
     *
     * @return
     */
    public int getFrame(){
		return this.frame;
	}
	
    /**
     *
     * @return
     */
    public int getTime(){
		return (int)System.currentTimeMillis()-startTime;
	}
}
