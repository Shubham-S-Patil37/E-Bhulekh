 package controller;
import model.Utilities;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import connection.LoginHandeling;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginController implements Initializable {
    RotateTransition rt1,rt2,rt3;
    public static String password, status;
    public static Stage stage;
    public static String loginUser;
    public static String loginUserName;
    
    Utilities util = new Utilities();
    public static AnchorPane moving_apane;
    public static StackPane mainPane;
    
    @FXML
    private StackPane sPane;
    @FXML
    private AnchorPane aPane;
    @FXML
    private ImageView backImg;
    @FXML
    private ImageView vehicle;
    @FXML
    private AnchorPane movingPane;
    @FXML
    private AnchorPane loginPane;
    @FXML
    private JFXTextField userid;
    @FXML
    private JFXButton loginBtn;
    @FXML
    private JFXPasswordField passwords;
    @FXML
    private JFXButton newRegister;
    @FXML
    private JFXButton closeButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {     
        backImg.setImage(new Image(new File("Images/logIn.jpg").toURI().toString()));
        vehicle.setImage(new Image(new File("Images/crane.png").toURI().toString()));
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                userid.requestFocus();
            }
        });        
        
        Utilities.movePanetoX(movingPane, 1700, 0, 5);
        makeStatic();
    }    

    void makeStatic(){
        moving_apane = movingPane;
        mainPane = sPane;
    }

    @FXML
    private void getLogin(ActionEvent event) throws IOException {
        password = passwords.getText();
        status = LoginHandeling.login(userid.getText(), passwords.getText());
        if(status == null)
            Utilities.Notification("Notification", "Invalid User ID and Passwords");
        else
        {
            loginUser = userid.getText();
//            if(status.equals("talathi"))
//            {
                Parent root = FXMLLoader.load(getClass().getResource("/view/MainFxml.fxml"));
                Scene scene = loginBtn.getScene();
                root.translateYProperty().set(scene.getHeight());

                sPane.getChildren().add(root);

                Timeline timeline = new Timeline();
                KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
                KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
                timeline.getKeyFrames().add(kf);
                timeline.setOnFinished(t -> { sPane.getChildren().remove(aPane); });
                timeline.play();
//            }
//            else if(status.equals("user"))
//            {
//                Parent root = FXMLLoader.load(getClass().getResource("/view/MainFxml.fxml"));
//                Scene scene = loginBtn.getScene();
//                root.translateYProperty().set(scene.getHeight());
//
//                sPane.getChildren().add(root);
//
//                Timeline timeline = new Timeline();
//                KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
//                KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
//                timeline.getKeyFrames().add(kf);
//                timeline.setOnFinished(t -> { sPane.getChildren().remove(aPane); });
//                timeline.play();
//            }
        }
    }

    @FXML
    private void newRegister(ActionEvent event) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("/view/NewRegister.fxml"));
            Scene scene = loginBtn.getScene();
            root.translateYProperty().set(scene.getHeight());

            sPane.getChildren().add(root);

            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(t -> { sPane.getChildren().remove(aPane); });
            timeline.play();
    }

    @FXML
    private void forgetPassword(ActionEvent event) {
    }

    @FXML
    private void closeButtonClickMethod(ActionEvent event) {
        stage.close();
    }

    public void setStage(Stage primaryStage) {
        stage = primaryStage;
    }
}
