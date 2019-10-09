package com.samuel.spring.dao;



import com.samuel.spring.model.Author;
import com.samuel.spring.model.Book;
import com.samuel.spring.model.Editor;
import com.samuel.spring.model.Genre;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.query.Query;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class BookDAOImpl implements BookDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private GenreDAO genreDAO;
	
	
	@Override
	public long save(Book book) {
		
		 sessionFactory.getCurrentSession().save(book);
		 
		 return book.getId();
	}

	@Override
	public Book get(long id) {
		
		return sessionFactory.getCurrentSession().load(Book.class, id);
	}

	@Override
	public LinkedHashSet<Book> list() {
		
		
		
		String request="select b.id as book_id, g.id as genre_id, a.id as author_id, b.title as book_title, b.summary as book_summary, b.price as book_price, b.date as book_date, b.image as book_image, a.author_nom as author_nom, a.author_prenom as author_prenom, e.id as editor_id, e.name as editor_name,g.genre_libelle as genre_libelle" + 
				" from Book b inner join BOOK_AUTHOR ba on ba.book_id=b.id" + 
				" inner join Author a on ba.author_id=a.id" + 
				" inner join Editor e on b.editor_id = e.id" + 
				" inner join BOOK_GENRE bg on bg.book_id= b.id" + 
				" inner join Genre g on bg.genre_id=g.id"+
				" order by b.id asc ,a.id asc"			
				;
		
		// la précision des scalar permet d'indiquer la valeur du type des colonnes de la requettes 
		Query listQuery= sessionFactory.getCurrentSession().createSQLQuery(request)
				.addScalar("book_id",StandardBasicTypes.LONG)
				.addScalar("book_title",StandardBasicTypes.STRING)
				.addScalar("book_image",StandardBasicTypes.STRING)
				.addScalar("book_summary",StandardBasicTypes.STRING)
				.addScalar("book_price",StandardBasicTypes.DOUBLE)
				.addScalar("book_date",StandardBasicTypes.STRING)
				
				.addScalar("author_id",StandardBasicTypes.LONG)
				.addScalar("author_nom",StandardBasicTypes.STRING)
				.addScalar("author_prenom",StandardBasicTypes.STRING)
				
				.addScalar("genre_id",StandardBasicTypes.LONG)
				.addScalar("genre_libelle",StandardBasicTypes.STRING)
				
				.addScalar("editor_id", StandardBasicTypes.LONG)
				.addScalar("editor_name",StandardBasicTypes.STRING);
		
		
	
		
			listQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			
			
			List allRecords= listQuery.list();

		Iterator iterator0 =allRecords.iterator();
		
		//Ce compteur va nous permettre de savoir si on est au premier résultat de la requette ou non
		boolean compteur=true;
		

		//Déclaration de la liste de livre qui sera retournée
		LinkedHashSet<Book> listOfBooks = new LinkedHashSet<Book>();
		
		//Déclaration de la liste des genres à aujouter au livre
		LinkedHashSet<Genre> listOfGenres = new LinkedHashSet<>();
		
		//Déclaration de la liste des authors à ajouter au livre
		LinkedHashSet<Author> listOfAuthors = new LinkedHashSet<>();
		//Objet nous permettant d'avoir accès aux champs du résultat de la requette
		
		
		
		
		//On déclare le livre qu'on va rajouter à la liste de livre par la suite
		Book bookToSend = new Book();
		
		
		//Pour connaitre l'id précédent, cela nous permmettra de savoir s'il sagit du même livre ou non	
		long idPrecedentLivre= 0;
		//Pour connaitre l'id précédent, cela nous permmettra de savoir s'il sagit du même livre ou non	
		long idPrecedentAuthor=0;
		
		
		
		
		//Déclaration d'une variable qui nous permettra de savoir si on est à la dernière ligne 
		//Du résultat de la requette
		
		int rows=1;
		
		//Tant qu'il ya un prochain élément dans iterator
		while(iterator0.hasNext() ) {
			
			
			Map result =(Map)iterator0.next();			
		
			
			//Si compteur vaut true c'est qu'on est au premier parcours
			if(compteur) {
				
				//Set de l'id du livre
				bookToSend.setId((long)result.get("book_id"));

				//Set du titre du livre			
				bookToSend.setTitle((String)result.get("book_title"));
				
				//Set du résumé dulivre
				bookToSend.setSummary((String)result.get("book_summary"));
				//Set du prix du livre
				bookToSend.setPrice((double)result.get("book_price"));
				//Set de la date du livre
				bookToSend.setDate((String)result.get("book_date"));
				//Set de l'image du livre
				bookToSend.setImage((String)result.get("book_image"));
				
				//Set de l'éditeurdu livre
				bookToSend.setEditor(new Editor((long)result.get("editor_id"),(String)result.get("editor_name")));
				
				//Constitution de la liste des auteurs livre
				//On ajoute l'auteur correspondant à l'enregistrement
				listOfAuthors.add(new Author((long)result.get("author_id"),(String)result.get("author_nom"),(String)result.get("author_prenom")));
				
				
				//Constitution de la liste des genres du livre
				listOfGenres.add(new Genre((long)result.get("genre_id"),(String)result.get("genre_libelle")));
				
				
				
				
				idPrecedentLivre= (long)result.get("book_id");
				idPrecedentAuthor=(long)result.get("author_id");
				
				//On initialise le compteur à zéro pour signifier qu'on n'est plus au premier enregistrement
				compteur=false;				
			}
			else {
				
				//On vérifie si on n'est dans le même livre
				
				if(idPrecedentLivre != (long)(result.get("book_id")) ) {
					
						
						//Si on n'est plus dans le même livre, on enregistre le livre et on set les informations de celui courant
						bookToSend.setAuthors(listOfAuthors);
						bookToSend.setGenres(listOfGenres);
					
						
						
						listOfBooks.add(bookToSend);
						
						
						//On vide les données de l'ancien livre pour le reinitialiser
						bookToSend= new Book();
						
						//Nouveau record 
					
						//Set de l'id du livre
						bookToSend.setId((long)result.get("book_id"));
						//Set du titre du livre
						bookToSend.setTitle((String)result.get("book_title"));
						//Set du résumé dulivre
						bookToSend.setSummary((String)result.get("book_summary"));
						//Set du prix du livre
						bookToSend.setPrice((double)result.get("book_price"));
						//Set de la date du livre
						bookToSend.setDate((String)result.get("book_date"));
						//Set de l'image du livre
						bookToSend.setImage((String)result.get("book_image"));
						
						//Set de l'éditeurdu livre
						bookToSend.setEditor(new Editor((long)result.get("editor_id"),(String)result.get("editor_name")));
						
						
						
						//On vide les listes d'auteurs et genres du livre 
						listOfAuthors.clear();
						listOfGenres.clear();
						
						
						
						//Constitution de la nouvelle liste des auteurs livre
						//On ajoute l'auteur correspondant à l'enregistrement
						listOfAuthors.add(new Author((long)result.get("author_id"),(String)result.get("author_nom"),(String)result.get("author_prenom")));
						
					
						
						//Constitution de la nouvelle liste des genres du livre
						listOfGenres.add(new Genre((long)result.get("genre_id"),(String)result.get("genre_libelle")));
						

						
						//On stocke les nouvelles valeurs des id du livre et de l'auteur
						idPrecedentLivre= (long)result.get("book_id");
						idPrecedentAuthor=(long)result.get("author_id");
						
					}
				
					else {
					
				
						//On vérifie si l'auteur précédent est ègal à l'auteur actuel
						if( idPrecedentAuthor == (long)result.get("author_id") ) {
							//Si oui c'est que c'est seulement le genre qui a changé
							//On ajoute alors le nouveau genre à la liste des genres
							
							listOfGenres.add(new Genre((long)result.get("genre_id"),(String)result.get("genre_libelle")));
							
		
						}
						
						//Et si l'auteur a changé
						else {
							
							//Si oui on ajoute le nouvel auteur or comme le résultat de la requêtte est trié sur l'id de l'auteur
							//On ne se soucis pas de savoir le genre correspondant
							
							listOfAuthors.add(new Author((long)result.get("author_id"),(String)result.get("author_nom"),(String)result.get("author_prenom")));
							
							//on retient la valeur de l'ancien auteur
							idPrecedentAuthor=(long)result.get("author_id");
						}
					}
			}
			
			if( allRecords.size()== rows) {
				
				
				bookToSend.setAuthors(listOfAuthors);
				bookToSend.setGenres(listOfGenres);
				
				
				listOfBooks.add(bookToSend);
				
				System.out.println(bookToSend);
				
			}
			
			rows++;
			
		}
		
		
		
		return listOfBooks;
	}
	
	
	@Override
	public void update(long id, Book book) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Book oldBook = session.byId(Book.class).load(id);
		
		oldBook.setTitle(book.getTitle());
		oldBook.setImage(book.getImage());
		oldBook.setPrice(book.getPrice());
		oldBook.setSummary(book.getSummary());
		
		oldBook.setGenres(book.getGenres());
		oldBook.setDate(book.getDate());
		session.flush();
		
	}

	@Override
	public void delete(long id) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Book book = session.byId(Book.class).load(id);
		
		session.delete(book);
		
	}
	
	
	
	
	
	//POur l'instant l'enegistrement des genres n'est pas optimale du coup je vais reformater les tables
	//de façon à obternir des enregistrements uniques
	//Il sagit des tables genre et book_genre
	
	
	//sélectionner les genres déjà enregistrer en cas de doublon on supprime les doublons et on set l'id
	//de la table book_genre avec l'id de la première occurrence du genre 
	
	
	
	@SuppressWarnings("deprecation")
	@Override
	public void setGoodDataBaseForm() {
		
		//Définition de la requête
		String query1= "select g.id as genre_id , g.libelle as genre_libelle from Genre as g";

		Query query= sessionFactory.getCurrentSession().createQuery(query1);
			
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List list= query.list();
			
			Iterator iterate1= list.iterator();
			Iterator iterate2= list.iterator();
			
			while(iterate1.hasNext()) {
				
				//On stocke les valeurs des des id des occurrences
				List<Long> idOccurence = new ArrayList<>();
				
				
				
				//On map premier pour pouvoir recupérer les valeur via les alias définies
				Map premier =(Map)iterate1.next();
				//System.out.println("Le premier"+premier);
				while(iterate2.hasNext()) {
					
					//On map second pour pouvoir recupérer les valeurs via les alias définies
					//System.out.println("Seconde iteration"+(Map)iterate2.next());
					
					
					Map second = (Map)iterate2.next();
					
					//System.out.println("Le deuxième"+second);
					if( premier.get("genre_libelle").equals(second.get("genre_libelle")) && premier.get("genre_id") != second.get("genre_id")) {
						
						
						idOccurence.add((long)second.get("genre_id"));
						//System.out.println("on est dans le if");
						
					}
					

					//Une fois sortie de la boucle for on a toutes les occurrences d'un genre
					//Si cette liste est vide on ne fait rien sinon on modifie la table association
					//en settant l'ancienne id de l'occurence 
					
					//Table association
					//On supprime l'enregistrement correspondant l'id de l'occurence 
					
					System.out.println("avant le if ça a marché");
					System.out.println(idOccurence.isEmpty());
					if(!idOccurence.isEmpty()) {
						
					String updateTableAssociation="update BOOK_GENRE set genre_id=:value1 where genre_id=:value2";
					Query query2 = sessionFactory.getCurrentSession().createSQLQuery(updateTableAssociation);
					query2.setParameter("value1",(long)premier.get("genre_id"));
	     			query2.setParameter("value2",idOccurence.get(0));
	     			query2.executeUpdate();
	     			
	     			System.out.println("ça n'a pas marché");
	     			
	     			//On supprime l'occurence dans la table genre
						
						genreDAO.delete(idOccurence.get(0));
	     			String deleteGenre = "delete from Genre where id=: value3";
	     			
	     			Query query3 = sessionFactory.getCurrentSession().createQuery(deleteGenre);
	     			query3.setParameter("value3",idOccurence.get(0));
	     			query3.executeUpdate();
	   
	     			
					}
				}

					
					
				}
	}
	}
				
				
			
			
		
	
