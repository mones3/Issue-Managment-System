
package edu.ncsu.csc216.issue_manager.model.issue;




import java.util.ArrayList;

import edu.ncsu.csc216.issue_manager.model.command.Command;
import edu.ncsu.csc216.issue_manager.model.command.Command.CommandValue;
import edu.ncsu.csc216.issue_manager.model.command.Command.Resolution;

/**
 * Concrete class representing the State Pattern context. An Issue keeps track
 * of all issue information including the current state. The state is updated
 * when a Command encapsulating a transition is given to the Issue. Issue
 * encapsulates the state pattern interface, five concrete state classes and one
 * enumeration.
 * 
 * @author symone
 *
 */
public class Issue {

	/**
	 * An enumeration that represents the the type of issue. Contains two types(Bug
	 * and enhancement)
	 */
	public enum IssueType { ENHANCEMENT, BUG }
	
	/** A constant string for the “Enhancement” issue type. */
	public static final String I_ENHANCEMENT = "Enhancement";

	/** A constant string for the “Bug” issue type. */
	public static final String I_BUG = "Bug";

	/** A constant string for the new state’s name with the value “New”. */
	public static final String NEW_NAME = "New";

	/** A constant string for the working state’s name with the value “Working”. */
	public static final String WORKING_NAME = "Working";

	/**
	 * A constant string for the confirmed state’s name with the value “Confirmed”.
	 */
	public static final String CONFIRMED_NAME = "Confirmed";

	/**
	 * A constant string for the verifying state’s name with the value “Verifying”.
	 */
	public static final String VERIFYING_NAME = "Verifying";

	/** A constant string for the closed state’s name with the value “Closed”. */
	public static final String CLOSED_NAME = "Closed";

	/** Unique issue id for an issue. */
	private int issueId;

	/** Issue’s summary information from when the issue is created. */
	private String summary;

	/** User id of the issue owner or null if there is not an assigned owner. */
	private String owner;

	/** Current state for the issue of type IssueState */
	private IssueState state;

	/**
	 * Issue resolution of type Resolution or null if there is not an assigned
	 * Resolution.
	 */
	private Resolution resolution;

	/** Type of issue either a IssueType.ENHANCEMENT or IssueType.BUG */
	private IssueType issueType;

	/**
	 * True if the issue is confirmed. The confirmed field can only be set to true
	 * if the issue is a bug.
	 */
	private boolean confirmed;

	/** Counter for the class */
	public static int counter = 1;

	/** An ArrayList of notes  for the issue*/
	private ArrayList<String> notes = new ArrayList<String>();

	/**
	 * Constructs a Issue from the provided IssueType, summary, and note. If any of
	 * the parameters are null or empty strings or the id is less than 1, then an
	 * IllegalArgumentException is thrown with the message “Issue cannot be
	 * created.”. The rest of the fields are initialized to the parameter values,
	 * null, false, or an empty object type as appropriate (Checked through the set
	 * methods). The note is added to a constructed ArrayList string object.
	 * 
	 * @param issueId   issue id
	 * @param summary   summary of the issue as a string
	 * @param note      string of note on the issue
	 * @param issueType type of issue
	 * @throws IllegalArgumentException is thrown with the message “Issue cannot be
	 *                                  created." if there is an problem with any of
	 *                                  the parameter.
	 */
	public Issue(int issueId, IssueType issueType, String summary, String note) {
		if (issueType == null || summary == null || note == null || "".equals(summary) || "".equals(note)
				|| issueId < 1) {
			throw new IllegalArgumentException("Issue cannot be created.");
		}
		
		this.issueId = issueId;
		incrementCounter();
		this.issueType = issueType;
		this.summary = summary;
		this.state = new NewState();
		this.owner = null;
		this.confirmed = false;
		this.resolution = null;
		
		addNote(note);

	}

