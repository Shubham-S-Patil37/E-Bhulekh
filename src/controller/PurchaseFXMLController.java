package controller;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import connection.LoginHandeling;
import connection.Userhandling;
import static controller.MainFxmlController.slider;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import model.Utilities;

public class PurchaseFXMLController implements Initializable {
    TranslateTransition slide = new TranslateTransition();
    public static String candidateName, email, date_of_birth, address, district, taluka, village_name, mobileNumber, aadhar_id;  
    static StackPane slidinpane;
    static AnchorPane apane;
    @FXML
    private JFXComboBox<String> dist;
    @FXML
    private JFXComboBox<String> tal;
    @FXML
    private JFXComboBox<String> vilage;
    @FXML
    private JFXComboBox<String> name;
    @FXML
    private StackPane slidingPane;
    int counter = 0;
    @FXML
    private AnchorPane aPane;
    @FXML
    private ImageView imageView_7_12;
    @FXML
    private JFXButton closeButton;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        apane = aPane;
        //setData();
        setDistrictComboBox();        
//        slidingPane.setLayoutX(600);
//        slidingPane.setLayoutY(25);
//        slidingPane.setPrefSize(980, 890);
//        slidingPane.setTranslateX(1500);
//        slidinpane = slidingPane;
        setSlidingPanePosition();
        Utilities.setBGColour(LoginHandeling.bgColor, aPane);
        
    }
    private void setSlidingPanePosition()
    {
        slidingPane.setLayoutX(600);
        slidingPane.setLayoutY(120);
        slidingPane.setPrefSize(980, 890);
        slidingPane.setTranslateX(1500);
        slidinpane = slidingPane;        
    }
    private void setDistrictComboBox()
    {
        dist.getSelectionModel().clearSelection();
        dist.getItems().clear();
        ArrayList<String> dist_names = new Userhandling().SetDist();
        for(String name : dist_names)
        {
            dist.getItems().add(name.trim());
        }       
    }           
    
    void setData(){
        for(String name : new Userhandling().SetDist())
            dist.getItems().add(name);
    }
    
    @FXML
    private void selectDist(ActionEvent event) {
//        for(String name : Userhandling.SetTal(dist.getValue()))
//            tal.getItems().add(name);

        if ( dist.getSelectionModel().getSelectedItem() == null )
            tal.getItems().clear();
        else
        {
            district = dist.getSelectionModel().getSelectedItem().toString().trim();

            tal.getSelectionModel().clearSelection();
            tal.getItems().clear();
            ArrayList<String> tal_names = new Userhandling().SetTal(district);
            for(String name : tal_names)
            {
                tal.getItems().add(name.trim());
            }       
        }

    }

    @FXML
    private void selectTal(ActionEvent event) {
//        for(String name : Userhandling.Setvil(tal.getValue()))
//            vilage.getItems().add(name);
        if ( tal.getSelectionModel().getSelectedItem() == null )
            vilage.getItems().clear();
        else
        {

            taluka = tal.getSelectionModel().getSelectedItem().toString().trim();

            vilage.getSelectionModel().clearSelection();
            vilage.getItems().clear();
            ArrayList<String> vil = new Userhandling().Setvil(taluka);
            for(String name : vil)
            {
                vilage.getItems().add(name.trim());
            }   
        }

    }

    @FXML
    private void selectVilage(ActionEvent event) {
//        for(String name1 : Userhandling.Setname(dist.getValue(), tal.getValue(), vilage.getValue()))
//            name.getItems().add(name1);
        name.getItems().clear();
        slidingPane.setVisible(false);
        counter = 0;
        slidingPane.setTranslateX(1500);
        if ( vilage.getSelectionModel().getSelectedItem() != null )
        {
            village_name = vilage.getSelectionModel().getSelectedItem().toString().trim();
            for(String name1 : Userhandling.Setname(dist.getValue(), tal.getValue(), village_name))
                name.getItems().add(name1);
        }
    }

    @FXML
    private void selectName(ActionEvent event) {
        Get_7_12_NewController.ownerName = name.getSelectionModel().getSelectedItem().toString();
        if(counter == 0){
            getUtara();
            slide.setDuration(Duration.seconds(0.5));
            slide.setNode(slidingPane);
            slide.setToX(200);
            slide.play();
            counter++;
        }
        else
            getUtara();
    }
    
    void getUtara(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/Get_7_12_New.fxml"));
            slidingPane.getChildren().add(root);
            Get_7_12_NewController.getLandInfoFromBuyNow();
            Get_7_12_NewController.setProperData();            
            slidingPane.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(PurchaseFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//    void getUtara(){
//        try {
//            Parent root = FXMLLoader.load(getClass().getResource("/view/Get_7_12.fxml"));
//            slidingPane.getChildren().add(root);
//            
//            Get_7_12Controller.vilagename.setText("Vilage : "+vilage.getValue());
//            Get_7_12Controller.talukaname.setText("Takuka : "+tal.getValue());
//            Get_7_12Controller.distname.setText("Dist : "+dist.getValue());
//            Get_7_12Controller.surveyinfo.setText("Survey No. and Subdivision : "+dist.getValue()+" "+tal.getValue()+" "+vilage.getValue());
//            Get_7_12Controller.surveyinfo1.setText("Survey No. and Subdivision : "+dist.getValue()+" "+tal.getValue()+" "+vilage.getValue());
//            Get_7_12Controller.username.setText(name.getValue());
//            Get_7_12Controller.username1.setText(name.getValue());
////*************************************************** HardCoded Value *********************************************************************
//            Get_7_12Controller.binfaminingunit.setText("10");
//            Get_7_12Controller.nonagriunit.setText("20");
//            Get_7_12Controller.landarea.setText("10.02");
//            Get_7_12Controller.landsize.setText("20.4");
//            slidingPane.setVisible(true);
//        } catch (IOException ex) {
//            Logger.getLogger(PurchaseFXMLController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    @FXML
    private void closeButtonClickMethod(ActionEvent event) throws IOException {
        if(!MainFxmlFrontViewController.buttonString.equals("Home")){
            MainFxmlFrontViewController.buttonString = "Home";
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainFxmlFrontView.fxml"));
            Scene scene = closeButton.getScene();
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
}
