/**
 * 
 */
package edu.ncsu.csc216.issue_manager.model.manager;


import java.util.List;

import edu.ncsu.csc216.issue_manager.model.command.Command;
import edu.ncsu.csc216.issue_manager.model.io.IssueReader;
import edu.ncsu.csc216.issue_manager.model.io.IssueWriter;
import edu.ncsu.csc216.issue_manager.model.issue.Issue;
import edu.ncsu.csc216.issue_manager.model.issue.Issue.IssueType;

/**
 * IssueManager implements the Singleton design pattern and controls 
 * the creation and modification of IssueLists
 * This class works with the files that contain the saved Issue. Also, 
 * provides information to the GUI through methods like getIssueListAsArray(), 
 * getIssueListAsArrayByIssueType(String issueType), and getIssueById().
 * 
 * @author symone
 *
 */
public class IssueManager {
	
	/** The list of issue lists */
	public IssueList issueList;

	/** Instance variable for IssueManager */
	private static IssueManager singleton = null;

	/**
	 * Returns an instance of IssueManager
	 * @return instance of IssueManager
	 */
	public static IssueManager getInstance() {
		if (singleton == null) {
			singleton = new IssueManager();
		}
		return singleton;
	}
	
	/**
	 * Creates a new list containing issue objects
	 */
	public void createNewIssueList() {
		this.issueList = new IssueList();
		
	}
	
	/**
	 * Receives a filename and loads a list issues from file
	 * @param fileName the file name to load Issues to
	 */
	public void loadIssuesFromFile(String fileName) {
		IssueList issueLis = new IssueList();
		issueLis.addIssues(IssueReader.readIssuesFromFile(fileName));
		this.issueList = issueLis;
		
	}
	
	/**
	 * Saves list of Issues to file
	 * @param fileName to save list of issues to
	 */
	public void saveIssuesToFile(String fileName) {
		List<Issue> issuesToWrite = issueList.getIssues();
		IssueWriter.writeIssuesToFile(fileName, issuesToWrite);
	}
		
	
	
	/**
	 * Returns 2D Object array of Issue list.
	 * The 2D Object array stores [rows][columns]. 
	 * The array has 1 row for every Issue that is returned. 
	 * There are 4 columns: Index 0 - Issue’s id number, Index 1 -Issue’s state name, 
	 * Index 2 - Issue’s issue type, Index 3 - Issue’s summary
	 * @return 2D array object of Issue list
	 */
	public Object[][] getIssueListAsArray() {
		if(issueList == null) {
			issueList = new IssueList();
		}
		Object[][] rtnArray = new Object[issueList.getIssues().size()][4];
		for (int i = 0; i < issueList.getIssues().size(); i++) {
			Issue issue = issueList.getIssues().get(i);
			for (int j = 0; j < 4; j++) {
				if (j == 0) {
					rtnArray[i][j] = issue.getIssueId();
				} else if (j == 1) {
					rtnArray[i][j] = issue.getStateName();
				} else if (j == 2) {
					rtnArray[i][j] = issue.getIssueType();
				} else if (j == 3) {
					rtnArray[i][j] = issue.getSummary();
				}
			}
		}
		return rtnArray;
	}

	
	/**
	 * Returns Object 2D array using Issue type
	 * @param issueType the Issue's type 
	 * @return the Issue with the same type as the parameter given.
	 */
	public Object[][] getIssueListAsArrayByIssueType(String issueType) {
		if (issueType == null) {
			throw new IllegalArgumentException("Issue type is null");
		}
		
		List<Issue> issueSameType = issueList.getIssuesByType(issueType);
		//System.out.print(issueSameType);
		int size = issueSameType.size();
		//System.out.print(size);
		
		Object[][] rtnArray = new Object[size][4];
		
		IssueList issueListSame = new IssueList();
		issueListSame.addIssues(issueSameType);
	
		for (int i = 0; i < issueListSame.getIssues().size(); i++) {
			Issue issue = issueListSame.getIssues().get(i);
	
			for (int j = 0; j < 4; j++) {
				if (j == 0) {
					rtnArray[i][j] = issue.getIssueId();
				} else if (j == 1) {
					rtnArray[i][j] = issue.getStateName();
				} else if (j == 2) {
					rtnArray[i][j] = issue.getIssueType();
				} else if (j == 3) {
					rtnArray[i][j] = issue.getSummary();
				}
			} 
		
			
	}
		
		
		//System.out.print(Arrays.deepToString(rtnArray));
		return rtnArray;
	}

	
	/**
	 * Returns Issue by id. Used to find an issue by its id
	 * @param issueId Issue's is to be returned
	 * @return Issue with same issueId given
	 */
	public Issue getIssueById(int issueId) {
		if(issueList == null) {
			issueList = new IssueList();
		}
		return this.issueList.getIssueById(issueId);
	}
	
	
	/**
	 * Executes the command
	 * @param issueId issues's id to be executed
	 * @param c command to be executed
	 */
	public void executeCommand(int issueId, Command c) {
		if(issueList == null) {
			issueList = new IssueList();
		}
		this.issueList.executeCommand(issueId, c);
	}

		
	
	
	/**
	 * Adds Issue to List
	 * @param type Issue's type to be added to list
	 * @param summary Issue's summary to be added to list
	 * @param note Issue's note to be added to list
	 */
	public void addIssueToList(IssueType type, String summary, String note) {
		if(issueList == null) {
			issueList = new IssueList();
		}
		this.issueList.addIssue(type, summary, note);
	}
	
	
	/**
	 * Deletes Issue by Issue Id
	 * @param issueId the Issue's id to be deleted
	 */
	public void deleteIssueById(int issueId) {
		if(issueList == null) {
			issueList = new IssueList();
		}
		this.issueList.deleteIssueById(issueId);
	}

}
