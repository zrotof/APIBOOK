package com.samuel.spring.dao;

import com.samuel.spring.model.Genre;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;





@Repository
public class GenreDAOImpl implements GenreDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Long findGenreByLibelle(String libelle) {
		
		String requette= "select id from Genre "+
						 "where genre_libelle =:value";

		
      Query<Long> query = sessionFactory.getCurrentSession().createQuery(requette);
      query.setParameter("value",libelle,org.hibernate.type.StringType.INSTANCE);
		
		return query.uniqueResult();
	}

	@Override
	public long save(Genre genre) {
		
		sessionFactory.getCurrentSession().save(genre);
		
		return genre.getId();
	}

	@Override
	public void delete(long id) {
		Session session = sessionFactory.getCurrentSession();
		
		Genre genre = session.byId(Genre.class).load(id);
		session.delete(genre);
	}


	

}
