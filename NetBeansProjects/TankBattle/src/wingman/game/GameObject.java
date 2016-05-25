/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wingman.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.Observable;
import java.util.Observer;

import wingman.modifiers.AbstractGameModifier;

/**
 *
 * @author noslide
 */
abstract public class GameObject implements Observer {

    /**
     *
     */
    protected Point speed;

    /**
     *
     */
    protected Rectangle location;

    /**
     *
     */
    public Image img;

    /**
     *
     */
    protected int height;

    /**
     *
     */
    protected int width;
	ImageObserver observer;

    /**
     *
     */
    public boolean show;
	
    /**
     *
     */
    public GameObject(){}
	
    /**
     *
     * @param location
     * @param speed
     * @param img
     */
    public GameObject(Point location, Point speed, Image img){
		this.speed = speed;
		this.img=img;
		this.show=true;
		try{
	        height = img.getWidth(observer);
	        width = img.getHeight(observer);
		}
	    catch (NullPointerException e){
	    	height=0;
	    	width=0;
	    }
        this.location = new Rectangle(location.x,location.y,width,height);
	}
	
    /**
     *
     * @param location
     * @param img
     */
    public GameObject(Point location, Image img){
		this(location, new Point(0,0), img);
	}
	
    /**
     *
     * @param g
     * @param obs
     */
    public void draw(Graphics g, ImageObserver obs) {
    	if(show)
    		g.drawImage(img, location.x, location.y, obs);
    }
    
    /**
     *
     * @param img
     */
    public void setImage(Image img){
    	this.img = img;
		try{
	        height = img.getWidth(observer);
	        width = img.getHeight(observer);
	        this.location = new Rectangle(location.x,location.y,width,height);
		}
	    catch (NullPointerException e){
	    	height=0;
	    	width=0;
	    	this.location = new Rectangle(location.x,location.y,0,0);
	    }
    }
    
    /**
     *
     * @param w
     * @param h
     */
    public void update(int w, int h) {
    	location.x += speed.x;
        location.y += speed.y;
        
        if(location.y<-100 || location.y==h+100){
        	this.show=false;
        }
        
    }
    
	@Override
	public void update(Observable o, Object arg) {
		AbstractGameModifier modifier = (AbstractGameModifier) o;
		modifier.read(this);
	}
    
    /**
     *
     * @return
     */
    public int getX(){
    	return location.x;
    }
    
    /**
     *
     * @return
     */
    public int getY(){
    	return location.y;
    }
    
    /**
     *
     * @return
     */
    public int getSizeX(){
    	return width;
    }
    
    /**
     *
     * @return
     */
    public int getSizeY(){
    	return height;
    }
    
    /**
     *
     * @return
     */
    public Point getSpeed(){
    	return speed;
    }
    
    /**
     *
     * @param newLocation
     */
    public void setLocation(Point newLocation){
    	location.setLocation(newLocation);
    }
    
    /**
     *
     * @return
     */
    public Rectangle getLocation(){
    	return new Rectangle(this.location);
    }
    
    /**
     *
     * @return
     */
    public Point getLocationPoint(){
    	return new Point(location.x, location.y);
    }
    
    /**
     *
     * @param dx
     * @param dy
     */
    public void move(int dx, int dy){
    	location.translate(dx, dy);
    }
    
    /**
     *
     */
    public void move(){
    	location.translate(speed.x,speed.y);
    }

    /**
     *
     * @param otherObject
     * @return
     */
    public boolean collision(GameObject otherObject) {
        if(location.intersects(otherObject.getLocation())){
        	return true;
        }
        
        return false;
    }
    
    /**
     *
     */
    public void hide(){
    	this.show = false;
    }
    
    /**
     *
     */
    public void show(){
    	this.show = true;
    }
}