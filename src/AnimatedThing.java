import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

abstract class AnimatedThing {
    protected static double x, y;
    protected static ImageView sprite;
    protected static int attitude, index, maxIndex, sizeWindow, offset;
    public static int temps, timeFrames = 7;
    protected static double windowX = 800, windowY = 399;
    protected static double heroBaseX = (windowX/2)-50, heroBaseY = (windowY/2)-50;

    AnimatedThing (String imgSprite, double x, double y, int attitude, int offset){
        this.x = x;
        this.y = y;
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
