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
            String strSQL = "SELECT patient_history_id, patient_id, fist_name, last_name, DOB, gender, patient_history " +
                    "FROM patient_history ORDER BY last_name";
            ResultSet rs = stmt.executeQuery(strSQL);
            System.out.println("Existing Patient History Records:");
            while(rs.next()){
                System.out.print("patient_history_id: " + rs.getInt("patient_history_id") + ", ");
                System.out.print("patient_id: " + rs.getInt("patient_id") + ", ");
                System.out.print("fist_name: " + rs.getString("fist_name") + ", ");
                System.out.print("last_name: " + rs.getString("last_name") + ", ");
                System.out.print("DOB: " + rs.getString("DOB") + ", ");
                System.out.print("gender: " + rs.getString("gender") + ", ");
                System.out.println("patient_history: " + rs.getString("patient_history"));
            }

            Scanner input = new Scanner(System.in);
            System.out.print("\nType 'a' to add, 'u' to update, 'd' to delete: ");
            String strChoice = String.valueOf(input.next().charAt(0));

            switch(strChoice) {
                case "a":
                    // --- Add a Record ---
                    System.out.println("\n=== ADD PATIENT HISTORY RECORD ===");

                    System.out.print("Enter patient ID: ");
                    int patientId = input.nextInt();

                    System.out.print("Enter first name: ");
                    String fistName = input.next();

                    System.out.print("Enter last name: ");
                    String lastName = input.next();

                    System.out.print("Enter DOB (YYYY-MM-DD): ");
                    String DOB = input.next();

                    System.out.print("Enter gender: ");
                    String gender = input.next();

                    System.out.print("Enter patient history description: ");
                    input.nextLine();  // Consume newline
                    String patientHistory = input.nextLine();

                    // Build INSERT SQL statement (note that patient_history_id is auto-generated).
                    strSQL = "INSERT INTO patient_history (patient_id, fist_name, last_name, DOB, gender, patient_history) " +
                            "VALUES(" + patientId + ", '" + fistName + "', '" + lastName + "', '" + DOB + "', '" + gender + "', '" + patientHistory + "')";
                    System.out.println("SQL: " + strSQL);

                    // Execute the INSERT using RETURN_GENERATED_KEYS to obtain the auto-generated key
                    int affectedRows = stmt.executeUpdate(strSQL, Statement.RETURN_GENERATED_KEYS);
                    if (affectedRows > 0) {
                        ResultSet generatedKeys = stmt.getGeneratedKeys();
                        if (generatedKeys.next()) {
                            int newId = generatedKeys.getInt(1);
                            System.out.println("Record added successfully. Generated patient_history_id: " + newId);
                        }
                        generatedKeys.close();
                    } else {
                        System.out.println("No record was added.");
                    }
                    break;

                case "u":
                    // --- Update a Record ---
                    System.out.println("\n=== UPDATE PATIENT HISTORY RECORD ===");
                    System.out.print("Enter the patient_history_id to update: ");
                    int phId = input.nextInt();

                    // The following prompts allow you to skip updating any field
                    System.out.print("Enter new patient ID (or -1 to skip): ");
                    int newPatientId = input.nextInt();

                    System.out.print("Enter new first name (or 'x' to skip): ");
                    String newFistName = input.next();

                    System.out.print("Enter new last name (or 'x' to skip): ");
                    String newLastName = input.next();

                    System.out.print("Enter new DOB (YYYY-MM-DD) (or 'x' to skip): ");
                    String newDOB = input.next();

                    System.out.print("Enter new gender (or 'x' to skip): ");
                    String newGender = input.next();

                    System.out.print("Enter new patient history (or 'x' to skip): ");
                    input.nextLine();  // Consume newline
                    String newPatientHistory = input.nextLine();

                    if (newPatientId != -1) {
                        strSQL = "UPDATE patient_history SET patient_id=" + newPatientId + " WHERE patient_history_id=" + phId;
                        System.out.println("SQL: " + strSQL);
                        stmt.executeUpdate(strSQL);
                    }

                    if (!newFistName.equalsIgnoreCase("x")) {
                        strSQL = "UPDATE patient_history SET fist_name='" + newFistName + "' WHERE patient_history_id=" + phId;
                        System.out.println("SQL: " + strSQL);
                        stmt.executeUpdate(strSQL);
                    }

                    if (!newLastName.equalsIgnoreCase("x")) {
                        strSQL = "UPDATE patient_history SET last_name='" + newLastName + "' WHERE patient_history_id=" + phId;
                        System.out.println("SQL: " + strSQL);
                        stmt.executeUpdate(strSQL);
                    }

                    if (!newDOB.equalsIgnoreCase("x")) {
                        strSQL = "UPDATE patient_history SET DOB='" + newDOB + "' WHERE patient_history_id=" + phId;
                        System.out.println("SQL: " + strSQL);
                        stmt.executeUpdate(strSQL);
                    }

                    if (!newGender.equalsIgnoreCase("x")) {
                        strSQL = "UPDATE patient_history SET gender='" + newGender + "' WHERE patient_history_id=" + phId;
                        System.out.println("SQL: " + strSQL);
                        stmt.executeUpdate(strSQL);
                    }

                    if (!newPatientHistory.equalsIgnoreCase("x")) {
                        strSQL = "UPDATE patient_history SET patient_history='" + newPatientHistory + "' WHERE patient_history_id=" + phId;
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
