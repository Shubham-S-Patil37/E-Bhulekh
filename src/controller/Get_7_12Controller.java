package controller;
import com.jfoenix.controls.JFXButton;
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
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class Get_7_12Controller implements Initializable {
    public static Label vilagename, talukaname, distname, surveyinfo, surveyinfo1, username, binfaminingunit, nonagriunit, username1, landarea, landsize;
    @FXML
    private JFXButton requestBtn;
    @FXML
    private JFXButton previousBtn;
    @FXML
    private JFXButton nextBtn;
    @FXML
    private Label vilageName;
    @FXML
    private Label talukaName;
    @FXML
    private Label distName;
    @FXML
    private Label surveyInfo;
    @FXML
    private Label surveyInfo1;
    @FXML
    private Label userName;
    @FXML
    private Label binFarminingUnit;
    @FXML
    private Label nonAgriUnit;
    @FXML
    private Label userName1;
    @FXML
    private Label landSize;
    @FXML
    private Label landArea;
    @FXML
    private AnchorPane aPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        makeStatic();
        setData("","","","");
    }
    
    void makeStatic(){
        vilagename = vilageName;
        talukaname = talukaName;
        distname = distName;
        surveyinfo = surveyInfo;
        surveyinfo1 = surveyInfo1;
        username = userName;
        binfaminingunit = binFarminingUnit;
        nonagriunit = nonAgriUnit;
        username1 = userName1;
        landarea = landArea;
        landsize = landSize;
    }

    @FXML
    private void showPrevious(ActionEvent event) throws IOException {
            
            Parent root = FXMLLoader.load(getClass().getResource("/view/Get_7_12.fxml"));
            Scene scene = previousBtn.getScene();
            root.translateYProperty().set(scene.getHeight());

            PurchaseFXMLController.slidinpane.getChildren().add(root);

            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.seconds(0.2), kv);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(t -> { PurchaseFXMLController.slidinpane.getChildren().remove(aPane); });
            timeline.play();
    }

    @FXML
    private void showNext(ActionEvent event) throws IOException {

            Parent root = FXMLLoader.load(getClass().getResource("/view/Get_7_12.fxml"));
            Scene scene = nextBtn.getScene();
            root.translateYProperty().set(scene.getHeight());

            PurchaseFXMLController.slidinpane.getChildren().add(root);

            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.seconds(0.2), kv);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(t -> { PurchaseFXMLController.slidinpane.getChildren().remove(aPane); });
            timeline.play();
    }
    
    void setData(String vilage, String tal, String dist, String name){
            vilagename.setText("Vilage : "+vilage);
            talukaname.setText("Takuka : "+tal);
            distname.setText("Dist : "+dist);
            surveyinfo.setText("Survey No. and Subdivision : "+dist+" "+tal+" "+vilage);
            surveyinfo1.setText("Survey No. and Subdivision : "+dist+" "+tal+" "+vilage);
            username.setText(name);
            username1.setText(name);
//*************************************************** HardCoded Value *********************************************************************
            binfaminingunit.setText("10");
            nonagriunit.setText("20");
            landarea.setText("10.02");
            landsize.setText("20.4");
    }
}
