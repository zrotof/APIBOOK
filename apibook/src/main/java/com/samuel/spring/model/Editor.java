package com.samuel.spring.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="editor")
public class Editor implements Serializable{


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name;
	
	@OneToMany(cascade=CascadeType.ALL,
			mappedBy="editor")
	
	private Set<Book> books= new HashSet<>() ;
	

	public Editor() {
		
	}
	
	
public Editor(String name) {
		
		this.name=name;
		
	}
	
	public Editor(long id,String name) {
		this.id=id;
		this.name=name;
		
	}
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set <Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}
	
	public void addBook(Book book) {
		
		this.books.add(book);
		book.setEditor(this);
		
	}
	
	

}
