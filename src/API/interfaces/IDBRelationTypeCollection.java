package API.interfaces;

import java.util.List;

import API.ObjectTypeProperties;
import API.RelationTypeProperties;

public interface IDBRelationTypeCollection {
	
	public int create(RelationTypeProperties relationProperties);
	
	public List<RelationTypeProperties> select();

}
