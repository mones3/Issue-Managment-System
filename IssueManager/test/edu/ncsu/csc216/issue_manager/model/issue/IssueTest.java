/**
 * 
 */
package edu.ncsu.csc216.issue_manager.model.issue;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.issue_manager.model.command.Command;
import edu.ncsu.csc216.issue_manager.model.command.Command.CommandValue;
import edu.ncsu.csc216.issue_manager.model.command.Command.Resolution;
import edu.ncsu.csc216.issue_manager.model.issue.Issue.IssueType;

/**
 * Test Issue class
 * 
 * @author symone
 *
 */
public class IssueTest {

	/** Valid ArrayList of notes used to test constructor using all parameters */
	private static final ArrayList<String> VALID_NOTES1 = new ArrayList<String>(Arrays.asList("[New] note for issue1"));
	
	

	/**
	 * Test Constructor with Four parameters using valid values
	 */
	@Test
	public void testConstructorIntIssueTypeStringStringValid() {
		Issue issue1 = new Issue(1, IssueType.BUG, "Summary for issue 1", "note1");
		assertEquals(1, issue1.getIssueId());
		assertEquals("Summary for issue 1", issue1.getSummary());
		assertEquals("-[New] note1\n", issue1.getNotesString());

	}

	/**
	 * Test NewState class
	 */
	@Test
	public void testTaskStateNew() {
		// Testing NewA
		Issue issue1 = new Issue(1, IssueType.ENHANCEMENT, "Summary for issue 1", "note1");
		Command command1 = new Command(CommandValue.ASSIGN, "ownerID", null, "note for command");
		assertEquals(Issue.NEW_NAME, issue1.getStateName());
		issue1.update(command1);
		assertEquals("ownerID", issue1.getOwner());
		assertSame(Issue.WORKING_NAME, issue1.getStateName());
		

		// Test NewB
		Issue issue2 = new Issue(1, IssueType.BUG, "Summary for issue 2", "note 2");
		Command command2 = new Command(CommandValue.CONFIRM, "ownerID", null, "note for command");
		assertEquals(Issue.NEW_NAME, issue2.getStateName());
		issue2.update(command2);
		assertEquals("ownerID", issue2.getOwner());
		assertSame(Issue.CONFIRMED_NAME, issue2.getStateName());
		

		// Test NewC
		Issue issue3 = new Issue(1, IssueType.ENHANCEMENT, "Summary for issue 3", "note 3");
		Command command3 = new Command(CommandValue.RESOLVE, "ownerID", Resolution.DUPLICATE, "note for command");
		assertEquals(Issue.NEW_NAME, issue3.getStateName());
		issue3.update(command3);
		assertEquals("Duplicate", issue3.getResolution());
		assertEquals("ownerID", issue3.getOwner());
		assertSame(Issue.CLOSED_NAME, issue3.getStateName());
		

		// Invalid command for New State
		try {
			Issue issue4 = new Issue(1, IssueType.ENHANCEMENT, "Summary for issue 3", "note 3");
			Command command4 = new Command(CommandValue.RESOLVE, "ownerID", Resolution.WORKSFORME, "note for command");
			issue4.update(command4);
			fail();
		} catch (UnsupportedOperationException e) {

			assertEquals("Invalid information.", e.getMessage());
		}

	}
	
	
	
	
	/**
	 * Test FSM 
	 */
	@Test
	public void issueFsmTest1() {
		IssueType issueTypeE = IssueType.ENHANCEMENT;
		IssueType issueTypeB = IssueType.BUG;
		String summary = "Summary";
		String note = "note";
		List<String> notes = new ArrayList<String>();
		notes.add(note);
		Command command = new Command(CommandValue.ASSIGN, "ownerID", null, "note1");
		Issue newIssue = new Issue(1, issueTypeE, summary, note);
		assertEquals(newIssue.getStateName(), "New");
		newIssue.update(command);
		assertEquals(newIssue.getStateName(), "Working");
		command = new Command(CommandValue.RESOLVE, "ownerID", Resolution.FIXED, "note2");
		newIssue.update(command);
		assertEquals("Verifying", newIssue.getStateName());
		command = new Command(CommandValue.VERIFY, "ownerID", null, "note3");
		newIssue.update(command);
		assertEquals("Closed", newIssue.getStateName());
		command = new Command(CommandValue.REOPEN, "ownerID", null, "note4");
		newIssue.update(command);
		assertEquals("Working", newIssue.getStateName());
		
		try {
			newIssue.update(command);
		} catch (UnsupportedOperationException e) {
			assertEquals("Working", newIssue.getStateName());
		}
		try {
			command = new Command(CommandValue.RESOLVE, "ownerID", Resolution.WORKSFORME, "note");
			newIssue.update(command);
		} catch (UnsupportedOperationException e) {
			assertEquals("Working", newIssue.getStateName());
		}
		command = new Command(CommandValue.RESOLVE, "ownerID", Resolution.DUPLICATE, "note");
		newIssue.update(command);
		assertEquals("Closed", newIssue.getStateName());

		Issue newIssue1 = new Issue(1, issueTypeB, summary, note);
		command = new Command(CommandValue.CONFIRM, null, null, "note1");
		newIssue1.update(command);
		assertEquals("Confirmed", newIssue1.getStateName());
		command = new Command(CommandValue.RESOLVE, null, Resolution.WONTFIX, "note1");
		newIssue1.update(command);
		assertEquals("Closed", newIssue1.getStateName());
		command = new Command(CommandValue.REOPEN, null, null, "note2");
		newIssue1.update(command);
		assertEquals("Confirmed", newIssue1.getStateName());
		command = new Command(CommandValue.ASSIGN, "ownerID", null, "note2");
		newIssue1.update(command);
		assertEquals("Working", newIssue1.getStateName());
	}
	
