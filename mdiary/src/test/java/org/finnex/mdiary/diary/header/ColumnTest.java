/**
 * 
 */
package org.finnex.mdiary.diary.header;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;

import org.junit.Test;

/**
 * @author finneon
 *
 */
public class ColumnTest {

	@Test
	public void testSetupColumnSuccessFully()
	{
		Column col = new Column("Test", null);
		assertThat(col.getName(), equalTo("Test"));
		assertThat(col.getValidator(), nullValue(Validator.class));
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void testNameIsNull()
	{
		new Column(null, null);
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void testNameIsEmpty()
	{
		new Column("", null);
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void testNameWithWhitepace()
	{
		new Column(" ", null);
	}

}
