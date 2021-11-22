import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.*;

public class GameScene extends Scene {
    protected static double windowX = 600, windowY = 300;//taille de la fenêtre x=[0-800] et y=[0-400], if faut garder une proportion 2:1
    static double camOriginX, camOriginY, camX, camY;
    Pane pane;
    staticThing desertL = new staticThing("C:\\imgRunner\\BG12.png", 0, 0, 0);
    staticThing desertR = new staticThing("C:\\imgRunner\\BG12.png", 800, 0, 0);
    staticThing hearts0 = new staticThing("C:\\imgRunner\\hearts0.png", 10, 0, 50);
    staticThing hearts1 = new staticThing("C:\\imgRunner\\hearts1.png", 10, 0, 50);
    staticThing hearts2 = new staticThing("C:\\imgRunner\\hearts2.png", 10, 0, 50);
    staticThing hearts3 = new staticThing("C:\\imgRunner\\hearts3.png", 10, 0, 50);
    Camera cam;
    Hero hero = new Hero(0.05*600, 0.8*300-50);
    ArrayList<Foe> alFoe=new ArrayList<Foe>();//Liste d'ennemis

    private int numberOfLives;
    public static int left, right, up, down, shoot;
    AnimationTimer timer = new AnimationTimer() {
        public void handle(long time) {

            //Mise à jour des valeurs de camX et camY
            camX = hero.x + camOriginX;
            camY = hero.y + camOriginY;

            hero.update();
            for(int i=0;i<alFoe.size();i++)
            {
                alFoe.get(i).update();
            }
            Camera.update(time, hero);
            update(time);
            AnimatedThing.temps++;

            if (right == 1) {
                hero.incrementation = 5;//hero run faster
                hero.timeFrames = 5;//so the period of the animation should be lower
            } else if (left == 1) {
                hero.incrementation = 1;//hero run slower
                hero.timeFrames = 30;//so the period of the animation should be higher
            } else if (down == 1) {
                hero.incrementation = 0;//hero is stopped
                hero.timeFrames = Integer.MAX_VALUE;//so the period should be infinite
            } else {
                hero.incrementation = 3;//hero run at normal speed
                hero.timeFrames = 7;//so the period stay at the initialization level (=7)
            }
            hero.x += hero.incrementation;//évolution de la position du héros

            if (up == 1) {
                hero.jump();//hero jump
            }

            if (hero.y == 0) hero.jumpLvl = 0;//remise à 0 de la limite de saut

            if (camX>=800+camOriginX) {
                hero.x = 0; //repartir au début lorsqu'on atteint le bout de l'image
            }

            if (hero.detectCollision(alFoe.get(0).getHitBox())==1) System.out.println("Collision !");
            else System.out.println(".");
        }
    };

    public GameScene(Pane pane, double camOriginX, double camOriginY) {
        super(pane, windowX, windowY, true);
        GameScene.camOriginX = camOriginX;
        GameScene.camOriginY = camOriginY;
        numberOfLives = 1;

        //camX et camY correspondent ensemble à la position du hero imaginaire* + les coordonnées (dans le paysage) du coin supérieur gauche de la caméra
        //*imaginaire car hero.x et hero.y ne correspondent pas à la position qu'on voit sur la fenêtre (il reste dans la fenêtre et ne quitte pas l'écran) mais on imagine un héros qui évolue dans x et y
        camX = hero.x + camOriginX;
        camY = hero.y + camOriginY;
        cam = new Camera(camX, camY); //transformation des coordonnées de la caméra (effet ressort)

        this.pane = pane;
        this.pane.getChildren().add(desertL.getImg());//mettre l'imageView dans pane (la fenêtre)
        this.pane.getChildren().add(desertR.getImg());
        this.pane.getChildren().add(hero.getImg());

        alFoe.add(new Foe(150,190));//ajouter un ennemi
        this.pane.getChildren().add(alFoe.get(0).getImg());
        alFoe.get(0).sprite.setX(alFoe.get(0).BaseX);
        alFoe.get(0).sprite.setY(alFoe.get(0).BaseY);

        timer.start();
    }

    public void update(long time) {

        //-------------affichage_paysage_DEBUT-------------
        if (cam.getX() < 0) {//si la caméra est avant le 1er background (situation possible à cause du système ressort et le retour à 0 de hero.x)
            desertR.imageView.setViewport(new Rectangle2D(800+cam.getX(), cam.getY(), 0-cam.getX(), windowY));
            desertR.imageView.setX(0);

            desertL.imageView.setViewport(new Rectangle2D(0, cam.getY(), windowX+cam.getX(), windowY));
            desertL.imageView.setX(0-cam.getX());

        } else if (cam.getX() < 800-windowX) {//si la caméra est dans le 1er background
            desertL.imageView.setViewport(new Rectangle2D(cam.getX(), cam.getY(), windowX, windowY));
            desertL.imageView.setX(0);

            desertR.imageView.setX(-800);

        } else if ((cam.getX() >= 800-windowX) && (cam.getX() < 800)) {//si la caméra est entre le 1er et le 2e background
            desertL.imageView.setViewport(new Rectangle2D(cam.getX(), cam.getY(), 800-cam.getX(), windowY));
            desertL.imageView.setX(0);

            desertR.imageView.setViewport(new Rectangle2D(0, cam.getY(), cam.getX()+windowX-800, windowY));
            desertR.imageView.setX(800-cam.getX());

        } else if (cam.getX() >= 800) {//si la caméra est dans le 2e background
            desertR.imageView.setViewport(new Rectangle2D(cam.getX()%800, cam.getY(), 1600-cam.getX(), windowY));
            desertR.imageView.setX(0);

            if ((cam.getX()+windowX-1600) >= 0) { //si la caméra dépasse le 2e background (il serait dans un "3e" background)
                desertL.imageView.setViewport(new Rectangle2D(0, cam.getY(), cam.getX()+windowX-1600, windowY));
                }
            desertL.imageView.setX(1600-cam.getX());
        }
        //-------------affichage_paysage_FIN-------------

        //-------------position du heros sur l'écran_DEBUT-------------
        hero.sprite.setX(hero.BaseX - (cam.getX()-camX)); //on soustrait la position du milieu par la transformation (transformation = différence entre le résultat de l'équation diff et l'entrée)
                                                                  //si la caméra est en retard par rapport au héros, la tranformation est négative -> le sprite du héros part à droite sur la fenêtre
                                                                  //lorsqu'il n'y aura plus de retard, le sprite revient au milieu (transformation = nulle)
        hero.sprite.setY(hero.BaseY - (cam.getY()-camY));
        //-------------position du heros sur l'écran_FIN-------------

        //-------------mise à jour de l'affichage des coeurs_DEBUT-------------
        pane.getChildren().remove(hearts0.getImg());
        pane.getChildren().remove(hearts1.getImg());
        pane.getChildren().remove(hearts2.getImg());
        pane.getChildren().remove(hearts3.getImg());
        if (numberOfLives == 0) pane.getChildren().add(hearts0.getImg());
        if (numberOfLives == 1) pane.getChildren().add(hearts1.getImg());
        if (numberOfLives == 2) pane.getChildren().add(hearts2.getImg());
        if (numberOfLives >= 3) pane.getChildren().add(hearts3.getImg());
        //-------------mise à jour de l'affichage des coeurs_FIN-------------

    }

    public void SetLife(int life) {
        this.numberOfLives = life;
    }
}
