package API;

import java.sql.Connection;

import API.interfaces.IUserSession;

public class SessionKeeper {
	
	public static IUserSession session;
	
	public static Connection SQLSession;

	public static IUserSession getSession() {
		return session;
	}
	
}
