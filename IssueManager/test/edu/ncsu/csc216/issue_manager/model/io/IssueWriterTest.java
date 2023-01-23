/**
 * 
 */
package edu.ncsu.csc216.issue_manager.model.io;

import static org.junit.jupiter.api.Assertions.*;



import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.issue_manager.model.issue.Issue;
import edu.ncsu.csc216.issue_manager.model.issue.Issue.IssueType;


/**
 * Test IssueWriter class
 * 
 * @author symone
 *
 */
public class IssueWriterTest {

	/**
	 * Test writing to file
	 */
	@Test
	public void testWriteToFile() {
		

		ArrayList<String> note1 = new ArrayList<String>();
		ArrayList<String> note2 = new ArrayList<String>();
		note1.add("[New] note 1");
		note2.add("[New] note 1");
		note2.add("[Confirmed] note 2");
		note2.add("[Closed] note 3");
		
		Issue.setCounter(1);
		Issue newEnhancement = new Issue(1, IssueType.ENHANCEMENT, "Issue description", "note");
	
		assertEquals("Enhancement", newEnhancement.getIssueType());

		
		ArrayList<Issue> testIssue = new ArrayList<Issue>();
		
		testIssue.add(0, newEnhancement);
		IssueWriter.writeIssuesToFile("test-files/act_enhancement_new.txt", testIssue);
		checkFiles("test-files/exp_enhancement_new.txt", "test-files/act_enhancement_new.txt");
		
		
		
	}
	/**
	 * Helper method to compare two files for the same contents
	 * 
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try (Scanner expScanner = new Scanner(new FileInputStream(expFile));
				Scanner actScanner = new Scanner(new FileInputStream(actFile));) {

			while (expScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}

			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}

}
