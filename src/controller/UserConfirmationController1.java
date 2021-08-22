package controller;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import connection.LoginHandeling;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
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
import javafx.util.Duration;
import model.EmailSender;
import model.Utilities;
import static model.Utilities.Notification;
import connection.Userhandling;
import javafx.scene.layout.StackPane;
import static model.Utilities.movetoY;

public class UserConfirmationController1 implements Initializable {
    String otp,idstr;
    @FXML
    private JFXButton varifyBtn;
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
    private JFXButton notificationcloseBtn;
    @FXML
    private AnchorPane notificationPnl;
    @FXML
    private AnchorPane apane;
    @FXML
    private JFXButton varifyOTPBtn;
    @FXML
    private StackPane sPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customInitializer();
    }    
    
    void customInitializer(){
        varifyBtn.setDisable(true);
        resendOTP.setDisable(true);
        notificationPnl.setVisible(false);
        notificationPnl.setTranslateY(-1000);
        
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
    private void varifyMail(ActionEvent event) {
        otp = Utilities.otpGerator(otp);

        if(Utilities.checkConnnection())
//            if(Userhandling.viewUser("email", emailID.getText()))
                if(!emailID.getText().equals("")){
                    EmailSender.sendEmail(emailID.getText(), "User Varifiaction", "\n\n User Varification \n Your OTP "+otp+"\n OTP Valid for next 30 Seconds");
                    Notification("Notification","Check Your E Mail");
                    varifyBtn.setDisable(false);
                    resendOTP.setDisable(false);
                }
                else{
                    Notification("Notification","Invalid E Mail ID");
                    varifyBtn.setDisable(true);
                    resendOTP.setDisable(true);
                }
//            else
//                Notification("Notification", "E Mail arleady use");
        else
            Notification("Notification", "Connection Loss");
    }

    @FXML
    private void varifyUser(ActionEvent event) {
//        int id = Integer.parseInt(Utilities.otpGerator(idstr));
//            if(otp.equals(otpBox1.getText()+""+otpBox2.getText()+""+otpBox3.getText()+""+otpBox4.getText()))
//                if(Userhandling.userInsert(id, NewRegisterController.name, NewRegisterController.userName, NewRegisterController.passwords, NewRegisterController.getSelectedGender, NewRegisterController.date_of_birth, emailID.getText(), NewRegisterController.mobilenumber, NewRegisterController.add))
//                    if(LoginHandeling.loginInsert(id, NewRegisterController.userName, NewRegisterController.passwords, NewRegisterController.name, NewRegisterController.userSeleceted)){
//                        notificationPnl.setVisible(true);
//                        notificationPnl.setPrefSize(928, 387);
//                        notificationPnl.setLayoutX(501);
//                        notificationPnl.setLayoutY(307);
//                        movetoY(notificationPnl, 100, 5);
//                    }   
//                    else
//                        Notification("Notification", "Enter Valid Data");         
//                else
//                    Notification("Notification", "Enter Valid Data");
//            else
//                Notification("Notification", "Enter Valid OTP");
    }

    @FXML
    private void getLogin(ActionEvent event) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
            Scene scene = notificationcloseBtn.getScene();
            root.translateYProperty().set(scene.getHeight());

            sPane.getChildren().add(root);

            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(t -> { sPane.getChildren().remove(apane); });
            timeline.play();
    }

    @FXML
    private void reSendOTP(ActionEvent event) {
        Utilities.otpGerator(otp);
        if(Utilities.checkConnnection())
            if(!emailID.getText().equals("")){
                EmailSender.sendEmail(emailID.getText(), "User Varifiaction", "\n\n User Varification \n Your OTP "+otp+"\n OTP Valid for next 30 Seconds");
                Notification("Notification","Check Your E Mail");
                varifyBtn.setDisable(false);
            }
            else{
                Notification("Notification","Invalid E Mail ID");
                varifyBtn.setDisable(true);
            }
        else
            Notification("Notification", "Connection Loss");
    }
}
