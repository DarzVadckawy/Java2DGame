package components;

import JGames2D.JGLevel;
import JGames2D.JGSoundEffect;
import JGames2D.JGSoundManager;
import JGames2D.JGSprite;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.net.URL;

import static utils.Contants.*;

public class MenuScenes extends JGLevel {


    JGSprite title = null;

    //JGSprite test = null;

    JGSprite pointer = null;

    JGSprite [] buttons = new JGSprite[4];

    String [] names = {"btn_play", "btn_controls", "btn_credits", "btn_exit"};

    JGSoundEffect click = null;

    JGSoundEffect select = null;



    public MenuScenes(){

        click = JGSoundManager.loadSoundEffect(createURL("/Sounds/toc.wav"));

        select = JGSoundManager.loadSoundEffect(createURL("/Sounds/click.wav"));

    }




    private URL createURL(String pathImage){

        return getClass().getResource(pathImage);

    }


    @Override
    public void execute() {

        pointer.position.setXY(gameManager.inputManager.getMousePosition().getX(),
                gameManager.inputManager.getMousePosition().getY());

//        if (gameManager.inputManager.keyPressed(KeyEvent.VK_LEFT)){
//
//            test.setMirror(true);
//        }
//
//        if (gameManager.inputManager.keyPressed(KeyEvent.VK_RIGHT)){
//
//            test.setMirror(false);
//        }

        if (gameManager.inputManager.keyPressed(KeyEvent.VK_SPACE)){

            gameManager.setCurrentLevel(OPENSCENE);
            return;

        } if (gameManager.inputManager.keyPressed(KeyEvent.VK_ESCAPE )){

            gameManager.finish();

        }

        for (int i = 0; i<buttons.length; i++){

            if(pointer.collide(buttons[i])){

                if(buttons[i].getCurrentAnimationIndex() == 0) {

                    buttons[i].setCurrentAnimation(1);

                    click.play();

                }
                
                if (gameManager.inputManager.mouseClicked()){

                    select.play();

                    if(i == 0){

                        gameManager.setCurrentLevel(GAMESCENE);
                        return;

                    } else if (i == 1){

                        gameManager.setCurrentLevel(CONTROLSCENE);
                        return;


                    } else if (i == 2){

                        gameManager.setCurrentLevel(CREDITSCENE);
                        return;

                    } else if (i == 3){

                        gameManager.finish();
                    }

                }

            } else {

                buttons[i].setCurrentAnimation(0);
            }

        }

    }

    @Override
    public void init() {

//        test = this.createSprite(createURL("/Images/spr_buldoguewalk.png"), 4, 4);
//
//        test.position.setXY(gameManager.windowManager.width/2, gameManager.windowManager.height/2);
//
//        test.addAnimation(15, true ,0 ,11);

       // test.setMirror(true);

        title = this.createSprite(createURL("/Images/gametitle.png"),1,1);

        title.position.setXY(gameManager.windowManager.width/2, title.frameHeight/2);

        for (int i = 0; i<buttons.length; i++){

            buttons[i] = this.createSprite(createURL("/Images/" + names[i] + ".png"), 2, 1);

            buttons[i].addAnimation(1, false, 0);

            buttons[i].addAnimation(1, false, 1);

            buttons[i].zoom.setXY(0.6, 0.6);

            buttons[i].position.setXY(gameManager.windowManager.width/2, 180+i*120);
        }

        pointer = this.createSprite(createURL("/Images/mira.png"), 1, 1);

        gameManager.windowManager.setBackgroundColor(Color.GREEN);
    }
}
