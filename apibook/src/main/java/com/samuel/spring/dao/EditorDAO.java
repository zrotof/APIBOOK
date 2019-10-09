package com.samuel.spring.dao;

import java.util.List;

import com.samuel.spring.model.Editor;

public interface EditorDAO {

			//Save the record
			long save(Editor editor); 
			
			//Get a single record
			Editor get(long id);
			
			//Get all records
			List <Editor> list();
			
			//Update a record
			void update(long id, Editor editor);
			
			//Delete a record
			void delete(long id);
			
			Long findEditorByName( String name);
		
	
}
