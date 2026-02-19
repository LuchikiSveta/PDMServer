package API;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import API.interfaces.IDBAttributeType;
import API.interfaces.IDBAttributeTypeCollection;
import API.interfaces.IDBObjectCollection;
import API.interfaces.IDBObjectTypeCollection;
import API.interfaces.IUserSession;
import API.interfaces.MetaDataHelper;

public class UserSession implements IUserSession{

	long userID = -1;
	
	public UserSession(String login, String password) throws Exception {
		
		Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/storage", "root", "82548391");
			
		String SQL = "SELECT o.version_id\r\n"
				   + "FROM storage.object_versions o\r\n"
				   + "-- Ищем логин\r\n"
				   + "JOIN storage.attributes a_login ON o.version_id = a_login.object_version_id\r\n"
				   + "    AND a_login.attribute_type_id = 4\r\n"
				   + "    AND a_login.attribute_value = ?\r\n"
				   + "-- Ищем пароль\r\n"
				   + "JOIN storage.attributes a_pass ON o.version_id = a_pass.object_version_id\r\n"
				   + "    AND a_pass.attribute_type_id = 5\r\n"
				   + "    AND a_pass.attribute_value = ?\r\n"
				   + "-- Фильтруем только пользователей\r\n"
				   + "WHERE o.type_id = 1;";
				
		PreparedStatement statment = conn.prepareStatement(SQL);
				
		statment.setString(1, login);
		statment.setString(2, password);
		
		ResultSet result = statment.executeQuery();
		
		
		
		if(result.next()) {
			userID = result.getLong(1);
		}
		
		if(userID == -1) throw new Exception("Пользователь не найден!");
		
		new MetaDataHelper();
		
	}
	
	public IDBObjectCollection getObjectCollection(int objectType) {
		return new DBObjectCollection(objectType);
	}

	public IDBAttributeTypeCollection getAttributeTypeCollection() {
		
		return new DBAttributeTypeCollection();
		
	}

	public IDBAttributeType getAttributeType(int attributeTypeID) {
		
		return new DBAttributeType(attributeTypeID);
	}

	public IDBObjectTypeCollection getObjectTypeCollection(int parentTypeID) {
		
		return new DBObjectTypeCollection(parentTypeID);
	}

}
