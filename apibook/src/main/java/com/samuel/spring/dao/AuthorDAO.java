package com.samuel.spring.dao;

import java.util.List;

import com.samuel.spring.model.Author;

public interface AuthorDAO {
	
	
	//Save the record
	long save(Author author); 
	
	//Get a single record
	Author get(long id);
	
	//Get all records
	List <Author> list();
	
	//Update a record
	void update(long id, Author author);
	
	//Delete a record
	void delete(long id);
	
	

}
