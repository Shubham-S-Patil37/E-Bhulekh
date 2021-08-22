package controller;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import connection.Connection;
import connection.LoginHandeling;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import model.EmailSender;
import model.Utilities;

public class ChangeMailController implements Initializable {
    String otp;
    PreparedStatement pst;
    @FXML
    private JFXTextField emailTxt;
    @FXML
    private JFXButton varifyBtn;
    @FXML
    private JFXTextField otp1;
    @FXML
    private JFXTextField otp2;
    @FXML
    private JFXTextField otp3;
    @FXML
    private JFXTextField otp4;
    @FXML
    private JFXButton changeMail;
    @FXML
    private AnchorPane backgroundPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Utilities.setBGColour(LoginHandeling.bgColor, backgroundPane);
    }    

    @FXML
    private void sendOTP(ActionEvent event) {
        if(Utilities.checkConnnection()){
            if(emailTxt.getText().equals(""))
                Utilities.Notification("Notification", "Enter Valid e mail id");
            else{
                otp = Utilities.otpGerator(otp);
                EmailSender.sendEmail(emailTxt.getText(), "E mail Varification", "\n\n User Varification \n Your OTP "+otp+"\n OTP Valid for next 30 Seconds");
                Utilities.Notification("Notification", "OTP has been send check your email");
            }
        }
        else
            Utilities.Notification("Notification", "Check Internet connection");
    }

    @FXML
    private void updateMail(ActionEvent event) throws SQLException {
        if(otp.equals(otp1.getText()+""+otp2.getText()+""+otp3.getText()+""+otp4.getText())){
            pst = Connection.getPreStatement("update userinfo set email = '"+emailTxt.getText()+"' where id = '"+LoginHandeling.id+"'");
            pst.execute();
            SettingController.changeMailStg.close();
            Utilities.Notification("Notification", "E mail ID updated");
        }
        else
            Utilities.Notification("Notification", "Invalid OTP Entered");
    }
}
