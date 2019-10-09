package com.samuel.spring.service;


import com.samuel.spring.dao.AuthorDAO;
import com.samuel.spring.dao.BookDAO;
import com.samuel.spring.dao.EditorDAO;
import com.samuel.spring.dao.GenreDAO;
import com.samuel.spring.model.Author;
import com.samuel.spring.model.Book;
import com.samuel.spring.model.Editor;
import com.samuel.spring.model.Genre;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;



@Service
public class BookServiceImpl implements BookService{

	@Autowired
	private BookDAO bookDAO;

	@Autowired
	private GenreDAO genreDAO;
	

	@Autowired
	private EditorDAO editorDAO;
	

	@Autowired
	private AuthorDAO authorDAO;

	
	@Override
	@Transactional
	public long save(Book book) {
		
		
		
		


//Gestion de la sauvegardes des auteurs de livre, de l'éditeur, des genres et les infos du livre 	
		
		
		
		
		//Contruction du livre à sauvegarder
		        Book bookToSave = new Book();
		        bookToSave.setDate(book.getDate());
				bookToSave.setImage(book.getImage());
				bookToSave.setPrice(book.getPrice());
				bookToSave.setSummary(book.getSummary());
				bookToSave.setTitle(book.getTitle());	
		        
	       
		
		//Sauvegarder les Authors de livres en base dans la table Authors
		//Les auteurs seront construient et ajoutés au fur et à mesure dans la liste authors
		
		
			for (Author author : book.getAuthors()) {
				
				//Construction de l'auteur suivant l'array d'auteurs recupéré
				Author authorToSave= new Author(author.getId(),author.getAuthor_nom(),author.getAuthor_prenom());
				//Affectation du livre écrit par un auteur 
				authorToSave.addBook(bookToSave);

				authorDAO.save(authorToSave);
			}
			

		//Construction de l'éditeur recupéré par le post		
			
			//On vérifie si on a déjà l'éditeur en base de données on stocke le résultat dans resultszveEditor
			
			Long resultSaveEditor = editorDAO.findEditorByName(book.getEditor().getName());
			
			//Si on n'a pas de résultats donc si la requette renvoit null
			
			
			
			if(resultSaveEditor == null) {
				Editor editorToSave= new Editor(book.getEditor().getName());
			
				editorDAO.save(editorToSave);
				
				editorToSave.addBook(bookToSave);
				
			}
			//Si on a un résultat
			else {
				
				bookToSave.setEditor( new Editor(resultSaveEditor,book.getEditor().getName()));
				
		
			}
			
			

		//Attribuer les genres du livre
			
		
			
			//On crée une liste de genres dans laquelle on va stocker les nouveaux genres pas encore inscrits en BDD s'il existent
			Set<Genre> listGenres = new HashSet<>();
			//Boucle pour parcourir Afficher ce que contient la liste
			for(Genre genre : book.getGenres()) {
				
				//On rcupère la valeur de la requette dans la varible result
				Long resultSaveGenre =genreDAO.findGenreByLibelle(genre.getGenre_libelle());
				
				//Check if a genre alreagy exist and if yes, then we don't save it
				
				if ( resultSaveGenre == null) {
					//Construction du genre suivant l'array de genre récupéré
					
					Genre genreToSave = new Genre(genre.getGenre_libelle());
					
					//On sauvegarde le livre en BDD
					genreDAO.save(genreToSave);
					//Affectation d'un genre à un livre
					genreToSave.addBook(bookToSave); 
					
				}
				else {
					//On constitu la liste des nouveau éléménts à ajouter au livre sans pour autant les créés de nouveau
					bookToSave.getGenres().add(new Genre(resultSaveGenre,genre.getGenre_libelle()));
				}
				
			}
			
	return bookDAO.save(bookToSave);		
		
	}

	@Override
	@Transactional
	public Book get(long id) {
		
		return bookDAO.get(id);
		
	}

	@Override
	@Transactional
	public Set<Book> list() {
		
		
		//Supprimer les doublons de genre en base de données du dernier enregistrement
		//bookDAO.setGoodDataBaseForm();
		
		return bookDAO.list();
		
	}

	@Override
	@Transactional
	public void update(long id, Book book) {
		
		bookDAO.update(id, book);
		
	}

	@Override
	@Transactional
	public void delete(long id) {
		
		bookDAO.delete(id);		
		
	}
	
	

	
	
}
