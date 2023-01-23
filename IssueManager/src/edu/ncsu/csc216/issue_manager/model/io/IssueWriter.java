/**
 * 
 */
package edu.ncsu.csc216.issue_manager.model.io;

import java.io.PrintStream;


import java.util.List;

import edu.ncsu.csc216.issue_manager.model.issue.Issue;

/**
 * 
 * IssueWriter has one public method writeIssuesToFile that receives 
 * a String with the file name to write to and a List of Issues to write. 
 * 
 * @author symone
 *
 */
public class IssueWriter {
	
	/**
	 * Receives a String with the file name to write to and a List of Issues to write
	 * IssueWriter uses Issue’s toString() method to create the properly 
	 * formatted output for a Issue. If there are any errors, an IllegalArgumentException 
	 * is thrown with the message “Unable to save file.”
	 * @param fileName file name to write to
	 * @param issues list of issues to write
	 * @throws IllegalArgumentException If any error occurs
	 */
	public static void writeIssuesToFile(String fileName, List<Issue> issues) {
		try {
			PrintStream fileWriter = new PrintStream(fileName);
			
			for (int i = 0; i < issues.size(); i++) {
				fileWriter.print(issues.get(i).toString());
				
			}
			fileWriter.close();
		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to save file");
		}
	}
}
