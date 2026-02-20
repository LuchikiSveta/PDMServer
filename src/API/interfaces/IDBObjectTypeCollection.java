package API.interfaces;

import java.util.List;

import API.ObjectTypeProperties;

public interface IDBObjectTypeCollection {
	
	int create(ObjectTypeProperties typeProperties);
	
	List<ObjectTypeProperties> select();
	
}
