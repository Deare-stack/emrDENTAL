package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class PatientHistoryManagement {
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

            // Retrieve and display existing patient history records.
            String strSQL = "SELECT patient_history_id, patient_id, patient_history_records, procedure_id, Insurance_provider, doctor, office_visit_id " +
                    "FROM patient_history ORDER BY patient_history_id";
            ResultSet rs = stmt.executeQuery(strSQL);
            System.out.println("Existing Patient History Records:");
            while(rs.next()){
                System.out.print("patient_history_id: " + rs.getInt("patient_history_id") + ", ");
                System.out.print("patient_id: " + rs.getInt("patient_id") + ", ");
                System.out.print("patient_history_records: " + rs.getString("patient_history_records") + ", ");
                System.out.print("procedure_id: " + rs.getString("procedure_id") + ", ");
                System.out.print("Insurance_provider: " + rs.getString("Insurance_provider") + ", ");
                System.out.print("doctor: " + rs.getString("doctor") + ", ");
                System.out.println("office_visit_id: " + rs.getString("office_visit_id"));
            }

            Scanner input = new Scanner(System.in);
            System.out.print("\nType 'u' to update, 'd' to delete: ");
            String strChoice = String.valueOf(input.next().charAt(0));

            switch(strChoice) {
                case "u":
                    // --- Update a Record ---
                    System.out.println("\n=== UPDATE PATIENT HISTORY RECORD ===");
                    System.out.print("Enter the patient_history_id to update: ");
                    int phId = input.nextInt();

                    System.out.print("Enter new history records (or 'x' to skip): ");
                    input.nextLine();  // consume newline
                    String newRecords = input.nextLine();


                    System.out.print("Enter new Insurance provider (or 'x' to skip): ");
                    String newInsurance = input.nextLine();  // <— read the full line

                    System.out.print("Enter new doctor (or 'x' to skip): ");
                    String newDoctor = input.nextLine();     // <— read the full line


                    if (!newRecords.equalsIgnoreCase("x")) {
                                              strSQL = "UPDATE patient_history SET patient_history_records='" + newRecords + "' WHERE patient_history_id=" + phId;
                                               System.out.println("SQL: " + strSQL);
                                               stmt.executeUpdate(strSQL);
                    }

                                        if (!newInsurance.equalsIgnoreCase("x")) {
                                            strSQL = "UPDATE patient_history SET Insurance_provider='" + newInsurance + "' WHERE patient_history_id=" + phId;
                                            System.out.println("SQL: " + strSQL);
                                            stmt.executeUpdate(strSQL);
                                        }
                                        if (!newDoctor.equalsIgnoreCase("x")) {
                                            strSQL = "UPDATE patient_history SET doctor='" + newDoctor + "' WHERE patient_history_id=" + phId;
                                            System.out.println("SQL: " + strSQL);
                                            stmt.executeUpdate(strSQL);
                                       }

                    System.out.println("Record updated successfully.");
                    break;

                case "d":
                    // --- Delete a Record ---
                    System.out.println("\n=== DELETE PATIENT HISTORY RECORD ===");
                    System.out.print("Enter the patient_history_id to delete: ");
                    int deleteId = input.nextInt();
                    strSQL = "DELETE FROM patient_history WHERE patient_history_id=" + deleteId;
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
