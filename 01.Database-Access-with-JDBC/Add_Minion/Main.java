package Database_Access_with_JDBC.Exercises.Add_Minion;

import java.sql.*;
import java.util.Scanner;

public class Main {
    static Connection conn;

    static {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", "local", "");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Minion: ");
        String[] minionData = sc.nextLine().split("\\s+");
        System.out.print("Villain: ");
        String villain = sc.nextLine();
        boolean newVillainOrMinion = false;

        if (!checkEntryInDB(minionData[2], "towns")) {
            String addTown = String.format("insert into towns(`name`) values ('%s')", minionData[2]);
            if (insertRecord(addTown)) {
                System.out.printf("Town %s was added to the database.%n", minionData[2]);
            }
        }

        if (!checkEntryInDB(villain, "villains")) {
            String addVillain = String.format("insert into villains(`name`, evilness_factor) values ('%s', 'evil')", villain);
            if (insertRecord(addVillain)) {
                System.out.printf("Villain %s was added to the database.%n", villain);
            }
            newVillainOrMinion = true;
        }

        int townID = getID(minionData[2], "towns");
        if (!checkEntryInDB(minionData[1], "minions")) {
            String addMinion = String.format("insert into minions(`name`, town_id, age) values ('%s', %d, %d)", minionData[0], townID,
                    Integer.parseInt(minionData[1]));
            if (insertRecord(addMinion)) {
                System.out.printf("Successfully added %s to be minion of %s%n", minionData[0], villain);
            }
            newVillainOrMinion = true;
        }

        if (newVillainOrMinion) {
            int minionID = getID(minionData[0], "minions");
            int villainID = getID(villain, "villains");
            String addMinionVillain = String.format("insert into minions_villains(minion_id, villain_id) values (%d, %d)",
                        minionID, villainID);
            insertRecord(addMinionVillain);
        }
    }

    private static int getID(String value, String tableName) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("select id from "  + tableName +" where `name` = ?;");
        ps.setString(1, value);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    public static boolean checkEntryInDB(String entryName, String tableName) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("select * from "  + tableName +" where `name` = ?;");
        ps.setString(1, entryName);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    public static boolean insertRecord(String sqlStatement) throws SQLException {
        Statement stmt = conn.createStatement();
        return stmt.executeUpdate(sqlStatement) > 0;
    }
}
