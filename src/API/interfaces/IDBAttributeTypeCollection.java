package API.interfaces;

import java.util.List;

import API.AttributeTypeProperties;

public interface IDBAttributeTypeCollection {
	
	int create(AttributeTypeProperties attrProperties);
	
	List<AttributeTypeProperties> select();

}
