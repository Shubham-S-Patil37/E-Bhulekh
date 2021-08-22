
package e_bhulekh;

import java.util.Scanner;


public class moveImageView {
/*

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

    public class Controller implements Initializable {
        @FXML
        ImageView player;



        TranslateTransition transition = new TranslateTransition();
        private double startDragX;
        private double startDragY;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

         player.setOnMousePressed(e -> {
             startDragX = e.getSceneX();
             startDragY = e.getSceneY();
         });

         player.setOnMouseDragged(e -> {
             double X = e.getSceneX();
              if(X - startDragX > 0)
                 player.setTranslateX(5);
             else if (X - startDragX  < 0)
                 player.setTranslateX(-5);
         });

        //This code makes the player move downwards continuously 
        transition.setDuration(Duration.seconds(15));
        transition.setNode(player);
        transition.setToY(800);
        transition.play();
    }
}    
*/
    
/*
public class Controller implements Initializable {
    @FXML
    ImageView player;

    private double startDragX;
    private double startDragY;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        button.setMousePressed(e -> {
            startDragX = e.getSceneX();
            startDragY = e.getSceneY();
        });

        button.setMouseDragged(e -> {
            button.setTranslateX(e.getSceneX() - startDragX);
            button.setTranslateY(e.getSceneY() - startDragY);
        });
    }
}

*/
    
    static int getNumber(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Number :- ");
        return sc.nextInt();
    }
    
    static void vaifyNumber(int number){
        int count = 0;
        for(int i = 1; i <= number; i++)
            count = (i%2==0) ? count++ : count;
        
        System.out.println("Given number is "+((count == 2) ? "Prime Number":"Not Prime Number"));
    }
    
    public static void main(String args[]){
        vaifyNumber(getNumber());
    }
}
