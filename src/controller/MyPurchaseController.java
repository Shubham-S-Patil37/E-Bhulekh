package controller;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import connection.Connection;
import connection.LoginHandeling;
import connection.Userhandling;
import static controller.MainFxmlController.slider;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import model.Utilities;

public class MyPurchaseController implements Initializable {
    TranslateTransition slide = new TranslateTransition();
    int counter = 0;
    static AnchorPane apane;
    public static String village, taluka, district, code, ownerName, landArea, landTax, purchaseDate ;
    
    public static String surveyInfo;
    String landCode;
    static ResultSet resultSet = null;
    
    
    @FXML
    private JFXComboBox selectLand;
    @FXML
    private StackPane slidingPane;
    @FXML
    private AnchorPane aPane;
    @FXML
    private JFXButton closeButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setDataToCombo();
        slidingPane.setLayoutX(800);
        slidingPane.setLayoutY(120);
        slidingPane.setPrefSize(980, 890);
        slidingPane.setTranslateX(1500);
        PurchaseFXMLController.slidinpane = slidingPane;
        Utilities.setBGColour(LoginHandeling.bgColor, aPane);
        apane = aPane;
    }    

    private void setDataToCombo()
    {
        selectLand.getSelectionModel().clearSelection();
        selectLand.getItems().clear();

        ArrayList<String> names = Userhandling.setLandInfo(LoginController.loginUserName);
        if ( names.isEmpty() )
            selectLand.getItems().add("No Records Found");
        else
        {
            for(String name : names)
            {
                selectLand.getItems().add(name.trim());
            }       
        }
    }
    
    @FXML
    private void getSelectedLand(ActionEvent event) {
        if ( selectLand.getSelectionModel().getSelectedItem().toString().equals("No Records Found") || selectLand.getSelectionModel().getSelectedItem() == null)
        {
            
        }
        else
        {
            Get_7_12_NewController.code = selectLand.getSelectionModel().getSelectedItem().toString();            
            //Get_7_12_NewController.getLandInfoUsingLandCode(selectLand.getSelectionModel().getSelectedItem().toString());
//            Get_7_12_NewController.getLandInfo();
//            Get_7_12_NewController.setProperData();
            //getLandInfo( selectLand.getSelectionModel().getSelectedItem().toString());
            if(counter == 0){
                getUtara();
                slide.setDuration(Duration.seconds(2));
                slide.setNode(slidingPane);
                slide.setToX(0);
                slide.play();
                counter++;
            }
            else
                getUtara();
        }
    }
    private void getLandInfo( String landCode)
    {
        try {
            resultSet = Connection.selectQuery("SELECT * FROM landinfo where code = '" + landCode +"';");
            while (resultSet.next()) 
            {
                village = resultSet.getString("vilage");
                taluka = resultSet.getString("taluka");
                district = resultSet.getString("dist");
                code = resultSet.getString("code");
                ownerName = resultSet.getString("username");
                landArea = resultSet.getString("area");
                purchaseDate = resultSet.getString("buydate");                
                landTax = "" + roundFloat(Float.parseFloat(landArea) * 0.7f , 2 );
                surveyInfo = code + ", "+ village + ", "+ taluka + ", "+ district; 
            }
        } catch (SQLException e) {}
        
    }
    void getUtara(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/Get_7_12_New.fxml"));
            slidingPane.getChildren().add(root);
            Get_7_12_NewController.getLandInfo();
            Get_7_12_NewController.setProperData();
            
//            Get_7_12_NewController.getLandInfo(code); 
//            Get_7_12_NewController.setProperData();
            
//            Get_7_12_NewController.vilagename.setText("Vilage : " + village);
//            Get_7_12_NewController.talukaname.setText("Takuka : " + taluka);
//            Get_7_12_NewController.distname.setText("Dist : " + district);
//            Get_7_12_NewController.surveyinfo.setText("Survey No. and Subdivision : " + surveyInfo );
//            Get_7_12_NewController.surveyinfo1.setText(surveyInfo);
//            Get_7_12_NewController.username.setText(ownerName);
//            Get_7_12_NewController.username1.setText(ownerName);
////*************************************************** HardCoded Value *********************************************************************
//            Get_7_12_NewController.binfaminingunit.setText(landArea);
//            Get_7_12_NewController.nonagriunit.setText(landTax);
//            Get_7_12_NewController.landarea.setText(landArea);
//            Get_7_12_NewController.landsize.setText(landTax);
        } catch (IOException ex) {
            Logger.getLogger(PurchaseFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private static float roundFloat(float f, int places) {
 
        BigDecimal bigDecimal = new BigDecimal(Float.toString(f));
        bigDecimal = bigDecimal.setScale(places, RoundingMode.HALF_UP);
        return bigDecimal.floatValue();
    }    
//    void getUtara(){
//        try {
//            Parent root = FXMLLoader.load(getClass().getResource("/view/Get_7_12.fxml"));
//            slidingPane.getChildren().add(root);
//            
//            Get_7_12Controller.vilagename.setText("Vilage : Kharde");
//            Get_7_12Controller.talukaname.setText("Takuka : Shirpur");
//            Get_7_12Controller.distname.setText("Dist : Dhule");
//            Get_7_12Controller.surveyinfo.setText("Survey No. and Subdivision : Kharde Shirpur Dhule");
//            Get_7_12Controller.surveyinfo1.setText("Survey No. and Subdivision : Kharde Shirpur Dhule");
//            Get_7_12Controller.username.setText("Shubham Patil");
//            Get_7_12Controller.username1.setText("Shubham Patil");
////*************************************************** HardCoded Value *********************************************************************
//            Get_7_12Controller.binfaminingunit.setText("10");
//            Get_7_12Controller.nonagriunit.setText("20");
//            Get_7_12Controller.landarea.setText("10.02");
//            Get_7_12Controller.landsize.setText("20.4");
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