	/**
	 * The fields of the Issue are set to the values from the constructor, which are
	 * used from the IssueReader class. Throws and IllegalArgumentException with the
	 * message "Issue cannot be created." is there is a problem with any of the
	 * parameters (Checked thought the set methods)
	 * 
	 * @param id         issue's id
	 * @param state      the state of the issue
	 * @param issueType  the type of issue
	 * @param summary    the issue's summary
	 * @param owner      the issue's owner
	 * @param confirmed  the issue's confirmed
	 * @param resolution the issue's resolution
	 * @param notes      the issue's note
	 * @throws IllegalArgumentException is thrown with the message “Issue cannot be
	 *                                  created." if there is a problem with any of
	 *                                  the parameter.
	 */
	public Issue(int id, String state, String issueType, String summary, String owner, boolean confirmed,
			String resolution, ArrayList<String> notes) {
	
		
		if (issueType == null || "".equals(issueType) || summary == null || "".equals(summary) || notes == null  || id < 0 || resolution == null) {
			throw new IllegalArgumentException("Issue cannot be created.");
		}
		
		
		if (notes.isEmpty()) {
			throw new IllegalArgumentException("Issue cannot be created.");
		}

		if (state.equals(WORKING_NAME) && ("".equals(owner) || owner == null)) {
			throw new IllegalArgumentException("Issue cannot be created.");
		}
		
		if (state.equals(WORKING_NAME) && issueType.equals(I_BUG) && !confirmed) {
			throw new IllegalArgumentException();
		}

		if (issueType.equals(I_ENHANCEMENT) && resolution.equals(Command.R_WORKSFORME)) {
			throw new IllegalArgumentException("Issue cannot be created.");
		}

		if ("".equals(owner) && (state.equals(VERIFYING_NAME) || state.equals(WORKING_NAME))) {
		throw new IllegalArgumentException("Issue cannot be created.");
		}

		if (!"".equals(resolution) && !(state.equals(VERIFYING_NAME) || state.equals(CLOSED_NAME))) {
			throw new IllegalArgumentException("Issue cannot be created.");
		}
		if (state.equals(VERIFYING_NAME) && !resolution.equals(Command.R_FIXED)) {
			throw new IllegalArgumentException("Issue cannot be created.");
		}

		if (state.equals(CONFIRMED_NAME) && issueType.equals(I_ENHANCEMENT)) {
			throw new IllegalArgumentException("Issue cannot be created.");
		}
		if (state.equals(CLOSED_NAME) && "".equals(resolution)) {
			throw new IllegalArgumentException("Issue cannot be created.");
		}

		if (confirmed && issueType.equals(I_ENHANCEMENT)) {
			throw new IllegalArgumentException("Issue cannot be created.");
		}
		setIssueId(id);
		
		setState(state);
		setIssueType(issueType);
		setSummary(summary);
		setOwner(owner);
		setConfirmed(confirmed);
		setResolution(resolution);
		setNotes(notes);

	}

	/**
	 * Sets the Issue's state. Throws IllegalArgumentException if parameter is null
	 * or an empty string with the message "Issue cannot be created."
	 * 
	 * @param state the Issues state to be set
	 * @throws IllegalArugmentException if parameter is null or an empty string
	 */
	private void setState(String state) {
		if (state == null || "".equals(state)) {
			throw new IllegalArgumentException("Issue cannot be created.");
		} else if (state.equals(NEW_NAME)) {
			this.state = new NewState();
		} else if (state.equals(WORKING_NAME)) {
			this.state = new WorkingState();
		} else if (state.equals(CONFIRMED_NAME)) {
			this.confirmed = true;
			this.state = new ConfirmedState();
		} else if (state.equals(VERIFYING_NAME)) {
			this.state = new VerifyingState();
		} else if (state.equals(CLOSED_NAME)) {
			this.state = new ClosedState();
		} else {
			throw new IllegalArgumentException("Issue cannot be created.");
		}

	}

	/**
	 * Sets Issue's resolution. Throws IllegalArgumentException if parameter is
	 * invalid with the message "Issue cannot be created."
	 * 
	 * @param resolution the Issue's resolution to set
	 * @throws IllegalArugmentException if parameter is an invalid string value
	 */
	private void setResolution(String resolution) {
		if (resolution.equals(Command.R_DUPLICATE)) {
			this.resolution = Resolution.DUPLICATE;
		} else if (resolution.equals(Command.R_FIXED)) {
			this.resolution = Resolution.FIXED;
		} else if (resolution.equals(Command.R_WONTFIX)) {
			this.resolution = Resolution.WONTFIX;
		} else if (resolution.equals(Command.R_WORKSFORME)) {
			this.resolution = Resolution.WORKSFORME;
		} else {
			this.resolution = null;
		}
	}

	/**
	 * Sets the Issue's type. Throws IllegalArgumentException if parameter is null
	 * or an empty string with the message "Issue cannot be created."
	 * 
	 * @param issueType the issue's type to set
	 * @throws IllegalArugmentException if parameter is null or an empty string
	 */
	private void setIssueType(String issueType) {
		if (issueType.equals(I_ENHANCEMENT)) {
			this.issueType = IssueType.ENHANCEMENT;
		} else if (issueType.equals(I_BUG)) {
			this.issueType = IssueType.BUG;
		} else {

			throw new IllegalArgumentException("Issue cannot be created.");
		}

	}

