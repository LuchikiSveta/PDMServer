package API;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import API.interfaces.IDBObjectTypeCollection;

public class DBObjectTypeCollection implements IDBObjectTypeCollection {
	
	int type;
	
	DBObjectTypeCollection(int type){
		this.type = type;
	}
	
	public List<ObjectTypeProperties> select() {
		
		List<ObjectTypeProperties> objectTypes = new ArrayList<ObjectTypeProperties>();
		
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/storage", "root", "82548391")){
			
			String SQL = "SELECT type_id, description, parent_object_type FROM storage.object_types WHERE parent_object_type = ?;";
			
			PreparedStatement statment = conn.prepareStatement(SQL);
			
			statment.setInt(1, type);
			
			ResultSet result = statment.executeQuery();
			
			while(result.next()) {
				ObjectTypeProperties object = new ObjectTypeProperties();
				
				object.objectTypeID = result.getInt(1);
				object.objectName = result.getString(2);
				object.parentTypeID = result.getInt(3);
				
				objectTypes.add(object);
				
			}
		
		} catch (Exception e) {
			return null;
		}

		return objectTypes;
		
	}
	
}
