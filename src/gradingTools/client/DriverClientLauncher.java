package gradingTools.client;

import inputport.InputPort;
import port.PortLauncher;
import gradingTools.server.DriverServerObject;

public interface DriverClientLauncher extends PortLauncher {
	public static final String CLIENT_NAME =  "Driver Client";
	DriverServerObject getDriverServerProxy();
	SynchronizingConnectionListener getSynchronizingConnectionListener();
	InputPort getMainPort();


}
