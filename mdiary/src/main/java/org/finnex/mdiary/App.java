package org.finnex.mdiary;

import org.finnex.mdiary.diary.Diary;
import org.finnex.mdiary.diary.DiaryException;
import org.finnex.mdiary.diary.EntryStructure;
import org.finnex.mdiary.diary.MDiary;
import org.finnex.mdiary.diary.entry.Entry;
import org.finnex.mdiary.diary.header.MovieHeader;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws DiaryException
    {
        Diary diary = new MDiary();
        EntryStructure struct = diary.getEntries();
        
        MovieHeader header = MovieHeader.getInstance();
        for(Entry e : struct.getEntries())
        {
        	for(int i=0; i<header.getColumns().size(); i++)
			System.out.println(e.getField(header.getColumns().get(i)));
        }
        header = null;
        struct = null;
    }
}
