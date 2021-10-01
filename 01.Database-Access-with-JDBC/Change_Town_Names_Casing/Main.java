package Database_Access_with_JDBC.Exercises.Change_Town_Names_Casing;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scan = new Scanner(System.in);
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", "local", "");
        System.out.print("Enter country: ");
        String country = scan.nextLine();
        PreparedStatement ps = conn.prepareStatement("update towns set `name` = upper(`name`) where `country` = ?;");
        ps.setString(1, country);
        if (ps.executeUpdate() > 0) {
            List<String> cities = new ArrayList<>();
            PreparedStatement psResult = conn.prepareStatement("select `name` from towns where country = ?");
            psResult.setString(1, country);
            ResultSet rs = psResult.executeQuery();
            while (rs.next()) {
                cities.add(rs.getString("name"));
            }
            System.out.printf("%d town names were affected.%n[%s]", cities.size(), String.join(", ", cities));
        } else {
            System.out.println("No town names were affected.");
        }
    }
}
