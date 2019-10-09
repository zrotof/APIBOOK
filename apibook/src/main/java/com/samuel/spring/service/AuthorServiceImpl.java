package com.samuel.spring.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samuel.spring.dao.AuthorDAO;
import com.samuel.spring.model.Author;


@Service
public class AuthorServiceImpl implements AuthorService{

	
	
	@Autowired
	private AuthorDAO authorDAO;
	
	
	@Override
	@Transactional
	public long save(Author author) {
		return authorDAO.save(author);
	}

	@Override
	@Transactional
	public Author get(long id) {
		// TODO Auto-generated method stub
		return authorDAO.get(id);
	}

	@Override
	@Transactional
	public List<Author> list() {
		return authorDAO.list();
	}

	@Override
	@Transactional
	public void update(long id, Author author) {

		authorDAO.update(id, author);
	}

	@Override
	@Transactional
	public void delete(long id) {
		authorDAO.delete(id);
	}

}
