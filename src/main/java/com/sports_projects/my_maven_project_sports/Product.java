package com.sports_projects.my_maven_project_sports;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Product {
	private String name;
	private Map<String, Rating> rating;
	private int numStars;
	private String comment;
	
	public Product(String name) {
		this.name = name;
		rating = new HashMap<String, Rating>();
	}
	
	public String getName() {
		return name;
	}
	
	public void setRating(String user, Rating r) {
		rating.put(user, r);
	}
	
	public Collection<Integer> getAllRatings(){
		Collection<Rating> r = rating.values();
		return r.stream()
		.map(Rating::getNumStars)
		.collect(Collectors.toList());
	}
	
	public List<String> toStringRating() {
		if(rating.isEmpty()) return List.of();
		Collection<Rating> r = rating.values();
		List<String> result = r.stream()
		.sorted()
		.map(Rating::toString)
		.toList();
		return result;
	}
	
	public double averageStars() {
		Collection<Rating> r = rating.values();
		return r.stream()
		.mapToDouble(Rating::getNumStars)
		.average()
		.orElse(-1.0);
		
	}
}
