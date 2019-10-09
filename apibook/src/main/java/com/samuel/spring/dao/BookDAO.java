package com.samuel.spring.dao;

import com.samuel.spring.model.Book;

import java.util.LinkedHashSet;



public interface BookDAO {

	
	//Save the record
	long save(Book book); 
			
	//Get a single record
	Book get(long id);
			
	//Get all records
	LinkedHashSet<Book> list();
	//Update a record 
			
	void update(long id, Book book);
			
	//Delete a record
	void delete(long id);
	
	//Modifier les tables pour éliminer les doublons
	void setGoodDataBaseForm();
	 
}
