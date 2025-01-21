package com.sports_projects.my_maven_project_sports;

@SuppressWarnings("serial")
public class SportsException extends Exception {
	public SportsException (String reason) {
		super(reason);
	}
}