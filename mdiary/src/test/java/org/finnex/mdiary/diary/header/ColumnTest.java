/**
 * 
 */
package org.finnex.mdiary.diary.header;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.easymock.EasyMock;
import org.finnex.mdiary.diary.header.validator.Validator;
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
	
	
	@Test
	public void testSetupColumnWithValidatorSuccessFully()
	{
		Validator val = EasyMock.createMock(Validator.class);
		EasyMock.replay(val);
		
		Column col = new Column("Test", val);
		assertThat(col.getName(), equalTo("Test"));
		assertThat(col.getValidator(), notNullValue(Validator.class));
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
