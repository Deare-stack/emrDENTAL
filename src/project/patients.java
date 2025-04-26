package project;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
public class patients
{	
	//static final String driverName="com.mysql.jdbc.Driver";
	static final String driverName="com.mysql.cj.jdbc.Driver";
	static final String url ="jdbc:mysql://localhost/dentalemr";
	static final String user="root";
	static final String password="123456";
	public static void main(String a[])
	{
		try
		{       
			//Class.forName(driverName);
			Connection con = DriverManager.getConnection(url,user,password);
			Statement stmt = con.createStatement();
			String strSQL="select patient_id, fist_name, last_name, DOB from patients order by patient_id";
			ResultSet rs = stmt.executeQuery(strSQL);
			while(rs.next())
			{
				System.out.print(rs.getInt("patient_id") + ",");
				System.out.print(rs.getString("last_name") + ",");
				System.out.print(rs.getString("fist_name") + ",");
				System.out.println(rs.getString("DOB"));
			}
			
			Scanner input=new Scanner(System.in);
			System.out.print("\nType 'a' to add, 'u' to update, 'd' to delete: ");
			String strChoice=String.valueOf(input.next().charAt(0));
			
			switch (strChoice)
			{
				case "a":
					// --- Add a new patient and patient history record ---

					// Prompt for patient details
					System.out.println("\n=== ADD NEW PATIENT AND PATIENT HISTORY, AND OFFICE VISIT RECORD ===");

					System.out.print("Enter first name: ");
					String firstName = input.next();

					System.out.print("Enter last name: ");
					String lastName = input.next();

					System.out.print("Enter DOB (YYYY-MM-DD): ");
					String DOB = input.next();

					System.out.print("Enter age: ");
					int age = input.nextInt();

					System.out.print("Enter gender(Male/Female): ");
					String gender = input.next();

					System.out.print("Enter phone number: ");
					String phone = input.next();

					System.out.print("Enter email: ");
					String email = input.next();

					System.out.print("Enter address: ");
					input.nextLine();  // consume leftover newline
					String address = input.nextLine();

					System.out.print("Enter city: ");
					String city = input.nextLine();

					System.out.print("Enter state: ");
					String state = input.nextLine();

					System.out.print("Enter zip code: ");
					int zip = input.nextInt();

					// Insert new patient into the 'patients' table
					String insertPatientSQL =
							"INSERT INTO patients " +
									"(fist_name, last_name, DOB, age, gender, phone, email, patients_address, patients_city, state_state, zip_code) " +
									"VALUES ('" + firstName + "', '" + lastName + "', '" + DOB + "', " + age + ", '" + gender + "', '" + phone + "', '" + email + "', '" + address + "', '" + city + "', '" + state + "', " + zip + ")";

					System.out.println("SQL: " + insertPatientSQL);
					stmt.executeUpdate(insertPatientSQL, Statement.RETURN_GENERATED_KEYS);

					// Get the generated patient_id
					ResultSet generatedKeys = stmt.getGeneratedKeys();
					int patientId = -1;
					if (generatedKeys.next()) {
						patientId = generatedKeys.getInt(1);  // patient_id of the newly added patient
					}
					generatedKeys.close();

					// Now insert the patient history record into the 'patient_history' table
					System.out.print("Enter history records: ");
					input.nextLine();  // consume newline
					String historyRecords = input.nextLine();

					System.out.print("Enter Insurance provider: ");
					String insurance = input.nextLine();

					System.out.print("Enter doctor name: ");
					String doctor = input.nextLine();

					// Insert patient history into the 'patient_history' table
					String insertHistorySQL =
							"INSERT INTO patient_history " +
									"(patient_id, patient_history_records, Insurance_provider, doctor) " +
									"VALUES(" + patientId + ", '" + historyRecords + "', '" + insurance + "', '" + doctor + "')";

					System.out.println("SQL: " + insertHistorySQL);
					stmt.executeUpdate(insertHistorySQL);

					// Prompt for office visit
					System.out.print("Enter office visit date (YYYY-MM-DD HH:MM:SS): ");
					String visitDate = input.nextLine();

					System.out.print("Enter visit type: ");
					String visitType = input.nextLine();

					System.out.print("Enter note: ");
					String note = input.nextLine();

// Insert into office_visits
					strSQL =
							"INSERT INTO office_visits " +
									"(office_vist_date, patient_id, visit_type, note) " +
									"VALUES('" + visitDate + "', " + patientId + ", '" + visitType + "', '" + note + "')";
					stmt.executeUpdate(strSQL);

					System.out.println("New office visit record added successfully.");

// finally...
					System.out.println("New patient and patient history record, and office visit records added successfully.");
					break;
					
				case "u":
					System.out.println("===UPDATE===");
					System.out.print("enter id of patient to update: ");
					int id=input.nextInt();
					
					System.out.print("enter new last_name or 'x' to skip: ");
					lastName=input.next();
					
					System.out.print("enter new first_name or 'x' to skip: ");
					firstName=input.next();
					
					System.out.print("enter new DOB or 'x' to skip: ");
					DOB=input.next();
					
					if (!lastName.equals("x"))
					{
						strSQL="update patients set last_name='" + lastName + "' where patient_id=" + id;
						System.out.println(strSQL);
						stmt.executeUpdate(strSQL);
					}
					
					if (!firstName.equals("x"))
					{
						strSQL="update patients set fist_name='" + firstName + "' where patient_id=" + id;
						System.out.println(strSQL);
						stmt.executeUpdate(strSQL);
					}
					
					if (!DOB.equals("x"))
					{
						strSQL="update patients set DOB='" + DOB + "' where patient_id=" + id;
						System.out.println(strSQL);
						stmt.executeUpdate(strSQL);
					}
					break;
					
				case "d":
					System.out.println("===DELETE===");
					System.out.print("enter id of patient to delete: ");
					id=input.nextInt();
					strSQL="delete from patients where patient_id=" + id;
					System.out.println(strSQL);
					stmt.executeUpdate(strSQL);
					break;
					
			}
			rs.close();
			con.close();
		}
		catch (SQLException ex) {ex.printStackTrace();}			// was ClassNotFoundException
		catch (Exception e) {e.printStackTrace();}				// was SQLException
	}
}

