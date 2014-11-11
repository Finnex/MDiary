/**
 * 
 */
package org.finnex.mdiary.diary.header;

/**
 * @author finneon
 *
 */
public class Column
{

	private String name;
	private Validator val;

	public Column(String name, Validator val)
	{
		if(name == null || name.trim().isEmpty())
		{
			throw new IllegalArgumentException("Column name is not supposed to be null");
		}
		
		this.name = name;
		this.val = val;
	}
	
	
	public String getName()
	{
		return name;
	}
	
	
	public Validator getValidator()
	{
		return val;
	}

}
