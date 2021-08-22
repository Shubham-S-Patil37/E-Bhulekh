package controller;
import com.jfoenix.controls.JFXButton;
import connection.Connection;
import connection.Userhandling;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.Utilities;

public class Get_7_12_NewController implements Initializable {
    public static Label vilagename, talukaname, distname, surveyinfo, surveyinfo1, username, binfaminingunit, nonagriunit, username1, landarea, landsize, buyDate_Label;
    public static JFXButton req_btn, prev_btn, next_btn;
    public static String village, taluka, district, code, ownerName, landAreaSize, landTax, purchaseDate, preHash, currentHash, nextHash, status;
    public static String surveyInformation;
    static ResultSet resultSet = null;
    
    public static String ownerID, requesterID;
    
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
    @FXML
    private Label buyDate;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        makeStatic();
        req_btn.setVisible(false);
        prev_btn.setVisible(true);
        next_btn.setVisible(true);          
    }
    static void resetAll()
    {
        req_btn.setVisible(false);
        prev_btn.setVisible(true);
        next_btn.setVisible(true);  
        
        code = "";
        preHash = "";
        nextHash = "";
        currentHash = "";
        ownerName = "";
        
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
        buyDate_Label = buyDate;
        req_btn = requestBtn;
        prev_btn = previousBtn;
        next_btn = nextBtn;
    }

    public static void setProperData()
    {
        Get_7_12_NewController.vilagename.setText("Vilage : " + village);
        Get_7_12_NewController.talukaname.setText("Takuka : " + taluka);
        Get_7_12_NewController.distname.setText("Dist : " + district);
        Get_7_12_NewController.surveyinfo.setText("Survey No. and Subdivision : " + surveyInformation );
        Get_7_12_NewController.surveyinfo1.setText(surveyInformation);
        Get_7_12_NewController.username.setText(ownerName);
        Get_7_12_NewController.username1.setText(ownerName);

        Get_7_12_NewController.binfaminingunit.setText(landAreaSize);
        Get_7_12_NewController.nonagriunit.setText(landTax);
        Get_7_12_NewController.landarea.setText(landAreaSize);
        Get_7_12_NewController.landsize.setText(landTax);        
        Get_7_12_NewController.buyDate_Label.setText("Date : " + purchaseDate);        
    }
    public static void getLandInfo()
    {
        try {
            resultSet = Connection.selectQuery("SELECT * FROM landinfo where code = '" + code +"';");
            while (resultSet.next()) 
            {
                village = resultSet.getString("vilage");
                taluka = resultSet.getString("taluka");
                district = resultSet.getString("dist");
                code = resultSet.getString("code");
                ownerName = resultSet.getString("username");
                landAreaSize = resultSet.getString("area");
                purchaseDate = resultSet.getString("buydate");                
                preHash = resultSet.getString("pre_hash");                
                currentHash = resultSet.getString("hash");                
                nextHash = resultSet.getString("next_hash");                
                status = resultSet.getString("status");                

                landTax = "" + roundFloat(Float.parseFloat(landAreaSize) * 0.7f , 2 );
                surveyInformation = code + ", "+ village + ", "+ taluka + ", "+ district; 
                
                if ( status.equals("Active"))
                {
                    req_btn.setVisible(true);
                }
                if ( preHash.equals(""))
                {
                    prev_btn.setVisible(false);
                }
                if( nextHash.equals("0"))
                {
                    next_btn.setVisible(false);
                }
            }
        } catch (SQLException e) {}        
    }
    public static void getLandInfoFromBuyNow()
    {
        try {
            resultSet = Connection.selectQuery("SELECT * FROM landinfo where username = '" + ownerName +"';");
            while (resultSet.next()) 
            {
                village = resultSet.getString("vilage");
                taluka = resultSet.getString("taluka");
                district = resultSet.getString("dist");
                code = resultSet.getString("code");
                ownerName = resultSet.getString("username");
                landAreaSize = resultSet.getString("area");
                purchaseDate = resultSet.getString("buydate");                
                preHash = resultSet.getString("pre_hash");                
                currentHash = resultSet.getString("hash");                
                nextHash = resultSet.getString("next_hash");                
                status = resultSet.getString("status");                

                landTax = "" + roundFloat(Float.parseFloat(landAreaSize) * 0.7f , 2 );
                surveyInformation = code + ", "+ village + ", "+ taluka + ", "+ district; 
                
                if ( status.equals("Active"))
                {
                    req_btn.setVisible(true);
                }
                if ( preHash.equals("0"))
                {
                    prev_btn.setVisible(false);
                }
                if( nextHash.equals("0"))
                {
                    next_btn.setVisible(false);
                }
            }
        } catch (SQLException e) {}
        
    }


    public static void getLandInfoUsingLandCode( String landCode)
    {
        resetAll();
        try {
            resultSet = Connection.selectQuery("SELECT * FROM landinfo where code = '" + landCode +"';");
            while (resultSet.next()) 
            {
                village = resultSet.getString("vilage");
                taluka = resultSet.getString("taluka");
                district = resultSet.getString("dist");
                code = resultSet.getString("code");
                ownerName = resultSet.getString("username");
                landAreaSize = resultSet.getString("area");
                purchaseDate = resultSet.getString("buydate");                
                preHash = resultSet.getString("pre_hash");                
                currentHash = resultSet.getString("hash");                
                nextHash = resultSet.getString("next_hash");                  
               
                status = resultSet.getString("status");                

                landTax = "" + roundFloat(Float.parseFloat(landAreaSize) * 0.7f , 2 );
                surveyInformation = code + ", "+ village + ", "+ taluka + ", "+ district; 
                
                if ( status.equals("Active"))
                {
                    req_btn.setVisible(true);
                }
                if ( preHash.equals("0"))
                {
                    prev_btn.setVisible(false);
                }
                if( nextHash.equals("0"))
                {
                    next_btn.setVisible(false);
                }
            }
        } catch (SQLException e) {}
        
    }

    private void getLandInfoUsingPreHash( String prevHash)
    {
        resetAll();
        try {
            resultSet = Connection.selectQuery("SELECT * FROM landinfo where hash = '" + prevHash +"';");
            if (resultSet.next()) 
            {
                village = resultSet.getString("vilage");
                taluka = resultSet.getString("taluka");
                district = resultSet.getString("dist");
                code = resultSet.getString("code");
                ownerName = resultSet.getString("username");
                landAreaSize = resultSet.getString("area");
                purchaseDate = resultSet.getString("buydate");                
                preHash = resultSet.getString("pre_hash");                
                currentHash = resultSet.getString("hash");                
                nextHash = resultSet.getString("next_hash");                 
                
                status = resultSet.getString("status");                

                landTax = "" + roundFloat(Float.parseFloat(landAreaSize) * 0.7f , 2 );
                surveyInformation = code + ", "+ village + ", "+ taluka + ", "+ district; 
                
                if ( status.equals("Active"))
                {
                    requestBtn.setVisible(true);
                }
                if ( preHash.equals("0"))
                {
                    prev_btn.setVisible(false);
                }
                if( nextHash.equals("0"))
                {
                    next_btn.setVisible(false);
                }
                
            }
//            else
//            {
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        PurchaseFXMLController.slidinpane.setVisible(false);
//                    }
//                });
//            }
        } catch (SQLException e) {}
        
    }

    private void getLandInfoUsingNextHash( String next_Hash)
    {
        resetAll();
        try {
            resultSet = Connection.selectQuery("SELECT * FROM landinfo where hash = '" + next_Hash +"';");
            if (resultSet.next()) 
            {
                village = resultSet.getString("vilage");
                taluka = resultSet.getString("taluka");
                district = resultSet.getString("dist");
                code = resultSet.getString("code");
                ownerName = resultSet.getString("username");
                landAreaSize = resultSet.getString("area");
                purchaseDate = resultSet.getString("buydate");                
                preHash = resultSet.getString("pre_hash");                
                currentHash = resultSet.getString("hash");                
                nextHash = resultSet.getString("next_hash");                  
               
                status = resultSet.getString("status");                

                landTax = "" + roundFloat(Float.parseFloat(landAreaSize) * 0.7f , 2 );
                surveyInformation = code + ", "+ village + ", "+ taluka + ", "+ district; 
                
                if ( status.equals("Active"))
                {
                    requestBtn.setVisible(true);
                }
                if ( preHash.equals("0"))
                {
                    prev_btn.setVisible(false);
                }
                if( nextHash.equals("0"))
                {
                    next_btn.setVisible(false);
                } 
            }
//            else
//            {
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        PurchaseFXMLController.slidinpane.setVisible(false);
//                    }
//                });
//            }
        } catch (SQLException e) {}
        
    }
    
    private static float roundFloat(float f, int places) {
 
        BigDecimal bigDecimal = new BigDecimal(Float.toString(f));
        bigDecimal = bigDecimal.setScale(places, RoundingMode.HALF_UP);
        return bigDecimal.floatValue();
    }    
    @FXML
    private void showPrevious(ActionEvent event) throws IOException {
            getLandInfoUsingPreHash(preHash);
            setProperData();
//            Parent root = FXMLLoader.load(getClass().getResource("/view/Get_7_12_New.fxml"));
//            Scene scene = previousBtn.getScene();
//            root.translateYProperty().set(scene.getHeight());
//
//            PurchaseFXMLController.slidinpane.getChildren().add(root);
//
//            Timeline timeline = new Timeline();
//            KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
//            KeyFrame kf = new KeyFrame(Duration.seconds(0.2), kv);
//            timeline.getKeyFrames().add(kf);
//            timeline.setOnFinished(t -> { PurchaseFXMLController.slidinpane.getChildren().remove(aPane); });
//            timeline.play();
    }

    @FXML
    private void showNext(ActionEvent event) throws IOException {
            getLandInfoUsingNextHash(nextHash);
            setProperData();
//            Parent root = FXMLLoader.load(getClass().getResource("/view/Get_7_12_New.fxml"));
//            Scene scene = nextBtn.getScene();
//            root.translateYProperty().set(scene.getHeight());
//
//            PurchaseFXMLController.slidinpane.getChildren().add(root);
//
//            Timeline timeline = new Timeline();
//            KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
//            KeyFrame kf = new KeyFrame(Duration.seconds(0.2), kv);
//            timeline.getKeyFrames().add(kf);
//            timeline.setOnFinished(t -> { PurchaseFXMLController.slidinpane.getChildren().remove(aPane); });
//            timeline.play();
    }
    
