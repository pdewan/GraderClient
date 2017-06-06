package gradingTools.server;

import java.rmi.Remote;
/*
 * The interface is implemented by ARemoteGraderServer in project
 * RemoteGraderServer.
 * It is used by the dispatcher server.
 * ARemoteGraderServer needs the full grader. Actually it needs all checks.
 * So it is kept separate from the interface, so the dispatcher server
 * is not bound to the grader.
 */
public interface RemoteGraderServer extends Remote{
	public static final String START_DRIVE = "Grading server: Calling driver with args:";
	public static final String END_DRIVE = "Grading server: Retrned from calling driver";

	Exception drive (String[] args);
}