	/**
	 * Set the Issue's counter
	 * 
	 * @param count Issue's count
	 */
	public static void setCounter(int count) {
		counter = count;

	}

	/**
	 * Returns the IssueType variable as a string
	 * 
	 * @return issue type as a string
	 */
	public String getIssueType() {
		if (issueType == IssueType.BUG) {
			return I_BUG;
		}
		return I_ENHANCEMENT;
	}

	/**
	 * Returns the issue's Resolution variable as a string
	 * 
	 * @return resolution the issue's Resolution variable
	 */
	public String getResolution() {
		if (this.resolution == Resolution.DUPLICATE) {
			return Command.R_DUPLICATE;
		} else if (this.resolution == Resolution.FIXED) {
			return Command.R_FIXED;
		} else if (this.resolution == Resolution.WONTFIX) {
			return Command.R_WONTFIX;
		} else if (this.resolution == Resolution.WORKSFORME) {
			return Command.R_WORKSFORME;
		}
		return null;
	}

	/**
	 * Returns the Issue's id
	 * 
	 * @return issueId issue id to be returned
	 */
	public int getIssueId() {
		return issueId;
	}

	/**
	 * Sets the issue's id. Throws IllegalArgumentException if parameter is less
	 * than 1 with the message "Issue cannot be created."
	 * 
	 * @param issueId the issueId to set
	 * @throws IllegalArugmentException if parameter is less than 1.
	 */
	private void setIssueId(int issueId) {
		if (issueId <= 0) {
			throw new IllegalArgumentException("Issue cannot be created.");
		}
		this.issueId = issueId;
	}

	/**
	 * Returns the Issue's summary
	 * 
	 * @return the summary to be returned
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * Sets the Issue's summary. Throws IllegalArgumentException if parameter is
	 * null or an empty string with the message "Issue cannot be created."
	 * 
	 * @param summary the summary to set
	 * @throws IllegalArugmentException if parameter is null or an empty string
	 */
	private void setSummary(String summary) {
		if (summary == null || "".equals(summary)) {
			throw new IllegalArgumentException("Issue cannot be created.");
		}
		this.summary = summary;
	}

	/**
	 * Returns the Issue's owner
	 * 
	 * @return the owner to be returned
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * Sets the Issue's owner.
	 * 
	 * @param owner the owner to set
	 */
	private void setOwner(String owner) {
		if ("".equals(owner)) {
			this.owner = null;
		} else {
			this.owner = owner;
		}

	}

	/**
	 * Returns Issue's confirmed field
	 * 
	 * @return the confirmed value to be returned
	 */
	public boolean isConfirmed() {
		return confirmed;
	}

	/**
	 * Sets the confirmed value.
	 * 
	 * @param confirmed the confirmed to set.
	 */
	private void setConfirmed(boolean confirmed) {

		this.confirmed = confirmed;
	}

	/**
	 * Method used to increase counter
	 */
	public static void incrementCounter() {
		counter++;
	}

	/**
	 * Returns the Issue's list of notes.
	 * 
	 * @return the notes to be returned
	 */
	public ArrayList<String> getNotes() {
		return notes;
	}

	/**
	 * Sets the Issue's list of notes.
	 * 
	 * @param notes the notes to set
	 */
	private void setNotes(ArrayList<String> notes) {
		this.notes = notes;
	}

	/**
	 * Returns the issue's note as a string
	 * 
	 * @return the issue's note to be returned.
	 */
	public String getNotesString() {
		String rtn = "";
		for (int i = 0; i < notes.size(); i++) {
			rtn += "-" + notes.get(i) + "\n";
		}
		return rtn;
	}

	/**
	 * Ensures that the note is not null or an empty string. If the note has
	 * contents, then the state name in square brackets is prepended to the note
	 * 
	 * @param note issue's note to be added
	 */
	private void addNote(String note) {
		if (note == null || "".equals(note)) {
			throw new IllegalArgumentException("Issue cannot be created.");
		}
		notes.add(notes.size(), "[" + getStateName() + "] " + note);

	}

	/**
	 * Return the StateName variable as a string
	 * 
	 * @return state name as a string
	 */
	public String getStateName() {
		return state.getStateName();

	}

