package Database_Access_with_JDBC.Exercises.Print_All_Minion_Names;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scan = new Scanner(System.in);
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", "local", "");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select `name` from minions");
        List<String> minions = new ArrayList<>();
        while (rs.next()) {
            minions.add(rs.getString(1));
        }
        for (int i = 0, j = minions.size() - 1; i <= minions.size() / 2 & j >= minions.size() / 2; i++, j--) {
            System.out.println(minions.get(i));
            System.out.println(minions.get(j));
        }
    }
}
