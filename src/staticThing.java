import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class staticThing {

    private double x;
    private double y;
    ImageView imageView;

    staticThing (String background, double x, double y, double fit) {
        Image image = new Image(background);
        this.imageView = new ImageView(image);
        this.x = x;
        this.y = y;
        imageView.setX(x);
        imageView.setY(y);
        imageView.setFitWidth(fit);
        imageView.setPreserveRatio(true);
    }

    public ImageView getImg () {
        return this.imageView;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }
}
