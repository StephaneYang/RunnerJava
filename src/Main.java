import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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

            theScene.setOnMousePressed(event -> {
                GameScene.up = 1;
                System.out.println("Jump");
            });

            theScene.setOnMouseReleased(event -> {
                GameScene.up = 0;
            });

            theScene.setOnKeyPressed(event -> {
                switch (event.getCode()){
                    case UP : GameScene.up = 1;break;
                    case DOWN: GameScene.down = 1;break;
                    case LEFT:  GameScene.left = 1;break;
                    case RIGHT: GameScene.right = 1;break;
                    case SPACE: GameScene.shoot = 1;break;
                }
            });

            theScene.setOnKeyReleased(event -> {
                switch (event.getCode()){
                    case UP : GameScene.up = 0;break;
                    case DOWN: GameScene.down = 0;break;
                    case LEFT:  GameScene.left = 0;break;
                    case RIGHT: GameScene.right = 0;break;
                    case SPACE: GameScene.shoot = 0;break;
                }
            });
        }
        catch (IllegalArgumentException e) {System.out.println("imgRunner est introuvable dans le C: \nVeuillez vérifier que le dossier imgRunner est bien dans le C:");}
    }

    public static void main(String[] args) {
        launch(args);
    }
}
