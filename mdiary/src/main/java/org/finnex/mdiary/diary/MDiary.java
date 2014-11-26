/**
 * 
 */
package org.finnex.mdiary.diary;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import org.finnex.mdiary.diary.db.DBController;
import org.finnex.mdiary.diary.db.EntryDAO;
import org.finnex.mdiary.diary.entry.Entry;
import org.finnex.mdiary.diary.header.MovieHeader;

/**
 * @author finneon
 * 
 * This program is for my best friend Jana Buwitt. It's the product of all my programming
 * experiences. Therefore my intention is to create a powerful but fly-weight Diary where
 * a user can memorize almost everything he/she wants to.
 *
 */
public class MDiary implements Diary
{
	
	private DBController getDBC()
			throws FileNotFoundException, SQLException
	{
		String url = "jdbc:sqlite:mmdb.db";
		String driver = "org.sqlite.JDBC";
		return new DBController(driver, url);
	}

	public MDiary()
	{
		//TODO How to prepare this class for unit tests?
		//TODO out-source the EntryDAO!
		//TODO implement the EntryService to ensure security for users.
	}

	public void updateEntry(Entry entry) throws DiaryException
	{
		try
		{
			new EntryValidator(MovieHeader.getInstance()).validate(entry);
		}
		catch (ValidatorException e1)
		{
			throw new DiaryException(e1);
		}
		
		try
		{
			EntryDAO dao = new EntryDAO(getDBC(), "movie");
			dao.update(entry);
			dao.close();
		}
		catch (FileNotFoundException | SQLException e)
		{
			throw new DiaryException();
		}
	}

	public void deleteEntry(Entry entry) throws DiaryException
	{
		try
		{
			EntryDAO dao = new EntryDAO(getDBC(), "movie");
			dao.delete(entry);
			dao.close();
		}
		catch (FileNotFoundException | SQLException e)
		{
			throw new DiaryException();
		}
	}

	public void createEntry(Entry entry) throws DiaryException
	{
		try
		{
			new EntryValidator(MovieHeader.getInstance()).validate(entry);
		}
		catch (ValidatorException e1)
		{
			throw new DiaryException(e1);
		}
		
		try
		{
			EntryDAO dao = new EntryDAO(getDBC(), "movie");
			dao.create(entry);
			dao.close();
		}
		catch (FileNotFoundException | SQLException e)
		{
			throw new DiaryException();
		}
		
	}

	@Override
	public Entry getEntryByID(long id) throws DiaryException
	{
		try
		{
			EntryDAO dao = new EntryDAO(getDBC(), "movie");
			Entry entrie = dao.getEntityById(id);
			dao.close();
			return entrie;
		}
		catch (FileNotFoundException | SQLException e)
		{
			throw new DiaryException();
		}
	}

	@Override
	public EntryStructure getEntries() throws DiaryException
	{
		try
		{
			EntryDAO dao = new EntryDAO(getDBC(), "movie");
			List<Entry> entries = dao.getAllEntities();
			dao.close();
			
			MovieHeader header = MovieHeader.getInstance();
			EntryStructure struct = new EntryStructure(header);
			
			for(Entry e : entries)
				struct.add(e);
			
			return struct;
		}
		catch (FileNotFoundException | SQLException e)
		{
			throw new DiaryException();
		}
	}

}
