package Database_Access_with_JDBC.Exercises.Get_Minion_Names;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", "local", "");
        System.out.print("Enter villain id: ");
        int id = Integer.parseInt(sc.nextLine());
        PreparedStatement villains = conn.prepareStatement("select id, `name` from villains where id = ?;");
        villains.setInt(1, id);
        ResultSet rs = villains.executeQuery();
        if (rs.next()) {
            System.out.printf("Villain: %s%n", rs.getString("name"));
            PreparedStatement minions = conn.prepareStatement("select `name`, age from minions m\n" +
                    "join minions_villains mv on m.id = mv.minion_id\n" +
                    "where mv.villain_id = ?;");
            minions.setInt(1, id);
            ResultSet rsMinions = minions.executeQuery();
            int num = 1;
            while (rsMinions.next()) {
                System.out.printf("%d. %s %d%n", num, rsMinions.getString("name"), rsMinions.getInt("age"));
                num++;
            }
        } else {
            System.out.printf("No villain with ID %d exists in the database.^n", id);
        }
    }
}