//    void setData(String vilage, String tal, String dist, String name){
//            vilagename.setText("Vilage : "+vilage);
//            talukaname.setText("Takuka : "+tal);
//            distname.setText("Dist : "+dist);
//            surveyinfo.setText("Survey No. and Subdivision : "+dist+" "+tal+" "+vilage);
//            surveyinfo1.setText("Survey No. and Subdivision : "+dist+" "+tal+" "+vilage);
//            username.setText(name);
//            username1.setText(name);
////*************************************************** HardCoded Value *********************************************************************
//            binfaminingunit.setText("10");
//            nonagriunit.setText("20");
//            landarea.setText("10.02");
//            landsize.setText("20.4");
//    }

    @FXML
    private void requestButtonClicked(ActionEvent event) throws SQLException {
        
        ownerID = Userhandling.getOwnerID(code);
        requesterID = Userhandling.getRequesterID();
        
        if ( ownerID.equals("") || requesterID.equals(""))
        {
            
        }
        else
        {
            resultSet = Connection.selectQuery("SELECT * FROM request where dist = '" + district + "' and tal = '"+ taluka + "' and vilage = '" + village + "' and ownerid = '"+ ownerID +"' and requesterid = '"+ requesterID +"' and status = 'pending' and landcode = '" + code + "';"); 
            if ( resultSet.next())
                Utilities.Notification("Notification", "No Need. Request Already Done");
            else
            {
                Userhandling.requestInsert(district, taluka, village, ownerID, requesterID, code);
                Utilities.Notification("Success", "Request Done!");
                
            }
        }
        
    }
}
