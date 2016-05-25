package lazarus;

import java.util.Observer;
import java.util.Random;
import java.io.IOException;
import java.io.InputStreamReader;
import java.awt.Image;
import java.awt.Point;
import java.io.BufferedReader;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import wingman.game.PlayerShip;
import wingman.modifiers.AbstractGameModifier;

/**
 *
 * @author noslide
 */
public class LazarusLevel extends AbstractGameModifier implements Observer {

    String filename;
    BufferedReader level;
    Box currentBox, nextBox;
    int w, h;

    /**
     *
     * @param filename
     */
    public LazarusLevel(String filename) {
        this.filename = filename;
        w = 16;
        h = 12;
    }

    /**
     *
     * @param theObject
     */
    public void read(Object theObject) {
    }

    /**
     *
     */
    public void load() {
        LazarusWorld world = LazarusWorld.getLazarus();
        try {
            this.level = new BufferedReader(new InputStreamReader(LazarusWorld.class.getResource(this.filename).openStream()));
            String line = this.level.readLine();
            this.h = 0;
            this.w = line.length();
            while (line != null) {
                for (int n = line.length(), i = 0; i < n; i++) {
                    char c = line.charAt(i);
                    if (c == 'w') {
                        Box wall = new Box(new Point(i * 40, this.h * 40), new Point(0, 0), 5, (Image) LazarusWorld.sprites.get("wall"));
                        world.wallArray.add(wall);
                    }
                    if (c == 's') {
                        StopButton stopbutton = new StopButton(i, this.h);
                        world.stopButtons.add(stopbutton);
                    }
                    if (c == 'p') {
                        int[] controls = {37, 0, 39, 0, 0};
                        world.addNewPlayer(new PlayerShip[]{new Lazarus(new Point(i * 40, this.h * 40), (Image) LazarusWorld.sprites.get("player1"), controls, "Player1")});
                    }
                    if (c == 'b') {
                        Box firstBox = null;
                        switch (new Random().nextInt((4 - 1) + 1) + 1) {
                            case 1:
                                firstBox = new Box(new Point(i * 40, this.h * 40), new Point(0, 0), 1, (Image) LazarusWorld.sprites.get("CardBox"));
                                break;
                            case 2:
                                firstBox = new Box(new Point(i * 40, this.h * 40), new Point(0, 0), 2, (Image) LazarusWorld.sprites.get("WoodBox"));
                                break;
                            case 3:
                                firstBox = new Box(new Point(i * 40, this.h * 40), new Point(0, 0), 3, (Image) LazarusWorld.sprites.get("MetalBox"));
                                break;
                            case 4:
                                firstBox = new Box(new Point(i * 40, this.h * 40), new Point(0, 0), 4, (Image) LazarusWorld.sprites.get("StoneBox"));
                                break;
                        }
                        world.addBckgrd(firstBox);
                        this.nextBox = firstBox;
                        this.currentBox = firstBox;
                    }
                }
                line = this.level.readLine();
                this.h += 1;
            }
            this.level.close();
        } catch (IOException ex) {
            Logger.getLogger(LazarusLevel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (LazarusWorld.getLazarus().boxesFalling.size() < 1 && !LazarusWorld.getLazarus().menu.isWaiting()) {
            Box nextBox = getRandomBox(0, 440);
            this.currentBox = this.nextBox;
            Lazarus player = (Lazarus) LazarusWorld.getLazarus().players.listIterator().next();
            this.currentBox.setLocation(new Point(player.getLocation().x, 0));
            this.currentBox.setSpeed(new Point(0, 2));
            LazarusWorld.getLazarus().boxesFalling.add(this.currentBox);
            this.nextBox = nextBox;
            LazarusWorld.getLazarus().boxArray.add(this.nextBox);
            setChanged();
            notifyObservers();
        }
        if (LazarusWorld.getLazarus().gameOver) {
            LazarusWorld.getLazarus().clock.deleteObserver(this);
        }
    }

    private Box getRandomBox(int x, int y) {
        Box randomBox = null;
        switch (new Random().nextInt((4 - 1) + 1) + 1) { // 
            case 1:
                randomBox = new Box(new Point(x, y), new Point(0, 0), 1, (Image) LazarusWorld.sprites.get("CardBox"));
                break;
            case 2:
                randomBox = new Box(new Point(x, y), new Point(0, 0), 2, (Image) LazarusWorld.sprites.get("WoodBox"));
                break;
            case 3:
                randomBox = new Box(new Point(x, y), new Point(0, 0), 3, (Image) LazarusWorld.sprites.get("MetalBox"));
                break;
            case 4:
                randomBox = new Box(new Point(x, y), new Point(0, 0), 4, (Image) LazarusWorld.sprites.get("StoneBox"));
                break;
        }
        return randomBox;
    }
}
