/**
 * 
 */
package org.finnex.mdiary.diary.entry;

import java.util.HashMap;
import java.util.Map;

import org.finnex.mdiary.diary.db.AbstractEntity;
import org.finnex.mdiary.diary.header.Column;

/**
 * @author finneon
 *
 */
public class Entry extends AbstractEntity
{
	private Map<Column, EntryField<?>> fields = new HashMap<Column, EntryField<?>>();
	
	
	void addField(Column col, EntryField<?> field)
	{
		if(col == null || field == null)
		{
			throw new IllegalArgumentException();
		}
		
		fields.put(col, field);
	}
	
	
	public EntryField<?> getField(Column col)
	{
		if(col == null)
		{
			throw new IllegalArgumentException();
		}
		
		return fields.get(col);
	}
}
