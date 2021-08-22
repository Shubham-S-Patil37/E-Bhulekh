package connection;

import controller.Get_7_12_NewController;
import controller.LoginController;
import controller.UserConfirmationController;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Userhandling {

    static ArrayList<String> ar = new ArrayList<>();
    static ResultSet resultSet = null;
    static String mobileNo,email,id;
    static PreparedStatement pst;
    public static boolean userInsert1(String id, String name, String username, String pass, String gender, String dob, String email, String contact, String add) {
        try {
            pst = Connection.getPreStatement("insert into user(name,username,password, gender, dob, email, contact, address, id)"
                    + "values (?,?,?,?,?,?,?,?,?)");
            pst.setString(1, name    );
            pst.setString(2, username);
            pst.setString(3, pass    );
            pst.setString(4, gender  );
            pst.setString(5, dob     );
            pst.setString(6, email   );
            pst.setString(7, contact );
            pst.setString(8, add     );
            pst.setString(9, id      );
            pst.execute();
            return true;
        } catch (Exception e) { return false; }
    }

    public static boolean talathiInsert1(String dist, String tal, String vil, String name, String username, String password, String gender, String dbo, String email, String contact, String address){
        try {
            pst = Connection.getPreStatement("insert into talathiinfo(dist, tal, vil, name, username, password, gender, dbo, email, contact, address)"
                    + "values (?,?,?,?,?,?,?,?,?,?,?)");
            pst.setString(1, dist);
            pst.setString(2, tal);
            pst.setString(3, vil);
            pst.setString(4, name);
            pst.setString(5, username);
            pst.setString(6, password);
            pst.setString(7, gender);
            pst.setString(8, dbo);
            pst.setString(9, email);
            pst.setString(10, contact);
            pst.setString(11, address);
            pst.execute();
            return true;
        } catch (Exception e) { return false; }
    }

    public static boolean userInsert(String id,String dist, String tal, String vil, String name, String username, String password, String gender, String dbo, String email, String contact, String address){
        try {
            pst = Connection.getPreStatement("insert into userinfo(dist, tal, vil, name, username, password, gender, dbo, email, contact, address, id)"
                    + "values (?,?,?,?,?,?,?,?,?,?,?,?)");
            pst.setString(1, dist);
            pst.setString(2, tal);
            pst.setString(3, vil);
            pst.setString(4, name);
            pst.setString(5, username);
            pst.setString(6, password);
            pst.setString(7, gender);
            pst.setString(8, dbo);
            pst.setString(9, email);
            pst.setString(10, contact);
            pst.setString(11, address);
            pst.setString(12, id);
            pst.execute();
            return true;
        } catch (Exception ex) { Logger.getLogger(UserConfirmationController.class.getName()).log(Level.SEVERE, null, ex); return false; }
    }
    
    public static boolean landInsert(String dist, String taluka, String vilage, String code, String id, String username, String area, String buydate, String selldate, String status, String prehash, String hash, String nexthash) throws SQLException{
        pst = Connection.getPreStatement("insert into landinfo(dist, taluka, vilage, code, userid, username, area, buydate, selldate, status, pre_hash, hash, next_hash)"
            + "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        pst.setString(1, dist);
        pst.setString(2, taluka);
        pst.setString(3, vilage);
        pst.setString(4, code);
        pst.setString(5, id);
        pst.setString(6, username);
        pst.setString(7, area);
        pst.setString(8, buydate);
        pst.setString(9, selldate);
        pst.setString(10, status);
        pst.setString(11, prehash);
        pst.setString(12, hash);
        pst.setString(13, nexthash);
        return pst.execute();
    }
    
    public static boolean requestInsert(String one, String two, String three, int oid, int rid, String code) {
        try {
            String d = new String(one.toLowerCase());
            pst = Connection.getPreStatement("insert into request(dist, tal, vilage, ownerid, requesterid, status, landcode)"
                    + "values ( ?,?,?,?,?,?,?)");
            pst.setString(1, d);
            pst.setString(2, two.toString().toLowerCase());
            pst.setString(3, three.toString().toLowerCase());
            pst.setInt(4, oid);
            pst.setInt(5, rid);
            pst.setString(6, "pending");
            pst.setString(7, code);
            pst.execute();
            return true;
        } catch (Exception e) { return false; }
    }

    public static boolean userId(int id) {
        ResultSet resultSet = Connection.selectQuery("SELECT * FROM login where id = '"+4+"'");
        try {
            if (resultSet.next()){
//                (Integer.parseInt(resultSet.getObject(1).toString()) == id) ? 0 : (int)resultSet.getObject(1);
                return true;
            }
            else
                return false;
        } catch (SQLException e) {return false;}
    }
    
    public static String getRequesterID() throws SQLException {
        ResultSet resultSet = Connection .selectQuery("SELECT id FROM userinfo where username = '" + LoginController.loginUser + "';" );
        if ( resultSet.next())
            return resultSet.getString("id");
        
        return "";
    }

    public static String getOwnerID(String landCode) throws SQLException {
        ResultSet resultSet = Connection .selectQuery("SELECT userid FROM landinfo where code = '" + landCode + "' and status = 'Active';" );
        if ( resultSet.next())
            return resultSet.getString("userid");
        
        return "";
    }
    
    public static void requestInsert( String dist,String tal,String vil,String ownerID , String reqID , String code ) throws SQLException {        
        PreparedStatement ps=  Connection.getPreStatement("insert into request(dist, tal, vilage, ownerid, requesterid, status, landcode)" +
                 "values ( ?,?,?,?,?,?,?)");
         
         ps.setString(1,dist.toLowerCase());
         ps.setString(2,tal.toLowerCase());
         ps.setString(3,vil.toLowerCase());
         ps.setString(4, ownerID);
         ps.setString(5, reqID);
         ps.setString(6, "pending");
         ps.setString(7, code);
         ps.execute();
    }

    public ArrayList<String> SetDist() {
        ArrayList<String> ar = new ArrayList<>();
        resultSet = Connection.selectQuery("SELECT * FROM district");
        try {
            while (resultSet.next()) 
                ar.add(resultSet.getObject(2).toString());
            return ar;
        } catch (SQLException e) {return null;}
    }

    public static ArrayList<String> SetTal(String dist) {
        try {
//            ResultSet resultSet = Connection.selectQuery("SELECT * FROM taluka where Dist_Name = '"+dist+"'");
//            while(resultSet.next())
//                ar.add(resultSet.getString("Tal_Name"));
//            return ar;
            resultSet = Connection.selectQuery("SELECT * FROM taluka");
            while (resultSet.next()) 
                if (dist.contains(resultSet.getObject(1).toString())) 
                    ar.add(resultSet.getObject(2).toString()); 
        } catch (SQLException ex) {Logger.getLogger(Userhandling.class.getName()).log(Level.SEVERE, null, ex);}
        return ar;
    }

    public static ArrayList<String> Setvil(String tal) {
        ar.removeAll(ar);
        resultSet = Connection.selectQuery("SELECT * FROM vilage;");
        try {
            while (resultSet.next())
                if (tal.contains(resultSet.getObject(1).toString())) 
                    ar.add(resultSet.getObject(2).toString());
        } catch (SQLException e) {}
        return ar;
    }

    public static ArrayList<String> Setname(String dist, String taluka, String vilage) {
        try {
            ar.removeAll(ar);
            resultSet = Connection.selectQuery("SELECT * FROM landinfo;");
            while (resultSet.next()) 
                if (dist.toUpperCase().contains(resultSet.getObject(1).toString().toUpperCase())) 
                    if (taluka.toUpperCase().contains(resultSet.getObject(2).toString().toUpperCase())) 
                        if (vilage.toUpperCase().contains(resultSet.getObject(3).toString().toUpperCase())) 
                            if (resultSet.getObject(10).toString().contains("Active"))
                            {
//                                Get_7_12_NewController.code = resultSet.getObject(4).toString();
//                                Get_7_12_NewController.ownerName = resultSet.getObject(6).toString();
//                                ar.add((resultSet.getObject(4).toString() + " " + resultSet.getObject(6).toString()));
                                ar.add((resultSet.getObject(6).toString()));
                            }
                                
        } catch (SQLException e) {}
        return ar;
    }

    public static ArrayList<String> setLandInfo(String loginUser) {
        try {
            ar.removeAll(ar);
            resultSet = Connection.selectQuery("SELECT username, code, status FROM landinfo where username = '" + loginUser +"' and status = 'Active';");
            while (resultSet.next()) 
            {
                //System.out.println(resultSet.getObject(1).toString());
                System.out.println(resultSet.getString("username"));
                ar.add((resultSet.getString("code")));
//                System.out.println(resultSet.getObject(4).toString());
//                System.out.println(resultSet.getObject(4).toString());
                //ar.add((resultSet.getObject(4).toString() + " " + resultSet.getObject(6).toString()));
            }
        } catch (SQLException e) {}
        return ar;
    }

//    public static boolean viewUserDetails(String whereCoulumn, String condition, String user_type){
    public static boolean viewUser(String whereCoulumn, String condition){
        try {
//            if(user_type.equals("user")){
                resultSet = Connection.selectQuery("SELECT * FROM userinfo where "+whereCoulumn+" = '"+condition+"'");
                if(resultSet.next()){
                    id       = resultSet.getString("id");
                    email    = resultSet.getString("email");
                    mobileNo = resultSet.getString("contact");
                    return true; 
                }
//            }
//            if(user_type.equals("user")){
//                resultSet = Connection.selectQuery("SELECT * FROM user where "+whereCoulumn+" = '"+condition+"'");
//                    
//                return true;
//            }
        } catch (SQLException ex) {
            Logger.getLogger(Userhandling.class.getName()).log(Level.SEVERE, null, ex);
            return false; 
        }
        return false;
    }

}
