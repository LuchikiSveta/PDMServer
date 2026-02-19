package API.interfaces;

public interface IUserSession {
	
	IDBObjectCollection getObjectCollection(int objectType);
	
	IDBAttributeTypeCollection getAttributeTypeCollection();
	
	IDBAttributeType getAttributeType(int attributeTypeID);
	
	IDBObjectTypeCollection getObjectTypeCollection (int parentTypeID);

}
