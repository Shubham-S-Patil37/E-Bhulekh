package controller;
import com.jfoenix.controls.JFXButton;
import connection.Connection;
import connection.LoginHandeling;
import connection.Userhandling;
import static controller.MainFxmlController.slider;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.Duration;
import model.Utilities;

public class RequestFXMLController implements Initializable {
    static ObservableList<ObservableList> data;
    ObservableList role;
    static AnchorPane apane;
    int count = 0;
    
    @FXML
    private JFXButton allRequestBtn;
    @FXML
    private JFXButton pendingRequestBtn;
    @FXML
    private JFXButton approveRequestBtn;
    @FXML
    private AnchorPane mainRequestAPane;
    @FXML
    private TableView RequestTbl;
    @FXML
    private Label commonLeb;
    @FXML
    private JFXButton closeButton;
    @FXML
    private AnchorPane sidePane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        commonLeb.setText("All Requests");
        apane = mainRequestAPane;
        setRequestTableViewColumns();
        updateAllRequestTableView();
        sidePane.setStyle("-fx-background:transparent;");
//        approveRequestTableSetColumn();
//        RequestTableSetData("select * from request");
        Utilities.setBGColour(LoginHandeling.bgColor, mainRequestAPane);
    }

    private void setRequestTableViewColumns()
    {
        try 
        {
            String[]    columnNames =   {
                                           "District",
                                           "Taluka",
                                           "Village",
                                           "Owner ID",
                                           "Requester ID",
                                           "Status",
                                           "Land Code",
                                       };
                ResultSet rs = Connection.selectQuery("select * from request");
                /**
                 * ********************************
                 * TABLE COLUMN ADDED DYNAMICALLY *
                 *********************************
                 */
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    //We are using non property style for making dynamic table
                    final int j = i;
                    TableColumn col = new TableColumn(columnNames[i]);

                    if(i == 0 || i == 1 || i == 2 || i == 5)
                        col.setPrefWidth(150.0);
                    else
                        col.setPrefWidth(150.0);
                   col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                       public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                           return new SimpleStringProperty(param.getValue().get(j).toString());
                       }
                   });

                   RequestTbl.getColumns().addAll(col);
                   System.out.println("Column [" + i + "] ");
               }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in Setting JFXTable Columns in Request TableView");
        }
    }
    
    private void updateAllRequestTableView()
    {   
        count = 0;
        RequestTbl.getItems().removeAll();
        data = FXCollections.observableArrayList();
 
        try 
        {
            String reqID = Userhandling.getRequesterID();
            ResultSet rs = Connection.selectQuery("select * from request where requesterid = '" + reqID +"'");
           /**
            * ******************************
            * Data added to ObservableList *
            *******************************
            */
            while ( rs.next())
            {
                ObservableList<String> row = FXCollections.observableArrayList();

                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                        row.add(rs.getString(i).trim());
                }
                System.out.println("Row [1] added " + row);
                data.add(row);
            }
            //FINALLY DATA ADDED TO TableView
            RequestTbl.setItems(data);
            RequestTbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            //RequestTbl.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in Addign Data to Table");
        }       
    }     

    private void updatePendingRequestTableView()
    {   
        count = 0;
        RequestTbl.getItems().removeAll();
        data = FXCollections.observableArrayList();
 
        try 
        {
            String reqID = Userhandling.getRequesterID();
            ResultSet rs = Connection.selectQuery("select * from request where requesterid = '" + reqID + "' and status = 'pending';");
           /**
            * ******************************
            * Data added to ObservableList *
            *******************************
            */
            while ( rs.next())
            {
                ObservableList<String> row = FXCollections.observableArrayList();

                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                        row.add(rs.getString(i).trim());
                }
                System.out.println("Row [1] added " + row);
                data.add(row);
            }
            //FINALLY DATA ADDED TO TableView
            RequestTbl.setItems(data);
            RequestTbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            //RequestTbl.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in Addign Data to Table");
        }       
    }     

    private void updateApprovedRequestTableView() {   
        count = 0;
        RequestTbl.getItems().removeAll();
        data = FXCollections.observableArrayList();
 
        try 
        {
            String reqID = Userhandling.getRequesterID();
            ResultSet rs = Connection.selectQuery("select * from request where requesterid = '" + reqID + "' and status = 'rejected';");
           /**
            * ******************************
            * Data added to ObservableList *
            *******************************
            */
            while ( rs.next())
            {
                ObservableList<String> row = FXCollections.observableArrayList();

                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                        row.add(rs.getString(i).trim());
                }
                System.out.println("Row [1] added " + row);
                data.add(row);
            }
            //FINALLY DATA ADDED TO TableView
            RequestTbl.setItems(data);
            RequestTbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            //RequestTbl.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in Addign Data to Table");
        }       
    }     
    
    @FXML
    private void getAllRequest(ActionEvent event) {
        commonLeb.setText("All Requests");
        updateAllRequestTableView();
//        RequestTableSetData("select * from request");
    }

    @FXML
    private void getAllPendingRequest(ActionEvent event) {
        commonLeb.setText("Pending Requests");
        updatePendingRequestTableView();
//        RequestTableSetData("select * from request where status = 'pending'");
    }

    @FXML
    private void getAllApproveRequest(ActionEvent event) {
        commonLeb.setText("Rejected Requests");
        updateApprovedRequestTableView();
//        RequestTableSetData("select * from request where status = 'approve'");
    }
    
    void approveRequestTableSetColumn(){
        try {
            String[] columnNames =  { "dist", "tal", "vilage", "ownerid", "requesterid", "status", "landcode"};
            ResultSet rs = Connection.selectQuery("select * from request");
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(columnNames[i]);

                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                
                RequestTbl.getColumns().addAll(col);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RequestFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void RequestTableSetData(String query){
        try {
            RequestTbl.getItems().removeAll();
            data = FXCollections.observableArrayList();
//            ResultSet rs = Connection.selectQuery("select dist, tal, vilage, ownerid, requesterid, landcode, status from request where status = 'pending'");
            ResultSet rs = Connection.selectQuery(query);
            while (rs.next())
            {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
                    row.add(rs.getString(i));
                data.add(row);
            }
            RequestTbl.setItems(data);
        } catch (SQLException ex) { System.out.println(""+ex); }
    }

    @FXML
    private void closeButtonClickMethod(ActionEvent event) throws IOException {
        if(!MainFxmlFrontViewController.buttonString.equals("Home")){
            MainFxmlFrontViewController.buttonString = "Home";
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainFxmlFrontView.fxml"));
            Scene scene = closeButton.getScene();
            root.translateXProperty().set(scene.getWidth());

            MainFxmlController.slider.getChildren().add(root);

            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(t -> { slider.getChildren().remove(apane); });
            timeline.play(); 
        }
    }
}

