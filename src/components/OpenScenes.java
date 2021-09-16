package components;

import JGames2D.JGEngine;
import JGames2D.JGLevel;
import JGames2D.JGSprite;
import JGames2D.JGTimer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.net.URL;

import static utils.Contants.MENUSCENE;

public class OpenScenes extends JGLevel {

    JGTimer time = null;

    JGSprite enterprise = null;

    int state = 0;


    private URL createURL(String pathImage){

        return getClass().getResource(pathImage);

    }


    @Override
    public void execute() {

        if(state == 0){

            enterprise.zoom.setXY(enterprise.zoom.getX()-0.05, enterprise.zoom.getY()-0.05);

            if (enterprise.zoom.getX()<=0.5){

                state = 1;
            }

        }else if(state == 1) {


            time.update();

            if (time.isTimeEnded()){

                gameManager.setCurrentLevel(MENUSCENE);

                return;
            }

        }



        if (gameManager.inputManager.mousePressed()){

            gameManager.setCurrentLevel(MENUSCENE);

            return;
        }



        if (gameManager.inputManager.keyPressed(KeyEvent.VK_ESCAPE)){

            gameManager.finish();
        }

        if (gameManager.inputManager.keyPressed(KeyEvent.VK_SPACE)){

            gameManager.setCurrentLevel(MENUSCENE);
            return;
        }
    }

    @Override
    public void init() {

        enterprise = this.createSprite(createURL("/Images/spr_produtors.png"), 1, 1);

        enterprise.position.setXY(gameManager.windowManager.width/2, gameManager.windowManager.height/2);

        enterprise.zoom.setXY(2, 2);

        time= new JGTimer(3000);

        gameManager.windowManager.setBackgroundColor(Color.YELLOW);
    }
}
