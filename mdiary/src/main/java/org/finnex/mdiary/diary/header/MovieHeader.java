/**
 * 
 */
package org.finnex.mdiary.diary.header;

import org.finnex.mdiary.diary.header.validator.TextValidator;

/**
 * @author finneon
 *
 */
public class MovieHeader
	extends DefaultHeader
		implements Header
{

	private static MovieHeader instance;
	/**
	 * @param columns
	 */
	private MovieHeader()
	{
		super(
				new Column("Title", new TextValidator()),
				new Column("Description", new TextValidator()),
				new Column("Feedback", new TextValidator()),
				new Column("Genre", new TextValidator()),
				new Column("Stars", null)
		);
	}
	
	
	public static MovieHeader getInstance()
	{
		if(instance == null)
		{
			instance = new MovieHeader();
		}
		
		return instance;
	}

}
