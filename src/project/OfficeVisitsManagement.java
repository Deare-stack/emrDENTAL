package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class OfficeVisitsManagement {
    // Database connection parameters.
    static final String driverName = "com.mysql.cj.jdbc.Driver";
    static final String url = "jdbc:mysql://localhost/dentalemr";
    static final String user = "root";
    static final String password = "123456";

    public static void main(String[] args) {
        try {
            // Establish connection to the database.
            Connection con = DriverManager.getConnection(url, user, password);
            Statement stmt = con.createStatement();

            // Retrieve and display existing office visit records.
            String strSQL = "SELECT office_visit_id, office_vist_date, patient_id, visit_type, note "
                    + "FROM office_visits ORDER BY office_visit_id";
            ResultSet rs = stmt.executeQuery(strSQL);
            System.out.println("Existing Office Visits:");
            while(rs.next()){
                System.out.print("office_visit_id: " + rs.getInt("office_visit_id") + ", ");
                System.out.print("office_vist_date: " + rs.getString("office_vist_date") + ", ");
                System.out.print("patient_id: " + rs.getInt("patient_id") + ", ");
                System.out.print("visit_type: " + rs.getString("visit_type") + ", ");
                System.out.print("note: " + rs.getString("note") + ", ");
            }

            // Create a Scanner object to obtain user input.
            Scanner input = new Scanner(System.in);
            System.out.print("\n Type 'u' to update, 'd' to delete: ");
            String strChoice = String.valueOf(input.next().charAt(0));

            switch(strChoice) {
                case "u":
                    // --- Update a Record ---
                    System.out.println("\n=== UPDATE OFFICE VISIT ===");
                    System.out.print("Enter the office_visit_id to update: ");
                    int officeVisitId = input.nextInt();
                    input.nextLine();  // <— consume the leftover newline

                    System.out.print("Enter new office visit date (YYYY-MM-DD HH:MM:SS) or 'x' to skip: ");
                    String newVisitDate = input.nextLine();  // now reads the full line

                    System.out.print("Enter new visit type or 'x' to skip: ");
                    String newVisitType = input.nextLine();

                    System.out.print("Enter new note or 'x' to skip: ");
                    String newNote = input.nextLine();

                    if (!newVisitDate.equalsIgnoreCase("x")) {
                        strSQL =
                                "UPDATE office_visits SET office_vist_date='" + newVisitDate +
                                        "' WHERE office_visit_id=" + officeVisitId;
                        System.out.println("SQL: " + strSQL);
                        stmt.executeUpdate(strSQL);
                    }

                    if (!newVisitType.equalsIgnoreCase("x")) {
                        strSQL =
                                "UPDATE office_visits SET visit_type='" + newVisitType +
                                        "' WHERE office_visit_id=" + officeVisitId;
                        System.out.println("SQL: " + strSQL);
                        stmt.executeUpdate(strSQL);
                    }

                    if (!newNote.equalsIgnoreCase("x")) {
                        strSQL =
                                "UPDATE office_visits SET note='" + newNote +
                                        "' WHERE office_visit_id=" + officeVisitId;
                        System.out.println("SQL: " + strSQL);
                        stmt.executeUpdate(strSQL);
                    }

                    System.out.println("Record updated successfully.");
                    break;

                case "d":
                    // --- Delete a Record ---
                    System.out.println("\n=== DELETE OFFICE VISIT ===");
                    System.out.print("Enter the office_visit_id to delete: ");
                    int deleteId = input.nextInt();
                    strSQL = "DELETE FROM office_visits WHERE office_visit_id=" + deleteId;
                    System.out.println("SQL: " + strSQL);
                    stmt.executeUpdate(strSQL);
                    System.out.println("Record deleted successfully.");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

            // Clean up resources.
            rs.close();
            stmt.close();
            con.close();
            input.close();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
