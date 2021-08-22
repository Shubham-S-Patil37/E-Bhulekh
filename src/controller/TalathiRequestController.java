package controller;
import com.jfoenix.controls.JFXButton;
import connection.Connection;
import connection.LoginHandeling;
import connection.Userhandling;
import static controller.MainFxmlController.slider;
import java.io.IOException;
import java.net.URL;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.Duration;
import model.Utilities;
import static model.Utilities.Notification;

public class TalathiRequestController implements Initializable {
    public static String district, taluka, village_name, land_Code, owner_Name, land_Area, status, prev_puchase_date, current_purchase_date, ownerID, requesterID;
    public static String talathi_district = "", talathi_taluka = "", talathi_village_name = "";
    public static String self_hash = "0", pre_hash = "0", next_hash = "0";
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    static ObservableList<ObservableList> data;
    ObservableList request;
    static AnchorPane apane;
    int count = 0;
    
    @FXML
    private JFXButton approveRequestBtn;
    @FXML
    private AnchorPane mainRequestAPane;
    @FXML
    private Label commonLeb;
    @FXML
    private JFXButton closeButton;
    @FXML
    private AnchorPane sidePane;
    @FXML
    private TableView talathiRequestsTable;
    @FXML
    private JFXButton rejectRequestBtn;
    @FXML
    private JFXButton pendingRequestBtn;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        commonLeb.setText("All Requests");
        apane = mainRequestAPane;
        System.out.println("Login User ID = " + LoginController.loginUser);
        setRequestTableViewColumns();

