package com.samuel.spring.dao;

import com.samuel.spring.model.Genre;

public interface GenreDAO {
	
	//Recherche de l'id du genre par son libelle
	Long findGenreByLibelle(String libelle);
	
	//Sauvegarder les genres
	long save(Genre genre); 
	
	//Delete a genre
	void delete(long id);
		
}
