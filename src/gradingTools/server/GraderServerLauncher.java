package gradingTools.server;


public interface GraderServerLauncher {
	public static String DRIVER_SERVER_NAME = "driver server";
	public static String DRIVER_SERVER_START_MESSAGE = "Started Driver Server with server id:";
	public static final String DRIVER_SERVER_ID = "12345";
	public static final Class DRIVER_SERVER_CLASS = RemoteGraderServer.class;
	public static String computeServerId(int aServerNumber) {
		int aBaseNumber = Integer.parseInt(DRIVER_SERVER_ID);

		return "" + (aBaseNumber + aServerNumber);
	}


}