        try {
            fetchTalathiInfo();
        } catch (SQLException ex) {
            Logger.getLogger(TalathiRequestController.class.getName()).log(Level.SEVERE, null, ex);
        }
        updateAllRequestTableView();
        resetButtons();
        sidePane.setStyle("-fx-background:transparent;");
//        approveRequestTableSetColumn();
//        RequestTableSetData("select * from request");
        Utilities.setBGColour(LoginHandeling.bgColor, mainRequestAPane);
    }
    private void resetButtons()
    {
        approveRequestBtn.setDisable(true);
        rejectRequestBtn.setDisable(true);
        pendingRequestBtn.setDisable(true);        
    }
    private void fetchTalathiInfo() throws SQLException
    {
        if ( LoginController.status.equals("talathi"))
        {
            ResultSet rs = Connection.selectQuery("select * from userinfo where username = '"+ LoginController.loginUser + "'" );
            if ( rs == null )
                Notification("Notification","Unable to Fetch Talathi Info!");
            else
            {
                while(rs.next())
                {
                    talathi_district = rs.getString("dist").trim();
                    talathi_taluka = rs.getString("tal").trim();
                    talathi_village_name = rs.getString("vil").trim();

//                    System.out.println("Talathi Dist = " + talathi_district);
//                    System.out.println("Talathi Tal = " + talathi_taluka);
//                    System.out.println("Talathi Vil = " + talathi_village_name);
                }             
            }
        }
        else
            Notification("Notification", "Only Talathi can see the data");
               
    }
    private void setRequestTableViewColumns()
    {
        try 
        {
        String[]    columnNames =       {
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

                   talathiRequestsTable.getColumns().addAll(col);
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
        talathiRequestsTable.getItems().removeAll();
        data = FXCollections.observableArrayList();
 
        try 
        {
            //ResultSet rs = Connection.selectQuery("select * from request where requesterid = '" + reqID +"'");
            if ( talathi_district.equals(""))
            {
                
            }
            else
            {
                ResultSet rs = Connection.selectQuery("select * from request where dist = '" + talathi_district + "' and tal = '" + talathi_taluka
                        + "' and vilage = '" + talathi_village_name+ "'" );

               /**
                * ******************************
                * Data added to ObservableList *
                *******************************
                */
                if ( rs == null )
                   Notification("Notification", "Unable to load Data !");
                else
                {
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
                    talathiRequestsTable.setItems(data);
                    talathiRequestsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                    //RequestTbl.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in Addign Data to Table");
        }       
    }     

    @FXML
    private void closeButtonClickMethod(ActionEvent event) throws IOException {
        if(!TalathiFrontViewController.buttonString.equals("Home")){
            TalathiFrontViewController.buttonString = "Home";
            Parent root = FXMLLoader.load(getClass().getResource("/view/TalathiFrontView.fxml"));
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

    @FXML
    private void talathiRequestsTableClicked(MouseEvent event) throws SQLException {
        
        request = (ObservableList)talathiRequestsTable.getSelectionModel().getSelectedItem();
        
//        district = request.get(0).toString();
        taluka = request.get(1).toString();
        village_name = request.get(2).toString();
        ownerID = request.get(3).toString();
        requesterID = request.get(4).toString();
        status = request.get(5).toString();
        land_Code = request.get(6).toString();
        
        System.out.println("Owner ID = " + ownerID);        
        
        if(status.equals("pending"))
        {
            approveRequestBtn.setDisable(false);
            rejectRequestBtn.setDisable(false);
            pendingRequestBtn.setDisable(true);
        }
        if(status.equals("rejected"))
        {
            approveRequestBtn.setDisable(false);
            rejectRequestBtn.setDisable(true);
            pendingRequestBtn.setDisable(false);
        }
        
    }

    public static String calculateBlockHash(String dataToHash) 
    {
        MessageDigest digest = null;
        byte[] bytes = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            bytes = digest.digest(dataToHash.getBytes(UTF_8));
        } catch (NoSuchAlgorithmException ex) {

        }
        StringBuffer buffer = new StringBuffer();
        for (byte b : bytes) {
            buffer.append(String.format("%02x", b));
        }
        return buffer.toString();
    }

    private void updateLandRecord()
    {

        Connection.query("update landinfo set "
                                                + "selldate = '" + current_purchase_date + "', "
                                                + "status = 'Sold" + "', "
                                                + "next_hash = '" + self_hash +"' "
                                                + "where hash = '"+ pre_hash + "';") ;
        
    }

    private boolean findPreviousLandRecord() throws SQLException
    {
        ResultSet rs = Connection.selectQuery("select * from landinfo where userid = '"+ ownerID +"' and dist = '" + district + "' and taluka = '" + taluka 
                + "' and vilage = '" + village_name + "' and code = '" + land_Code + "'" );
        if ( rs == null )
            Notification("Notification","Unable to FInd Previous Data!");
        else
        {
            while(rs.next())
            {
//                owner_Name = rs.getString("username");
                land_Area = rs.getString("area");
                prev_puchase_date = rs.getString("buydate");
                pre_hash = rs.getString("hash");
//                System.out.println("Previous Hash = " + owner_Name);
                System.out.println("Land Area = " + land_Area);
                System.out.println("Previous Purchase Date = " + prev_puchase_date);
                System.out.println("Previous Hash = " + pre_hash);
                //System.out.println("counter "+rs.getObject(6).toString());break;
                return true;
            }             
        }
        return false;
    }    

    @FXML
    private void approveRequestBtnClicked(ActionEvent event) throws SQLException {
        current_purchase_date   = formatter.format(LocalDate.now());
        System.out.println("Current Date = " + current_purchase_date);

        if (findPreviousLandRecord())
        {
            if( pre_hash.equals("0"))
                Notification("Notification","Unable to fetch Pre-Hash!");
            else
            {
                findRequesterName();
                String DataToHash = district + taluka + village_name + land_Code + owner_Name + land_Area + current_purchase_date + pre_hash;
                self_hash = calculateBlockHash(DataToHash);

                Userhandling.landInsert(district, taluka, village_name, land_Code, requesterID, 
                        owner_Name, land_Area, current_purchase_date, "", "Active", pre_hash, self_hash, "0");

                updateLandRecord();
                deleteRecordFromRequestsTable();
                updateAllRequestTableView();
                Notification("Success","Record Approved Successfully");
            }
        }
        else
            Notification("Notification","Error Occured!");
    }
    private void findRequesterName() throws SQLException
    {
            ResultSet rs = Connection.selectQuery("select name from userinfo where id = '"+ requesterID + "'" );
            if ( rs == null )
                Notification("Notification","Unable to Fetch Requester Info!");
            else
            {
                while(rs.next())
                {
                    owner_Name = rs.getString("name").trim();
                    System.out.println("Requester Name = " + owner_Name);
                }             
            }
        
    }
    private void deleteRecordFromRequestsTable()
    {
        Connection.query("delete from request where dist = '" + district + "' and tal = '" + taluka  + "' and vilage = '" + village_name 
               + "' and ownerid = '" + ownerID + "' and requesterid = '" + requesterID + "' and status = 'pending' and landcode = '" + land_Code +"'" );
    }
    
    @FXML
    private void rejectRequestBtnClicked(ActionEvent event) {

        Connection.query("update request set status = 'rejected' where dist = '" + district + "' and tal = '" + taluka  + "' and vilage = '" + village_name 
               + "' and ownerid = '" + ownerID + "' and requesterid = '" + requesterID + "' and status = 'pending' and landcode = '" + land_Code +"'" ); 

        updateAllRequestTableView();
        resetButtons();
    }

    @FXML
    private void pendingRequestBtnClicked(ActionEvent event) {
        Connection.query("update request set status = 'pending' where dist = '" + district + "' and tal = '" + taluka  + "' and vilage = '" + village_name 
               + "' and ownerid = '" + ownerID + "' and requesterid = '" + requesterID + "' and status = 'rejected' and landcode = '" + land_Code +"'" ); 

        updateAllRequestTableView();
        resetButtons();
    }
}

