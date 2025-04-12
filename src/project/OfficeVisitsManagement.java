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
            String strSQL = "SELECT office_visit_id, office_vist_date, patient_id, fist_name, last_name, DOB, "
                    + "procedure_id, procedures, patient_history_id, patient_history, `Insurance provider`, doctor "
                    + "FROM office_visits ORDER BY office_vist_date DESC";
            ResultSet rs = stmt.executeQuery(strSQL);
            System.out.println("Existing Office Visits:");
            while(rs.next()){
                System.out.print("office_visit_id: " + rs.getInt("office_visit_id") + ", ");
                System.out.print("office_vist_date: " + rs.getString("office_vist_date") + ", ");
                System.out.print("patient_id: " + rs.getInt("patient_id") + ", ");
                System.out.print("fist_name: " + rs.getString("fist_name") + ", ");
                System.out.print("last_name: " + rs.getString("last_name") + ", ");
                System.out.print("DOB: " + rs.getString("DOB") + ", ");
                System.out.print("procedure_id: " + rs.getInt("procedure_id") + ", ");
                System.out.print("procedures: " + rs.getString("procedures") + ", ");
                System.out.print("patient_history_id: " + rs.getInt("patient_history_id") + ", ");
                System.out.print("patient_history: " + rs.getString("patient_history") + ", ");
                System.out.print("Insurance provider: " + rs.getString("Insurance provider") + ", ");
                System.out.println("doctor: " + rs.getString("doctor"));
            }

            // Create a Scanner object to obtain user input.
            Scanner input = new Scanner(System.in);
            System.out.print("\nType 'a' to add, 'u' to update, 'd' to delete: ");
            String strChoice = String.valueOf(input.next().charAt(0));

            switch(strChoice) {
                case "a":
                    // --- Add a Record ---
                    System.out.println("\n=== ADD OFFICE VISIT ===");
                    System.out.print("Enter office visit date (YYYY-MM-DD): ");
                    String officeVisitDate = input.next();

                    System.out.print("Enter patient ID: ");
                    int patientId = input.nextInt();

                    System.out.print("Enter first name: ");
                    String fistName = input.next();

                    System.out.print("Enter last name: ");
                    String lastName = input.next();

                    System.out.print("Enter DOB (YYYY-MM-DD): ");
                    String DOB = input.next();

                    System.out.print("Enter procedure ID: ");
                    int procedureId = input.nextInt();

                    System.out.print("Enter procedures description: ");
                    input.nextLine(); // Consume newline
                    String procedures = input.nextLine();

                    System.out.print("Enter patient history ID: ");
                    int patientHistoryId = input.nextInt();

                    System.out.print("Enter patient history description: ");
                    input.nextLine(); // Consume newline
                    String patientHistory = input.nextLine();

                    System.out.print("Enter Insurance provider: ");
                    String insuranceProvider = input.nextLine();

                    System.out.print("Enter doctor's name: ");
                    String doctor = input.nextLine();

                    // Build INSERT SQL statement
                    strSQL = "INSERT INTO office_visits (office_vist_date, patient_id, fist_name, last_name, DOB, " +
                            "procedure_id, procedures, patient_history_id, patient_history, `Insurance provider`, doctor) " +
                            "VALUES('" + officeVisitDate + "', " + patientId + ", '" + fistName + "', '" + lastName + "', '" + DOB + "', " +
                            procedureId + ", '" + procedures + "', " + patientHistoryId + ", '" + patientHistory + "', '" + insuranceProvider + "', '" + doctor + "')";
                    System.out.println("SQL: " + strSQL);

                    // 使用 RETURN_GENERATED_KEYS 来获取新插入记录生成的主键值
                    int affectedRows = stmt.executeUpdate(strSQL, Statement.RETURN_GENERATED_KEYS);
                    if (affectedRows > 0) {
                        ResultSet generatedKeys = stmt.getGeneratedKeys();
                        if (generatedKeys.next()) {
                            int newId = generatedKeys.getInt(1);
                            System.out.println("Record added successfully. Generated office_visit_id: " + newId);
                        }
                        generatedKeys.close();
                    } else {
                        System.out.println("No record was added.");
                    }
                    break;

                case "u":
                    // --- Update a Record ---
                    System.out.println("\n=== UPDATE OFFICE VISIT ===");
                    System.out.print("Enter the office_visit_id to update: ");
                    int officeVisitId = input.nextInt();

                    System.out.print("Enter new office visit date (or 'x' to skip): ");
                    String newOfficeVisitDate = input.next();

                    System.out.print("Enter new patient ID (or -1 to skip): ");
                    int newPatientId = input.nextInt();

                    System.out.print("Enter new first name (or 'x' to skip): ");
                    String newFistName = input.next();

                    System.out.print("Enter new last name (or 'x' to skip): ");
                    String newLastName = input.next();

                    System.out.print("Enter new DOB (or 'x' to skip): ");
                    String newDOB = input.next();

                    System.out.print("Enter new procedure ID (or -1 to skip): ");
                    int newProcedureId = input.nextInt();

                    System.out.print("Enter new procedures description (or 'x' to skip): ");
                    input.nextLine();  // Consume newline
                    String newProcedures = input.nextLine();

                    System.out.print("Enter new patient history ID (or -1 to skip): ");
                    int newPatientHistoryId = input.nextInt();

                    System.out.print("Enter new patient history (or 'x' to skip): ");
                    input.nextLine();  // Consume newline
                    String newPatientHistory = input.nextLine();

                    System.out.print("Enter new Insurance provider (or 'x' to skip): ");
                    String newInsuranceProvider = input.nextLine();

                    System.out.print("Enter new doctor's name (or 'x' to skip): ");
                    String newDoctor = input.nextLine();

                    if (!newOfficeVisitDate.equalsIgnoreCase("x")) {
                        strSQL = "UPDATE office_visits SET office_vist_date='" + newOfficeVisitDate + "' WHERE office_visit_id=" + officeVisitId;
                        System.out.println("SQL: " + strSQL);
                        stmt.executeUpdate(strSQL);
                    }

                    if (newPatientId != -1) {
                        strSQL = "UPDATE office_visits SET patient_id=" + newPatientId + " WHERE office_visit_id=" + officeVisitId;
                        System.out.println("SQL: " + strSQL);
                        stmt.executeUpdate(strSQL);
                    }

                    if (!newFistName.equalsIgnoreCase("x")) {
                        strSQL = "UPDATE office_visits SET fist_name='" + newFistName + "' WHERE office_visit_id=" + officeVisitId;
                        System.out.println("SQL: " + strSQL);
                        stmt.executeUpdate(strSQL);
                    }

                    if (!newLastName.equalsIgnoreCase("x")) {
                        strSQL = "UPDATE office_visits SET last_name='" + newLastName + "' WHERE office_visit_id=" + officeVisitId;
                        System.out.println("SQL: " + strSQL);
                        stmt.executeUpdate(strSQL);
                    }

                    if (!newDOB.equalsIgnoreCase("x")) {
                        strSQL = "UPDATE office_visits SET DOB='" + newDOB + "' WHERE office_visit_id=" + officeVisitId;
                        System.out.println("SQL: " + strSQL);
                        stmt.executeUpdate(strSQL);
                    }

                    if (newProcedureId != -1) {
                        strSQL = "UPDATE office_visits SET procedure_id=" + newProcedureId + " WHERE office_visit_id=" + officeVisitId;
                        System.out.println("SQL: " + strSQL);
                        stmt.executeUpdate(strSQL);
                    }

                    if (!newProcedures.equalsIgnoreCase("x")) {
                        strSQL = "UPDATE office_visits SET procedures='" + newProcedures + "' WHERE office_visit_id=" + officeVisitId;
                        System.out.println("SQL: " + strSQL);
                        stmt.executeUpdate(strSQL);
                    }

                    if (newPatientHistoryId != -1) {
                        strSQL = "UPDATE office_visits SET patient_history_id=" + newPatientHistoryId + " WHERE office_visit_id=" + officeVisitId;
                        System.out.println("SQL: " + strSQL);
                        stmt.executeUpdate(strSQL);
                    }

                    if (!newPatientHistory.equalsIgnoreCase("x")) {
                        strSQL = "UPDATE office_visits SET patient_history='" + newPatientHistory + "' WHERE office_visit_id=" + officeVisitId;
                        System.out.println("SQL: " + strSQL);
                        stmt.executeUpdate(strSQL);
                    }

                    if (!newInsuranceProvider.equalsIgnoreCase("x")) {
                        strSQL = "UPDATE office_visits SET `Insurance provider`='" + newInsuranceProvider + "' WHERE office_visit_id=" + officeVisitId;
                        System.out.println("SQL: " + strSQL);
                        stmt.executeUpdate(strSQL);
                    }

                    if (!newDoctor.equalsIgnoreCase("x")) {
                        strSQL = "UPDATE office_visits SET doctor='" + newDoctor + "' WHERE office_visit_id=" + officeVisitId;
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
