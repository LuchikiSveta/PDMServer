package API;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import API.interfaces.IDBAttribute;

public class DBAttribute implements IDBAttribute {

	long attrTypeID = 0;
	long objID = 0;
	
	public DBAttribute(long objID, long attrTypeID) {
		this.attrTypeID = attrTypeID;
		this.objID = objID;
	}
	
	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setValue(Object value) {
		
		try {
			
			Connection conn = SessionKeeper.SQLSession;
			
			String SQL = "UPDATE storage.attributes SET attribute_value = ? WHERE attribute_type_id = ? AND object_version_id = ?;";
			
			PreparedStatement statment = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
			
			statment.setObject(1, value);
			statment.setLong(2, attrTypeID);
			statment.setLong(3, objID);
			
			int count = statment.executeUpdate();
			
			if(count != 1) return false;
			else return true;
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	

}