	/**
	 * Returns the string representation of the Issue that is printed during file
	 * save operations
	 */
	@Override
	public String toString() {
		String s;

		if (resolution == null) {
			s = "*" + this.getIssueId() + "," + this.getStateName() + "," + this.getIssueType() + ","
					+ this.getSummary() + "," + this.getOwner() + "," + confirmed + ",\n";
			s += getNotesString();

		} else {
			s = "*" + this.getIssueId() + "," + this.getStateName() + "," + this.getIssueType() + ","
					+ this.getSummary() + "," + this.getOwner() + "," + confirmed + "," + this.getResolution() + "\n";
			s += getNotesString();
		}
		return s;
	}

	/**
	 * Updates the Issue's command
	 * 
	 * @param command the command to be updated
	 */
	public void update(Command command) {
		state.updateState(command);

	}

	/**
	 * Interface for states in the Issue State Pattern. All concrete issue states
	 * must implement the IssueState interface. The IssueState interface should be a
	 * private interface of the Issue class.
	 * 
	 * @author Dr. Sarah Heckman (sarah_heckman@ncsu.edu)
	 */
	private interface IssueState {

		/**
		 * Update the Issue based on the given Command. An UnsupportedOperationException
		 * is throw if the Command is not a valid action for the given state.
		 * 
		 * @param command Command describing the action that will update the Issue's
		 *                state.
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 *                                       for the given state.
		 */
		void updateState(Command command);

		/**
		 * Returns the name of the current state as a String.
		 * 
		 * @return the name of the current state as a String.
		 */
		String getStateName();

	}

	/**
	 * Concrete state class that implements IssueState. An inner class of Issue.
	 * Supports the two behaviors: update when given a Command and know its name.
	 * Represents the new state of the Issue Manager FSM.
	 * 
	 * @author symone
	 *
	 */
	private class NewState implements IssueState {

		/**
		 * Updates the new state when given a command
		 * 
		 * @param command the command
		 */
		@Override
		public void updateState(Command command) {

			if (issueType == IssueType.ENHANCEMENT && command.getCommand() == CommandValue.ASSIGN) {
				if (command.getOwnerId() == null || command.getOwnerId().length() == 0) {
					throw new UnsupportedOperationException("Invalid information.");
				} else {

					setState(WORKING_NAME);
					notes.add("[" + state.getStateName() + "] " + command.getNote());
				}
			} else if (issueType == IssueType.BUG && command.getCommand() == CommandValue.CONFIRM) {

				setState(CONFIRMED_NAME);
				notes.add("[" + state.getStateName() + "] " + command.getNote());
			} else if (command.getCommand() == CommandValue.RESOLVE) {
				if (issueType == IssueType.ENHANCEMENT) {
					if (command.getResolution() == Resolution.FIXED
							|| command.getResolution() == Resolution.WORKSFORME) {
						throw new UnsupportedOperationException("Invalid information.");
					}

					resolution = command.getResolution();
					setState(CLOSED_NAME);
					notes.add("[" + state.getStateName() + "] " + command.getNote());
				} else if (issueType == IssueType.BUG) {
					if (command.getResolution() != Resolution.FIXED) {
						resolution = command.getResolution();
						setState(CLOSED_NAME);
						notes.add("[" + state.getStateName() + "] " + command.getNote());
					} else {
						throw new UnsupportedOperationException("Invalid information.");
					}
				}
			} else {
				throw new UnsupportedOperationException("Invalid information.");
			}
			owner = command.getOwnerId();
		}

		/**
		 * Returns the name of the new state as a String.
		 * 
		 * @return the name of the new state as a String.
		 */
		@Override
		public String getStateName() {
			return NEW_NAME;
		}

	}

	/**
	 * Concrete state class that implements IssueState. An inner class of Issue.
	 * Supports the two behaviors: update when given a Command and know its name.
	 * Represents the working state of the Issue Manager FSM.
	 * 
	 * @author symone
	 *
	 */
	private class WorkingState implements IssueState {

		/**
		 * Updates the working state when given a command
		 * 
		 * @param command the command
		 */
		@Override
		public void updateState(Command command) {
			if (command.getCommand() == CommandValue.RESOLVE && command.getResolution() == Resolution.FIXED) {
				setState("Verifying");
				notes.add("[" + state.getStateName() + "] " + command.getNote());
				resolution = command.getResolution();
			} else if (command.getCommand() == CommandValue.RESOLVE && command.getResolution() != Resolution.FIXED) {
				if (command.getResolution() == Resolution.WORKSFORME && issueType != IssueType.BUG) {
					throw new UnsupportedOperationException("Invalid information.");
				}

				setState("Closed");
				notes.add("[" + state.getStateName() + "] " + command.getNote());
				resolution = command.getResolution();
			} else {
				throw new UnsupportedOperationException();
			}

		}

