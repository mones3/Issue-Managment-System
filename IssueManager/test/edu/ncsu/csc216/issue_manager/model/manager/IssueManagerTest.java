/**
 * 
 */
package edu.ncsu.csc216.issue_manager.model.manager;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.issue_manager.model.issue.Issue;
import edu.ncsu.csc216.issue_manager.model.issue.Issue.IssueType;

/**
 * Test IssueManager class
 * @author symone
 *
 */
public class IssueManagerTest {

	/**
	 * Test case for the IssueManager class
	 */
	@Test
	public void issueManagerTest() {
		IssueManager manager;		
		manager = IssueManager.getInstance();
		manager.createNewIssueList();
		manager.addIssueToList(IssueType.BUG, "summary", "note");
		manager.addIssueToList(IssueType.ENHANCEMENT, "summary", "note");
		manager.addIssueToList(IssueType.BUG, "summary2", "note2");
		try {
			manager.getIssueListAsArrayByIssueType(null);
		} catch (IllegalArgumentException e) {
			assertEquals("Issue type is null", e.getMessage());
		}
		Object[][] rtnArray = manager.getIssueListAsArrayByIssueType("Bug");
		assertEquals(2, rtnArray.length);
		assertEquals(1, rtnArray[0][0]);
		assertEquals("New", rtnArray[0][1]);
		assertEquals("Bug", rtnArray[0][2]);
		assertEquals("summary", rtnArray[0][3]);

		assertEquals(3, rtnArray[1][0]);
		assertEquals("New", rtnArray[1][1]);
		assertEquals("Bug", rtnArray[1][2]);
		assertEquals("summary2", rtnArray[1][3]);
		
		rtnArray = manager.getIssueListAsArray();
		assertEquals(3, rtnArray.length);
		assertEquals(1, rtnArray[0][0]);
		assertEquals("New", rtnArray[0][1]);
		assertEquals("Bug", rtnArray[0][2]);
		assertEquals("summary", rtnArray[0][3]);

		Issue.setCounter(1);
		Issue retIssue = manager.getIssueById(1);
		Issue expIssue = new Issue(Issue.counter, IssueType.BUG, "summary", "note");
		assertEquals(expIssue.toString(), retIssue.toString());
		
		manager.deleteIssueById(0);
		
		manager.saveIssuesToFile("test-files/issueManger.txt");
	}

}
