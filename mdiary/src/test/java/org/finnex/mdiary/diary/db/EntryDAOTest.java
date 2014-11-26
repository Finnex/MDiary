/**
 * 
 */
package org.finnex.mdiary.diary.db;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.finnex.mdiary.diary.entry.Entry;
import org.finnex.mdiary.diary.entry.EntryBuilder;
import org.finnex.mdiary.diary.entry.EntryBuilderException;
import org.finnex.mdiary.diary.entry.EntryField;
import org.finnex.mdiary.diary.header.MovieHeader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author finneon
 *
 */
public class EntryDAOTest extends AbstractDBUnitTest<Entry>
{
	public EntryDAOTest() throws SQLException, FileNotFoundException
	{
		super("org.sqlite.JDBC", "jdbc:sqlite:md.db");
	}
	
	
	@Before
	public void prepareForSelectAll() throws SQLException, ClassNotFoundException
	{
		executeInsert("insert into movie (id, title, description, feedback, genre, stars)" +
				"values (1, 'Spiderman', 'Man with spider power.', 'good movie', 'action, romance', 4)");
	
		executeInsert("insert into movie (id, title, description, feedback, genre, stars)" +
				"values (2, 'Hulk', 'Man with giant power.', 'nice movie', 'action', 3)");
	
		executeInsert("insert into movie (id, title, description, feedback, genre, stars)" +
				"values (3, 'Iron Man', 'Man of iron.', 'great movie', 'action, humor', 5)");
	}
	
	@After
	public void RollBackSelect() throws SQLException, ClassNotFoundException
	{
	    clearTable("movie");
	    getController().commit();
	}
	

	private DBController getDB() throws SQLException, FileNotFoundException
	{
		return new DBController("org.sqlite.JDBC", "jdbc:sqlite:md.db");
	}
	

	@Test
	public void testSelectMovie() throws FileNotFoundException, SQLException
	{
		EntryDAO dao = new EntryDAO(getDB(), "movie");
		Entry e = dao.getEntityById(1);
		
		MovieHeader header = MovieHeader.getInstance();
		assertThat(e.getId(), equalTo((long)1));
		assertThat(e.getField(header.getColumnByName("Title")).getValue().toString(), equalTo("Spiderman"));
		assertThat(e.getField(header.getColumnByName("Description")).getValue().toString(), equalTo("Man with spider power."));
		assertThat(e.getField(header.getColumnByName("Feedback")).getValue().toString(), equalTo("good movie"));
		assertThat(e.getField(header.getColumnByName("Genre")).getValue().toString(), equalTo("action, romance"));
		assertThat(e.getField(header.getColumnByName("Stars")).getValue().toString(), equalTo("4"));
	}

	
	@Test
	public void testSelectNotExistingMovie() throws FileNotFoundException, SQLException
	{
		EntryDAO dao = new EntryDAO(getDB(), "movie");
		Entry e = dao.getEntityById(4);
		
		assertThat(e, is(nullValue()));
	}
	
	
	@Test
	public void testSelectAllMovies() throws FileNotFoundException, SQLException
	{
		EntryDAO dao = new EntryDAO(getDB(), "movie");
		List<Entry> eList = dao.getAllEntities();
		
		assertThat(eList.size(), is(3));
	}
	
	
	@Test
	public void testInsertMovie() throws FileNotFoundException, SQLException, EntryBuilderException
	{
		EntryDAO dao = new EntryDAO(getDB(), "movie");
		MovieHeader header = MovieHeader.getInstance();
		
		Entry e = new EntryBuilder(MovieHeader.getInstance())
			.addField("Title", new EntryField<String>("Film"))
			.addField("Description", new EntryField<String>("A Film"))
			.addField("Feedback", new EntryField<String>("A nice Film"))
			.addField("Genre", new EntryField<String>("Action"))
			.addField("Stars", new EntryField<Integer>(5))
			.build();
		
		Long key = dao.create(e );
		
		assertThat(key, is(e.getId()));
		
		ResultSet existingMovies = executeSelect("select * from movie where id = "+
				e.getId());
		
		while(existingMovies.next())
		{
			assertThat(existingMovies.getInt("id"), is(not(0)));
			
			String title = e.getField(header.getColumnByName("Title")).toString();
			assertThat(existingMovies.getString("title"), equalTo(title));
			
			String desc = e.getField(header.getColumnByName("Description")).toString();
			assertThat(existingMovies.getString("description"), equalTo(desc));
			
			String feedback = e.getField(header.getColumnByName("Feedback")).toString();
			assertThat(existingMovies.getString("feedback"), equalTo(feedback));
			
			String genre = e.getField(header.getColumnByName("Genre")).toString();
			assertThat(existingMovies.getString("genre"), equalTo(genre));
			
			Integer stars = Integer.parseInt(e.getField(header.getColumnByName("Stars")).toString());
			assertThat(existingMovies.getInt("stars"), equalTo(stars));
		}
	}
	
	
	@Test
	public void testUpdateMovie() throws FileNotFoundException, SQLException, EntryBuilderException
	{
		EntryDAO dao = new EntryDAO(getDB(), "movie");
		MovieHeader header = MovieHeader.getInstance();
		
		Entry e = new EntryBuilder(MovieHeader.getInstance())
			.addField("Title", new EntryField<String>("Film"))
			.addField("Description", new EntryField<String>("A Film"))
			.addField("Feedback", new EntryField<String>("A nice Film"))
			.addField("Genre", new EntryField<String>("Action"))
			.addField("Stars", new EntryField<Integer>(5))
			.build();
		e.setId(1);
		
		dao.update(e);
		
		ResultSet existingMovies = executeSelect("select * from movie where id = "+
				e.getId());
		
		while(existingMovies.next())
		{
			assertThat(existingMovies.getInt("id"), is(not(0)));
			assertThat(e.getId(), is((long)1));
			
			String title = e.getField(header.getColumnByName("Title")).toString();
			assertThat(existingMovies.getString("title"), equalTo(title));
			
			String desc = e.getField(header.getColumnByName("Description")).toString();
			assertThat(existingMovies.getString("description"), equalTo(desc));
			
			String feedback = e.getField(header.getColumnByName("Feedback")).toString();
			assertThat(existingMovies.getString("feedback"), equalTo(feedback));
			
			String genre = e.getField(header.getColumnByName("Genre")).toString();
			assertThat(existingMovies.getString("genre"), equalTo(genre));
			
			Integer stars = Integer.parseInt(e.getField(header.getColumnByName("Stars")).toString());
			assertThat(existingMovies.getInt("stars"), equalTo(stars));
		}
	}
	
	
	@Test
	public void testDeleteMovie() throws FileNotFoundException, SQLException
	{
		EntryDAO dao = new EntryDAO(getDB(), "movie");
		Entry e = dao.getEntityById(1);
		dao.delete(e);
		
		assertThat(dao.getEntityById(1), is(nullValue()));
	}
}
