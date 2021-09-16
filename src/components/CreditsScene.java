package components;

import JGames2D.JGLevel;
import JGames2D.JGSprite;
import JGames2D.JGVector2D;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.net.URL;

import static utils.Contants.MENUSCENE;

public class CreditsScene extends JGLevel {


    JGSprite credits = null;

    private URL createURL(String pathImage){

        return getClass().getResource(pathImage);

    }


    @Override
    public void execute() {

        if(gameManager.inputManager.keyPressed(KeyEvent.VK_ESCAPE)){
            gameManager.setCurrentLevel(MENUSCENE);
            return;
        }

        if(credits.isMoveEnded()){
            gameManager.setCurrentLevel(MENUSCENE);
            return;

        }

    }

    @Override
    public void init() {

        credits = this.createSprite(createURL("/Images/spr_credits.png"), 1 ,1);

        credits.position.setXY(gameManager.windowManager.width/2, gameManager.windowManager.height+credits.frameHeight/2);

        credits.moveTo(8000, new JGVector2D(credits.position.getX(), -credits.frameHeight/2));

        gameManager.windowManager.setBackgroundColor(Color.CYAN);

    }
}
