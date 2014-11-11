/**
 * 
 */
package org.finnex.mdiary.diary.entry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.finnex.mdiary.diary.header.Column;
import org.finnex.mdiary.diary.header.Header;

/**
 * @author finneon
 *
 */
public class EntryBuilder {

	private Header header;
	private Column[] significantCols;
	private Map<Column, EntryField<?>> fields = new HashMap<Column, EntryField<?>>();


	/**
	 * 
	 */
	public EntryBuilder(Header header, Column... significantCols)
	{
		this.header = header;
		this.significantCols = significantCols;
	}
	
	
	public EntryBuilder addField(String colName, EntryField<?> field)
			throws EntryBuilderException
	{
		if(colName == null || colName.trim().isEmpty())
		{
			throw new IllegalArgumentException("Column name is null or emtpy");
		}
		
		if(field == null)
		{
			throw new IllegalArgumentException("EntryField is null");
		}
		
		Column col = header.getColumnByName(colName);
		
		if(col == null)
		{
			throw new EntryBuilderException("No matching column found");
		}
		
		fields.put(col, field);
		return this;
	}
	
	
	public Entry build() throws EntryBuilderException
	{
		List<String> colNames = getMissingColumns();
		
		if(colNames.isEmpty())
		{
			String msg = "Significant columns are missing: ";
			throw new EntryBuilderException(msg+colNames.toString());
		}
		
		Entry entry = new Entry();
		
		for(Map.Entry<Column, EntryField<?>> e: fields.entrySet())
		{
			entry.addField(e.getKey(), e.getValue());
		}
		
		return entry;
	}


	private List<String> getMissingColumns()
	{
		List<String> cols = new ArrayList<String>();
		
		for(Column col : significantCols)
		{
			EntryField<?> field = fields.get(col);
			if(field == null)
			{
				cols.add(col.getName());
			}
		}
		
		return cols;
	}

}
