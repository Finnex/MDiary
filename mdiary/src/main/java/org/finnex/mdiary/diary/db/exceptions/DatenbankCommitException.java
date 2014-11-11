package org.finnex.mdiary.diary.db.exceptions;

import java.sql.SQLException;

public class DatenbankCommitException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public DatenbankCommitException(SQLException e)
	{
	    super(e);
	}
}
