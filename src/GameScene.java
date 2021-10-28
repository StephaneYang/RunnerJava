import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class GameScene extends Scene {
    static Pane pane;
    static staticThing desertL = new staticThing("C:\\imgRunner\\desert.png", 0, 0, 0);
    static staticThing desertR = new staticThing("C:\\imgRunner\\desert.png", 800, 0, 0);
    static staticThing hearts0 = new staticThing("C:\\imgRunner\\hearts0.png", 10, 0, 50);
    static staticThing hearts1 = new staticThing("C:\\imgRunner\\hearts1.png", 10, 0, 50);
    static staticThing hearts2 = new staticThing("C:\\imgRunner\\hearts2.png", 10, 0, 50);
    static staticThing hearts3 = new staticThing("C:\\imgRunner\\hearts3.png", 10, 0, 50);
    static Camera cam;
    static Hero hero = new Hero(0, 86);
    static private int numberOfLives;
    static double camOriginX, camOriginY, camX, camY;

    public GameScene(Pane pane, double camOriginX, double camOriginY) {
        super(pane, hero.windowX, hero.windowY, true);
        GameScene.camOriginX = camOriginX;
        GameScene.camOriginY = camOriginY;
        numberOfLives = 3;
        GameScene.pane = pane;
        GameScene.pane.getChildren().add(desertL.getImg());//mettre l'imageView dans pane (la fenêtre)
        GameScene.pane.getChildren().add(desertR.getImg());

        timer.start();
    }

    AnimationTimer timer = new AnimationTimer() {
        public void handle(long time) {
            hero.update(time, 0);
            Camera.update(time, hero);
            GameScene.update(time);
            hero.temps++;

        }
    };

    public static void update(long time) {
        if (hero.temps % hero.timeFrames == 0) {
            //camX et camY correspondent ensemble à la position du hero imaginaire* + les coordonnées (dans le paysage) du coin supérieur gauche de la caméra
            //*imaginaire car hero.x et hero.y ne correspondent pas à la position qu'on voit sur la fenêtre (il reste dans la fenêtre et ne quitte pas l'écran) mais on imagine un hero qui évolue dans x et y
            GameScene.camX = hero.x + camOriginX;
            GameScene.camY = hero.y + camOriginY;

            hero.x += 10; //évolution de la position du héros
            cam = new Camera(camX, camY);
            if (cam.getX() < 800-hero.windowX) {
                desertL.imageView.setViewport(new Rectangle2D(cam.getX(), cam.getY(), hero.windowX, hero.windowY));
            } else if ((cam.getX() >= 800-hero.windowX) && (cam.getX() < 800)) {
                Rectangle2D viewportRect = new Rectangle2D(cam.getX(), cam.getY(), 800 - cam.getX(), hero.windowY);
                desertL.imageView.setViewport(viewportRect);
                Rectangle2D viewportRect1 = new Rectangle2D(0, cam.getY(), cam.getX()+hero.windowX-800, hero.windowY);
                desertR.imageView.setViewport(viewportRect1);
                desertR.imageView.setX(800 - cam.getX());
            } else if (cam.getX()>=800) {
                desertR.imageView.setViewport(new Rectangle2D(cam.getX()-800, cam.getY(), hero.windowX, hero.windowY));
                desertR.imageView.setX(0);
                if (cam.getX()>=800+camOriginX) {
                hero.x = 0;
                }
            }

            hero.sprite.setX(hero.heroBaseX - (cam.getX()-camX)); //on soustrait la position du milieu par la transformation (différence entre le résultat de l'équation diff et l'entrée)
                                                                  //si la caméra est en retard par rapport au héros, la tranformation est négative -> le sprite du héros va se décaler vers la droite sur la fenêtre
                                                                  //lorsqu'il n'y aura plus de retard, le sprite revient au milieu (transformation nulle)
            hero.sprite.setY(hero.heroBaseY - (cam.getY()-camY));

            pane.getChildren().remove(hearts0.getImg());
            pane.getChildren().remove(hearts1.getImg());
            pane.getChildren().remove(hearts2.getImg());
            pane.getChildren().remove(hearts3.getImg());
            pane.getChildren().remove(hero.getImg());

            if (numberOfLives == 0) pane.getChildren().add(hearts0.getImg());
            if (numberOfLives == 1) pane.getChildren().add(hearts1.getImg());
            if (numberOfLives == 2) pane.getChildren().add(hearts2.getImg());
            if (numberOfLives == 3) pane.getChildren().add(hearts3.getImg());

            pane.getChildren().add(hero.getImg());
            System.out.println("heroX ="+hero.x+", herobaseX ="+hero.heroBaseX+", camGetX ="+cam.getX());
        }
    }

    public void SetLife(int life) {
        this.numberOfLives = life;
    }
}
