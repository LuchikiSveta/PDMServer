package API.interfaces;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MetaDataHelper {
	
	static List<ObjectType> objectTypes = new ArrayList<ObjectType>();
	
	public MetaDataHelper() throws Exception{
		
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/storage", "root", "82548391")){
		
			String SQL = "SELECT type_id, description, attributes, parent_object_type FROM storage.object_types;";
			
			PreparedStatement statment = conn.prepareStatement(SQL);
			
			ResultSet result = statment.executeQuery();
			
			while(result.next()) {
				ObjectType type = new ObjectType();
				
				type.objectTypeID = result.getInt(1);
				type.objectTypeName = result.getString(2);
				type.parentTypeID = result.getInt(4);
				
				objectTypes.add(type);
				
			}
		
		} catch (Exception e) {
			throw new Exception("Ошибка подключения к базе данных!");
		}
		
	}
	
	public static List<Integer> getObjectTypeChildrenID(int parentTypeID) {
		
		List<Integer> children = new ArrayList<Integer>();
		
		findChildren(children, parentTypeID);
		
		return children;
		
	}
	
	private static void findChildren(List<Integer> children, int parent) {
		
		for(ObjectType type : objectTypes) {
			
			if(type.parentTypeID == parent) {
				
				findChildren(children, type.objectTypeID);
				
				children.add(type.objectTypeID);
				
			}
			
		}
		
	}
	
	class ObjectType {
		
		int objectTypeID;
		String objectTypeName;
		int parentTypeID;
		
	}

}
