package API;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import API.interfaces.IDBAttributeTypeCollection;

public class DBAttributeTypeCollection implements IDBAttributeTypeCollection{

	public int create(AttributeTypeProperties attrProperties) {
		
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/storage", "root", "82548391")){
			
			String SQL = "INSERT INTO storage.attribute_types (attribute_type_name, attribute_type_value) VALUES (?, ?);";
			
			PreparedStatement statment = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
			
			statment.setString(1, attrProperties.attributeName);
			statment.setInt(2, attrProperties.valueAttributeType);
			
			statment.executeUpdate();
			
			ResultSet result = statment.getGeneratedKeys();
			
			result.next();
			
			return result.getInt(1);
			
		
		} catch (Exception e) {
			
			e.printStackTrace();
			
			return -1;
		}
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
