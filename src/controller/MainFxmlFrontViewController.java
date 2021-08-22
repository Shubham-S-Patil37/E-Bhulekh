package controller;
import com.jfoenix.controls.JFXButton;
import connection.LoginHandeling;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.Utilities;

public class MainFxmlFrontViewController implements Initializable {
    static public String buttonString = "Home";
    public static AnchorPane apane;
    @FXML
    private AnchorPane aPane;
    @FXML
    private JFXButton buynow;
    @FXML
    private JFXButton mypurchase;
    @FXML
    private JFXButton request;
    @FXML
    private AnchorPane loadingPane;
    @FXML
    private ImageView profileFrontPhoto;
    @FXML
    private Label wellcomeLabel;
    @FXML
    private ImageView requestImg;
    @FXML
    private ImageView mypurchesImg;
    @FXML
    private ImageView buyNowImg;
    @FXML
    private JFXButton closeButton;
    @FXML
    private ImageView buyNowImg1;
    @FXML
    private ImageView mypurchesImg1;
    @FXML
    private ImageView requestImg1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        buyNowImg.setImage(new Image(new File("Images/shopping_cart.png").toURI().toString()));
        mypurchesImg.setImage(new Image(new File("Images/shopping_cart.png").toURI().toString()));
        requestImg.setImage(new Image(new File("Images/request.png").toURI().toString()));
        Utilities.setBGColour(LoginHandeling.bgColor, aPane);
        apane = aPane;
    }    

    @FXML
    private void buyNow(ActionEvent event) throws IOException {
        if(!buttonString.equals("buyNow")){
            buttonString = "buyNow";
            Parent root = FXMLLoader.load(getClass().getResource("/view/PurchaseFXML.fxml"));
            Scene scene = buynow.getScene();
            root.translateXProperty().set(scene.getWidth());
            MainFxmlController.slider.getChildren().add(root);
            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(t -> { MainFxmlController.slider.getChildren().remove(aPane); });
            timeline.play(); 
        }
    }

    @FXML
    private void getMyRequest(ActionEvent event) throws IOException {
        if(!buttonString.equals("getMyRequest")){
            buttonString = "getMyRequest";
            Parent root = FXMLLoader.load(getClass().getResource("/view/RequestFXML.fxml"));
            Scene scene = buynow.getScene();
            root.translateXProperty().set(scene.getWidth());
            MainFxmlController.slider.getChildren().add(root);
            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(t -> { MainFxmlController.slider.getChildren().remove(aPane); });
            timeline.play(); 
        }
    }

    @FXML
    private void myPurchase(ActionEvent event) throws IOException {
        if(!buttonString.equals("getMyRequest")){
            buttonString = "getMyRequest";
            Parent root = FXMLLoader.load(getClass().getResource("/view/MyPurchase.fxml"));
            Scene scene = buynow.getScene();
            root.translateXProperty().set(scene.getWidth());
            MainFxmlController.slider.getChildren().add(root);
            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(t -> { MainFxmlController.slider.getChildren().remove(aPane); });
            timeline.play(); 
        }
    }

    @FXML
    private void closeButtonClickMethod(ActionEvent event) {
        LoginController.stage.close();
    }
}
