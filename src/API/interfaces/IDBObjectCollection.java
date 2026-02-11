package API.interfaces;

import java.sql.SQLException;

import API.kernel.search.DBRecordSetParams;

public interface IDBObjectCollection {
	
	public Object[][] select(DBRecordSetParams params) throws SQLException, Exception;

}
