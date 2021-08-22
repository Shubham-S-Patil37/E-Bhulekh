package controller;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import connection.Connection;
import connection.LoginHandeling;
import connection.Userhandling;
import java.io.IOException;
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
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import model.EmailSender;
import model.UserAction;
import model.UserActionType;
import model.Utilities;
import static model.Utilities.Notification;

public class UserConfirmationController implements Initializable {
    String otp;
    public static String candidateName, email, date_of_birth, address, district, taluka, village_name, mobileNumber, aadhar_id; 
    private Timeline mTimeline;
    private UserAction mTimerAction;    
    
    @FXML
    private JFXComboBox<String> selectDist;
    @FXML
    private JFXComboBox<String> selectTal;
    @FXML
    private JFXComboBox<String> selectVill;
    @FXML
    private JFXTextField emailID;
    @FXML
    private JFXTextField otpBox1;
    @FXML
    private JFXTextField otpBox2;
    @FXML
    private JFXTextField otpBox3;
    @FXML
    private JFXTextField otpBox4;
    @FXML
    private Label countingLeb;
    @FXML
    private JFXButton resendOTP;
    @FXML
    private JFXButton varifyOTPBtn;
    @FXML
    private JFXButton varifyBtn;
    @FXML
    private StackPane sPane;
    @FXML
    private AnchorPane aPane;
    @FXML
    private JFXButton closeButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customInitializer();
        setDistrictComboBox(); 
//        for(String name : new Userhandling().SetDist())
//            selectDist.getItems().add(name);
    }    
     private void setDistrictComboBox()
    {
        selectDist.getSelectionModel().clearSelection();
        selectDist.getItems().clear();
        ArrayList<String> dist_names = new Userhandling().SetDist();
        for(String name : dist_names)
        {
            selectDist.getItems().add(name.trim());
        }       
    }          
    void customInitializer(){
        varifyBtn.setDisable(true);
        resendOTP.setDisable(true);

        otpBox1.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if (!newValue.matches("\\d{0,1}")) {
                    otpBox1.setText(oldValue);
                }
            }
        });

        otpBox2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if (!newValue.matches("\\d{0,1}")) {
                    otpBox2.setText(oldValue);
                }
            }
        });

        otpBox3.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if (!newValue.matches("\\d{0,1}")) {
                    otpBox3.setText(oldValue);
                }
            }
        });

        otpBox4.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if (!newValue.matches("\\d{0,1}")) {
                    otpBox4.setText(oldValue);
                }
            }
        });
    }

    @FXML
    private void getSelectedDist(ActionEvent event) {
//        for(String name : Userhandling.SetTal(selectDist.getValue()))
//            selectTal.getItems().add(name);
        
        if ( selectDist.getSelectionModel().getSelectedItem() == null )
            selectTal.getItems().clear();
        else
        {
            district = selectDist.getSelectionModel().getSelectedItem().toString().trim();

            selectTal.getSelectionModel().clearSelection();
            selectTal.getItems().clear();
            ArrayList<String> tal_names = new Userhandling().SetTal(district);
            for(String name : tal_names)
            {
                selectTal.getItems().add(name.trim());
            }       
        }        
    }

    @FXML
    private void reSendOTP(ActionEvent event) {
        Utilities.otpGerator(otp);

        if(Utilities.checkConnnection())
            if(!emailID.getText().equals("")){

                prepareAction(UserActionType.FOCUS);
                countingLeb.setVisible(true);
                mTimeline.play();
                
                resendOTP.setDisable(true);
                varifyOTPBtn.setDisable(false);
                varifyBtn.setDisable(false);
                
                EmailSender.sendEmail(emailID.getText(), "User Verification", "\n\n User Verification \n Your OTP "+otp+"\n OTP Valid for next 30 Seconds");
                Notification("Notification","OTP Sent. Check Your E Mail");
                varifyBtn.setDisable(false);
            }
            else{
                Notification("Notification","E-Mail ID field should not be empty");
                varifyBtn.setDisable(true);
            }
        else
            Notification("Notification", "No Internet Connection");
    }

    @FXML
    private void varifyMail(ActionEvent event) {
        otp = Utilities.otpGerator(otp);
//         e mail used is required pending
        if(Utilities.checkConnnection())
        {
            if(selectDist.getSelectionModel().getSelectedItem() == null)
            {
                Notification("Notification", "Select District");
            }
            else if(selectTal.getSelectionModel().getSelectedItem() == null)
            {
                Notification("Notification", "Select Taluka");
            }
            else if(selectVill.getSelectionModel().getSelectedItem() == null)
            {
                Notification("Notification", "Select Village");
            }
            else
            {
//            if(!selectDist.getValue().toString().equals(""))
//                if(!selectTal.getValue().toString().equals(""))
//                    if(!selectVill.getValue().toString().equals("")){
                if(!emailID.getText().equals("")){
                    if(!Userhandling.viewUser("email", emailID.getText())){
                        prepareAction(UserActionType.FOCUS);
                        countingLeb.setVisible(true);
                        mTimeline.play();
                        varifyBtn.setDisable(false);

                        EmailSender.sendEmail(emailID.getText(), "User Verification", "\n\n User Verification \n Your OTP "+otp+"\n OTP Valid for next 30 Seconds");
                        Notification("Notification","OTP Sent. Check Your E Mail");
                        varifyBtn.setDisable(false);
                    }
                    else
                        Notification("Notification", "E Mail arleady use");
                }
                else{
                    Notification("Notification","Invalid E Mail ID");
                    varifyBtn.setDisable(true);
                }
            }    
        }
        else
            Notification("Notification", "No Internet Connection");
    }

    @FXML
    private void varifyUser(ActionEvent event) throws SQLException {
        if( otpBox1.getText().equals("") || otpBox2.getText().equals("") || otpBox3.getText().equals("") || otpBox4.getText().equals("") )
            Notification("Notification", "Invalid or Oncorrect OTP");
        else
        {
            String id = "";
            int x = 0;
            ResultSet rs = Connection.selectQuery("SELECT * FROM userinfo;");
            while ( rs.next() )
                x++;
            
            id = ""+ (x+1);
            
            System.out.println("Next Id = " + id);

            
    //        String idstr = null;
    //        int id = Integer.parseInt(Utilities.otpGerator(idstr));
    //        while(userId(id))
    //            id = Integer.parseInt(Utilities.otpGerator(idstr));

            if(otp.equals(otpBox1.getText()+""+otpBox2.getText()+""+otpBox3.getText()+""+otpBox4.getText()))
                if(Userhandling.userInsert(id, selectDist.getSelectionModel().getSelectedItem().toString(), selectTal.getSelectionModel().getSelectedItem().toString(), selectVill.getSelectionModel().getSelectedItem().toString(), NewRegisterController.name, NewRegisterController.userName, NewRegisterController.passwords, NewRegisterController.getSelectedGender, NewRegisterController.date_of_birth, emailID.getText(), NewRegisterController.mobilenumber, NewRegisterController.add))
                    if(LoginHandeling.loginInsert(id, NewRegisterController.userName, NewRegisterController.passwords, NewRegisterController.name, NewRegisterController.userSeleceted)){
                        try {
                            Notification("Success", "New User Added Successfully");
                            Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
                            Scene scene = varifyBtn.getScene();
                            root.translateYProperty().set(scene.getHeight());

                            sPane.getChildren().add(root);

                            Timeline timeline = new Timeline();
                            KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
                            KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
                            timeline.getKeyFrames().add(kf);
                            timeline.setOnFinished(t -> { sPane.getChildren().remove(aPane); });
                            timeline.play();
                        } catch (IOException ex) {
                            Logger.getLogger(UserConfirmationController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }   
                    else
                        Notification("Notification", "Enter Valid Data 2");         
                else
                    Notification("Notification", "Enter Valid Data 1");
            else
                Notification("Notification", "Enter Valid OTP");
        }
    }

    @FXML
    private void getSelectedTal(ActionEvent event) {
//        for(String name : Userhandling.Setvil(selectTal.getValue()))
//            selectVill.getItems().add(name);
        
        if ( selectTal.getSelectionModel().getSelectedItem() == null )
            selectVill.getItems().clear();
        else
        {

            taluka = selectTal.getSelectionModel().getSelectedItem().toString().trim();

            selectVill.getSelectionModel().clearSelection();
            selectVill.getItems().clear();
            ArrayList<String> vil = new Userhandling().Setvil(taluka);
            for(String name : vil)
            {
                selectVill.getItems().add(name.trim());
            }   
        }        
    }

    public void prepareAction(UserActionType type){

        int h = 0, m = 0, s = 0;
        mTimerAction = new UserAction(type, "");

        // set time from the user input
        //int userTime = (h*60*60)+(m*60)+s;
        int userTime = 30;
        if (mTimerAction.getType() == UserActionType.FOCUS )
        {
//            setTaskText("WORK IN PROGRESS");
            if (userTime > 0){
                mTimerAction.setRemainingSeconds(userTime);
            }
        }
        else if (mTimerAction.getType() == UserActionType.BREAK )
        {
//            setTaskText("TIME FOR A BREAK");
        }
        setTimerText(mTimerAction.getRemainingSeconds());
        mTimeline = new Timeline();
        mTimeline.setCycleCount(mTimerAction.getRemainingSeconds());
        mTimeline.getKeyFrames().add(
            new KeyFrame(Duration.seconds(1), event -> 
            {
                mTimerAction.countDown();
                setTimerText(mTimerAction.getRemainingSeconds());
            })
        );
        
        mTimeline.setOnFinished(event -> {
            setTimerOff();
        });
    }
    public void setTimerOff()
    {
        varifyBtn.setDisable(true);
        countingLeb.setText("30");
        varifyOTPBtn.setDisable(true);
        resendOTP.setDisable(false);        
        mTimeline.pause();
    }
    
    public void setTimerText(int remainingSeconds){
        int seconds = remainingSeconds % 60;
        setTimerText(""+String.format("%02d",seconds));
    }

    public void setTimerText(String timerText){
        countingLeb.setText(timerText);
    }


    @FXML
    private void closeButtonClickMethod(ActionEvent event) {
        Platform.exit();
    }
}
