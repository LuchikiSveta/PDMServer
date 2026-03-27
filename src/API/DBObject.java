package API;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import API.interfaces.IDBAttributeCollection;
import API.interfaces.IDBObject;

public class DBObject implements IDBObject {
	
	private long objectVersionID;
	
	public DBObject(long objectVersionID) {
		this.objectVersionID = objectVersionID;
	}

	@Override
	public IDBAttributeCollection getAttributeCollection() {
		// TODO Auto-generated method stub
		return null;
	}

	public long getCheckoutBy() {
		
		try {
			
			Connection conn = SessionKeeper.SQLSession;
			
			String SQL =  "SELECT who_editing_id FROM storage.object_versions WHERE version_id = ?;";
			
			PreparedStatement statment = conn.prepareStatement(SQL);
			
			statment.setLong(1, objectVersionID);
			
			ResultSet result = statment.executeQuery();
			
			while(result.next()) {
				return result.getLong(1);
			}
			
		} catch (Exception e) {
			
		}
		
		return 0;
	}
	
	public Date getModifyDate() {
		
		try {
			
			Connection conn = SessionKeeper.SQLSession;
			
			String SQL =  "SELECT updated_at FROM storage.object_versions WHERE version_id = ?;";
			
			PreparedStatement statment = conn.prepareStatement(SQL);
			
			statment.setLong(1, objectVersionID);
			
			ResultSet result = statment.executeQuery();
			
			while(result.next()) {
				return result.getTimestamp(1);
			}
			
		} catch (Exception e) {
			
		}
		
		return null;
	}
	
}
