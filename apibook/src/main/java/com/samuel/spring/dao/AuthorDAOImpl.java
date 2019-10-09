package com.samuel.spring.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.samuel.spring.model.Author;

@Repository
public class AuthorDAOImpl implements AuthorDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public long save(Author author) {
		sessionFactory.getCurrentSession().save(author);
		
		return author.getId(); 
	}

	@Override
	public Author get(long id) {
		
		return sessionFactory.getCurrentSession().get(Author.class, id);
	}

	@Override
	public List<Author> list() {
		
		List <Author> list = sessionFactory.getCurrentSession().createQuery("from Author").list();
				return list;
	}

	@Override
	public void update(long id, Author author) {
		
		Session session= sessionFactory.getCurrentSession();
		
		Author oldAuthor = session.byId(Author.class).load(id);
		
		oldAuthor.setAuthor_nom(author.getAuthor_nom());
		oldAuthor.setAuthor_prenom(author.getAuthor_prenom());
		session.flush();
	}

	@Override
	public void delete(long id) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Author author = session.byId(Author.class).load(id);
		session.delete(author);
		
	}

}
