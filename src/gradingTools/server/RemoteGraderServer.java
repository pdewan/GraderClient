package gradingTools.server;

import java.rmi.Remote;

public interface RemoteGraderServer extends Remote{
	public static final String START_DRIVE = "Grading server: Calling driver with args:";
	public static final String END_DRIVE = "Grading server: Retrned from calling driver";

	Exception drive (String[] args);
}
