package org.finnex.mdiary.diary.entry;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.finnex.mdiary.diary.header.Column;
import org.junit.Test;

public class EntryTest {

	@Test
	public void testAddFieldSuccessfully()
	{
		Entry e = new Entry();
		
		EntryField<String> field = new EntryField<String>("Testinhalt");
		Column col = new Column("Test", null);
		
		e.addField(col,  field);
		
		assertThat(e.getField(col), is(not(nullValue())));
		assertThat(e.getField(col).toString(), equalTo(field.getValue()));
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddFieldWithNullCol()
	{
		Entry e = new Entry();
		
		e.addField(null, new EntryField<String>("Testinhalt"));
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddFieldWithNullField()
	{
		Entry e = new Entry();
		
		e.addField(new Column("Test", null),  null);
	}

}