	/**
	 *FSM Test 2
	 */
	@Test
	public void issueFsmTest2() {
		IssueType issueTypeE = IssueType.ENHANCEMENT;
		IssueType issueTypeB = IssueType.BUG;
		String summary = "Summary";
		String note = "note";
		List<String> notes = new ArrayList<String>();
		notes.add(note);
		Command command = new Command(CommandValue.RESOLVE, "", Resolution.WONTFIX, "note");
		Issue newIssue = new Issue(1, issueTypeE, summary, note);
		newIssue.update(command);
		assertEquals(newIssue.getStateName(), "Closed");

		command = new Command(CommandValue.RESOLVE, "", Resolution.WONTFIX, "note");
		newIssue = new Issue(1, issueTypeB, summary, note);
		newIssue.update(command);
		assertEquals(newIssue.getStateName(), "Closed");

		command = new Command(CommandValue.REOPEN, "", null, "note");
		newIssue.update(command);
		assertEquals(newIssue.getStateName(), "New");
	}

	/**
	 * Test Constructor with all parameters using invalid values
	 */
	@Test
	public void testConstructorAllParametersInValid() {
		// null note
		try{
			 new Issue(1, Issue.NEW_NAME, Issue.I_ENHANCEMENT, "Summary for issue 1", "", false, "", null);
		}  catch (IllegalArgumentException e) {

			assertEquals("Issue cannot be created.", e.getMessage());
		}
		
		// null issueType
		try{
			 new Issue(1, Issue.NEW_NAME, null, "Summary for issue 1", "", false, "", VALID_NOTES1);
		}  catch (IllegalArgumentException e) {

			assertEquals("Issue cannot be created.", e.getMessage());
		}
	}

