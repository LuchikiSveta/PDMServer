package API;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import API.interfaces.IDBObject;
import API.interfaces.IDBObjectCollection;
import API.interfaces.MetaDataHelper;
import API.kernel.search.ColumnDescriptor;
import API.kernel.search.DBRecordSetParams;

public class DBObjectCollection implements IDBObjectCollection {

	int objectType = -1;
	
	public DBObjectCollection(int objectType) {
		this.objectType = objectType;
	}
	
	public Object[][] select(DBRecordSetParams params) throws Exception {
	
		if(params.columnDescriptors == null) throw new Exception("Не заданы запрашиваемые столбцы!");

		String selectClause_1 = String.join(
		    ",\r\n",
		    java.util.Arrays.stream(params.columnDescriptors)
		        .map(attr -> "    MAX(CASE WHEN at.attribute_type_name = '" + attr.attributeName + "' THEN a.attribute_value END) AS " + attr.attributeName)
		        .toArray(String[]::new)
		);
		
		String selectClause_2 = String.join(
			    ", ",
			    java.util.Arrays.stream(params.columnDescriptors)
			        .map(attr -> "'" + attr.attributeName + "'")
			        .toArray(String[]::new)
			);
		
		List<Integer> list = MetaDataHelper.getObjectTypeChildrenID(objectType);
		
		list.add(objectType);
		
		String selectClause_3 = String.join(
			    ", ",
			    java.util.Arrays.stream(list.toArray())
			        .map(attr -> "'" + ((int)attr) + "'")
			        .toArray(String[]::new)
			);
		
		Connection conn = SessionKeeper.SQLSession;
		
		String SQL =  "SELECT\r\n"
					+ "    ov.version_id AS object_id,\r\n"
					+ selectClause_1 + "\r\n"
					+ "FROM\r\n"
					+ "    object_versions ov\r\n"
					+ "JOIN\r\n"
					+ "    attributes a ON ov.version_id = a.object_version_id\r\n"
					+ "JOIN\r\n"
					+ "    attribute_types at ON a.attribute_type_id = at.attribute_type_id\r\n"
					+ "WHERE\r\n"
					+ "    -- Условия: объект должен удовлетворять ВСЕМ перечисленным критериям\r\n"
					+ "    EXISTS (\r\n"
					+ "        SELECT 1\r\n"
					+ "        FROM attributes a2\r\n"
					+ "        JOIN attribute_types at2 ON a2.attribute_type_id = at2.attribute_type_id\r\n"
					+ "        WHERE a2.object_version_id = ov.version_id\r\n"
					+ "          AND ov.type_id IN ("+selectClause_3+")\r\n"
					//+ "          AND (\r\n"
					//+ "              (ov.type_id = 1)\r\n"
					//+ "          )\r\n"
					+ "    )\r\n"
					+ "    AND at.attribute_type_name IN (" + selectClause_2 + ")\r\n"
					+ "GROUP BY\r\n"
					+ "    ov.version_id\r\n"
					+ "ORDER BY\r\n"
					+ "    ov.version_id;";
		
		PreparedStatement statment = conn.prepareStatement(SQL);
		
		ResultSet result = statment.executeQuery();
		
		ResultSetMetaData meta = result.getMetaData();
	    int colCount = meta.getColumnCount();
	    
	    List<String[]> rows = new ArrayList<>();
	    
	    while (result.next()) {
	        String[] row = new String[colCount];
	        for (int i = 1; i <= colCount; i++) {
	            Object val = result.getString(i);
	            row[i - 1] = (val == null) ? null : val.toString();
	        }
	        rows.add(row);
	    }
	    
	    return rows.toArray(new String[0][]);
	    
	}

	
	public IDBObject create() {
		
		try {
			
			Connection conn = SessionKeeper.SQLSession;
			
			String SQL =  "SELECT storage.addObject(?, ?, 1000);";
			
			PreparedStatement statment = conn.prepareStatement(SQL);
			
			statment.setInt(1, objectType);
			statment.setLong(2, SessionKeeper.getSession().getUserID());
			
			ResultSet result = statment.executeQuery();
			
			result.next();
			
			return new DBObject(result.getInt(1));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	
	
}
