package e_bhulekh;
import controller.LoginController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class E_Bhulekh extends Application {
    public static Stage stg;
    public static Scene scene;
    private double xOffset = 0;
    private double yOffset = 0;
    @Override
    public void start(Stage primaryStage) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ProfileView.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Setting.fxml"));
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainFxml.fxml"));
        Parent root = (Parent) loader.load();
        LoginController controller = loader.getController();
        controller.setStage(primaryStage);

        primaryStage.initStyle(StageStyle.TRANSPARENT);

        //grab your root here
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        //move around here
        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });

        Scene scene = new Scene(root);
        //set transparent
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> primaryStage.close());
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
