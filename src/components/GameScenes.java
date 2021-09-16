package components;

import JGames2D.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import static utils.Contants.MENUSCENE;

public class GameScenes extends JGLevel {

    JGLayer layer = null;

    JGSprite airplane = null;

    JGSprite imgGameOver = null;

    ArrayList<JGSprite> vectorEnemy = null;

    ArrayList<JGSprite> vectorShoots = null;

    ArrayList<JGSprite> vectorExplosions = null;

    JGTimer shootTime = null;

    JGTimer enemyTIme = null;

    JGTimer recoveryTime = null;

    Random random = new Random();

    JGSoundEffect shoot = null;

    JGSoundEffect explosion = null;

    JGMusic music = null;

    int totalLifes = 2;

    int totalPoints = 0;

    boolean gameOver = false;

    JGFont fontlifes = null;

    JGFont fontPoints = null;

    int timePoints = 0;


    public void colisionPlayerEnemy(){

        recoveryTime.update();


        if (!recoveryTime.isTimeEnded()){

           airplane.visible = !airplane.visible;
           return;

        } else {

            airplane.visible = true;
        }

        for (JGSprite enemy : vectorEnemy){

            if (enemy.visible && enemy.collide(airplane)){

                recoveryTime.restart(3000);

                enemy.visible = false;

                totalLifes--;

                fontlifes.setText("Vidas: " + totalLifes);

                if (totalLifes < 2){

                    fontlifes.setColor(Color.RED);
                }
                
                if (totalLifes == 0){

                    imgGameOver.visible = true;

                    gameOver = true;
                }

                break;
            }
        }


    }


    public GameScenes (){

        shoot = JGSoundManager.loadSoundEffect(createURL("/Sounds/SHOT.wav"));

        explosion = JGSoundManager.loadSoundEffect(createURL("/Sounds/boom.wav"));

        music = JGSoundManager.loadMusic(createURL("/Sounds/DangerZone.mp3"));
    }

    public void updateExplosion(){

        for (JGSprite explosion : vectorExplosions){

            if(explosion.getCurrentAnimation().isEnded()){

                explosion.visible = false;
            }

        }

    }


    public void createExplosion(int px, int py){

        JGSprite newExplosion = null;

        explosion.play();

        for (JGSprite explosion : vectorExplosions){

            if (explosion.visible == false){

                newExplosion = explosion;

                newExplosion.visible = true;

                newExplosion.restartAnimation();

                break;
            }

        }

        if(newExplosion == null){


            newExplosion = createSprite(createURL("/Images/spr_bigexplosion.png"),2,4);

            newExplosion.addAnimation(10, false, 0,7);

            vectorExplosions.add(newExplosion);
        }

        newExplosion.position.setXY(px,py);


    }



    public void updatePoints(){

        if (timePoints == 0){
            return;
        }

        timePoints--;
        totalPoints++;

        fontPoints.setText(String.format("%05d", totalPoints));

    }




    public void colisionShootEnemy(){

        for (JGSprite enemy : vectorEnemy){

            for (JGSprite shoot : vectorShoots ){

                if (enemy.visible && shoot.visible && shoot.collide(enemy)){

                    timePoints+= 50;

                    shoot.visible = false;

                    enemy.visible = false;

                    createExplosion((int)enemy.position.getX(), (int)enemy.position.getY());

                    break;
                }
            }

        }

    }



    private URL createURL(String pathImage){

        return getClass().getResource(pathImage);

    }


    private void createEnemy(){

        enemyTIme.update();

        if (!enemyTIme.isTimeEnded()){

            return;
        }

        enemyTIme.restart();

        JGSprite newEnemy = null;

        for (JGSprite enemy : vectorEnemy){

            if(enemy.visible == false){

                newEnemy = enemy;
                newEnemy.visible =  true;
                break;
            }

        }


        if (newEnemy == null){
            newEnemy = createSprite(createURL("/Images/spr_enemy.png"), 1, 4);
            newEnemy.addAnimation(10, true, 0, 2);
            vectorEnemy.add(newEnemy);
        }

        newEnemy.position.setY(-newEnemy.frameHeight/2);

        int position = random.nextInt(3);

        if (position == 0){

            newEnemy.position.setX(newEnemy.frameWidth/2);
            newEnemy.direction.setX(1);

        } else if (position == 1){

            newEnemy.position.setX(gameManager.windowManager.width/2);
            newEnemy.direction.setX(0);

        } else if (position == 2){

            newEnemy.position.setX(gameManager.windowManager.width-newEnemy.frameWidth/2);
            newEnemy.direction.setX(-1);
        }

    }


    private void refreshEnemy(){

        for (JGSprite enemy : vectorEnemy){

            enemy.position.setY(enemy.position.getY()+10);
            enemy.position.setX(enemy.position.getX()+10*enemy.direction.getX());

            if (enemy.position.getY() > gameManager.windowManager.height+enemy.frameHeight/2){

                enemy.visible = false;
            }
        }


    }


