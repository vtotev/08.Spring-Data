package Database_Access_with_JDBC.Exercises.Increase_Age_Stored_Procedure;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {

//        CREATE PROCEDURE `usp_get_older` (minion_id int)
//        BEGIN
//        update minions
//        set age = age + 1
//        where id = minion_id;
//        END

        Scanner scan = new Scanner(System.in);
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", "local", "");
        System.out.print("Enter minion ID: ");
        int minion_ID = Integer.parseInt(scan.nextLine());

        CallableStatement callProc = conn.prepareCall("call usp_get_older(?);");
        callProc.setInt(1, minion_ID);
        callProc.execute();

        PreparedStatement psSelect = conn.prepareStatement("select `name`, age from minions where id = ?;");
        psSelect.setInt(1, minion_ID);
        ResultSet rs = psSelect.executeQuery();
        if (rs.next()) {
            System.out.printf("%s %d", rs.getString(1), rs.getInt(2));
        }
    }
}
