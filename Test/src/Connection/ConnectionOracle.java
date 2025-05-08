
package Connection;

import java.sql.*;


public class ConnectionOracle {
    
    /**
     * Function get Oracle Connection 
     * 
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public static Connection getOracleConnection() throws ClassNotFoundException,
            SQLException {

        //Host name
        String hostName = "localhost";
        //SID Oralce
        String sid = "ORCL21";
        //Username
        String userName = "QLKho";
        //Password
        String password = "abc";
        
        // Khai báo class Driver cho DB Oracle
        // Việc này cần thiết với Java 5
        // Java6 tự động tìm kiếm Driver thích hợp.
        // Nếu bạn dùng Java6, thì ko cần dòng này cũng được.
        Class.forName("oracle.jdbc.driver.OracleDriver");

        // Cấu trúc URL Connection dành cho Oracle
        // Ví dụ: jdbc:oracle:thin:@localhost:1521:db11g
        String connectionURL = "jdbc:oracle:thin:@" + hostName + ":1521:" + sid;

        //Tạo đối tượng connection
        Connection conn = DriverManager.getConnection(connectionURL, userName,
                password);
        
        return conn;
    }


}
