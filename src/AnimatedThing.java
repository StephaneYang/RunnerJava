import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

abstract class AnimatedThing {
    protected static double x, y;
    protected static ImageView sprite;
    //attitude : état de notre héro = numéro de la ligne d'une animation (en commencant par 0)
    //index : index d'une image = numéro d'une colonne
    //maxIndex : nb d'image pour une animation complète
    //offset : distance horizontale entre chaque frames dans notre fichier hero.jpg
    protected static int attitude, index, maxIndex, sizeWindow, offset;
    public static int temps, timeFrames = 7;
    protected static double windowX = 600, windowY = 300;
    protected static double heroBaseX = 0.5*windowX-50, heroBaseY = 0.8*windowY-50;

    AnimatedThing (String imgSprite, double x, double y, int attitude, int offset){
        AnimatedThing.x = x;
        AnimatedThing.y = y;
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

    public static void update(long time, int attitude) {
        AnimatedThing.attitude = attitude;
        if (temps % timeFrames == 0) {
            if (attitude == 0 || attitude == 2) AnimatedThing.maxIndex = 5;
            if (attitude == 1 || attitude == 3) AnimatedThing.maxIndex = 1;
            index++;
            if (index > AnimatedThing.maxIndex){
                index = 0;
            }
            Rectangle2D viewportRect = new Rectangle2D(index*offset, attitude*159, 75, 100);
            sprite.setViewport(viewportRect);

        }
    }

}
