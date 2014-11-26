/**
 * 
 */
package org.finnex.mdiary.diary.header.validator;

import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

/**
 * @author finneon
 *
 */
public class TextValidatorTest {

	@Test
	public void testValidateSuccessfully()
	{
		assertThat(new TextValidator().<String>validate("Test"), is(true));
	}
	
	
	@Test
	public void testValidateSuccessfullyWithSpecChar()
	{
		assertThat(new TextValidator().<String>validate("Test!"), is(true));
	}
	
	
	@Test
	public void testValidateWithQuoteChar()
	{
		assertThat(new TextValidator().<String>validate("\"Test\""), is(false));
	}
	
	
	@Test
	public void testValidateWithPlus()
	{
		assertThat(new TextValidator().<String>validate("Test+"), is(false));
	}
	
	
	@Test
	public void testValidateWithMinus()
	{
		assertThat(new TextValidator().<String>validate("Test-"), is(false));
	}

}
