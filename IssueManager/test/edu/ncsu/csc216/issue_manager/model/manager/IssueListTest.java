/**
 * 
 */
package edu.ncsu.csc216.issue_manager.model.manager;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.issue_manager.model.command.Command;
import edu.ncsu.csc216.issue_manager.model.command.Command.CommandValue;
import edu.ncsu.csc216.issue_manager.model.issue.Issue;
import edu.ncsu.csc216.issue_manager.model.issue.Issue.IssueType;

/**
 * Test IssueList class
 * @author symone
 *
 */
public class IssueListTest {

	/**
	 * Test for the issue list class
	 */
	@Test
	public void testIssueList() {
		
		
		List<Issue> expIssueList = new ArrayList<Issue>();
		Issue bug = new Issue(1, IssueType.BUG, "Summary", "note");
		expIssueList.add(bug);
		
		
		IssueList testList = new IssueList();
		testList.addIssue(IssueType.BUG, "Summary", "note");
		testList.addIssue(IssueType.ENHANCEMENT, "Summary", "note");
		//assertEquals(2, Issue.counter);
		
		testList.deleteIssueById(1);
		testList.deleteIssueById(1);
		Issue.setCounter(0);
		
		testList.addIssues(expIssueList);
		
		List<Issue> returnedIssueList;
		returnedIssueList = testList.getIssuesByType("Bug");
		assertEquals(expIssueList.toString(), returnedIssueList.toString());
		testList.deleteIssueById(1);
		
		testList = new IssueList();
		testList.addIssue(IssueType.BUG, "Summary", "note");
		
		Command c = new Command(CommandValue.CONFIRM, "", null, "note 1");
		testList.executeCommand(1, c);
		bug.update(c);
		Issue retIssue = testList.getIssueById(1);
		assertEquals(bug.toString(), retIssue.toString());
		
	}

}