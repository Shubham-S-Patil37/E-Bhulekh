package controller;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import static controller.LoginController.mainPane;
import java.io.File;
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
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import static model.Utilities.Notification;
import connection.Userhandling;
import static connection.Userhandling.userId;
import javafx.application.Platform;

public class NewRegisterController implements Initializable {
    public static String getSelectedGender,userSeleceted, date_of_birth;
    public static String name,userName,passwords,mobilenumber,add;
    @FXML
    private ImageView profileImg;
    @FXML
    private JFXTextField nameTxt;
    @FXML
    private JFXTextField userNameTxt;
    @FXML
    private JFXPasswordField passwordsTxt;
    @FXML
    private JFXRadioButton genderM;
    @FXML
    private ToggleGroup gender;
    @FXML
    private JFXRadioButton genderF;
    @FXML
    private Button nextBtn;
    @FXML
    private AnchorPane apane;
    @FXML
    private JFXDatePicker dob;
    @FXML
    private JFXTextArea address;
    @FXML
    private JFXTextField mobileNo;
    @FXML
    private JFXRadioButton user;
    @FXML
    private ToggleGroup userType;
    @FXML
    private JFXRadioButton talathi;
    @FXML
    private JFXButton closeButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {       
        //profileImg.setImage(new Image(new File("Images/profile.jpeg").toURI().toString()));
        getSelectedGender = "Male";
        userSeleceted = "user";
        genderM.setSelected(true);
        user.setSelected(true);
//        mobileNo.textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue, 
//                String newValue) {
//                if (!newValue.matches("\\d{10}")) {
//                    mobileNo.setText(oldValue);
//                }
//            }
//        });
    }    

    @FXML
    private void varifyEmail(ActionEvent event) throws IOException {
        if(!nameTxt.getText().equals(""))
            if(!userNameTxt.getText().equals(""))
                if(!passwordsTxt.getText().equals(""))
                    if(!mobileNo.getText().equals(""))
                        if(dob.getValue() != null)
                            if(!address.getText().equals("")){
                                if(!Userhandling.viewUser("contact", mobileNo.getText()))
                                    if(!Userhandling.viewUser("username", userNameTxt.getText())){
                                        name          = nameTxt.getText();
                                        userName      = userNameTxt.getText();
                                        passwords     = passwordsTxt.getText();
                                        mobilenumber  = mobileNo.getText();
                                        date_of_birth = dob.getValue().toString();
                                        add           = address.getText();

                                        Parent root = FXMLLoader.load(getClass().getResource("/view/UserConfirmation.fxml"));
                                        Scene scene = nextBtn.getScene();
                                        root.translateYProperty().set(scene.getHeight());
                                        mainPane.getChildren().add(root);
                                        Timeline timeline = new Timeline();
                                        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
                                        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
                                        timeline.getKeyFrames().add(kf);
                                        timeline.setOnFinished(t -> { mainPane.getChildren().remove(apane); });
                                        timeline.play();
                                    }
                                    else
                                        Notification("Notification","Username Already Used");
                                else
                                    Notification("Notification","Mobile Number Already Register");
                            }
                            else
                                Notification("Notification","Enter Your Valid Address");
                        else
                            Notification("Notification","Select Your Date of birth");
                    else
                        Notification("Notification","Enter Correct Mobile Number");
                else
                    Notification("Notification","Enter Correct Passwords");
            else
                Notification("Notification","Enter Correct User Name");
        else
            Notification("Notification","Enter Correct Name");
    }

    @FXML
    private void genderMSelect(ActionEvent event)   { getSelectedGender = "male";  }

    @FXML
    private void genderFSelect(ActionEvent event)   { getSelectedGender = "female";}

    @FXML
    private void userSelected(ActionEvent event)    { userSeleceted = "user";      }

    @FXML
    private void talathiSelected(ActionEvent event) { userSeleceted = "talathi";   }

    @FXML
    private void closeButtonClickMethod(ActionEvent event) {
        Platform.exit();
    }
}
