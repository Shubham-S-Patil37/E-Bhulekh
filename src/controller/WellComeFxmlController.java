package controller;
import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class WellComeFxmlController implements Initializable {
    MainFxmlController mfc = new MainFxmlController();
    @FXML
    private StackPane sPane;
    @FXML
    private AnchorPane aPane;
    @FXML
    private JFXButton butNowBtn;
    @FXML
    private JFXButton closeBtn;
    @FXML
    private ImageView backImg;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        backImg.setImage(new Image(new File("Images/add copy.png").toURI().toString()));
    }    

    @FXML
    private void buyNow(ActionEvent event) {
    }

    @FXML
    private void getClose(ActionEvent event) {
//        MainFxmlController.loding_apane.setVisible(false);
    }
    
}
