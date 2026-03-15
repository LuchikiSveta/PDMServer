package API.PDMBrowser;

import py4j.GatewayServer;

public class PDMBrowserConnector {

	public void init() {
		GatewayServer gatewayServer = new GatewayServer(new PDMBrowser());
        gatewayServer.start();
	}
	
}
