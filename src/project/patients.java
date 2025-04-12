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
			String strSQL="select patient_id, fist_name, last_name, DOB from patients order by last_name";
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
					System.out.println("===ADD===");
					System.out.print("enter last_name: ");
					String strLastname=input.next();
					
					System.out.print("enter first_name: ");
					String strFirstname=input.next();
					
					System.out.print("enter patient's DOB (YYYY-MM-DD): ");
					String strDOB=input.next();

					System.out.print("enter patient's age: ");
					String strage=input.next();

					System.out.print("enter gender(Male/Female): ");
					String strgender=input.next();

					System.out.print("enter patient's phone number: ");
					String strphone=input.next();

					System.out.print("enter patient's email (for example patient1@example.com): ");
					String stremail=input.next();

					System.out.print("enter patient's insurance: ");
					String strinsurance=input.next();

					System.out.print("enter patient's doctor: ");
					String strdoc=input.next();

					strSQL = "INSERT INTO patients (last_name, fist_name, DOB, age, gender, phone, email, procedure_id, procedures, patient_history_id, patient_history, Insurance_provider, doctor, office_vist_date) " +
							"VALUES ('" + strLastname + "', '" + strFirstname + "', '" + strDOB + "', " + strage + ", '"
							+ strgender + "', '" + strphone + "', '" + stremail + "', null, null, null, null, '"
							+ strinsurance + "', '" + strdoc + "', null)";
					System.out.println("SQL: " + strSQL);
					stmt.executeUpdate(strSQL);
					System.out.println("Record added successfully.");
					break;
					
				case "u":
					System.out.println("===UPDATE===");
					System.out.print("enter id of patient to update: ");
					int id=input.nextInt();
					
					System.out.print("enter new last_name or 'x' to skip: ");
					strLastname=input.next();
					
					System.out.print("enter new first_name or 'x' to skip: ");
					strFirstname=input.next();
					
					System.out.print("enter new DOB or 'x' to skip: ");
					strDOB=input.next();
					
					if (!strLastname.equals("x"))
					{
						strSQL="update patients set last_name='" + strLastname + "' where patient_id=" + id;
						System.out.println(strSQL);
						stmt.executeUpdate(strSQL);
					}
					
					if (!strFirstname.equals("x"))
					{
						strSQL="update patients set fist_name='" + strFirstname + "' where patient_id=" + id;
						System.out.println(strSQL);
						stmt.executeUpdate(strSQL);
					}
					
					if (!strDOB.equals("x"))
					{
						strSQL="update patients set DOB='" + strDOB + "' where patient_id=" + id;
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

