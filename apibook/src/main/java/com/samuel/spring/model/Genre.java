package com.samuel.spring.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="Genre")
public class Genre implements Serializable{
	




	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String genre_libelle;
	

	@ManyToMany (mappedBy = "genres",
		     fetch = FetchType.LAZY)
	private Set <Book> books= new HashSet<Book>();
	


	public Genre() {
		
	}
	
	public Genre(String libelle) {

		this.genre_libelle = libelle;
	}
	
	public Genre(long id, String genre_libelle) {

		this.id=id;
		this.genre_libelle = genre_libelle;
	}
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getGenre_libelle() {
		return this.genre_libelle;
	}

	public void setGenre_libelle(String libelle) {
		this.genre_libelle = libelle;
	}
	
	
	
	
	//methode d'ajout de livre
	public void addBook(Book book) {
		
		this.books.add(book);
		book.getGenres().add(this);
	} 	
	
	
	@Override
	public String toString() {
		return "Genre [Id=" + id + ", libelle=" + genre_libelle + "]";
	} 
	


}
