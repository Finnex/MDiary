package org.finnex.mdiary.diary.db.exceptions;

import java.sql.SQLException;

public class DBCloseConnectionException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public DBCloseConnectionException(SQLException e)
	{
		super(e);
	}

}
