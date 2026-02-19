package API.interfaces;

import java.util.List;

import API.ObjectTypeProperties;

public interface IDBObjectTypeCollection {
	
	List<ObjectTypeProperties> select();
	
}
