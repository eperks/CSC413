package wingman.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.Observable;
import lazarus.LazarusWorld;

import wingman.WingmanWorld;
import wingman.game.PlayerShip;
import wingman.modifiers.AbstractGameModifier;
import wingman.modifiers.motions.MenuController;

/**
 *
 * @author noslide
 */
public class GameMenu extends InterfaceObject {
	int selection;
	MenuController controller;
	boolean waiting;
	
    /**
     *
     */
    public GameMenu(){
		selection = 0;
		controller = new MenuController(this);
		waiting = true;
	}

    /**
     *
     * @param g2
     * @param x
     * @param y
     */
    public void draw(Graphics g2, int x, int y){
		g2.setFont(new Font("Calibri", Font.PLAIN, 24));
		if(selection==0)
			g2.setColor(Color.RED);
		else
			g2.setColor(Color.WHITE);
		g2.drawString("Play", 200,200);
		if(selection==1)
			g2.setColor(Color.RED);
		else
			g2.setColor(Color.WHITE);
		g2.drawString("Quit", 200, 300);
	}
	
    /**
     *
     */
    public void down(){
		if(selection<1)
			selection++;
	}
	
    /**
     *
     */
    public void up(){
		if(selection>0)
			selection--;
	}
	
    /**
     *
     */
    public void applySelection(){
		LazarusWorld world = LazarusWorld.getLazarus();
		Dimension size = world.getSize();
		if(selection == 0){
//                    world.level.load();
		}
                else if(selection ==1){
			System.exit(0);
		}
		
//		WingmanWorld.sound.play("Resources/strobe.mp3");
		
		controller.deleteObservers();
		world.removeKeyListener(controller);
		world.level.load();
		waiting=false;
	}
	
	public void update(Observable o, Object arg) {
		AbstractGameModifier modifier = (AbstractGameModifier) o;
		modifier.read(this);
	}
	
    /**
     *
     * @return
     */
    public boolean isWaiting(){
		return this.waiting;
	}
}
