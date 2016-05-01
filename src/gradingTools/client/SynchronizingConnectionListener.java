package gradingTools.client;

import inputport.ConnectionListener;

public interface SynchronizingConnectionListener extends ConnectionListener {
	void waitForConnectionStatus();

}
