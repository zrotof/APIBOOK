package com.samuel.spring.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Book")
public class Book implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	
	private String image;
	
	private double price;
	
	private String summary;
	
	private String title;
	
	 
	private String date ;
	
	@ManyToOne
	private Editor editor;
	
	@ManyToMany(fetch = FetchType.LAZY)
		@JoinTable(name="BOOK_GENRE",
		joinColumns=@JoinColumn(name="BOOK_ID"),
		inverseJoinColumns = @JoinColumn(name="GENRE_ID"))
	
	private Set<Genre> genres= new HashSet<>();
	
	
	@ManyToMany(cascade = CascadeType.ALL)
		@JoinTable(name = "BOOK_AUTHOR",
		joinColumns = @JoinColumn(name="BOOK_ID"),
		inverseJoinColumns = @JoinColumn(name="AUTHOR_ID"))
	private Set <Author> authors= new HashSet<>();
	
	
	
	public Book() {
		
	}
	
	public Book(String image, double price, String summary, String title, String date, Editor editor) {
		
		this.image = image;
		this.price = price;
		this.summary = summary;
		this.title = title;
		this.date = date;
		this.editor = editor;

	}
	
public Book(String image, double price, String summary, String title, String date, Editor editor, Set<Author> authors, Set<Genre> genres) {
		
		this.image = image;
		this.price = price;
		this.summary = summary;
		this.title = title;
		this.date = date;
		this.editor = editor;
		this.authors=authors;
		this.genres=genres;

	}
	
	
	
	
	
	
	public Set<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<Author> authors) {
		this.authors=authors;
	}

	public Set<Genre> getGenres() {
		return genres;
	}

	public void setGenres(Set<Genre> genres) {
		this.genres = genres;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
	this.id = id;
	}


	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		
		this.date = date;
	}

	public Editor getEditor() {
		return editor;
	}

	public void setEditor(Editor editor) {
		this.editor = editor;
	}
	
	
	@Override
	public String toString() {
		return "Book [Id=" + id + ", titre=" + title + " auteurs="+authors+" genres="+genres+"]";
	} 
}
