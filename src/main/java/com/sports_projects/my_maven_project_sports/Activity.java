package com.sports_projects.my_maven_project_sports;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

public class Activity implements Comparable<Activity> {
	private String name;
	private Collection<Category> categories;
	private List<Product> products;
	
	public Activity(String name) {
		this.name = name;
		categories = new TreeSet<Category>();
		products = new LinkedList<Product>();
	}
	
	public int compareTo(Activity other) {
		return this.name.compareTo(other.name);
	}
	
	public String getName() {
		return name;
	}
	
	public void addCategory(Category c) {
		categories.add(c);
	}
	
	public Collection<Category> getCategories(){
		return categories;
	}
	
	public void addProduct(Product p) {
		products.add(p);
	}
	
	public List<String> getProducts(){
		return products.stream().map(Product::getName).toList();
	}
	
	public List<Product> getProductsObjects(){
		return products;
	}
}
