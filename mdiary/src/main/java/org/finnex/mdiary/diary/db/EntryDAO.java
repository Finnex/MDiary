/**
 * 
 */
package org.finnex.mdiary.diary.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.finnex.mdiary.diary.db.exceptions.DatenAbfrageException;
import org.finnex.mdiary.diary.entry.Entry;
import org.finnex.mdiary.diary.entry.EntryBuilder;
import org.finnex.mdiary.diary.entry.EntryBuilderException;
import org.finnex.mdiary.diary.entry.EntryField;
import org.finnex.mdiary.diary.header.Column;
import org.finnex.mdiary.diary.header.Header;
import org.finnex.mdiary.diary.header.MovieHeader;

/**
 * @author finneon
 *
 */
public class EntryDAO extends AbstractDAO<Entry> {

	public EntryDAO(DBController dbc, String tableName)
	{
		super(dbc, tableName);
	}

	@Override
	protected Entry createNewEntityFromResultSet(ResultSet rs)
	{
		MovieHeader header = MovieHeader.getInstance();
		Column no = header.getColumnByName("No.");
		Column title = header.getColumnByName("Title");
		Column desc = header.getColumnByName("Description");
		Column feedback = header.getColumnByName("Feedback");
		Column genre = header.getColumnByName("Genre");
		Column stars = header.getColumnByName("Stars");
		
		try
		{
			EntryField<Long> noField = new EntryField<Long>(rs.getLong("nr"));
			EntryField<String> titleField = new EntryField<String>(rs.getString("title"));
			EntryField<String> descField = new EntryField<String>(rs.getString("Description"));
			EntryField<String> fdField = new EntryField<String>(rs.getString("Feedback"));
			EntryField<String> genreField = new EntryField<String>(rs.getString("genre"));
			EntryField<Integer> starsField = new EntryField<Integer>(rs.getInt("stars"));

			return new EntryBuilder(header)
				.addField(no.getName(), noField)
				.addField(title.getName(), titleField)
				.addField(desc.getName(), descField)
				.addField(feedback.getName(), fdField)
				.addField(genre.getName(), genreField)
				.addField(stars.getName(), starsField)
				.build();
		}
		catch (EntryBuilderException | SQLException e)
		{
			throw new DatenAbfrageException(e);
		}
	}

	@Override
	protected String createUpdateSql(Entry obj)
	{
		Header header = MovieHeader.getInstance();
		
		Column no = header.getColumnByName("No.");
		Column title = header.getColumnByName("Title");
		Column desc = header.getColumnByName("Description");
		Column feedback = header.getColumnByName("Feedback");
		Column genre = header.getColumnByName("Genre");
		Column stars = header.getColumnByName("Stars");
		
		//TODO more dynamic query for update-SQL 
		return "update "+TABLENAME+" set "+
				title.getName()   +" = "+ obj.getField(title)+ ", "+
				desc.getName()    +" = "+ obj.getField(desc)+ ", "+
				feedback.getName()+" = "+ obj.getField(feedback)+ ", "+
				genre.getName()   +" = "+ obj.getField(genre)+ ", "+
				stars.getName()   +" 0 "+ obj.getField(stars)+ " "+
				"where "+no.getName()+ " = "+obj.getField(no);
	}

	@Override
	protected String createDeleteSQL(Entry obj)
	{
		MovieHeader header = MovieHeader.getInstance();
		Column entryId = header.getColumnByName("No.");
		
		return "delete from "+TABLENAME+" where"+obj.getField(entryId);
	}

	@Override
	protected String createCreateSQL(Entry obj)
	{
		Header header = MovieHeader.getInstance();
		List<Column> columns = header.getColumns();
		
		StringBuilder headerNames = new StringBuilder();
		StringBuilder entryContent = new StringBuilder();
		
		for(Column col : columns)
		{
			headerNames.append(col);
			entryContent.append(obj.getField(col));
			
			if(columns.indexOf(col) != (columns.size()-1))
			{
				headerNames.append(", ");
				entryContent.append(", ");
			}
		}
		
		return "insert into "+TABLENAME+" ("+headerNames.toString()+")"
			+ "values ("+entryContent.toString()+")";
	}
	
	
	/**
	 * Returns all data from one table or one relation.
	 */
	@Override
	public List<Entry> getAllEntities()
	{
		return queryForList("select * from "+TABLENAME);
	}

}
