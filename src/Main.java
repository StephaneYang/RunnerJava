import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.MalformedURLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Group root = new Group();
            Pane pane = new Pane(root);

            GameScene theScene = new GameScene(pane, 100, 100);
            primaryStage.setTitle("Demo");
            primaryStage.setScene(theScene);
            primaryStage.show();
        }
        catch (IllegalArgumentException e) {System.out.println("imgRunner est introuvable dans le C: \nVeuillez vérifier que le dossier imgRunner est bien dans le C:");}
    }

    public static void main(String[] args) {
        launch(args);
    }
}
