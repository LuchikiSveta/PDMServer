package API;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import API.interfaces.IDBAttributeTypeCollection;

public class DBAttributeTypeCollection implements IDBAttributeTypeCollection{

	public int create(AttributeTypeProperties attrProperties) {
		
		
		
		return 0;
	}

	public List<AttributeTypeProperties> select() {
		
		List<AttributeTypeProperties> attributeTypes = new ArrayList<AttributeTypeProperties>();
		
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/storage", "root", "82548391")){
			
			String SQL = "SELECT attribute_type_id, attribute_type_name, attribute_type_value FROM storage.attribute_types;";
			
			PreparedStatement statment = conn.prepareStatement(SQL);
			
			ResultSet result = statment.executeQuery();
			
			while(result.next()) {
				AttributeTypeProperties attribute = new AttributeTypeProperties();
				
				attribute.attributeTypeID = result.getInt(1);
				attribute.attributeName = result.getString(2);
				attribute.valueAttributeType = result.getInt(3);
				
				attributeTypes.add(attribute);
				
			}
		
		} catch (Exception e) {
			return null;
		}

		return attributeTypes;
		
	}

}
