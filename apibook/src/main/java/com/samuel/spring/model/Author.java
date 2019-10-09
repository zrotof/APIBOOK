package com.samuel.spring.model;

import java.io.Serializable;
import java.util.HashSet;
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
@Table(name ="Author")
public class Author implements Serializable{

	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private long id;
	
	private String author_nom;
	
	private String author_prenom;
	
	@ManyToMany (mappedBy = "authors",
			     cascade = CascadeType.PERSIST,
			     fetch = FetchType.EAGER)
	private Set <Book> books= new HashSet<Book>();

	public Author() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Author(long id, String nom, String prenom) {
		
		this.id=id;
		this.author_nom = nom;
		this.author_prenom = prenom;
		
	}
	
	/*  Ce setter me semble inutile
	public Set<Book> getBooks() {
		return books;
	}
	*/
	

	public void setBooks(Set<Book> books) {
		this.books = books;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAuthor_nom() {
		return author_nom;
	}

	public void setAuthor_nom(String nom) {
		this.author_nom = nom;
	}

	public String getAuthor_prenom() {
		return author_prenom;
	}

	
	public void setAuthor_prenom(String prenom) {
		this.author_prenom = prenom;
	}
	
	
	//methode d'ajout de livre
	public void addBook(Book book) {
		
		this.books.add(book);
		book.getAuthors().add(this);
		
	} 	
		
	@Override
	public String toString() {
		return "Author [Id=" + id + ", nom=" + author_nom + ", prenom=" + author_prenom + "]";
	} 
	
	
	
	
}
