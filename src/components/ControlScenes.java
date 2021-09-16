package components;

import JGames2D.JGLevel;
import JGames2D.JGSprite;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.net.URL;

import static utils.Contants.MENUSCENE;

public class ControlScenes extends JGLevel {


    JGSprite controls = null;

    private URL createURL(String pathImage){

        return getClass().getResource(pathImage);

    }

    @Override
    public void execute() {

        if (gameManager.inputManager.keyPressed(KeyEvent.VK_ESCAPE)){

            gameManager.setCurrentLevel(MENUSCENE);
            return;
        }

    }

    @Override
    public void init() {

        controls = this.createSprite(createURL("/Images/controls.png"),1,1);

        controls.position.setXY(gameManager.windowManager.width/2, gameManager.windowManager.height/2);

        controls.zoom.setXY(0.5,0.5);
        gameManager.windowManager.setBackgroundColor(Color.ORANGE);

    }
}
