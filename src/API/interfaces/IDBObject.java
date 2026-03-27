package API.interfaces;

import java.util.Date;

public interface IDBObject {

	IDBAttributeCollection getAttributeCollection();
	
	long getCheckoutBy();
	
	Date getModifyDate();
	
}
