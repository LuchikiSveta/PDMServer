package main;

import java.io.FileReader;
import java.io.StringWriter;
import java.util.List;

import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;
import javax.swing.JFrame;

import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import API.AttributeTypeProperties;
import API.SessionKeeper;
import API.UserSession;
import API.PDMBrowser.PDMBrowserConnector;
import API.interfaces.IDBAttributeTypeCollection;
import API.interfaces.IDBObjectCollection;
import API.interfaces.IUserSession;
import API.interfaces.MetaDataHelper;
import API.kernel.search.ColumnDescriptor;
import API.kernel.search.DBRecordSetParams;
import API.navigator.AttributeTable;
import API.navigator.SelectionWindow;
import main.GUI.AdminWindow;
import main.GUI.LoginWindow;

public class Main {
	
	static ScriptEngineManager manager = new ScriptEngineManager();
    static javax.script.ScriptEngine engine = manager.getEngineByName("python");
    static Invocable invocable = (Invocable) engine;

    private static Object load(IUserSession session) throws ScriptException, NoSuchMethodException{
        return invocable.invokeFunction("load",session);
    }
    
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
		
		engine.eval(new FileReader("D:\\test.py"));
		load(dialog.session);
		
		/*new PDMBrowserConnector().init();
		
		new AdminWindow(dialog.session);
		
		new SelectionWindow();*/
		
	}

}
