package com.sports_projects.my_maven_project_sports;


public class Rating implements Comparable<Rating>{
	private String comment;
	private int numStars;
	
	public Rating(String comment, int numStars) {
		this.comment = comment;
		this.numStars = numStars;
	}
	
	public String getComment() {
		return comment;
	}
	
	public int getNumStars() {
		return numStars;
	}
	
	public int compareTo(Rating r) {
		return Double.compare(r.numStars, this.numStars);
	}
	
	public String toString() {
		return numStars + " : " + comment;
	}
}
