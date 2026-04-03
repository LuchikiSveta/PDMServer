package API;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import API.interfaces.IDBAttribute;
import API.interfaces.IDBAttributeCollection;

public class DBAttributeCollection implements IDBAttributeCollection {

	private long objectID;
	
	public DBAttributeCollection(long objectID) {
		this.objectID = objectID;
	}
	
	public IDBAttribute addAttribute(int attributeID) {
		
		try {
			
			Connection conn = SessionKeeper.SQLSession;
			
			String SQL =  "SELECT storage.addAttribute(?, ?);";
			
			PreparedStatement statment = conn.prepareStatement(SQL);
			
			statment.setLong(1, objectID);
			statment.setInt(2, attributeID);
			
			ResultSet result = statment.executeQuery();
			
			result.next();
			
			if(result.getInt(1) != 1) return null;
			
			return new DBAttribute(objectID, attributeID);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}

	public Object[][] GetAttributesDataTable() {
		
		try {
			
			Connection conn = SessionKeeper.SQLSession;
			
			String SQL =  "SELECT att.attribute_type_name, at.attribute_value FROM storage.attributes AS at "
					    + "CROSS JOIN storage.attribute_types AS att WHERE att.attribute_type_id = at.attribute_type_id AND at.object_version_id = ? ;";
			
			PreparedStatement statment = conn.prepareStatement(SQL);
			
			statment.setLong(1, objectID);
			
			ResultSet result = statment.executeQuery();
			
			List<String[]> rows = new ArrayList<>();
			
			while (result.next()) {
				String[] row = new String[2];
			    
				row[0] = result.getString(1);
				row[1] = result.getString(2);
				
			    rows.add(row);
			}
			    
			return rows.toArray(new String[0][]);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}

}
