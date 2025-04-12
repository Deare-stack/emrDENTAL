package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class sakila {
	
	//static final String driverName="com.mysql.jdbc.Driver";
	static final String driverName="com.mysql.cj.jdbc.Driver";
	static final String url ="jdbc:mysql://localhost/dentalemr";
	static final String user="root";
	static final String password="123456";
	 public static void main(String a[]){

	        try {
	       
	            //Class.forName(driverName);		// don't need if using cj driver
	            Connection con = DriverManager.getConnection(url,user,password);
	            Statement stmt = con.createStatement();
	            ResultSet rs = stmt.executeQuery(
	                    "select  p.patient_id, p.fist_name,p.last_name, count(*) as history_count\n" +
	                    "from patients p\n" +
	                    "join patient_history ph ON p.patient_id=ph.patient_id\n" +
	                    "group by p.patient_id, p.fist_name,p.last_name;\n");
	            
	            while(rs.next()){
	                System.out.print ("'"+rs.getInt("patient_id")+"',");
	                System.out.print ("'"+rs.getString("fist_name")+"',");
	                System.out.print("'"+rs.getString("last_name")+"',");
	                System.out.print ("'"+rs.getString("history_count")+"',");
	                int count = rs.getInt("history_count");
	                if(count >=40 ) {System.out.println("PLATINUM");}
	                else if(count >=30 && count <=39) {System.out.println("GOLD");}
	                else if(count >=20 && count <=29) {System.out.println("SILVER");}
	                else if(count >=0 && count <=1) {System.out.println("BRONZE");}
	               
	            }
	            rs.close();
	            con.close();
	        } catch (SQLException e) { // ClassNotFoundException
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (Exception e) {			// SQLException
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }
}

