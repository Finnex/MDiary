/**
 * 
 */
package org.finnex.mdiary.diary.db;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import org.easymock.EasyMock;
import org.finnex.mdiary.diary.entry.Entry;
import org.finnex.mdiary.diary.entry.EntryBuilder;
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
		super("org.sqlite.JDBC", "jdbc:sqlite:mmdb");
	}
	
	
	@Before
	public void prepareForSelectAll() throws SQLException, ClassNotFoundException
	{
		executeInsert("insert into movies (no, title, Description, Feedback, genre, stars)" +
				"values (1, 'Spiderman', 'Man with spider power.', 'good movie', 'action, romance', 4)");
	
		executeInsert("insert into movies (no, title, Description, Feedback, genre, stars)" +
				"values (2, 'Hulk', 'Man with giant power.', 'nice movie', 'action', 3)");
	
		executeInsert("insert into movies (no, title, Description, Feedback, genre, stars)" +
				"values (3, 'Iron Man', 'Man of iron.', 'great movie', 'action, humor', 5)");
	}
	
	@After
	public void RollBackSelect() throws SQLException, ClassNotFoundException
	{
	    clearTable("movies");
	
	    getController().commit();
	}
	

	private DBController getDB() throws SQLException, FileNotFoundException
	{
		return new DBController("org.sqlite.JDBC", "jdbc:sqlite:mmdb");
	}
	

	@Test
	public void testSelectMovie() throws FileNotFoundException, SQLException
	{
		EntryDAO dao = new EntryDAO(getDB(), "movie");
		Entry e = dao.getEntityById(1);
		
		MovieHeader header = MovieHeader.getInstance();
		assertThat(e.getField(header.getColumnByName("No.")).getValue().toString(), equalTo("1"));
		assertThat(e.getField(header.getColumnByName("title")).getValue().toString(), equalTo("Spiderman"));
		assertThat(e.getField(header.getColumnByName("Description")).getValue().toString(), equalTo("Man with spider power."));
		assertThat(e.getField(header.getColumnByName("Feedback")).getValue().toString(), equalTo("good movie"));
		assertThat(e.getField(header.getColumnByName("genre")).getValue().toString(), equalTo("action, romance"));
		assertThat(e.getField(header.getColumnByName("stars")).getValue().toString(), equalTo("4"));
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
	public void testInsertMovie() throws FileNotFoundException, SQLException
	{
		EntryDAO dao = new EntryDAO(getDB(), "movie");
		MovieHeader header = MovieHeader.getInstance();
		
		//TODO complete unit test
		Entry e = new Entry();
		
		Long key = dao.create(e );
	}
}
