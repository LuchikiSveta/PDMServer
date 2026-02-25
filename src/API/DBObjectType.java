package API;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import API.interfaces.IDBObjectType;

public class DBObjectType implements IDBObjectType {

	int objectTypeID = -1;
	
	public DBObjectType(int id) {
		objectTypeID = id;
	}
	
	public int getObjectType() {
		return objectTypeID;
	}

	@Override
	public String getObjectTypeName() {
		
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/storage", "root", "82548391")){
			
			String SQL = "SELECT description FROM storage.object_types WHERE (type_id = ?);";
			
			PreparedStatement statment = conn.prepareStatement(SQL);

			statment.setInt(1, objectTypeID);
			
			ResultSet result = statment.executeQuery();
			
			if(result.next()) return result.getString(1);
		
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
		return null;
		
	}

	@Override
	public int getParentTypeID() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public String toString() {
		return getObjectTypeName();
	}

}
