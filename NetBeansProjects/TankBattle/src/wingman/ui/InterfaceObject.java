package wingman.ui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.ImageObserver;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author noslide
 */
public abstract class InterfaceObject implements Observer {
	Point location;
	ImageObserver observer;
	
    /**
     *
     * @param g
     * @param x
     * @param y
     */
    public abstract void draw(Graphics g, int x, int y);

	public void update(Observable o, Object arg) {
	}
}