    public void createShoots(){
        JGSprite newShoot = null;

        shoot.play();
        
        for (JGSprite shoots : vectorShoots){
            
            if (shoots.visible == false){
                newShoot = shoots;
                newShoot.visible = true;
                break;
            }
            
        }

        if(newShoot == null){

            newShoot = createSprite(createURL("/Images/spr_shot.png"),1,1);
            vectorShoots.add(newShoot);
        }


        newShoot.position.setXY(airplane.position.getX(),
                airplane.position.getY()-airplane.frameHeight/2-newShoot.frameHeight/2);


    }


    @Override
    public void execute() {

        layer.speed.setX(0);

        if(gameManager.inputManager.keyPressed(KeyEvent.VK_ESCAPE)){

            gameManager.setCurrentLevel(MENUSCENE);
            music.stop();
            return;
        }

        if(gameOver && gameManager.inputManager.keyPressed(KeyEvent.VK_ENTER)){


            airplane.visible = true;

            fontPoints.setText("00000");

            fontlifes.setText("Vidas: 3");

            fontlifes.setColor(Color.yellow);

            recoveryTime.restart(0);

            totalLifes = 3;

            totalPoints = 0;

            timePoints = 0;

            gameOver = false;

            imgGameOver.visible = false;


        }



        refreshShoot();

        createEnemy();

        refreshEnemy();

        colisionShootEnemy();

        updateExplosion();

        if (gameOver ){

            airplane.visible = false;

            return;
        }


        controllerPlayer();

        colisionPlayerEnemy();

        updatePoints();




    }

    @Override
    public void init() {

        totalPoints = 0;

        timePoints = 0;
        imgGameOver = createSprite(createURL("/Images/spr_gameover.png"), 1, 1);

        imgGameOver.position.setXY(gameManager.windowManager.width/2, gameManager.windowManager.height/2);

        imgGameOver.zoom.setXY(0.4, 0.4);

        imgGameOver.visible = false;



        fontPoints = this.createFont("arial", Color.yellow, 30, true, false);

        fontPoints.position.setXY(100, 100);

        fontPoints.setText("00000");


        fontlifes = this.createFont("arial", Color.yellow, 30, true, false);

        fontlifes.position.setXY(700, 100);

        fontlifes.setText("Vidas: 3");


        gameOver = false;
        
        totalLifes = 3;

        recoveryTime = new JGTimer(0);

        music.play();

        music.setNumberOfLoops(-1);

        enemyTIme = new JGTimer(3000);

        shootTime = new JGTimer(500);

        createSprite(createURL("/Images/spr_shot.png"),1,1).visible = false;

        createSprite(createURL("/Images/spr_enemy.png"),1,4).visible = false;

        createSprite(createURL("/Images/spr_bigexplosion.png"),2,4).visible = false;


        vectorShoots = new ArrayList<>();

        vectorEnemy = new ArrayList<>();

        vectorExplosions = new ArrayList<>();



        airplane = this.createSprite(createURL("/Images/spr_airplane.png"),1,4);
        airplane.position.setXY(gameManager.windowManager.width/2,
                500);

        layer = this.createLayer(createURL("/Images/spr_elements.png"), new JGVector2D(25 , 19)
        , new JGVector2D(32, 32), true);

        for (int i = 0; i < 19;i++){

            for(int j = 0; j < 25; j++){

                layer.setFrameIndex(i, j, 2);

            }

        }

        layer.setFrameIndex(2 ,7,8);
        layer.setFrameIndex(6 ,5, 9);
        layer.setFrameIndex(10 , 3,8 );
        layer.setFrameIndex(16, 20 ,8);
        layer.setFrameIndex(10, 18, 8);

        layer.speed.setY(5);

        gameManager.windowManager.setBackgroundColor(Color.darkGray);

    }

    private void refreshShoot(){

        for (JGSprite shoots: vectorShoots){

            if (shoots.position.getY()<-shoots.frameHeight/2){
                
                shoots.visible = false;
                
            } else {

                shoots.position.setY(shoots.position.getY()-10);
                
            }
            
        }
    }

    public void controllerPlayer(){

        shootTime.update();

        if (gameManager.inputManager.keyPressed(KeyEvent.VK_SPACE)){

            if(shootTime.isTimeEnded()){
                createShoots();
                shootTime.restart();
            }


        }




        if(gameManager.inputManager.keyPressed(KeyEvent.VK_LEFT)){


            if (airplane.position.getX() >= 50){

                airplane.position.setX(airplane.position.getX()-10);

                layer.speed.setX(5);

            }



        } else if(gameManager.inputManager.keyPressed(KeyEvent.VK_RIGHT)){

            if (airplane.position.getX() <= 750 ) {

                airplane.position.setX(airplane.position.getX() + 10);

                layer.speed.setX(-5);

            }
        }


        if(gameManager.inputManager.keyPressed(KeyEvent.VK_UP)){

            if (airplane.position.getY() >= 70) {

                airplane.position.setY(airplane.position.getY() - 10);

                // layer.speed.setY(5);
            }

        } else if(gameManager.inputManager.keyPressed(KeyEvent.VK_DOWN)) {

            if (airplane.position.getY() <= 530) {


                airplane.position.setY(airplane.position.getY() + 10);

                // layer.speed.setY(-5);
            }

        }


    }
}
