/**
 * 
 */
package org.finnex.mdiary.diary.entry;

/**
 * @author finneon
 *
 */
public class EntryField<E>
{

	private E value;

	
	public EntryField(E value)
	{
		this.value = value;
	}
	
	
	public E getValue()
	{
		return value;
	}
	
	
	/**
	 * returns a String representation of the contained value.
	 */
	public String toString()
	{
		return value.toString();
	}

}
