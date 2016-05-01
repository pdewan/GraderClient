package gradingTools.server;

public interface DriverServerObject {
	public static final String START_DRIVE = "Grading server: Calling driver with args:";
	public static final String END_DRIVE = "Grading server: Retrned from calling driver";

	Exception drive (String[] args);
}
