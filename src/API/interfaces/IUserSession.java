package API.interfaces;

public interface IUserSession {
	
	long getUserID();
	
	IDBObjectCollection getObjectCollection(int objectType);
	
	IDBAttributeTypeCollection getAttributeTypeCollection();
	
	IDBAttributeType getAttributeType(int attributeTypeID);
	
	IDBObjectTypeCollection getObjectTypeCollection (int parentTypeID);
	
	IDBObjectType getObjectType(int typeID);
	
	IDBObject getObject(long objectVersionID);

}
