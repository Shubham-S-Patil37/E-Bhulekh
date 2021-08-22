package controller;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import connection.Connection;
import connection.LoginHandeling;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Utilities;

public class SettingController implements Initializable {
    public static Stage changePassStg,changeMailStg;
    public static Parent changePassRoot,changeMailRoot;
    @FXML
    private JFXButton changePass;
    @FXML
    private JFXButton changeMail;
    @FXML
    private JFXToggleButton bgcolour1;
    @FXML
    private JFXToggleButton bgcolour2;
    @FXML
    private JFXToggleButton bgcolour3;
    @FXML
    private JFXToggleButton bgcolour4;
    @FXML
    private AnchorPane backgroundPane;
    @FXML
    private JFXButton closeButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getSetBackround(LoginHandeling.bgColor);
        Utilities.setBGColour(LoginHandeling.bgColor, backgroundPane);
    }

    void paneList(int colurIndex){
        Utilities.setBGColour(colurIndex, MainFxmlFrontViewController.apane);
        Utilities.setBGColour(colurIndex, RequestFXMLController.apane);
        Utilities.setBGColour(colurIndex, MyPurchaseController.apane);
        Utilities.setBGColour(colurIndex, PurchaseFXMLController.apane);
    }

    void resetbgReset(){
        bgcolour1.setSelected(false);
        bgcolour2.setSelected(false);
        bgcolour3.setSelected(false);
        bgcolour4.setSelected(false);
    }
    
    void getSetBackround(int bgCount){
        switch(bgCount){
            case 1:
                resetbgReset();
                bgcolour1.setSelected(true);
                break;
            case 2:
                resetbgReset();
                bgcolour2.setSelected(true);
                break;
            case 3:
                resetbgReset();
                bgcolour3.setSelected(true);
                break;
            case 4:
                resetbgReset();
                bgcolour4.setSelected(true);
                break;
        }
    }

    @FXML
    private void backgroundColour1(ActionEvent event) throws SQLException {
        Connection.getPreStatement("update login set background = '1' where id = '"+LoginHandeling.id+"'").execute();
        resetbgReset();
        bgcolour1.setSelected(true);
        LoginHandeling.bgColor = 1;
        Utilities.setBGColour(1, backgroundPane);
        paneList(LoginHandeling.bgColor);
    }

    @FXML
    private void backgroundColour2(ActionEvent event) throws SQLException {
        Connection.getPreStatement("update login set background = '2' where id = '"+LoginHandeling.id+"'").execute();
        resetbgReset();
        bgcolour2.setSelected(true);
        LoginHandeling.bgColor = 2;
        Utilities.setBGColour(2, backgroundPane);
        paneList(LoginHandeling.bgColor);
    }

    @FXML
    private void backgroundColour3(ActionEvent event) throws SQLException {
        Connection.getPreStatement("update login set background = '3' where id = '"+LoginHandeling.id+"'").execute();
        resetbgReset();
        bgcolour3.setSelected(true);
        LoginHandeling.bgColor = 3;
        Utilities.setBGColour(3, backgroundPane);
        paneList(LoginHandeling.bgColor);
    }

    @FXML
    private void backgroundColour4(ActionEvent event) throws SQLException {
        Connection.getPreStatement("update login set background = '4' where id = '"+LoginHandeling.id+"'").execute();
        resetbgReset();
        bgcolour4.setSelected(true);
        LoginHandeling.bgColor = 4;
        Utilities.setBGColour(4, backgroundPane);
        paneList(LoginHandeling.bgColor);
    }

    @FXML
    private void changePasswords(ActionEvent event) throws IOException {
        MainFxmlController.settingStg.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ChangePasswords.fxml"));
        changePassRoot = (Parent) fxmlLoader.load();
        changePassStg = new Stage();
        changePassStg.setScene(new Scene(changePassRoot));  
        changePassStg.show();
    }

    @FXML
    private void changeEmail(ActionEvent event) throws IOException {
        MainFxmlController.settingStg.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ChangeMail.fxml"));
        changeMailRoot = (Parent) fxmlLoader.load();
        changeMailStg = new Stage();
        changeMailStg.setScene(new Scene(changeMailRoot));  
        changeMailStg.show();
    }

    @FXML
    private void closeButtonClickMethod(ActionEvent event) {
        //LoginController.stage.close();
        MainFxmlController.settingStg.close();        
    }
}
