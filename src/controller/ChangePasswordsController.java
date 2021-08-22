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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.Utilities;

public class ChangePasswordsController implements Initializable {
    PreparedStatement ps;
    boolean charContain,numContain,specialContain;
    @FXML
    private JFXTextField oldpassTxt;
    @FXML
    private JFXTextField newPassTxt;
    @FXML
    private JFXTextField confirmPassTxt;
    @FXML
    private JFXButton changePassBtn;
    @FXML
    private AnchorPane backgroundPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Utilities.setBGColour(LoginHandeling.bgColor, backgroundPane);
    }

    @FXML
    private void changePassword(ActionEvent event) throws SQLException {
        if (oldpassTxt.getText().equals(LoginController.password)) 
            if(charContain && numContain && specialContain)
                if(newPassTxt.getText().equals(confirmPassTxt.getText())){
                    Connection.getPreStatement("update login set user_pass = '"+newPassTxt.getText()+"' where id = '"+LoginHandeling.id+"'").execute();
                    Utilities.Notification("Notification", "Password change successfully");
                }
                else
                    Utilities.Notification("Notification", "new password not Matches with confirm password");
            else
                Utilities.Notification("Notification", "Password Criteria not Matches");
        else
            Utilities.Notification("Notification", "Current password not Matches");
    }

    @FXML
    private void checkPasswordCriteria(KeyEvent event) {
        char ch = newPassTxt.getText().charAt(newPassTxt.getText().length()- 1);
        if ((ch >= 65 && ch <= 90) || (ch >= 97 && ch <= 122))    
            charContain = true;
        else if (ch >= 48 && ch <= 57)
            numContain = true;
        else
            specialContain = true;
        
    }
}
