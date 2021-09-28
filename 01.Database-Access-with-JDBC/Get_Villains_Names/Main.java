package Database_Access_with_JDBC.Exercises.Get_Villains_Names;

import java.sql.*;

public class Main{
    public static void main(String[] args) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", "local", "");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select v.`name`, count(mv.minion_id) as 'count' from villains v\n" +
                "join minions_villains mv on v.id = mv.villain_id\n" +
                "group by v.id having `count` > 15\n" +
                "order by `count` desc;");
        while (rs.next()) {
            System.out.printf("%s %d%n", rs.getString("name"), rs.getInt("count"));
        }
    }
}
