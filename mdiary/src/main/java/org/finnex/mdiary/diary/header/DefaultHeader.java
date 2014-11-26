/**
 * 
 */
package org.finnex.mdiary.diary.header;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author finneon
 *
 */
public class DefaultHeader implements Header
{
	
	private List<Column> columns;
	
	
	public DefaultHeader(Column... columns)
	{
		if(columns == null || columns.length == 0)
		{
			throw new IllegalArgumentException("initial columns don't exist");
		}
		
		this.columns = Arrays.asList(columns);
	}

	/* (non-Javadoc)
	 * @see org.finnex.mdiary.diary.Header#getColumnByName()
	 */
	public Column getColumnByName(String colName)
	{
		if(colName == null || colName.trim().length() == 0)
		{
			throw new IllegalArgumentException();
		}
		
		for(Column col : columns)
		{
			if(col.getName().equals(colName))
			{
				return col;
			}
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see org.finnex.mdiary.diary.Header#getColumns()
	 */
	public List<Column> getColumns()
	{
		return Collections.unmodifiableList(columns);
	}

}
