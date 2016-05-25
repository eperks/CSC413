package koalaGame.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class koalaLevel {
    BufferedReader level;
    String filename;
    int w, h;

    public koalaLevel(String name){
        String line;
        try {
            filename = name;
            level = new BufferedReader(new InputStreamReader(getClass().getResource(filename).openStream()));
            line = level.readLine();
            w = line.length();
            h=0;
            while(line!=null){
                h++;
                line = level.readLine();
            }
            level.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void load(){
        koalaWorld game = koalaWorld.getInstance();
        String line;
        try {
            level = new BufferedReader(new InputStreamReader(koalaWorld.class.getResource(filename).openStream()));
            line = level.readLine();
            w = line.length();
            h=0;
            while(line!=null){
                for(int i = 0, n = line.length() ; i < n ; i++) {
                    char c = line.charAt(i);

                    if(c=='0'){
                        game.addSprite(game.getSprites().get("wall1"),i*40,h*40,0,16,"wall");
                    }
                    if(c=='1'){
                        game.addSprite(game.getSprites().get("wall1"),i*40,h*40,1,16,"wall");
                    }
                    if(c=='2'){
                        game.addSprite(game.getSprites().get("wall1"),i*40,h*40,2,16,"wall");
                    }
                    if(c=='3'){
                        game.addSprite(game.getSprites().get("wall1"),i*40,h*40,3,16,"wall");
                    }
                    if(c=='4'){
                        game.addSprite(game.getSprites().get("wall1"),i*40,h*40,4,16,"wall");
                    }
                    if(c=='5'){
                        game.addSprite(game.getSprites().get("wall1"),i*40,h*40,5,16,"wall");
                    }
                    if(c=='6'){
                        game.addSprite(game.getSprites().get("wall1"),i*40,h*40,6,16,"wall");
                    }
                    if(c=='7'){
                        game.addSprite(game.getSprites().get("wall1"),i*40,h*40,7,16,"wall");
                    }
                    if(c=='8'){
                        game.addSprite(game.getSprites().get("wall1"),i*40,h*40,8,16,"wall");
                    }
                    if(c=='9'){
                        game.addSprite(game.getSprites().get("wall1"),i*40,h*40,9,16,"wall");
                    }
                    if(c=='a'){
                        game.addSprite(game.getSprites().get("wall1"),i*40,h*40,10,16,"wall");
                    }
                    if(c=='b'){
                        game.addSprite(game.getSprites().get("wall1"),i*40,h*40,11,16,"wall");
                    }
                    if(c=='c'){
                        game.addSprite(game.getSprites().get("wall1"),i*40,h*40,12,16,"wall");
                    }
                    if(c=='d'){
                        game.addSprite(game.getSprites().get("wall1"),i*40,h*40,13,16,"wall");
                    }
                    if(c=='e'){
                        game.addSprite(game.getSprites().get("wall1"),i*40,h*40,14,16,"wall");
                    }
                    if(c=='f'){
                        game.addSprite(game.getSprites().get("wall1"),i*40,h*40,15,16,"wall");
                    }
                    if(c=='t'){//tnt
                        game.addSprite(game.getSprites().get("tnt"), i * 40, h * 40,0,1, "tnt");
                    }
                    if(c=='x'){//exit
                        game.addSprite(game.getSprites().get("exit"), i * 40, h * 40, 0, 1, "exit");
                    }
                    if(c=='w'){//detonator
                        game.addSprite(game.getSprites().get("detonator"), i * 40, h * 40, 0, 2, "detonator");
                    }
                    if(c=='p'){
                        game.addPlayer1(game.getSprites().get("player"),i*40,h*40);
                    }
                }
                h++;
                line = level.readLine();
            }
            level.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
