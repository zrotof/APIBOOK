package com.samuel.spring.service;

import java.util.List;
import java.util.Set;

import com.samuel.spring.model.Book;


public interface BookService {

	

		
			//Save the record
			long save(Book book); 
			
			//Get a single record
			Book get(long id);
			
			//Get all records
			Set <Book> list();
			
			//Update a record
			void update(long id, Book book);
			
			//Delete a record
			void delete(long id);
	

	
}
