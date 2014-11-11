/**
 * 
 */
package org.finnex.mdiary.diary.header;

import java.util.List;

/**
 * @author finneon
 *
 */
public interface Header
{
	/**
	 * Returns a column for the passed column name. Returns null,
	 * if no column matches with the passed name.
	 * 
	 * @param colName
	 * @return
	 */
	Column getColumnByName(String colName);
	
	
	/**
	 * Returns an unmodifiable List of the contained columns.
	 * 
	 * @return List of Columns
	 */
	List<Column> getColumns();
}
