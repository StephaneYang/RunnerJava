import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class GameScene extends Scene {
    protected static double windowX = 600, windowY = 300;
    static double camOriginX, camOriginY, camX, camY;
    Pane pane;
    staticThing desertL = new staticThing("C:\\imgRunner\\BG12.png", 0, 0, 0);
    staticThing desertR = new staticThing("C:\\imgRunner\\BG12.png", 800, 0, 0);
    staticThing hearts0 = new staticThing("C:\\imgRunner\\hearts0.png", 10, 0, 50);
    staticThing hearts1 = new staticThing("C:\\imgRunner\\hearts1.png", 10, 0, 50);
    staticThing hearts2 = new staticThing("C:\\imgRunner\\hearts2.png", 10, 0, 50);
    staticThing hearts3 = new staticThing("C:\\imgRunner\\hearts3.png", 10, 0, 50);
    Camera cam;
    Hero hero = new Hero(0, 122);
    private int numberOfLives;
    AnimationTimer timer = new AnimationTimer() {
        public void handle(long time) {

            hero.update();
            Camera.update(time, hero);
            update(time);
            hero.temps++;

            hero.x += 3; //évolution de la position du héros

            if (hero.y == 0) hero.jumpLvl = 0;// remise à 0 de la limite de saut

            //Mise à jour des valeurs de camX et camY
            camX = hero.x + camOriginX;
            camY = hero.y + camOriginY;
        }
    };

    public GameScene(Pane pane, double camOriginX, double camOriginY) {
        super(pane, windowX, windowY, true);
        GameScene.camOriginX = camOriginX;
        GameScene.camOriginY = camOriginY;
        numberOfLives = 2;

        //camX et camY correspondent ensemble à la position du hero imaginaire* + les coordonnées (dans le paysage) du coin supérieur gauche de la caméra
        //*imaginaire car hero.x et hero.y ne correspondent pas à la position qu'on voit sur la fenêtre (il reste dans la fenêtre et ne quitte pas l'écran) mais on imagine un héros qui évolue dans x et y
        camX = hero.x + camOriginX;
        camY = hero.y + camOriginY;
        cam = new Camera(camX, camY); //transformation des coordonnées de la caméra (effet ressort)

        this.pane = pane;
        this.pane.getChildren().add(desertL.getImg());//mettre l'imageView dans pane (la fenêtre)
        this.pane.getChildren().add(desertR.getImg());
        this.pane.getChildren().add(hero.getImg());

        this.pane.setOnMouseClicked(event -> {
            hero.jump(event.getClickCount());
            if (hero.jumpLvl == 1) System.out.println("Jump");
            else if (hero.jumpLvl>2) System.out.println("You can not do more than double jump");
            else if (hero.jumpLvl==2) System.out.println("Double Jump");
        });

        timer.start();
    }

    public void update(long time) {

            if (camX>=800+camOriginX) {
                hero.x = 0; //repartir au début lorsqu'on atteint le bout de l'image
            }

            //-------------affichage_paysage_DEBUT-------------
            if (cam.getX() < 800-windowX) {
                desertL.imageView.setViewport(new Rectangle2D(cam.getX()%800, cam.getY(), windowX, windowY));
                desertL.imageView.setX(0);
                //desertR.imageView.setViewport(new Rectangle2D(cam.getX()%800, cam.getY(), hero.windowX, hero.windowY));
                desertR.imageView.setX(800);
            } else if ((cam.getX() >= 800-windowX) && (cam.getX() < 800)) {
                Rectangle2D viewportRect = new Rectangle2D(cam.getX(), cam.getY(), 800 - cam.getX(), windowY);
                desertL.imageView.setViewport(viewportRect);
                desertL.imageView.setX(0);

                Rectangle2D viewportRect1 = new Rectangle2D(0, cam.getY(), cam.getX()+windowX-800, windowY);
                desertR.imageView.setViewport(viewportRect1);
                desertR.imageView.setX(800 - cam.getX());
            } else if (cam.getX()>=800) {
                desertR.imageView.setViewport(new Rectangle2D(cam.getX()%800, cam.getY(), 1600 - cam.getX(), windowY));
                desertR.imageView.setX(0);
                if ((cam.getX()+windowX-1600)>=0) { // Pour éviter des erreurs
                Rectangle2D viewportRect2 = new Rectangle2D(0, cam.getY(), cam.getX()+windowX-1600, windowY);
                desertL.imageView.setViewport(viewportRect2);
                }
                desertL.imageView.setX(1600 - cam.getX());
            }
            //-------------affichage_paysage_FIN-------------

            //-------------position du heros sur l'écran_DEBUT-------------
            hero.sprite.setX(AnimatedThing.heroBaseX - (cam.getX()-camX)); //on soustrait la position du milieu par la transformation (transformation = différence entre le résultat de l'équation diff et l'entrée)
                                                                  //si la caméra est en retard par rapport au héros, la tranformation est négative -> le sprite du héros part à droite sur la fenêtre
                                                                  //lorsqu'il n'y aura plus de retard, le sprite revient au milieu (transformation = nulle)
            hero.sprite.setY(AnimatedThing.heroBaseY - (cam.getY()-camY));
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

            System.out.println("heroY ="+hero.y+", JumpLvl ="+hero.jumpLvl+", ax ="+cam.getAX()+", vx ="+cam.getVX());

    }

    public void SetLife(int life) {
        this.numberOfLives = life;
    }
}
