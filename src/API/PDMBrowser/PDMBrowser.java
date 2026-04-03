package API.PDMBrowser;

import API.SessionKeeper;
import API.kernel.search.ColumnDescriptor;
import API.kernel.search.ConditionStructure;
import API.kernel.search.DBRecordSetParams;

public class PDMBrowser implements IPDMBrowser {

	public SessionKeeper getSessionKeeper() {
		return new SessionKeeper();
	}

	public DBRecordSetParams getDBRecordSetParams(ConditionStructure[] conditionStructures, ColumnDescriptor[] columnDescriptors) {
		return new DBRecordSetParams(conditionStructures, columnDescriptors);
	}

	public ColumnDescriptor getColumnDescriptor(String attributeName) {
		return new ColumnDescriptor(attributeName);
	}


}
