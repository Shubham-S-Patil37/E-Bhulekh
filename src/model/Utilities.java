package model;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class Utilities {
    private double xOffset = 0;
    private double yOffset = 0;
    public static Stage stage ;
    public static Parent root;
    public static Scene scene;
    public static RotateTransition rt;
    public static TranslateTransition slide = new TranslateTransition();
    public static FXMLLoader fxmlLoader;
    static Image image ;
    static ImageView view;

    public void openFXML(String file, Stage stage){
        try {
            System.out.println("hi");
//            FXMLLoader loader = new FXMLLoader(getClass().getResource(file));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/newPkg/Setting.fxml"));
            Parent root = (Parent) loader.load();
            stage.initStyle(StageStyle.TRANSPARENT);
            //grab your root here
            root.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });
            //move around here
            root.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            });
            Scene scene = new Scene(root);
            //        primaryStage.setMaximized(true);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) { Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex); }
    }

    public static void movePanetoX(AnchorPane pane, int form , int upto , int delay){
        pane.setTranslateX(form);
        slide.setDuration(Duration.seconds(delay));
        slide.setNode(pane);
        slide.setToX(upto);
        slide.play();
    }

    public static void movetoX(AnchorPane pane, int x, int delay){
        slide.setDuration(Duration.seconds(delay));
        slide.setNode(pane);
        slide.setToX(x);
        slide.play();
    }

    public static void movetoY(AnchorPane pane, int Y, int delay){
//        pane.setTranslateY(form);
        slide.setDuration(Duration.seconds(delay));
        slide.setNode(pane);
        slide.setToY(Y);
        slide.play();
    }

    public static void getRotation(ImageView image, int delay, int Cycle){
        rt = new RotateTransition(Duration.millis(delay), image);
        rt.setByAngle(360);
        rt.setCycleCount(Cycle);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();
        rt.setOnFinished(event -> {});
    }

    public static void imageButton(String imageName, JFXButton button){
        image = new Image(new File("images/"+imageName).toURI().toString());
        view = new ImageView(image);
        view.setFitWidth(30);
        view.setFitHeight(30);
        view.setPreserveRatio(true);
        button.setGraphic(view);
    }
    
    public static void Notification(String Title, String Messages){
//        image = new Image(new File("images/menu.png").toURI().toString());
        Notifications noti = Notifications.create()
                .title(Title)
                .text(Messages)
//                .graphic(new ImageView(image))
                .hideAfter(javafx.util.Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT);
        noti.show();
    }
    
    public static Boolean checkConnnection(){
       Socket socket = new Socket();
        InetSocketAddress add = new InetSocketAddress("www.youtube.com",80);
        try{
            socket.connect(add,3000);
            return true;
        }
        catch(Exception e){
            return false;
        } finally{ try{ socket.close(); }catch(Exception e){ return false; } }    
    }
    
    public static String otpGerator(String string){
        string = ""+Math.round(Math.random() * 10000);
        while(string.length() != 4 )
            return string = ""+Math.round(Math.random() * 10000);
        return string;
    }
    
    public static void setBGColour(int bgcolour, AnchorPane pane){
        switch(bgcolour){
            case 1:
                pane.setStyle("-fx-background-color: linear-gradient(to right, #0f0c29, #302b63, #24243e);");
                break;
            case 2:
                pane.setStyle("-fx-background-color: linear-gradient(to right, c31432, #240b36);");
                break;
            case 3:
//                pane.setStyle("-fx-background-color: linear-gradient(to right, #ed213a, #93291e);");
                pane.setStyle("-fx-background-color: linear-gradient(to right, #4B008A , #017368 );");
                break;
            case 4:
                pane.setStyle("-fx-background-color: linear-gradient(to left, #aa076b, #61045f);");
                break;
        }
    }

    public static void main(String args[]){
        new Utilities().openFXML("",stage);
    }
}
