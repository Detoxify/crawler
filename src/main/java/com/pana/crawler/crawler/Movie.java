package com.pana.crawler.crawler;

import java.util.List;

public class Movie {

	private String name;
	
	private Float grade;
	
	private List<String> types;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getGrade() {
		return grade;
	}

	public void setGrade(Float grade) {
		this.grade = grade;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	@Override
	public String toString() {
		return "Movie [name=" + name + ", grade=" + grade + ", types=" + types
				+ "]";
	}

	
}
