/**
 * 
 */
package org.finnex.mdiary.diary;

import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.*;

import org.easymock.EasyMock;
import org.finnex.mdiary.diary.entry.Entry;
import org.finnex.mdiary.diary.entry.EntryBuilder;
import org.finnex.mdiary.diary.entry.EntryBuilderException;
import org.finnex.mdiary.diary.entry.EntryField;
import org.finnex.mdiary.diary.header.Column;
import org.finnex.mdiary.diary.header.DefaultHeader;
import org.finnex.mdiary.diary.header.validator.Validator;
import org.junit.Test;

/**
 * @author finneon
 *
 */
public class EntryValidatorTest
{

	@Test
	public void testValidateEntrySuccessfully() throws EntryBuilderException, ValidatorException
	{
		EntryField<String> field = new EntryField<String>("Test content");
		
		Validator val = EasyMock.createMock(Validator.class);
		EasyMock.expect(val.validate(field.toString())).andReturn(true);
		EasyMock.replay(val);
		
		
		DefaultHeader header = new DefaultHeader(new Column("Test", val));
		
		Entry e = new EntryBuilder(header).addField("Test", field).build();
		
		new EntryValidator(header).validate(e);
	}
	
	
	@Test
	public void testValidateEntryWithException() throws EntryBuilderException
	{
		EntryField<String> field = new EntryField<String>("Test content");
		
		Validator val = EasyMock.createMock(Validator.class);
		EasyMock.expect(val.validate(field.toString())).andReturn(false);
		EasyMock.replay(val);
		
		
		DefaultHeader header = new DefaultHeader(new Column("Test", val));
		
		Entry e = new EntryBuilder(header).addField("Test", field).build();
		
		try
		{
			new EntryValidator(header).validate(e);
			fail("An excepton should has been occured");
		}
		catch (ValidatorException e1)
		{
			assertThat(e1.getMessage(), equalTo("Invalid special characters in the test"));
		}
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void testValidateEntryWithNullAsHeader()
	{
		new EntryValidator(null);
	}
	
	
	/**
	 * No error should occur.
	 */
	@Test
	public void testValidateEntryWithNullAsValidator()
	{
		new EntryValidator(new DefaultHeader(new Column("Test", null)));
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void testValidateEntryWithNullAsEntry() throws ValidatorException
	{
		Validator val = EasyMock.createMock(Validator.class);
		EasyMock.replay(val);
		
		DefaultHeader header = new DefaultHeader(new Column("Test", val));
		new EntryValidator(header).validate(null);;
	}

}
