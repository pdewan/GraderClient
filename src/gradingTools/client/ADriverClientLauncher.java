package gradingTools.client;


import gradingTools.server.DriverServerLauncher;
import gradingTools.server.DriverServerObject;
import inputport.ConnectionListener;
import inputport.InputPort;
import inputport.rpc.duplex.AnAbstractDuplexRPCClientPortLauncher;
import port.ATracingConnectionListener;
import port.PortAccessKind;

public class ADriverClientLauncher extends AnAbstractDuplexRPCClientPortLauncher  implements DriverClientLauncher{
	SynchronizingConnectionListener connectionListener;
	public ADriverClientLauncher(String aServerHost, String aServerId) {
		super(DriverClientLauncher.CLIENT_NAME, aServerHost, aServerId, DriverServerLauncher.DRIVER_SERVER_NAME);
			
	}
	public ADriverClientLauncher(String aServerHost) {
		super(DriverClientLauncher.CLIENT_NAME, aServerHost, DriverServerLauncher.DRIVER_SERVER_ID, DriverServerLauncher.DRIVER_SERVER_NAME);
			
	}
	
//	protected PortAccessKind getPortAccessKind() {
//		return PortAccessKind.SIMPLEX;
//	}

	public ADriverClientLauncher(String aClientName, String aServerHost, String aServerId, String aServerName) {
		super(aClientName, aServerHost, aServerId, aServerName);
			
	}
	

	protected Class registeredServerClass() {
		return DriverServerLauncher.DRIVER_SERVER_CLASS;
	}

	

	
	protected DriverServerObject driverServerProxy;
	

	protected  ConnectionListener getConnectionListener (InputPort anInputPort) {
		if (connectionListener == null) {
			connectionListener = new ASynchronizingConnectionListener(anInputPort);			
		}
		return connectionListener;
	}
	

	
	protected  void createProxies() {

//		sessionServerProxy = (RMISimulationSessionServer) DirectedRPCProxyGenerator.generateRPCProxy((DuplexRPCClientInputPort) mainPort, registeredSessionServerClass());
		driverServerProxy = (DriverServerObject)  createProxy (registeredServerClass());
	}
	
//	public void drive (String[] args) {
//		if (driverServerProxy == null) {
//			System.err.println("Proxy for driver not bound");
//			return;
//		}
//	}


	@Override
	public DriverServerObject getDriverServerProxy() {
		return driverServerProxy;
	}
	static DriverClientLauncher singleton;
	public static DriverClientLauncher getInstance() {
		if (singleton == null) {
			singleton = new ADriverClientLauncher("localhost");
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
	public static DriverClientLauncher createAndLaunch(String aServerHost, int aServerNumber) {
		String aServerId = DriverServerLauncher.computeServerId(aServerNumber);
		ADriverClientLauncher aClient = new ADriverClientLauncher(aServerHost, aServerId);
		aClient.launch();
		
			
			try {
//				Thread.sleep(1000);
				aClient.getSynchronizingConnectionListener().waitForConnectionStatus();
				if (!aClient.getMainPort().isConnected(DriverServerLauncher.DRIVER_SERVER_NAME) ){
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
	public static DriverClientLauncher createAndLaunchLocal(int aServerNumber) {
		return createAndLaunch("localhost", aServerNumber);
	}

	
	public static void main (String[] args) {
		int defaultID = Integer.parseInt(DriverServerLauncher.DRIVER_SERVER_ID);
		
		for (int i = 0; i < 3; i ++) {
			DriverClientLauncher driverClient = createAndLaunchLocal (i);
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
