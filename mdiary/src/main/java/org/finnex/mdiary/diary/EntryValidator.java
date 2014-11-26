/**
 * 
 */
package org.finnex.mdiary.diary;

import org.finnex.mdiary.diary.entry.Entry;
import org.finnex.mdiary.diary.header.Column;
import org.finnex.mdiary.diary.header.Header;
import org.finnex.mdiary.diary.header.validator.Validator;

/**
 * @author finneon
 * 
 * This class is used to check the entry for forbidden characters.
 *
 */
public class EntryValidator
{
	
	private Header header;


	public EntryValidator(Header header)
	{
		if(header == null)
			throw new IllegalArgumentException("Passed header is null");
		
		this.header = header;
	}
	
	public void validate(final Entry entry) throws ValidatorException
	{
		if(entry == null)
			throw new IllegalArgumentException("Passed entry is null");
		
		for(Column col : header.getColumns())
		{
			Validator val = col.getValidator();
			if(val != null)
			{
				String field = entry.getField(col).toString();
				if(!val.validate(field))
					throw new ValidatorException(
						"Invalid special characters in the "+
							col.getName().toLowerCase());
			}
				
					
		}
	}

}
