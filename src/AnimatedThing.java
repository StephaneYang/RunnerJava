import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

abstract class AnimatedThing {
    //x et y ne correspondent pas à la position qu'on voit sur la fenêtre
    //(il reste dans la fenêtre et ne quitte pas l'écran) mais on imagine un héros qui évolue dans x et y
    protected static double x, y, oldx, oldy;
    protected static ImageView sprite;
    //attitude : état de notre héros = numéro de la ligne d'une animation (en commencant par 0)
    //index : index d'une image = numéro d'une colonne
    //maxIndex : nb d'image pour une animation complète
    //offset : distance horizontale entre chaque frames dans notre fichier hero.jpg
    protected static int attitude, maxIndex, offset, isJumping, isFalling;
    public static int temps, timeFrames = 7;
    protected static double index, windowX = 600, windowY = 300;
    protected static double heroBaseX = 0.1*windowX, heroBaseY = 0.8*windowY-50;

    AnimatedThing (String imgSprite, int attitude, int offset){
        AnimatedThing.x = 0;
        AnimatedThing.y = 0;
        AnimatedThing.oldy = 0;
        AnimatedThing.attitude = attitude;
        AnimatedThing.offset = offset;
        Image image = new Image(imgSprite);
        sprite = new ImageView(image);
        Rectangle2D viewportRect = new Rectangle2D(index*offset, attitude*159, 75, 100);
        sprite.setViewport(viewportRect);
    }

    public ImageView getImg () {
        return sprite;
    }

    public static void update(long time) {
        //définition des états du héros
        //Rq : "attitude" correspond également aux états du héros l'utilisation est différente
        //attitude permet de choisir la ligne d'animation tandis que les états ci-dessous permet de choisir l'index (colonne)
        if ((x-oldx)>0) {
            attitude = 0;
        }

        if ((y-oldy)<0){
            isJumping = 1;
            index = 0;
            attitude = 1;
        } else if ((y-oldy)>0){
            isFalling = 1;
            index = 1;
            attitude = 1;
        } else if ((y-oldy)==0) {
            isJumping = 0;
            isFalling = 0;
        }
        //Mise en mémoire en tant qu'ancienne valeur
        oldx = x;
        oldy = y;


        if (temps % timeFrames == 0) {
            if (attitude == 0 || attitude == 2) {
                AnimatedThing.maxIndex = 5;
                index++;
            }
            if (attitude == 1 || attitude == 3) {
                AnimatedThing.maxIndex = 1;
            }
            if (index >= AnimatedThing.maxIndex + 1){
                index = 0;
            }
            Rectangle2D viewportRect = new Rectangle2D((index-index%1)*offset, attitude*160, 120, 100);
            sprite.setViewport(viewportRect);

        }


    }

}
