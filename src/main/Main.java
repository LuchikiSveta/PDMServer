package main;

import java.util.List;

import API.UserSession;
import API.interfaces.IDBObjectCollection;
import API.interfaces.IUserSession;
import API.interfaces.MetaDataHelper;
import API.kernel.search.ColumnDescriptor;
import API.kernel.search.DBRecordSetParams;
import main.GUI.LoginWindow;

public class Main {

	public static void main(String[] args) throws Exception {
		/*
		IUserSession session = new UserSession("Maks", "1235");
		
		IDBObjectCollection collection = session.getObjectCollection(1);
		
		ColumnDescriptor[] columns = new ColumnDescriptor[] {
			new ColumnDescriptor("Имя"),
			new ColumnDescriptor("Фамилия"),
			new ColumnDescriptor("Дата_рождения")
		};
		
		DBRecordSetParams setParams = new DBRecordSetParams(null, columns);
		
		Object[][] data = collection.select(setParams);
		
		for (Object[] row : data) {
            for (Object cell : row) {
                System.out.print(cell + "\t");
            }
            System.out.println();
        }
		*/
		
		LoginWindow dialog = new LoginWindow();
		
		new MetaDataHelper();
		
		List<Integer> types = MetaDataHelper.getObjectTypeChildrenID(0);
		
		for (Integer row : types) {
			
			System.out.println(row);
			
		}
		
	}

}
