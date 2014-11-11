package org.finnex.mdiary.diary.db.exceptions;

import java.sql.SQLException;

public class DatensatzUpdateException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public DatensatzUpdateException(SQLException e)
	{
	    super(e);
	}
}
