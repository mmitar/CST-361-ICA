package util;

public class DatabaseException extends RuntimeException
{
	private static final long serialVersionUID = 0L;
	
	public DatabaseException(Throwable e)
	{
		super(e);
	}
}
