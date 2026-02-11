package API.kernel.search;

public class DBRecordSetParams {
	
	public ConditionStructure[] conditionStructures;
	
	public ColumnDescriptor[] columnDescriptors;
	
	/**
	 * @param conditionStructures - Структура условий запроса в БД
	 * @param columnDescriptors - Перечень колонок
	 */
	
	public DBRecordSetParams(ConditionStructure[] conditionStructures, ColumnDescriptor[] columnDescriptors) {
		this.columnDescriptors = columnDescriptors;
		this.conditionStructures = conditionStructures;
	}

}
