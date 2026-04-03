package API.PDMBrowser;

import API.SessionKeeper;
import API.kernel.search.ColumnDescriptor;
import API.kernel.search.ConditionStructure;
import API.kernel.search.DBRecordSetParams;

public interface IPDMBrowser {
	
	SessionKeeper getSessionKeeper();
	
	DBRecordSetParams getDBRecordSetParams(ConditionStructure[] conditionStructures, ColumnDescriptor[] columnDescriptors);

	ColumnDescriptor getColumnDescriptor(String attributeName);
	
}
