/**
 * 
 */
package edu.ncsu.csc216.issue_manager.model.io;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import edu.ncsu.csc216.issue_manager.model.issue.Issue;


/**
 * 
 * This class has one public method readIssuesFromFile that 
 * receives a String with the file name to read from. 
 * The class will use the Issue(id, state, issueType, summary, owner, confirmed, resolution, notes) 
 * constructor to create a Issue object from the file input. 
 * 
 * @author symone
 *
 */
public class IssueReader {
	
	/**
	 * Receives a String with the file name to read from. If there are any errors 
	 * when processing the file this method will throw an 
	 * IllegalArgumentException with the message “Unable to load file.”
	 * @param fileName file name to read from
	 * @return a list of issues
	 * @throws IllegalArgumentException if there are errors when processing file.
	 */
	public static List<Issue> readIssuesFromFile(String fileName){
		try {
			Scanner fileReader = new Scanner(new FileInputStream(fileName));
			String fileContents = fileReader.nextLine();
			List<Issue> issues = new ArrayList<Issue>();
			while (fileReader.hasNextLine()) {
				fileContents += "\n" + fileReader.nextLine();
			}
			Scanner stringScan = new Scanner(fileContents);
			stringScan.useDelimiter("\\r?\\n?[*]");
			while (stringScan.hasNext()) {
				issues.add(processIssue(stringScan.next()));
			}
			stringScan.close();
			fileReader.close();
			return issues;
		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to load file");
		}
	}


		
	
	
	/**
	 * This method receives a String which is a line from the input file. A Scanner
	 * is used to process the String parameter. The string is separated into tokens
	 * by using the comma character as a delimiter.
	 * @param line a string object containing the whole file 
	 * @return issue an issue string object 
	 */
	private static Issue processIssue(String line) {
		
		Scanner lineScan = new Scanner(line);
		int counter = 0;
		int id = 0;
		String state = null;
		String issueType = null;
		String summary = null;
		String owner = null;
		String stringConfirmed;
		boolean confirmed = false;
		String resolution = null;
		ArrayList<String> notes = new ArrayList<String>();
		lineScan.useDelimiter( "[,]");
		while (lineScan.hasNext()) {

			switch (counter) {
			case 0:
				String s = lineScan.next();
				id = Integer.parseInt(s);
				counter++;
				break;
			case 1:
				state = lineScan.next();
				counter++;
				break;
			case 2:
				issueType = lineScan.next();
				counter++;
				break;
			case 3:
				summary = lineScan.next();
				counter++;
				break;
			case 4:
				owner = lineScan.next();
				counter++;
				break;
			case 5:
				stringConfirmed = lineScan.next();
				if (Objects.equals(stringConfirmed, "true")) {
					confirmed = true;
				}
				counter++;
				break;
			case 6:
				lineScan.useDelimiter("\\r?\\n[-]");
				resolution = lineScan.next();
				if (",".equals(resolution)) {
					resolution = "";
				} else {
					resolution = resolution.substring(1);
				}
				while (lineScan.hasNext()) {
					notes.add(lineScan.next());
				}
				counter++;
				break;
				
			default:
				break;
			}
		}
		lineScan.close();
		Issue rtnIssue = new Issue(id, state, issueType, summary, owner, confirmed, resolution, notes);
		return rtnIssue;
	}
}
