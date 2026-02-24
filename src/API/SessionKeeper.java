package API;

import API.interfaces.IUserSession;

public class SessionKeeper {
	
	public static IUserSession session;

	public static IUserSession getSession() {
		return session;
	}
	
}
