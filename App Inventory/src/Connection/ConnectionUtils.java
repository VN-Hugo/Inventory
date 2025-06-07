
package Connection;

import java.sql.Connection;
import java.sql.SQLException;


public class ConnectionUtils {

  
   public static Connection getMyConnection() throws SQLException,
          ClassNotFoundException {

      return ConnectionOracle.getOracleConnection();
  }
    public static void closeConnection(Connection c) {
        try {
            if (c != null) {
                c.close();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

  public static void main(String[] args) throws SQLException,
          ClassNotFoundException {
 
      System.out.println("Get connection ... ");
 
      Connection conn = ConnectionUtils.getMyConnection();
 
      System.out.println("Get connection " + conn);
 
      System.out.println("Thành công!");
  }
}
