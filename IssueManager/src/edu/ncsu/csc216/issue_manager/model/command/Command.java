
package edu.ncsu.csc216.issue_manager.model.command;

/**
 * This class creates objects that encapsulate user actions that can cause the
 * state of a issue to update. Contains two inner enumerations for the possible
 * commands that can cause an issue to update.
 * 
 * @author symone
 *
 */
public class Command {

	/** A constant string for the “Fixed” resolution. */
	public static final String R_FIXED = "Fixed";

	/** A constant string for the “Duplicate” resolution. */
	public static final String R_DUPLICATE = "Duplicate";

	/** A constant string for the “WontFix” resolution. */
	public static final String R_WONTFIX = "WontFix";

	/** A constant string for the “WorksForMe” resolution. */
	public static final String R_WORKSFORME = "WorksForMe";

	/** Owner's Id of issue */
	private String ownerId;

	/** Represents note for the issue object */
	private String note;

	/** Resolution of issue */
	private Resolution resolution;

	/** Command value of issue */
	private CommandValue c;

	/**
	 * An enumeration that represents one of the five possible commands that a user
	 * can make for the Issue Manager.
	 * 
	 * @author symone
	 *
	 */
	public enum CommandValue { ASSIGN, CONFIRM, RESOLVE, VERIFY, REOPEN }

	/**
	 * An enumeration that represents the four possible ways a user can resolve an
	 * issue.
	 * 
	 * @author symone
	 *
	 */
	public enum Resolution { FIXED, DUPLICATE, WONTFIX, WORKSFORME }

	/**
	 * Constructor has 4 parameters: CommandValue c, String ownerId, Resolution r,
	 * String note Not all parameters are required for each CommandValue. Unneeded
	 * values will be passed as null or ignored when the Command is used by the
	 * Issue FSM. The following conditions result in an IllegalArgumentException
	 * with the message “Invalid information.” when constructing a Command object: A
	 * Command with a null CommandValue parameter A Command with a CommandValue of
	 * ASSIGN and a null or empty string ownerId. A Command with a CommandValue of
	 * RESOLVE and a null resolution. A Command with a null or empty note parameter.
	 * 
	 * @param ownerId owner of issue Id
	 * @param note    issue note
	 * @param c       command value
	 * @param resolution  Issue's resolution of type Resolution
	 * @throws IllegalArgumentException with the message “Invalid information.” when
	 *                                  constructing an invalid Command object
	 */
	public Command(CommandValue c, String ownerId, Resolution resolution, String note) {
		if (c == null) {
			throw new IllegalArgumentException("Invalid information.");
		}
		
		if (c == CommandValue.ASSIGN && (ownerId == null || ownerId.length() == 0)) {
			throw new IllegalArgumentException("Invalid information.");
		}
		
		if (c == CommandValue.RESOLVE && resolution == null) {
			throw new IllegalArgumentException("Invalid information.");
		}
		
		if (note == null || note.length() == 0) {
			throw new IllegalArgumentException("Invalid information.");
		} else {
			
			this.ownerId = ownerId;
			this.note = note;
			this.resolution = resolution;
			this.c = c;
		}	
	}

	/**
	 * Returns Owner's ID 
	 * 
	 * @return ownerId Owner's ID to be returned
	 */
	public String getOwnerId() {
		return ownerId;
	}

	/**
	 * Returns the Issue's note
	 * 
	 * @return note the issue's note to be returned
	 */
	public String getNote() {
		return note;
	}

	/**
	 * Returns the Issue's resolution
	 * 
	 * @return r resolution of issue to be returned
	 */
	public Resolution getResolution() {
		return this.resolution;

	}

	
	/**
	 * Returns command value
	 * 
	 * @return c command value to be returned
	 */
	public CommandValue getCommand() {
		return this.c;

	}

}
