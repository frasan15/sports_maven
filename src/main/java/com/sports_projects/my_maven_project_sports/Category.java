package com.sports_projects.my_maven_project_sports;

import java.util.LinkedList;
import java.util.List;

public class Category implements Comparable<Category> {
	private String name;
	private List<Product> products;
	
	public Category(String name) {
		this.name = name;
		products = new LinkedList<Product>();
	}
	
	public String getName() {
		return name;
	}
	
	public int compareTo(Category other) {
		return this.name.compareTo(other.name);
	}
	
	public void addProduct(Product p) {
		products.add(p);
	}
	
	public List<String> getProducts(){
		return products.stream().map(Product::getName).toList();
	}
	
	public boolean containsProduct(Product p) {
		return products.contains(p);
	}
}
