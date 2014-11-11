package org.finnex.mdiary.diary.db.exceptions;

import java.sql.SQLException;

import org.finnex.mdiary.diary.entry.EntryBuilderException;

public class DatenAbfrageException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public DatenAbfrageException(SQLException e)
	{
		super(e);
	}

	public DatenAbfrageException(EntryBuilderException e)
	{
		super(e);
	}

	public DatenAbfrageException(Exception e)
	{
		super(e);
	}
}
