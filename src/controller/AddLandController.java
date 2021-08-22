package controller;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
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
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import javafx.util.Duration;
import model.Utilities;
import static model.Utilities.Notification;

public class AddLandController implements Initializable {
    TranslateTransition slide = new TranslateTransition();
    public static String district, taluka, village_name, land_Code, owner_Name, land_Area, purchase_date;
    public static String self_hash, pre_hash, next_hash;
    static StackPane slidinpane;
    static AnchorPane apane;
    static ObservableList<ObservableList> data;
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");    
    ObservableList users;
    int count = 0;    
    
    @FXML
    private JFXComboBox<String> dist;
    @FXML
    private JFXComboBox<String> tal;
    @FXML
    private JFXComboBox<String> vilage;
    int counter = 0;
    @FXML
    private AnchorPane aPane;
    @FXML
    private JFXButton closeButton;
    @FXML
    private JFXTextField nameTxt;
    @FXML
    private JFXTextField landCode;
    @FXML
    private JFXTextField area;
    @FXML
    private JFXButton addLand;
    @FXML
    private JFXDatePicker date;
    @FXML
    private TableView ownerNamesTable;
    @FXML
    private JFXTextField searchNameTxt;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        apane = aPane;
        setDistrictComboBox();        
        Utilities.setBGColour(LoginHandeling.bgColor, aPane);
        setAllListeners();
        setTableViewColumns();
        resetAllFields();
        //updateTableView();
    }

    private void setAllListeners()
    {
        searchNameTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if (!newValue.equals("")) 
                {
                    updateTableRecords(newValue);
                    searchNameTxt.setText(newValue);
                }
                else if (newValue.equals(""))
                {
                    updateTableView();
                }
            }
        });
        
        area.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if (!newValue.matches("\\d{0,3}?(\\.\\d{0,2})?")) {
                    area.setText(oldValue);
                }
            }
        });
    }
    private void updateTableRecords(String newVal)
    {
        try 
        {
//            ResultSet rs = Connection.selectQuery("select @a:=@a+1, name from userinfo where name like '%" + searchNameTxt.getText() + "%'");
            ResultSet rs = Connection.selectQuery("select id, name from userinfo where name like '%" + newVal + "%'");
           /**
            * ******************************
            * Data added to ObservableList *
            *******************************
            */
            if( rs != null)
            {
                count = 0;
                ownerNamesTable.getItems().removeAll();
                data = FXCollections.observableArrayList();
 
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
                ownerNamesTable.setItems(data);
                ownerNamesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                //ownerNamesTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in Addign Data to Table");
        }       
        
    }
    private void setTableViewColumns()
    {
        try 
        {
            String[]    columnNames =   {
                                           "User ID",
                                           "User Name",
                                       };
                ResultSet rs = Connection.selectQuery("select id, name from userinfo");
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

                   ownerNamesTable.getColumns().addAll(col);
                   System.out.println("Column [" + i + "] ");
               }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in Setting JFXTable Columns in TableView");
        }
    }
    
    private void updateTableView()
    {   
        count = 0;
        ownerNamesTable.getItems().removeAll();
        data = FXCollections.observableArrayList();
 
        try 
        {
            ResultSet rs = Connection.selectQuery("select id, name from userinfo order by id asc");
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
            ownerNamesTable.setItems(data);
            ownerNamesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            //ownerNamesTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in Addign Data to Table");
        }       
    }     
    
    
    private void setDistrictComboBox()
    {
        dist.getSelectionModel().clearSelection();
        dist.getItems().clear();
        ArrayList<String> dist_names = new Userhandling().SetDist();
        for(String name : dist_names)
        {
            dist.getItems().add(name.trim());
        }       
    }

    @FXML
    private void selectDist(ActionEvent event) {
        if ( dist.getSelectionModel().getSelectedItem() == null )
            tal.getItems().clear();
        else
        {
            district = dist.getSelectionModel().getSelectedItem().toString().trim();
            tal.getSelectionModel().clearSelection();
            tal.getItems().clear();
            ArrayList<String> tal_names = new Userhandling().SetTal(district);
            for(String name : tal_names)
            {
                tal.getItems().add(name.trim());
            }       
        }
    }

    @FXML
    private void selectTal(ActionEvent event) {
        if ( tal.getSelectionModel().getSelectedItem() == null )
            vilage.getItems().clear();
        else
        {
            taluka = tal.getSelectionModel().getSelectedItem().toString().trim();
            vilage.getSelectionModel().clearSelection();
            vilage.getItems().clear();
            ArrayList<String> vil = new Userhandling().Setvil(taluka);
            for(String name : vil)
            {
                vilage.getItems().add(name.trim());
            }   
        }
    }

    @FXML
    private void selectVilage(ActionEvent event) {
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
            timeline.setOnFinished(t -> { slider.getChildren().remove(aPane); });
            timeline.play(); 
        }
    }

    @FXML
    private void landEntry(ActionEvent event) throws SQLException {
        
        if (        nameTxt.getText().equals("") 
                ||  landCode.getText().equals("") 
                ||  area.getText().equals("") 
                ||  date.getValue().equals("") 
                ||  dist.getSelectionModel().getSelectedItem() == null 
                ||  tal.getSelectionModel().getSelectedItem() == null 
                ||  vilage.getSelectionModel().getSelectedItem() == null  )
        {
            Notification("Notification","All Fields are Compulsory");
        }
        else
        {
            district = dist.getSelectionModel().getSelectedItem().toString();
            taluka = tal.getSelectionModel().getSelectedItem().toString();
            village_name = vilage.getSelectionModel().getSelectedItem().toString();
            land_Code = landCode.getText();
            owner_Name = nameTxt.getText();
            land_Area = area.getText();
            
            LocalDate local_date  = date.getValue();
            purchase_date   = formatter.format(local_date);
            
            String DataToHash = district + taluka + village_name + land_Code + owner_Name + land_Area + purchase_date;
            String hash = calculateBlockHash(DataToHash);
                    
            Userhandling.landInsert(district, taluka, village_name, land_Code, String.valueOf(LoginHandeling.id), 
                    owner_Name, land_Area, purchase_date, "", "Active", "0", hash, "0");

            Notification("Success","Record Inserted Successfully");
            resetAllFields();
            
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

//    private void findPreviousHash() throws SQLException
//    {
//        ResultSet rs = Connection.selectQuery("select * from votedvoters where Election_id = '"+ election_id +"'order by counter desc;");
//        if ( rs == null )
//            pre_hash = "0";
//        else
//        {
//            while(rs.next())
//            {
//                pre_hash = rs.getString("self_hash");
//                System.out.println("counter "+rs.getObject(6).toString());break;
//            }             
//        }
//    }
    
    private void resetAllFields()
    {
        nameTxt.setText("");
        landCode.setText("");
        area.setText("");
        date.setValue(LocalDate.now());
        dist.getSelectionModel().clearSelection();
        dist.getItems().clear();
        setDistrictComboBox();
        tal.getSelectionModel().clearSelection();
        tal.getItems().clear();
        vilage.getSelectionModel().clearSelection();
        vilage.getItems().clear();
        searchNameTxt.setText("");
        updateTableView();
    }
    @FXML
    private void tableViewMouseClicked(MouseEvent event) {
        users = (ObservableList)ownerNamesTable.getSelectionModel().getSelectedItem();
        
        nameTxt.setText(users.get(1).toString());
        updateTableView();
        searchNameTxt.setText("");
        
        
    }
}
