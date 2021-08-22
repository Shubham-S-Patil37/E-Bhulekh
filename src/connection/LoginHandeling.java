package connection;
import controller.LoginController;
import controller.UserConfirmationController;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginHandeling {
    static public int id, bgColor;
    static public String userType;
    public static boolean loginInsert(String id, String user_name,String user_pass,String name,String status)
    {
        try {
            System.out.println(id);
            PreparedStatement ps=  Connection.getPreStatement("insert into login(id, user_name,user_pass,name,status,background)" +
                "values ( ?,?,?,?,?,?)");
            ps.setString(1, id);
            ps.setString(2, user_name);
            ps.setString(3, user_pass);
            ps.setString(4, name);
            ps.setString(5, status);
            ps.setInt(6, 1);
            ps.execute();
            return true;
        }catch (Exception ex) { Logger.getLogger(UserConfirmationController.class.getName()).log(Level.SEVERE, null, ex); return false; }
    }

    public static String login(String userid , String pass)
    {   
//        ResultSet resultSet = Connection.selectQuery("select * from login where id = '"+userid+"' and user_pass = '"+pass+"'");
        ResultSet resultSet = Connection.selectQuery("select * from login");
         try{
            while(resultSet.next()){
                if(resultSet.getObject(2).toString().equals(userid) && resultSet.getObject(3).toString().equals(pass)){
                    id       = Integer.parseInt(resultSet.getObject(1).toString());
                    bgColor  = Integer.parseInt(resultSet.getObject(6).toString());
                    userType = resultSet.getObject(5).toString().toString();
                    LoginController.loginUserName = resultSet.getObject(4).toString().toString();
                    return userType;
                }
            }
        }catch(SQLException e){return null;}
         return null;
    }
    /*
    
        String sql="insert into emp values(id, '"+name+"','"+ city +"')";
    */
    
}
