package API;

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
	
}
