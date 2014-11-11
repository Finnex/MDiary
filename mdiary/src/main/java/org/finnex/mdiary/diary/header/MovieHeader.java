/**
 * 
 */
package org.finnex.mdiary.diary.header;

/**
 * @author finneon
 *
 */
public class MovieHeader
	extends AbstractHeader
		implements Header
{

	private static MovieHeader instance;
	/**
	 * @param columns
	 */
	private MovieHeader()
	{
		super(
				new Column("No.", null),
				new Column("Title", null),
				new Column("Description", null),
				new Column("Feedback", null),
				new Column("Genre", null),
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
