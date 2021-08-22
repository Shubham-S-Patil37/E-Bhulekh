package controller;
import model.Utilities;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class MainFxmlController implements Initializable {

    Utilities util = new Utilities();
    public static Stage settingStg,profileStg;
    public static Parent settingRoot,profileRoot;
    static StackPane slider;
    Parent root,root1;
    private AnchorPane aPane;
    @FXML
    private JFXButton logout;
    @FXML
    private JFXButton profileBtn;
    @FXML
    private AnchorPane menupane;
    @FXML
    private StackPane slidingPane;
    @FXML
    private JFXButton home;
    @FXML
    private AnchorPane mainApane;
    @FXML
    private StackPane sPane;
    @FXML
    private JFXButton setting;
    @FXML
    private JFXButton closeButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customInitializer();
        makeStatic();
        
    }
    
    void customInitializer(){
        Utilities.imageButton("profileIcon.png" , profileBtn);
        Utilities.imageButton("home.png"        , home);
        Utilities.imageButton("logout.png"      , logout);
        Utilities.imageButton("setting.png"      , setting);
    }
    
    void makeStatic(){
        try {
            slider = slidingPane;
            if(LoginController.status.equals("talathi"))
                root1 = FXMLLoader.load(getClass().getResource("/view/TalathiFrontView.fxml"));
            if(LoginController.status.equals("user"))
                root1 = FXMLLoader.load(getClass().getResource("/view/MainFxmlFrontView.fxml"));
            slidingPane.getChildren().add(root1);
        } catch (IOException ex) {
            Logger.getLogger(MainFxmlController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   

    @FXML
    private void getLogOut(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        Scene scene = logout.getScene();
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
    private void showProfile(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ProfileView.fxml"));
            profileRoot = (Parent) fxmlLoader.load();
            profileStg = new Stage();
            profileStg.setScene(new Scene(profileRoot)); 
            profileStg.initStyle(StageStyle.TRANSPARENT);
            profileStg.show();
        } catch (IOException ex) {
            Logger.getLogger(MainFxmlController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void getHomeBack(ActionEvent event) throws IOException {
        if(!MainFxmlFrontViewController.buttonString.equals("Home") || !TalathiFrontViewController.buttonString.equals("Home")){
            MainFxmlFrontViewController.buttonString = "Home";
            TalathiFrontViewController.buttonString = "Home";
            if(LoginController.status.equals("talathi"))
                root = FXMLLoader.load(getClass().getResource("/view/TalathiFrontView.fxml"));
            if(LoginController.status.equals("user"))
                root = FXMLLoader.load(getClass().getResource("/view/MainFxmlFrontView.fxml"));
            Scene scene = home.getScene();
            root.translateXProperty().set(scene.getWidth());

            MainFxmlController.slider.getChildren().add(root);

            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(t -> { slider.getChildren().remove(aPane); });
            timeline.play(); 
        }
    }

    @FXML
    private void showSetting(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Setting.fxml"));
            settingRoot = (Parent) fxmlLoader.load();
            settingStg = new Stage();
            settingStg.setScene(new Scene(settingRoot)); 
            settingStg.initStyle(StageStyle.TRANSPARENT);
            settingStg.show();
        } catch (IOException ex) {
            Logger.getLogger(MainFxmlController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void closeButtonClickMethod(ActionEvent event) {
        LoginController.stage.close();        
    }

}
