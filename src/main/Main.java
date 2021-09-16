package main;

import JGames2D.*;
import components.*;

public class Main {

    public static void main (String [] args){

            JGEngine gEngine = new JGEngine();
            
            gEngine.windowManager.setResolution(800, 600, 32);

            gEngine.windowManager.setfullScreen(false);


            gEngine.addLevel(new OpenScenes());

            gEngine.addLevel(new MenuScenes());

            gEngine.addLevel(new GameScenes());

            gEngine.addLevel(new ControlScenes());

            gEngine.addLevel(new CreditsScene());


            gEngine.start();

    }


}
