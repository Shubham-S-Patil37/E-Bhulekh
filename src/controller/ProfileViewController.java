package controller;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import connection.Connection;
import connection.LoginHandeling;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import model.Utilities;

public class ProfileViewController implements Initializable {
    ResultSet resultSet;
    PreparedStatement pst;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate localDate;
    String genderType;
    @FXML
    private JFXRadioButton genderM;
    @FXML
    private ToggleGroup gender;
    @FXML
    private JFXRadioButton genderF;
    @FXML
    private JFXDatePicker dob;
    @FXML
    private JFXRadioButton user;
    @FXML
    private ToggleGroup userType;
    @FXML
    private JFXRadioButton talathi;
    @FXML
    private JFXTextField nameTxt;
    @FXML
    private JFXTextField userName;
    @FXML
    private JFXTextField mobileNo;
    @FXML
    private JFXTextField addressTxt;
    @FXML
    private JFXTextField emailTxt;
    @FXML
    private JFXButton updateBtn;
    @FXML
    private JFXButton cancelBtn;
    @FXML
    private AnchorPane aPane;
    @FXML
    private JFXButton closeButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        custominitializer();
    }    

    void custominitializer()
    {
        
        Utilities.setBGColour(LoginHandeling.bgColor, aPane);
        
        mobileNo.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if (!newValue.matches("\\d{10}")) {
                    mobileNo.setText(oldValue);
                }
            }
        });
        
        user.setDisable(true);
        talathi.setDisable(true);
        user.setSelected( ( ( !LoginHandeling.userType.equals("user") )   ? false : true));
        talathi.setSelected(((!LoginHandeling.userType.equals("talathi")) ? false : true));
         try{
//            resultSet = Connection.selectQuery("select * from userinfo where id = '"+LoginHandeling.id+"'");
//            System.out.println(LoginHandeling.id);
//            System.out.println(resultSet.next());
//            if(resultSet.next()){
//                nameTxt.setText(resultSet.getString("name"));
//            }
            resultSet = Connection.selectQuery("select * from userinfo");
            while(resultSet.next()){
                if(resultSet.getObject(1).toString().equals(""+LoginHandeling.id)){
                    nameTxt.setText(resultSet.getObject(5).toString());
                    userName.setText(resultSet.getObject(6).toString());
                    mobileNo.setText(resultSet.getObject(11).toString());
                    genderM.setSelected(((resultSet.getObject(8).toString().equals("male"))? true : false));
                    genderF.setSelected(((resultSet.getObject(8).toString().equals("female"))? true : false));
                    localDate = LocalDate.parse(resultSet.getObject(9).toString(), formatter);
                    dob.setValue(localDate);
                    addressTxt.setText(resultSet.getObject(12).toString());
                    emailTxt.setText(resultSet.getObject(10).toString());
                }
            }
        }catch(SQLException e){}
    }

    @FXML
    private void genderMSelect(ActionEvent event) { genderType = "male"; }

    @FXML
    private void genderFSelect(ActionEvent event) { genderType = "female"; }

    @FXML
    private void closeButtonClickMethod(ActionEvent event) {
        //LoginController.stage.close();
        MainFxmlController.profileStg.close();
    }

    @FXML
    private void updateProfile(ActionEvent event) throws SQLException {
        pst = Connection.getPreStatement("update userinfo set name = ? ,username = ?, gender = ?, dbo = ?, contact = ?, address = ? where id = '"+LoginHandeling.id+"'");
        pst.setString(1, nameTxt.getText());
        pst.setString(2, userName.getText());
        pst.setString(3, genderType);
        pst.setString(4, dob.getValue().toString());
        pst.setString(5, mobileNo.getText());
        pst.setString(6, addressTxt.getText());
        pst.execute();
    }

    @FXML
    private void cancelButtonClick(ActionEvent event) {
        MainFxmlController.profileStg.close();
    }
}
