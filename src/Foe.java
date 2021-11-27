import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Foe extends AnimatedThing {
    String imgSprite;

    Foe(double BaseX, double BaseY, int foeNumero) {
        super("imgRunner/foe0.png", BaseX, BaseY, 40, 20);

        if (foeNumero == 0) sprite = new ImageView(new Image("imgRunner/foe0.png"));//écrasement du 1er argument du super
        if (foeNumero == 1) sprite = new ImageView(new Image("imgRunner/foe0.png"));
        if (foeNumero == 2) sprite = new ImageView(new Image("imgRunner/foe0.png"));
        if (foeNumero == 3) sprite = new ImageView(new Image("imgRunner/foe0.png"));
    }

}