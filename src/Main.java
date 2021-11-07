import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Group root = new Group();
        Pane pane = new Pane(root);
        GameScene theScene = new GameScene(pane,0,100);

        primaryStage.setTitle("Demo");
        primaryStage.setScene(theScene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
