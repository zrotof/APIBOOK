package com.samuel.spring.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.samuel.spring.model.Editor;

@Repository
public class EditorDAOImpl implements EditorDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public long save(Editor editor) {
		
		sessionFactory.getCurrentSession().save(editor);
		
		return editor.getId();
	}

	@Override
	public Editor get(long id) {
		
		return sessionFactory.getCurrentSession().get(Editor.class, id);
	}

	@Override
	public List<Editor> list() {
		
		List <Editor> list = sessionFactory.getCurrentSession().createQuery("from Editor").list();
		
		return list;
	}

	@Override
	public void update(long id, Editor editor) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Editor oldEditor= session.byId(Editor.class).load(id);
		
		oldEditor.setName(editor.getName());
		oldEditor.setBooks(editor.getBooks());
		
	}

	@Override
	public void delete(long id) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Editor editor = session.byId(Editor.class).load(id);
		session.delete(editor);
		
	}

	@Override
	public Long findEditorByName(String nom) {
		String requette= "select id from Editor where name =:value";
		
		
	Query<Long> query = sessionFactory.getCurrentSession().createQuery(requette);
	query.setParameter("value",nom,org.hibernate.type.StringType.INSTANCE);
	
	return query.uniqueResult();
	}



}
