package API.interfaces;

public interface IUserSession {
	
	IDBObjectCollection getObjectCollection(int objectType);
	
	IDBAttributeTypeCollection getAttributeTypeCollection();
	
	IDBAttributeType GetAttributeType(int attributeTypeID);

}
