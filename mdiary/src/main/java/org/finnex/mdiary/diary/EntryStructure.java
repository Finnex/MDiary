/**
 * 
 */
package org.finnex.mdiary.diary;

import java.util.ArrayList;
import java.util.List;

import org.finnex.mdiary.diary.entry.Entry;
import org.finnex.mdiary.diary.header.Header;

/**
 * @author finneon
 *
 */
public class EntryStructure {

	private Header header;
	private List<Entry> entries = new ArrayList<Entry>();

	/**
	 * Initializes the structure with a header.
	 */
	public EntryStructure(Header header)
	{
		if(header == null || header.getColumns().isEmpty())
		{
			String msg = "Header is not supposed to " +
					"be null or empty for the structure";
			throw new IllegalArgumentException(msg);
		}
		
		this.header = header;
	}
	
	
	public EntryStructure add(Entry entry)
	{
		if(entry == null)
		{
			throw new IllegalArgumentException("No null entry allowed");
		}
		
		entries.add(entry);
		return this;
	}
	
	
	public List<Entry> getEntries()
	{
		return entries;
	}
	
	
	public Header getHeader()
	{
		return header;
	}

}
