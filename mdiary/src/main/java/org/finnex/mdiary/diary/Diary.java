/**
 * 
 */
package org.finnex.mdiary.diary;

import org.finnex.mdiary.diary.entry.Entry;

/**
 * @author finneon
 *
 */
public interface Diary
{
	void updateEntry(Entry entry) throws DiaryException;
	
	
	void deleteEntry(Entry entry) throws DiaryException;
	
	
	void createEntry(Entry entry) throws DiaryException;
	
	
	Entry getEntryByID(long id) throws DiaryException;
	
	
	EntryStructure getEntries() throws DiaryException;
}
