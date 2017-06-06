package gradingTools.client;


import gradingTools.server.GraderServerLauncher;
import gradingTools.server.RemoteGraderServer;
import inputport.ConnectionListener;
import inputport.InputPort;
import inputport.rpc.duplex.AnAbstractDuplexRPCClientPortLauncher;
import port.ATracingConnectionListener;
import port.PortAccessKind;

public class AGraderServerClientLauncher extends AnAbstractDuplexRPCClientPortLauncher  implements GraderServerClientLauncher{
	SynchronizingConnectionListener connectionListener;
	public AGraderServerClientLauncher(String aServerHost, String aServerId) {
		super(GraderServerClientLauncher.CLIENT_NAME, aServerHost, aServerId, GraderServerLauncher.DRIVER_SERVER_NAME);
			
	}
	public AGraderServerClientLauncher(String aServerHost) {
		super(GraderServerClientLauncher.CLIENT_NAME, aServerHost, GraderServerLauncher.DRIVER_SERVER_ID, GraderServerLauncher.DRIVER_SERVER_NAME);
			
	}
	
//	protected PortAccessKind getPortAccessKind() {
//		return PortAccessKind.SIMPLEX;
//	}

	public AGraderServerClientLauncher(String aClientName, String aServerHost, String aServerId, String aServerName) {
		super(aClientName, aServerHost, aServerId, aServerName);
			
	}
	

	protected Class registeredServerClass() {
		return GraderServerLauncher.DRIVER_SERVER_CLASS;
	}

	

	
	protected RemoteGraderServer driverServerProxy;
	

	protected  ConnectionListener getConnectionListener (InputPort anInputPort) {
		if (connectionListener == null) {
			connectionListener = new ASynchronizingConnectionListener(anInputPort);			
		}
		return connectionListener;
	}
	

	
	protected  void createProxies() {

//		sessionServerProxy = (RMISimulationSessionServer) DirectedRPCProxyGenerator.generateRPCProxy((DuplexRPCClientInputPort) mainPort, registeredSessionServerClass());
		driverServerProxy = (RemoteGraderServer)  createProxy (registeredServerClass());
	}
	
//	public void drive (String[] args) {
//		if (driverServerProxy == null) {
//			System.err.println("Proxy for driver not bound");
//			return;
//		}
//	}


	@Override
	public RemoteGraderServer getDriverServerProxy() {
		return driverServerProxy;
	}
	static GraderServerClientLauncher singleton;
	public static GraderServerClientLauncher getInstance() {
		if (singleton == null) {
			singleton = new AGraderServerClientLauncher("localhost");
			singleton.launch();
			
			try {
				Thread.sleep(1000);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return singleton;
			
	}
	@Override
	public InputPort getMainPort() {
		return mainPort;
	}
	public SynchronizingConnectionListener getSynchronizingConnectionListener() {
		// getPort will create another port, it should be called createPort
		return (SynchronizingConnectionListener) getConnectionListener(mainPort);
	}
	/*
	 * Create the process and then wait for connectiont to finish.
	 * Latest gipc should not require a connection listener.
	 */
	public static GraderServerClientLauncher createAndLaunch(String aServerHost, int aServerNumber) {
		String aServerId = GraderServerLauncher.computeServerId(aServerNumber);
		AGraderServerClientLauncher aClient = new AGraderServerClientLauncher(aServerHost, aServerId);
		aClient.launch();
		
			
			try {
//				Thread.sleep(1000);
				aClient.getSynchronizingConnectionListener().waitForConnectionStatus();
				if (!aClient.getMainPort().isConnected(GraderServerLauncher.DRIVER_SERVER_NAME) ){
					// should put in synchronous connect into gipc
					System.out.println ("Could not connect to grading server #" + aServerNumber);
					return null;
				}
			 
					
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return aClient;
			
	}
	public static GraderServerClientLauncher createAndLaunchLocal(int aServerNumber) {
		return createAndLaunch("localhost", aServerNumber);
	}

	
	public static void main (String[] args) {
		int defaultID = Integer.parseInt(GraderServerLauncher.DRIVER_SERVER_ID);
		
		for (int i = 0; i < 3; i ++) {
			GraderServerClientLauncher driverClient = createAndLaunchLocal (i);
			if (driverClient != null) {
				System.out.println ("Grading client: calling server driver with args:" + args);
				Object retVal = driverClient.getDriverServerProxy().drive(new String[]{});
				System.out.println ("Grading client: returnd from calling server driver with args:" + args);
				if (retVal != null && retVal instanceof Exception) {
					Exception e = (Exception) retVal;
					System.out.println ("Nested exception:" + e);
					e.printStackTrace();
				}

			}
		}
		
	}
	
	
	
//	public  void  launchClient(String aMyName, HalloweenCommandProcessor aCommandProcessor, boolean aBroadcast) {
////		   RMISimulationInCoupler inCoupler = new AnRMISimulationInCoupler(aCommandProcessor);
//		   RMISimulationInCoupler inCoupler = createRMISimulationInCoupler();
//
//		try {
//			
//
//		   
//			sessionServerProxy.join(aMyName, inCoupler);
//		   
//			   if (aBroadcast) {
//				   new AnRMISimulationOutCoupler(aCommandProcessor, sessionServerProxy, aMyName);
//			   }
//				   
//		   } catch (Exception e) {
//			
//	      e.printStackTrace();
//	    }
//	  
//	  }
	
		

}
