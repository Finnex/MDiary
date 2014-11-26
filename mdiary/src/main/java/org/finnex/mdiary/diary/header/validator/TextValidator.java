/**
 * 
 */
package org.finnex.mdiary.diary.header.validator;

/**
 * 
 * @author finneon
 *
 */
public class TextValidator implements Validator
{

	@Override
	public <E> boolean validate(E value)
	{
		String newValue = value.toString().replaceAll("[^a-zA-Z0-9()äöüÄÖÜß?!.,; ]", "");
		return value.equals(newValue);
	}

}
