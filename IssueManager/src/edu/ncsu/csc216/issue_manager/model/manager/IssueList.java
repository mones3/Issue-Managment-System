
package edu.ncsu.csc216.issue_manager.model.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.ncsu.csc216.issue_manager.model.command.Command;
import edu.ncsu.csc216.issue_manager.model.issue.Issue;
import edu.ncsu.csc216.issue_manager.model.issue.Issue.IssueType;

/**
 * Concrete class that maintains a current list of Issues in the Issue Manager
 * system. These issues are ordered by id and duplicate ids are not allowed. A
 * IssueList maintains a List of Issues. IssueList also maintains a counter
 * which represents the id of the next Issue added to the list.
 * 
 * @author symone
 *
 */
public class IssueList {

	/** The list of issues */
	private List<Issue> issues;

	/**
	 * Constructor for list of issues
	 */
	public IssueList() {
		this.issues = new ArrayList<Issue>();
		Issue.setCounter(1);

	}

	/**
	 * Adds an issue to list with its type, summary, and owner
	 * 
	 * @param issueType issue's type to be listed to be added to list
	 * @param summary   issue's summary to be added to list
	 * @param note      issue's note to be added to list
	 * @return counter number of issues in list
	 */
	public int addIssue(IssueType issueType, String summary, String note) {

		Issue newIssue = new Issue(Issue.counter, issueType, summary, note);
		addIssue(newIssue);

		return newIssue.getIssueId();

	}

	/**
	 * Adds Issue object to list
	 * 
	 * @param issue object to be added to list
	 */
	private void addIssue(Issue issue) {
		issues.add(issue);
	}

	/**
	 * Adds a collection of Issues to the list
	 * 
	 * @param issues collection of Issues to be added to list
	 */
	public void addIssues(List<Issue> issues) {
		int maxId = 0;
		this.issues.clear();
		for (int i = 0; i < issues.size(); i++) {

			if (maxId < issues.get(i).getIssueId()) {
				maxId = issues.get(i).getIssueId();
			}

			Collections.sort(issues, (o1, o2) -> o1.getIssueId() - o2.getIssueId());

			this.issues.add(issues.get(i));

			for (int j = i + 1; j < issues.size(); j++) {

				if (issues.get(i).getIssueId() == issues.get(j).getIssueId()) {

					issues.remove(issues.get(j));
				}
			}
		}

		Issue.setCounter(maxId + 1);
	}

	/**
	 * Returns list of Issues
	 * 
	 * @return list of issues to be returned
	 */
	public List<Issue> getIssues() {
		return this.issues;

	}

	/**
	 * Returns list of Issues by type
	 * 
	 * @param type Issue's type to be returned
	 * @return list of issues to be returned by type
	 * @throws IllegalArgumentException if type is null
	 */
	public List<Issue> getIssuesByType(String type) {
		if (type == null) {
			throw new IllegalArgumentException();
		}
		String issueType;
		ArrayList<Issue> rtnIssues = new ArrayList<Issue>();
		for (int i = 0; i < issues.size(); i++) {
			issueType = issues.get(i).getIssueType();
			if (issueType == type) {
				rtnIssues.add(issues.get(i));
			}
		}
		return rtnIssues;
	}

	/**
	 * Returns Issue by id
	 * 
	 * @param id the id of issue to be returned
	 * @return id of Issue to be returned
	 */
	public Issue getIssueById(int id) {
		for (int i = 0; i < issues.size(); i++) {
			if (issues.get(i).getIssueId() == id) {
				return issues.get(i);
			}
		}
		return null;
	}

	/**
	 * Updating a Issue in the list through execution of a Command
	 * 
	 * @param id      of Issue to be updated
	 * @param command used to update Issue
	 */
	public void executeCommand(int id, Command command) {
		if (id >= 0) {
			Issue issue0 = this.getIssueById(id);
			int index = issues.indexOf(issue0);
			if (index >= 0) {
				issues.get(index).update(command);
			}
		}
	}

	/**
	 * Removes Issue by id
	 * 
	 * @param id of issue to be removed
	 */
	public void deleteIssueById(int id) {
		if (id >= 0) {
			Issue issue0 = this.getIssueById(id);
			int index = issues.indexOf(issue0);
			if (index >= 0) {
				issues.remove(index);
			}
		}
	}

}
