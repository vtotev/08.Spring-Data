package Database_Access_with_JDBC.Exercises.Remove_Villain;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scan = new Scanner(System.in);
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/minions_db", "local", "");
        System.out.print("Enter villain ID: ");
        int villainID = Integer.parseInt(scan.nextLine());

        try {
            conn.setAutoCommit(false);
            PreparedStatement psVillainData = conn.prepareStatement("select `name` from villains where id = ?;");
            psVillainData.setInt(1, villainID);
            ResultSet rs = psVillainData.executeQuery();
            if (rs.next()) {
                String villainName = rs.getString(1);

                PreparedStatement psReleaseMinions = conn.prepareStatement("delete from minions_villains where villain_id = ?;");
                psReleaseMinions.setInt(1, villainID);
                int minionsAffected = psReleaseMinions.executeUpdate();

                PreparedStatement psDeleteVillain = conn.prepareStatement("delete from villains where id = ?;");
                psDeleteVillain.setInt(1, villainID);
                if (psDeleteVillain.executeUpdate() > 0) {
                    System.out.printf("%s was deleted%n%d minions released%n", villainName, minionsAffected);
                    conn.commit();
                }
            } else {
                System.out.println("No such villain was found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback();
        }
    }


}
