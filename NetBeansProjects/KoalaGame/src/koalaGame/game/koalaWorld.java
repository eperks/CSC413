package koalaGame.game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import tankGame.game.*;

public class koalaWorld extends JPanel implements Runnable{
    static JFrame f;
    private BufferedImage bimg;
    private Thread thread;
    Graphics2D g2;
    protected int w,h; //width and height
    private Background background;
    protected ArrayList<koalaPlayer> players;
    ArrayList<Sprites> sp;
    ArrayList<Sprites> rescued;
    private koalaGameEvents gameEvents;
    KeyControl key;
    MouseControl mouse;
    private static final koalaWorld game = new koalaWorld();
    public static HashMap<String, BufferedImage> sprites;
    private static koalaLevel level;
    Point mapSize;
    public static final GameSounds sound = new GameSounds();
    String[] levels = new String[2]; //name of each level file
    int lev;

    private void initGameWorld(int lev){
        this.lev = lev;
        levels[0] = "resources/level0.txt";
        levels[1] = "resources/level1.txt";
        if(lev+1 > levels.length){//after last level
            f.dispose();
            noMoreLevel.init();
        }
        setFocusable(true); //set focus to use the keylistener
        sprites = new HashMap<String, BufferedImage>();
        loadSprites();
        players = new ArrayList<>();
        sp = new ArrayList<>();
        rescued = new ArrayList<>();
        level = new koalaLevel(levels[lev]);
        level.load();
        mapSize = new Point(level.w * 40, level.h * 40);
        //background
        background = new Background(mapSize.x,mapSize.y);
        background.initBackground(sprites.get("background"));
        gameEvents = new koalaGameEvents();
        key = new KeyControl();
        mouse = new MouseControl();
        addKeyListener(key);
        addMouseListener(mouse);
    }

    public void loadSprites(){
        sprites.put("background", getSprite("resources/Background.png"));
        sprites.put("player", getSprite("resources/player.png"));
        sprites.put("wall1", getSprite("resources/Wall.png"));
        sprites.put("tnt", getSprite("resources/TNT.png"));
        sprites.put("exit", getSprite("resources/Exit1.png"));
        sprites.put("rescued", getSprite("resources/Rescued.png"));
        sprites.put("restart", getSprite("resources/Restart.png"));
        sprites.put("koalaRescued", getSprite("resources/koalaRescued.png"));
        sprites.put("detonator", getSprite("resources/detonator.png"));
    }
    public BufferedImage getSprite(String name){
        BufferedImage img = null;
        try {
            img = ImageIO.read(getClass().getResource(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    public class KeyControl extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            gameEvents.pressed(e,players);
        }
        public void keyReleased(KeyEvent e) {
            gameEvents.released(e, players);
        }
    }
    public class MouseControl extends MouseAdapter{
        public void mouseClicked(MouseEvent e){gameEvents.clicked(e);}
    }

    public void addSprite(BufferedImage img, int x, int y, int sprite, int total, String type) {sp.add(new Sprites(img, x,y,sprite,total,type));}
    public void addPlayer1(BufferedImage img, int x, int y){
        players.add(new koalaPlayer(img, x, y, 0, 32));
    }

    @Override
    public void paint(Graphics g){
        if(bimg == null) {
            Dimension windowSize = getSize();
            bimg = (BufferedImage) createImage(windowSize.width,
                    windowSize.height);
            g2 = bimg.createGraphics();
        }
        draw();
        g.drawImage(bimg, 0, 0, this);
    }

    private void draw(){
        background.drawBackground(g2);
        gameEvents.process(players, sp);

        for(int i=0; i<sp.size(); i++)
            if(sp.get(i).getVisible())
                sp.get(i).draw(g2,this);

        for(int i=0; i<players.size(); i++) {
            if(players.get(i).show)
                players.get(i).draw(g2, this);
        }
        g2.drawImage(game.getSprites().get("rescued"),10,5,this);
        g2.drawImage(game.getSprites().get("restart"), 530, 5, this);
        for(int i=0; i<rescued.size();i++)
            if(rescued.get(i).getVisible())
                rescued.get(i).draw(g2,this);
        for(int i=0; i<players.size(); i++){
        }
    }

    @Override
    public void run() {
        Thread me = Thread.currentThread();
        while (thread == me) {
            repaint();
            try {
                thread.sleep(25);
            } catch (InterruptedException e) {
                break;
            }

        }
    }

    public void setDimensions(int w, int h) {
        this.w = w;
        this.h = h;
    }

    public static koalaWorld getInstance() {
        return game;
    }
    public HashMap<String, BufferedImage> getSprites() {
        return sprites;
    }

    public void start() {
        thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
    }

    public void restart(){
        removeKeyListener(key);
        removeMouseListener(mouse);
        game.initGameWorld(lev);
    }

    public void nextLevel(){
        removeKeyListener(key);
        removeMouseListener(mouse);
        f.dispose();
        init(lev+1);
    }

    public static void init(int lev){
        final koalaWorld game = koalaWorld.getInstance();
        f = new JFrame("KoalaGame");
        f.addWindowListener(new WindowAdapter() {
            public void windowGainedFocus(WindowEvent e) {
                game.requestFocusInWindow();
            }
        });
        f.getContentPane().add("Center", game);
        f.pack();
        f.setSize(new Dimension(640, 520));
        f.setLocationRelativeTo(null); // game opens in the middle of the screen
        game.setDimensions(640, 520);
        game.initGameWorld(lev);
        f.setVisible(true);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.start();
    }
}