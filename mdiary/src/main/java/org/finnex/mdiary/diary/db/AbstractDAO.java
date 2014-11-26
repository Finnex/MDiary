package org.finnex.mdiary.diary.db;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.finnex.mdiary.diary.db.exceptions.DatenAbfrageException;
import org.finnex.mdiary.diary.db.exceptions.DatensatzUpdateException;


public abstract class AbstractDAO<T extends AbstractEntity>
{
  protected final String TABLENAME;
  protected T obj;
  protected DBController dbcontroller;


  public AbstractDAO(DBController dbc, String tableName)
  {
    this.dbcontroller = dbc;
    this.TABLENAME = tableName;
  }


  /**
   * assemblies a query string with the passed id to call queryForSingleEntity.
   * 
   * @param id
   * @return
   */
  public T getEntityById(long id)
  {
	  final String sql = "select * from " + TABLENAME + " where id = " + id;
	  return queryForSingleEntity(sql, false);
  }


  /**
   * Queries a single entity from the database. 
   * Returns null if nothing was found or throws 
   * an IllegalStateException if the second parameter
   * was set to true.
   * 
   * Was more than one entity found, an 
   * IllegalStateException will be thrown as well, 
   * because of doublet's existence.
   *
   * @param sql
   * @param throwExceptionIfNotExists
   * @return
   * @throws SQLException
   */
  protected T queryForSingleEntity(final String sql, final boolean throwExceptionIfNotExists)
  {
    List<T> liste = new ArrayList<T>();
    try{
    ResultSet rs = select(sql);
    while (rs.next())
    {
      final T entity = createNewEntityFromResultSet(rs);
        loadDependencies(entity);
      liste.add(entity);
    }
    }
    catch(SQLException e)
    {
      throw new DatenAbfrageException(e);
    }

    switch (liste.size())
    {
      case 0:
        if (throwExceptionIfNotExists)
        {
          throw new IllegalStateException("Entity expected, but nothing found! \n" + sql);
        }
        else
        {
          return null;
        }
      case 1:
        return liste.get(0);
      default:
        throw new IllegalStateException("Too many entities. Just one expected! \n" + sql);
    }
  }


  protected void loadDependencies(T entity)
  {}


  protected void saveDependencies(T entity)
    throws SQLException
  {}


  protected List<T> queryForList(final String sql)
  {
    List<T> liste = new ArrayList<T>();
    ResultSet rs = select(sql);
    try
    {
      while (rs.next())
      {
        T entity = createNewEntityFromResultSet(rs);
        liste.add(entity);
      }
    }
    catch (SQLException e)
    {
      throw new DatenAbfrageException(e);
    }
    return liste;
  }


  protected abstract T createNewEntityFromResultSet(ResultSet rs);


  public void update(T obj)
  {
    final String sql = createUpdateSql(obj);
    Statement statement;
    try
    {
      statement = dbcontroller.getConnection().createStatement();
      statement.executeUpdate(sql);
      dbcontroller.commit();
    }
    catch (SQLException e)
    {
      throw new DatensatzUpdateException(e);
    }
  }


  protected abstract String createUpdateSql(T obj);


  public void delete(T obj)
  {
    final String sql = createDeleteSQL(obj);
    processQuery(sql);
  }


  protected abstract String createDeleteSQL(T obj);


  public long create(T obj)
  {
    try{
    final String sql = createCreateSQL(obj);
    final PreparedStatement preStatement = createPreparedStatement(sql);

    final int result = preStatement.executeUpdate();
    obj.setId(getGeneratedId(preStatement, result));
    return obj.getId();
    }
    catch(SQLException e)
    {
      throw new DatensatzUpdateException(e);
    }
  }


  protected long getGeneratedId(final PreparedStatement preStatement, final int result)
  {
    long generatedId = -1;
    if (result > 0)
    {
      try{
      ResultSet generatedKeys = preStatement.getGeneratedKeys();

      if (null != generatedKeys && generatedKeys.next())
      {
        generatedId = generatedKeys.getLong(1);
      }
      }
      catch(SQLException e)
      {
        throw new DatenAbfrageException(e);
      }
    }
    return generatedId;
  }


  protected abstract String createCreateSQL(T obj);


  protected ResultSet select(String sql)
  {
    try{
      Statement statement = dbcontroller.getConnection().createStatement();
      return statement.executeQuery(sql);
    }
    catch(SQLException e)
    {
      throw new DatenAbfrageException(e);
    }
  }


  protected List<T> getAllEntities()
  {
    String sql = "select * from " + TABLENAME;
    return queryForList(sql);
  }


  private int processQuery(String sql)
  {
    try{
    final PreparedStatement preStatement = createPreparedStatement(sql);
    int result = preStatement.executeUpdate();
    return result;
    }
    catch(SQLException e)
    {
      throw new DatenAbfrageException(e);
    }
  }


  protected PreparedStatement createPreparedStatement(String sql)
  {
    try{
      return dbcontroller.getConnection().prepareStatement(sql, new String[] { "id_" });
    }
    catch(SQLException e)
    {
      throw new DatenAbfrageException(e);
    }
  }
  
  public void close()
  {
	  dbcontroller.close();
  }
}