	/**
	 * Test Constructor with all parameters using valid values
	 */
	@Test
	public void testConstructorAllParametersValid() {
		// empty resolution and owner
		Issue issue = new Issue(1, Issue.NEW_NAME, Issue.I_ENHANCEMENT, "Summary for issue 1", "", false, "",
				VALID_NOTES1);
		assertEquals(1, issue.getIssueId());
		assertEquals("New", issue.getStateName());
		assertEquals("Enhancement", issue.getIssueType());
		assertEquals("Summary for issue 1", issue.getSummary());
		assertEquals(null, issue.getOwner());
		assertFalse(issue.isConfirmed());
		assertNull(issue.getResolution());
	
		

		Issue issue2 = new Issue(1, Issue.NEW_NAME, Issue.I_BUG, "Summary for issue 2", "", false,
				"", VALID_NOTES1);
		assertEquals(1, issue2.getIssueId());
		assertEquals("New", issue2.getStateName());
		assertEquals("Bug", issue2.getIssueType());
		assertEquals("Summary for issue 2", issue2.getSummary());
		assertEquals(null, issue2.getOwner());
		assertFalse(issue2.isConfirmed());
		assertEquals(null, issue2.getResolution());
		assertEquals("-[New] note for issue1\n", issue2.getNotesString());

	}

	/**
	 * Test Constructor with Four parameters with invalid values
	 */
	@Test
	public void testConstructorIntIssueTypeStringStringInvalid() {
		// Invalid owner id
		try {
			new Issue(0, Issue.IssueType.BUG, "Summary for issue 1", "note1");
			fail();
		} catch (IllegalArgumentException e) {

			assertEquals("Issue cannot be created.", e.getMessage());
		}

		try {
			new Issue(-1, Issue.IssueType.BUG, "Summary for issue 1", "note1");
			fail();
		} catch (IllegalArgumentException e) {

			assertEquals("Issue cannot be created.", e.getMessage());
		}

		// null and empty summary
		try {
			new Issue(1, Issue.IssueType.BUG, null, "note1");
			fail();
		} catch (IllegalArgumentException e) {

			assertEquals("Issue cannot be created.", e.getMessage());
		}

		try {
			new Issue(1, Issue.IssueType.BUG, "", "note1");
			fail();
		} catch (IllegalArgumentException e) {

			assertEquals("Issue cannot be created.", e.getMessage());
		}

		// null and empty note
		try {
			new Issue(1, Issue.IssueType.BUG, "Summary for issue 1", null);
			fail();
		} catch (IllegalArgumentException e) {

			assertEquals("Issue cannot be created.", e.getMessage());
		}

		try {
			new Issue(1, Issue.IssueType.BUG, "Summary for issue 1", "");
			fail();
		} catch (IllegalArgumentException e) {

			assertEquals("Issue cannot be created.", e.getMessage());
		}

	}
	
	/**
	 * FSM Test3 
	 */
	@Test
	public void issueFsmTest3() {
		IssueType issueTypeE = IssueType.ENHANCEMENT;
		String summary = "Enhancement Summary";
		String note = "note";
		List<String> notes = new ArrayList<String>();
		notes.add(note);
		Command  command = new Command(CommandValue.ASSIGN, "ownerID", null, "note1");
		Issue newIssue = new Issue(1, issueTypeE, summary, note);
		assertEquals(newIssue.getStateName(), "New");
		newIssue.update(command);
		assertEquals(newIssue.getStateName(), "Working");
		 command = new Command(CommandValue.RESOLVE, "ownerID", Resolution.FIXED, "fixed");
		newIssue.update( command);
		assertEquals("Verifying", newIssue.getStateName());
		 command = new Command(CommandValue.REOPEN, "ownerID", null, "verified");
		newIssue.update( command);
		assertEquals("Working", newIssue.getStateName());
	}
	
	/**
	 *FSM Test 4
	 */
	@Test
	public void issueFsmTest4() {
		IssueType issueTypeB = IssueType.BUG;
		String summary = "Summary";
		String note = "note";
		List<String> notes = new ArrayList<String>();
		notes.add(note);
		Command command = new Command(CommandValue.CONFIRM, "", Resolution.WONTFIX, "note");
		Issue newIssue = new Issue(1, issueTypeB, summary, note);
		newIssue.update(command);
		assertEquals(newIssue.getStateName(), "Confirmed");
		command = new Command(CommandValue.VERIFY, "", Resolution.WONTFIX, "note");
		try {
			newIssue.update(command);
		} catch (UnsupportedOperationException e) {
			assertEquals("Invalid information.", e.getMessage());
		}

		
	}
	
	


}
