/**
 * 
 */
package edu.ncsu.csc216.issue_manager.model.command;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.issue_manager.model.command.Command.CommandValue;
import edu.ncsu.csc216.issue_manager.model.command.Command.Resolution;

/**
 * Test for the Command class
 * 
 * @author symone
 *
 */
public class CommandTest {

	/**
	 * Tests get methods
	 */
	@Test
	public void testGetMethods() {
		Command command1 = new Command(CommandValue.ASSIGN, "1", Resolution.DUPLICATE, "Note1");
		assertEquals("Note1", command1.getNote());
		assertEquals("1", command1.getOwnerId());
		assertEquals(CommandValue.ASSIGN, command1.getCommand());
		assertEquals(Resolution.DUPLICATE, command1.getResolution());
		
		Command command2 = new Command(CommandValue.CONFIRM, "1", Resolution.FIXED, "Note1");
		assertEquals("Note1", command2.getNote());
		assertEquals("1", command2.getOwnerId());
		assertEquals(CommandValue.CONFIRM, command2.getCommand());
		assertEquals(Resolution.FIXED, command2.getResolution());
		
		Command command3 = new Command(CommandValue.RESOLVE, "1", Resolution.WORKSFORME, "Note1");
		assertEquals("Note1", command3.getNote());
		assertEquals("1", command3.getOwnerId());
		assertEquals(CommandValue.RESOLVE, command3.getCommand());
		assertEquals(Resolution.WORKSFORME, command3.getResolution());
		
		Command command4 = new Command(CommandValue.REOPEN, "1", Resolution.WONTFIX, "Note1");
		assertEquals("Note1", command4.getNote());
		assertEquals("1", command4.getOwnerId());
		assertEquals(CommandValue.REOPEN, command4.getCommand());
		assertEquals(Resolution.WONTFIX, command4.getResolution());
		
		Command command5 = new Command(CommandValue.VERIFY, "1", Resolution.WONTFIX, "Note1");
		assertEquals("Note1", command5.getNote());
		assertEquals("1", command5.getOwnerId());
		assertEquals(CommandValue.VERIFY, command5.getCommand());
		assertEquals(Resolution.WONTFIX, command5.getResolution());
		
		Command command6 = new Command(CommandValue.VERIFY, "1", null, "Note1");
		assertEquals("Note1", command6.getNote());
		assertEquals("1", command6.getOwnerId());
		assertEquals(CommandValue.VERIFY, command6.getCommand());
		assertEquals(null, command6.getResolution());

	}

	/**
	 * Tests Constructor with invalid parameters
	 */
	@Test
	public void testConstructorInvaildParam() {
		// Testing with CommandValue null
		try {
			new Command(null, "1", Resolution.DUPLICATE, "Note1");
			
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid information.", e.getMessage());
		}

		// Testing with CommandValue of ASSIGN and null ownerID
		try {
			new Command(CommandValue.ASSIGN, null, Resolution.DUPLICATE, "Note1");
			
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid information.", e.getMessage());
		}

		// Testing with CommandValue of ASSIGN and empty string ownerID
		try {
			new Command(CommandValue.ASSIGN, "", Resolution.DUPLICATE, "Note1");
		
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid information.", e.getMessage());
		}

		// Testing with CommandValue of RESOLVE and null resolution
		try {
			new Command(CommandValue.RESOLVE, "1", null, "Note1");
			
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid information.", e.getMessage());
		}

	
	//Testing with null and empty note parameter
			try {
				new Command(CommandValue.RESOLVE, "1", Resolution.DUPLICATE, null);
				
			} catch (IllegalArgumentException e) {
				assertEquals("Invalid information.", e.getMessage());
			}
			
			try {
				new Command(CommandValue.RESOLVE, "1", Resolution.DUPLICATE, "");
		
			} catch (IllegalArgumentException e) {
				assertEquals("Invalid information.", e.getMessage());
			}

		}

}
