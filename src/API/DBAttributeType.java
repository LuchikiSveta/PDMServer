package API;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import API.interfaces.IDBAttributeType;

public class DBAttributeType implements IDBAttributeType {

	int attributeID = 0;
	
	DBAttributeType(int attributeID) {
		this.attributeID = attributeID;
	}
	
	public void setName(String newName) {
		
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/storage", "root", "82548391")){
			
			String SQL = "UPDATE storage.attribute_types SET attribute_type_name = ? WHERE (attribute_type_id = ?);";
			
			PreparedStatement statment = conn.prepareStatement(SQL);
			
			statment.setString(1, newName);
			statment.setInt(2, attributeID);
			
			statment.executeUpdate();
		
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}

	}

	public void setAttributeType(int newValType) {
		
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/storage", "root", "82548391")){
			
			String SQL = "UPDATE storage.attribute_types SET attribute_type_value = ? WHERE (attribute_type_id = ?);";
			
			PreparedStatement statment = conn.prepareStatement(SQL);
			
			statment.setInt(1, newValType);
			statment.setInt(2, attributeID);
			
			statment.executeUpdate();
		
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}

}
