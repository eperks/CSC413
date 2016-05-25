package lazarus;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import wingman.GameClock;
import wingman.GameSounds;
import wingman.GameWorld;
import wingman.game.BackgroundObject;
import wingman.game.Bullet;
import wingman.game.PlayerShip;
import wingman.modifiers.AbstractGameModifier;
import wingman.ui.GameMenu;

/**
 *
 * @author noslide
 */
public class LazarusWorld
        extends GameWorld {

    private static LazarusWorld myGame = new LazarusWorld();
    private Thread thread;
    public ArrayList<Box> boxArray;
    public ArrayList<Box> wallArray;
    public ArrayList<Box> boxesFalling;
    public ArrayList<StopButton> stopButtons;
    private ArrayList<ArrayList<Box>> fallenBoxes;
    public ArrayList<PlayerShip> players;
    public static HashMap<String, Image> sprites = GameWorld.sprites;
    static boolean gameOver;
    static boolean winner;
    boolean gameEnded;
    static GameMenu menu;
    ImageObserver observer;
    SoundPlayer sp;
    Graphics2D g2;
    Point mapSize;
    public static final GameClock clock = new GameClock();
    public LazarusLevel level;
    private BufferedImage bimg;
    int sizeX, sizeY, sleepNumber = 8;

    static Image[] squished = new Image[11];
    static ArrayList<Movement> explosions = new ArrayList<Movement>(1000);

    private LazarusWorld() {
        setFocusable(true);
        this.boxesFalling = new ArrayList<Box>();
        this.fallenBoxes = new ArrayList<ArrayList<Box>>(16);
        this.stopButtons = new ArrayList<StopButton>();
        for (int i = 16; i > 0; i--) {
            this.fallenBoxes.add(new ArrayList<Box>());
        }
        this.background = new ArrayList<BackgroundObject>();
        this.players = new ArrayList<PlayerShip>();
        this.boxArray = new ArrayList<Box>();
        this.wallArray = new ArrayList<Box>();

    }

    /**
     *
     * @param argv
     */
    public static void main(String[] argv) {
        LazarusWorld myGame = getLazarus();
        JFrame f = new JFrame("Lazarus Game");
        f.getContentPane().add("Center", myGame);
        f.pack();
        f.setSize(new Dimension(640, 505));
        myGame.setNewDimensions(640, 480);
        myGame.init();
        f.setVisible(true);
        f.setResizable(false);
        f.setDefaultCloseOperation(3);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myGame.start();
    }

    /**
     *
     */
    public void init() {
        menu = new GameMenu();
        setBackground(Color.white);
        loadSprites();
        this.level = new LazarusLevel("Resources/level.txt");
        clock.addObserver(this.level);
        this.level.addObserver(this);
        this.gameOver = false;
        this.observer = this;
        this.mapSize = new Point(this.level.w * 40, this.level.h * 40);
        LazarusWorld.this.addBckgrd(new BackgroundObject[]{new LazarusBackground(this.mapSize.x, this.mapSize.y, GameWorld.getSpeed(), (Image) sprites.get("background"))});
        sp = new SoundPlayer(1, "Resources/Music.wav");
    }

    /**
     *
     */
    public void start() {
        this.thread = new Thread(this);
        this.thread.setPriority(1);
        this.thread.start();
    }

    /**
     *
     * @return
     */
    public static LazarusWorld getLazarus() {
        return myGame;
    }

    /**
     *
     * @return
     */
    public ListIterator<BackgroundObject> getObjs() {
        return this.background.listIterator();
    }

    /**
     *
     * @return
     */
    public ListIterator<Box> getBoxesComingDown() {
        return this.boxesFalling.listIterator();
    }

    /**
     *
     * @return
     */
    public ListIterator<PlayerShip> getPlayer() {
        return this.players.listIterator();
    }

    /**
     *
     * @param w
     * @param h
     */
    public void setNewDimensions(int w, int h) {
        this.sizeX = w;
        this.sizeY = h;
    }

    /**
     *
     * @param objects
     */
    public void addBckgrd(BackgroundObject... objects) {
        BackgroundObject[] arrayOfBackgroundObject;
        int j = (arrayOfBackgroundObject = objects).length;
        for (int i = 0; i < j; i++) {
            BackgroundObject object = arrayOfBackgroundObject[i];
            this.background.add(object);
            if ((object instanceof LazarusBackground)) {
            }
        }
    }

    /**
     *
     * @param objects
     */
    public void addNewPlayer(PlayerShip... objects) {
        PlayerShip[] arrayOfPlayerShip;
        int j = (arrayOfPlayerShip = objects).length;
        for (int i = 0; i < j; i++) {
            PlayerShip player = arrayOfPlayerShip[i];
            this.players.add(player);
        }
    }

    /**
     *
     * @param aBox
     */
    public void addBckgrd(Box... aBox) {
        Box[] arrayOfBox;
        int j = (arrayOfBox = aBox).length;
        for (int i = 0; i < j; i++) {
            Box object = arrayOfBox[i];
            this.boxArray.add(object);
        }
    }

    /**
     *
     * @param y
     * @param fallenBox
     */
    public void addFallenBox(int y, Box... fallenBox) {
        Box[] arrayOfBox;
        int j = (arrayOfBox = fallenBox).length;
        for (int i = 0; i < j; i++) {
            Box object = arrayOfBox[i];
            this.fallenBoxes.get(y).add(object);
        }
    }

    /**
     *
     * @param y
     * @return
     */
    public ListIterator<Box> findBoxAtY(int y) {
        return this.fallenBoxes.get(y).listIterator();
    }

    /**
     *
     * @param y
     * @param x
     * @return
     */
    public int numberOfFallenBoxes(int y, int x) {
        int count = 0;
        ListIterator<Box> listOfBoxes = findBoxAtY(y);
        while (listOfBoxes.hasNext()) {
            Box newBox = (Box) listOfBoxes.next();
            if (newBox.getY() < x) {
                count++;
            }
        }
        return count;
    }

    /**
     *
     * @return
     */
    public ListIterator<Box> getWallItems() {
        return this.wallArray.listIterator();
    }

    /**
     *
     * @param w
     * @param h
     * @return
     */
    @Override
    public Graphics2D createGraphics2D(int w, int h) {
        Graphics2D g2 = null;
        if ((this.bimg == null) || (this.bimg.getWidth() != w) || (this.bimg.getHeight() != h)) {
            this.bimg = ((BufferedImage) createImage(w, h));
        }
        g2 = this.bimg.createGraphics();
        g2.setBackground(getBackground());
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2.clearRect(0, 0, w, h);
        return g2;
    }

    @Override
    public void paint(Graphics g) {
        clock.tick();
        g2 = createGraphics2D(getSize().width, getSize().height);
        int w = getSize().width, h = getSize().height;
        ListIterator<?> iterator = getObjs();
        while (iterator.hasNext()) {
            BackgroundObject obj = (BackgroundObject) iterator.next();
            obj.update(w, h);
            obj.draw(g2, this);
        }
        if (menu.isWaiting()) {
            g2.drawImage((Image) sprites.get("Title"), sizeX / 7, sizeY / 9, null);
            menu.draw(g2, w, h);
        } else {
            ListIterator<Box> fallingBoxes = getBoxesComingDown();
            while (fallingBoxes.hasNext()) {
                Box fallingBox = fallingBoxes.next();
                Rectangle fallingBoxX = fallingBox.getLocation();
                int fallingY = fallingBoxX.x / 40;

                ListIterator<PlayerShip> players = getPlayer();
                while (players.hasNext()) {
                    Lazarus currentPlayer = (Lazarus) players.next();
                    if (fallingBox.collision(currentPlayer)) {
                        for (int i = 100; i > 0; i++) {
                        }
                        currentPlayer.die();
                    }
                }
                ListIterator<Box> theBox = findBoxAtY(fallingY);
                boolean falling = false;
                while (theBox.hasNext()) {
                    Box groundBox = (Box) theBox.next();
                    if (fallingBox.collision(groundBox)) {
                        if (groundBox.getStrength() < fallingBox.getStrength()) {
                            groundBox.hide();
                            theBox.remove();
                            GameSounds.play("Resources/Crush.wav");
                        } else {
                            theBox.add(fallingBox);
                            fallingBoxes.remove();
                            falling = true;
                        }
                    }
                    groundBox.draw(g2, this);
                }
                if ((!falling) && (fallingBox.getY() > 360)) {
                    addFallenBox(fallingY, new Box[]{fallingBox});
                    fallingBoxes.remove();
                }
            }
        }
        if (!this.gameOver && !menu.isWaiting()) {
            iterator = getPlayer();
            while (iterator.hasNext()) {
                PlayerShip player = (PlayerShip) iterator.next();
                if (!player.isDead()) {
                    drawWallBoxes();
                    drawStopButtons();
                    drawNewBox();
                    drawFallingBox(w, h);
                    drawExplosions();
                } else {
                    this.gameOver = true;
                }
            }
            PlayerShip p1 = (PlayerShip) this.players.get(0);
            p1.update(w, h);
            p1.draw(g2, this);
        } else {
            g2.setColor(Color.white);
            g2.setFont(new Font("Calibri", 0, 30));
            if (!this.winner && !menu.isWaiting() && this.gameOver) {
                drawExplosions();
                g2.drawString("\t\tGame Over!", this.sizeX / 4, 400);
                g2.drawImage((Image) sprites.get("Title"), sizeX / 7, sizeY / 9, null);
            } else if (this.winner) {
                g2.drawString("\t\tYou Win!", this.sizeX / 4, 200);
            }
        }
        g2.dispose();
        g.drawImage(this.bimg, 0, 0, this);
    }

    /**
     *
     * @param object
     */
    @Override
    public void addClockObserver(Observer object) {
        clock.addObserver(object);
    }

    public void drawExplosions() {
        for (int i = 0; i < explosions.size(); i++) {
            if (explosions.get(i).getFinished()) {
                explosions.remove(i);
                i--;
            } else {
                explosions.get(i).update();
            }
        }
        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).draw(g2, this);
        }
    }

    public void drawWallBoxes() {
        ListIterator<Box> listOWallBoxes = this.wallArray.listIterator();
        while (listOWallBoxes.hasNext()) {
            Box newWallBox = listOWallBoxes.next();
            newWallBox.draw(g2, this);
        }
    }

    public void drawStopButtons() {
        ListIterator<StopButton> stopButtons = this.stopButtons.listIterator();
        while (stopButtons.hasNext()) {
            StopButton newStopButton = stopButtons.next();
            newStopButton.draw(g2, this);
        }
    }

    public void drawNewBox() {
        ListIterator<Box> listONewBoxes = this.boxArray.listIterator();
        while (listONewBoxes.hasNext()) {
            Box newBox = listONewBoxes.next();
            newBox.draw(g2, this);
        }
    }

    public void drawFallingBox(int w, int h) {
        ListIterator<Box> listOBoxesComingDown = this.boxesFalling.listIterator();
        while (listOBoxesComingDown.hasNext()) {
            Box newFallingBox = listOBoxesComingDown.next();
            newFallingBox.update(w, h);
            newFallingBox.draw(g2, this);
        }
    }

    /**
     *
     */
    @Override
    protected void loadSprites() {
        sprites.put("Title", getSprite("Resources/Title.gif"));
        sprites.put("player1", getSprite("Resources/Lazarus_stand.gif"));
        sprites.put("background", getSprite("Resources/Background.bmp"));
        sprites.put("wall", getSprite("Resources/Wall.gif"));
        sprites.put("CardBox", getSprite("Resources/CardBox.gif"));
        sprites.put("WoodBox", getSprite("Resources/WoodBox.gif"));
        sprites.put("MetalBox", getSprite("Resources/MetalBox.gif"));
        sprites.put("Lazarus_stand", getSprite("Resources/Lazarus_stand.gif"));
        sprites.put("StoneBox", getSprite("Resources/StoneBox.gif"));
        sprites.put("StopButton", getSprite("Resources/Button.gif"));
        try {
            squished[0] = ImageIO.read(LazarusWorld.class.getResource("Resources/2455cb37c0.gif.000.gif"));
            squished[1] = ImageIO.read(LazarusWorld.class.getResource("Resources/2455cb37c0.gif.001.gif"));
            squished[2] = ImageIO.read(LazarusWorld.class.getResource("Resources/2455cb37c0.gif.002.gif"));
            squished[3] = ImageIO.read(LazarusWorld.class.getResource("Resources/2455cb37c0.gif.003.gif"));
            squished[4] = ImageIO.read(LazarusWorld.class.getResource("Resources/2455cb37c0.gif.004.gif"));
            squished[5] = ImageIO.read(LazarusWorld.class.getResource("Resources/2455cb37c0.gif.005.gif"));
            squished[6] = ImageIO.read(LazarusWorld.class.getResource("Resources/2455cb37c0.gif.006.gif"));
            squished[7] = ImageIO.read(LazarusWorld.class.getResource("Resources/2455cb37c0.gif.007.gif"));
            squished[8] = ImageIO.read(LazarusWorld.class.getResource("Resources/2455cb37c0.gif.008.gif"));
            squished[9] = ImageIO.read(LazarusWorld.class.getResource("Resources/2455cb37c0.gif.009.gif"));
            squished[10] = ImageIO.read(LazarusWorld.class.getResource("Resources/2455cb37c0.gif.010.gif"));
        } catch (IOException ex) {
            Logger.getLogger(LazarusWorld.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param name
     * @return
     */
    @Override
    public Image getSprite(String name) {
        Image img = null;
        try {
            img = ImageIO.read(LazarusWorld.class.getResource(name));
        } catch (IOException ex) {
            Logger.getLogger(LazarusWorld.class.getName()).log(Level.SEVERE, null, ex);
        }
        return img;
    }

    @Override
    public void run() {
        Thread athread = Thread.currentThread();
        while (this.thread == athread) {
            requestFocusInWindow();
            repaint();
            try {
                Thread.sleep(sleepNumber);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    @Override
    public void update(Observable object, Object arg) {
        AbstractGameModifier modifier = (AbstractGameModifier) object;
        modifier.read(this);
    }

    /**
     *
     * @param newObjects
     */
    @Override
    public void addBullet(Bullet... newObjects) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
