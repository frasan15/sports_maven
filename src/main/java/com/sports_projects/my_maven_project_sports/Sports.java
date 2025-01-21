package com.sports_projects.my_maven_project_sports;

import java.util.*;
import java.util.stream.Collectors;

 
/**
 * Facade class for the research evaluation system
 *
 */
public class Sports {
	Map<String, Activity> activities = new TreeMap<String, Activity>();
	Map<String, Product> products = new TreeMap<String, Product>();
	Map<String, Category> categories = new TreeMap<String, Category>();
	int categories_counter = 0;

    
    public void defineActivities (String... activities) throws SportsException {
    	if(activities.length == 0) throw new SportsException("no activity provided");
    	Activity activity;
    	for(String a : activities) {
    		activity = new Activity(a);
    		this.activities.put(a, activity);
    	}
    }

    
    public List<String> getActivities() {
    	Set<String> list = activities.keySet();
    	List<String> list1 = list.stream().toList();
        return list1;
    }


   
    public void addCategory(String name, String... linkedActivities) throws SportsException {
    	for (String linkedActivity : linkedActivities) {
    		Activity a = activities.get(linkedActivity);
    		if(a == null) throw new SportsException("${linkedActivity} has not been previously defined");
			Category c = new Category(name);
			a.addCategory(c);
			categories.put(name, c);
    	}
    	categories_counter++;
    }

    
    public int countCategories() {
        return categories_counter;
    }

   
    public List<String> getCategoriesForActivity(String activity) {
    	Activity a = activities.get(activity);
    	if (a==null) return new ArrayList<String>();
    	if (a.getCategories().isEmpty()) return new ArrayList<String>();
    	
    	List<String> list = (List<String>) a.getCategories()
    			.stream()
    			.map(Category::getName)
    			.collect(Collectors.toList());
        return list;
    }

    
    public void addProduct(String name, String activityName, String categoryName) throws SportsException {
    	Product p;
    	if(products.containsKey(name)) throw new SportsException("Product already existing");
    	else {
    		p = new Product(name);
    		products.put(name,  p);
    	}
    	
    	Activity a = activities.get(activityName);
    	Category c = categories.get(categoryName);
    	a.addProduct(p);
    	c.addProduct(p);
    }

    
    public List<String> getProductsForCategory(String categoryName){
    	Category c = categories.get(categoryName);
    	if (c==null) return new ArrayList<String>();
    	if (c.getProducts().isEmpty()) return new ArrayList<String>();
    	
    	List<String> list = new ArrayList<>(c.getProducts());
    	Collections.sort(list);
        return list;
    }

    
    public List<String> getProductsForActivity(String activityName){
    	Activity a = activities.get(activityName);
    	if(a==null) return new ArrayList<String>();
    	if(a.getProducts().isEmpty()) return new ArrayList<String>();
    	
    	List<String> list = new ArrayList<>(a.getProducts());
    	Collections.sort(list);
        return list;
    }

   
    public List<String> getProducts(String activityName, String... categoryNames){
    	Activity a = activities.get(activityName);
    	if(a==null) return new ArrayList<String>();
    	if(a.getProducts().isEmpty()) return new ArrayList<String>();
    	
    	List<String> list = a.getProducts();
    	List<String> sortedList = list.stream()
    	.filter(product -> {
    		Product productObj = products.get(product);
    		for(String category : categoryNames) {
    			Category c = categories.get(category);
    			if(c.containsProduct(productObj)) return true;
    		}
			return false;
    	}).sorted().toList();
        return sortedList;
    }

    
    public void addRating(String productName, String userName, int numStars, String comment) throws SportsException {
    	if(numStars<0 || numStars>5) throw new SportsException("Starts rating must be between 0 and 5");
    	Product p = products.get(productName);
    	Rating r = new Rating(comment, numStars);
    	p.setRating(userName, r);
    }



    
    public List<String> getRatingsForProduct(String productName) {
    	Product p = products.get(productName);
        return p.toStringRating();
    }


    
    public double getStarsOfProduct (String productName) {
    	Product p = products.get(productName);
        return p.averageStars();
    }

    
    public double averageStars() {
    	Collection<Product> p = products.values();
    	return p.stream()
    	.map(Product::getAllRatings)
    	.flatMap(Collection::stream)
    	.mapToDouble(x -> x)
    	.average()
    	.orElse(0.0);
    }

    
    public SortedMap<String, Double> starsPerActivity() {
    	SortedMap<String, Double> result = new TreeMap<String, Double>();
    	Collection<Activity> a = activities.values();
    	a.stream()
    	.forEach(x -> {
    		List<Product> p = x.getProductsObjects();
    		Double average = p.stream()
    		    	.map(Product::getAllRatings)
    		    	.flatMap(Collection::stream)
    		    	.mapToDouble(x1 -> x1)
    		    	.average()
    		    	.orElse(0.0);
    		if(average != 0.0 ) result.put(x.getName(), average);
    	});
    	
        return result;
    }

   
    public SortedMap<Double, List<String>> getProductsPerStars () {
    	SortedMap<Double, List<String>> result = new TreeMap<Double, List<String>>(Comparator.reverseOrder());
    	products.keySet().stream()
    	.filter(x -> this.getStarsOfProduct(x) != -1.0)
    	.forEach(x -> {
            Double stars = this.getStarsOfProduct(x);
            List<String> list = result.computeIfAbsent(stars, k -> new ArrayList<>());
            list.add(x);
        });
        return result;
    }

}