		/**
		 * Returns the name of the working state as a String.
		 * 
		 * @return the name of the working state as a String.
		 */
		@Override
		public String getStateName() {
			return WORKING_NAME;
		}

	}

	/**
	 * Concrete state class that implements IssueState. An inner class of Issue.
	 * Supports the two behaviors: update when given a Command and know its name.
	 * Represents the confirmed state of the Issue Manager FSM.
	 * 
	 * @author symone
	 *
	 */
	private class ConfirmedState implements IssueState {

		/**
		 * Updates the new state when given a command
		 * 
		 * @param command the command
		 */
		@Override
		public void updateState(Command command) {

			if (issueType == IssueType.BUG && command.getCommand() == CommandValue.ASSIGN
					&& !(command.getOwnerId() == null || command.getOwnerId().length() == 0)) {
				owner = command.getOwnerId();
				setState(WORKING_NAME);
				notes.add("[" + state.getStateName() + "] " + command.getNote());

			} else if (issueType == IssueType.BUG && command.getCommand() == CommandValue.RESOLVE
					&& command.getResolution() == Resolution.WONTFIX) {
				owner = command.getOwnerId();
				resolution = command.getResolution();
				setState(CLOSED_NAME);
				notes.add("[" + state.getStateName() + "] " + command.getNote());

			} else {
				throw new UnsupportedOperationException("Invalid information.");
			}

		}

		/**
		 * Returns the name of the confirmed state as a String.
		 * 
		 * @return the name of the confirmed state as a String.
		 */
		@Override
		public String getStateName() {
			return CONFIRMED_NAME;
		}

	}

	/**
	 * Concrete state class that implements IssueState. An inner class of Issue.
	 * Supports the two behaviors: update when given a Command and know its name.
	 * Represents the verifying state of the Issue Manager FSM.
	 * 
	 * @author symone
	 *
	 */
	private class VerifyingState implements IssueState {

		/**
		 * Updates the verifying state when given a command
		 * 
		 * @param command the command
		 */
		@Override
		public void updateState(Command command) {
			if (command.getCommand() == CommandValue.VERIFY) {
				setState(CLOSED_NAME);
				notes.add("[" + state.getStateName() + "] " + command.getNote());

			} else if (command.getCommand() == CommandValue.REOPEN) {
				setState(WORKING_NAME);
				resolution = null;
				notes.add("[" + state.getStateName() + "] " + command.getNote());
			} else {
				throw new UnsupportedOperationException("Invalid information.");
			}
		}

		/**
		 * Returns the name of the verifying state as a String.
		 * 
		 * @return the name of the verifying state as a String.
		 */
		@Override
		public String getStateName() {
			return VERIFYING_NAME;
		}

	}

	/**
	 * Concrete state class that implements Issue. An inner class of Issue. Supports
	 * the two behaviors: update when given a Command and know its name. Represents
	 * the closed state of the Issue Manager FSM.
	 * 
	 * @author symone
	 *
	 */
	private class ClosedState implements IssueState {

		/**
		 * Updates the closed state when given a command
		 * 
		 * @param command the command
		 */
		@Override
		public void updateState(Command command) {
			if (command.getCommand() == CommandValue.REOPEN) {
				if (owner != null && owner.length() != 0) {
					if (issueType == IssueType.BUG && !confirmed) {
						throw new UnsupportedOperationException("Invalid information.");
					} else {
						setState(WORKING_NAME);
						notes.add("[" + state.getStateName() + "] " + command.getNote());

					}
				} else if (owner == null || owner.length() == 0) {
					if (issueType == IssueType.BUG && confirmed) {
						setState(CONFIRMED_NAME);
						notes.add("[" + state.getStateName() + "] " + command.getNote());

					} else {
						setState(NEW_NAME);
						notes.add("[" + state.getStateName() + "] " + command.getNote());

					}
				}
				resolution = null;
			} else {
				throw new UnsupportedOperationException("Invalid information.");
			}
			resolution = command.getResolution();
		}

		/**
		 * Returns the name of the closed state as a String.
		 * 
		 * @return the name of the closed state as a String.
		 */
		@Override
		public String getStateName() {
			return CLOSED_NAME;
		}

	}

}
