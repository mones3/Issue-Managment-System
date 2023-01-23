/**
 * 
 */
package edu.ncsu.csc216.issue_manager.model.io;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.issue_manager.model.issue.Issue;

/**
 * Test IssueTeader class
 * 
 * @author symone
 *
 */
public class IssueReaderTest {


	/**
	 * Testing valid files
	 */
	@Test
	public void testValidFiles() {
		ArrayList<String> note1 = new ArrayList<String>();
		note1.add("[New] Note 1");
		ArrayList<String> note2 = new ArrayList<String>();
		note2.add("[New] Note 1");
		note2.add("[Confirmed] Note 2\nthat goes on a new line");
		ArrayList<String> note3 = new ArrayList<String>();
		note3.add("[New] Note 1");
		note3.add("[Confirmed] Note 2");
		note3.add("[Working] Note 3");
		ArrayList<String> note4 = new ArrayList<String>();
		note4.add("[New] Note 1");
		note4.add("[Working] Note 2\nthat goes on a new line");
		note4.add("[Verifying] Note 3");
		ArrayList<String> note6 = new ArrayList<String>();
		note6.add("[New] Note 1\nthat goes on a new line");
		note6.add("[Working] Note 2");
		note6.add("[Verifying] Note 3");
		note6.add("[Working] Note 4");
		note6.add("[Closed] Note 6");
		List<Issue> expectedList = new ArrayList<Issue>();
		expectedList.add(new Issue(1, "New", "Enhancement", "Issue description", "", false, "", note1));
		expectedList.add(new Issue(3, "Confirmed", "Bug", "Issue description", "", true, "", note2));
		expectedList.add(new Issue(7, "Working", "Bug", "Issue description", "owner", true, "", note3));
		expectedList.add(new Issue(14, "Verifying", "Enhancement", "Issue description", "owner", false, "Fixed", note4));
		expectedList.add(new Issue(15, "Closed", "Enhancement", "Issue description", "owner", false, "WontFix", note6));
		
		List<Issue> actualList = new ArrayList<Issue>();
		actualList = IssueReader.readIssuesFromFile("test-files/issue1.txt");
		assertEquals(expectedList.toString(), actualList.toString());
		
	}

	/**
	 * Testing Invalid files 
	 */
	@Test
	public void testInvalidFiles() {
		try {
			IssueReader.readIssuesFromFile("test-files/issue2.txt");
		} catch (IllegalArgumentException e) {
			assertEquals("Unable to load file", e.getMessage());
		}
		try {
			IssueReader.readIssuesFromFile("test-files/issue3.txt");
		} catch (IllegalArgumentException e) {
			assertEquals("Unable to load file", e.getMessage());
		}
		try {
			IssueReader.readIssuesFromFile("test-files/issue4.txt");
		} catch (IllegalArgumentException e) {
			assertEquals("Unable to load file", e.getMessage());
		}
		try {
			IssueReader.readIssuesFromFile("test-files/issue5.txt");
		} catch (IllegalArgumentException e) {
			assertEquals("Unable to load file", e.getMessage());
		}
		try {
			IssueReader.readIssuesFromFile("test-files/issue6.txt");
		} catch (IllegalArgumentException e) {
			assertEquals("Unable to load file", e.getMessage());
		}
		try {
			IssueReader.readIssuesFromFile("test-files/issue7.txt");
		} catch (IllegalArgumentException e) {
			assertEquals("Unable to load file", e.getMessage());
		}
		try {
			IssueReader.readIssuesFromFile("test-files/issue8.txt");
		} catch (IllegalArgumentException e) {
			assertEquals("Unable to load file", e.getMessage());
		}
		try {
			IssueReader.readIssuesFromFile("test-files/issue9.txt");
		} catch (IllegalArgumentException e) {
			assertEquals("Unable to load file", e.getMessage());
		}
		try {
			IssueReader.readIssuesFromFile("test-files/issue10.txt");
		} catch (IllegalArgumentException e) {
			assertEquals("Unable to load file", e.getMessage());
		}
		try {
			IssueReader.readIssuesFromFile("test-files/issue11.txt");
		} catch (IllegalArgumentException e) {
			assertEquals("Unable to load file", e.getMessage());
		}
		try {
			IssueReader.readIssuesFromFile("test-files/issue12.txt");
		} catch (IllegalArgumentException e) {
			assertEquals("Unable to load file", e.getMessage());
		}
		try {
			IssueReader.readIssuesFromFile("test-files/issue13.txt");
		} catch (IllegalArgumentException e) {
			assertEquals("Unable to load file", e.getMessage());
		}
		try {
			IssueReader.readIssuesFromFile("test-files/issue14.txt");
		} catch (IllegalArgumentException e) {
			assertEquals("Unable to load file", e.getMessage());
		}
		try {
			IssueReader.readIssuesFromFile("test-files/issue15.txt");
		} catch (IllegalArgumentException e) {
			assertEquals("Unable to load file", e.getMessage());
		}
		try {
			IssueReader.readIssuesFromFile("test-files/issue16.txt");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Unable to load file", e.getMessage());
		}
		try {
			IssueReader.readIssuesFromFile("test-files/issue17.txt");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Unable to load file", e.getMessage());
		}
	}

}

