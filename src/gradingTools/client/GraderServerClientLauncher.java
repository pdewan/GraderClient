package gradingTools.client;

import inputport.InputPort;
import port.PortLauncher;
import gradingTools.server.RemoteGraderServer;

public interface GraderServerClientLauncher extends PortLauncher {
	public static final String CLIENT_NAME =  "Driver Client";
	RemoteGraderServer getDriverServerProxy();
	SynchronizingConnectionListener getSynchronizingConnectionListener();
	InputPort getMainPort();


}
