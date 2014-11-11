package org.finnex.mdiary.diary.db;


import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractDBUnitTest<T extends AbstractEntity>
{
  DBController dbcontroller;
  Connection connection;
  Statement statement;

  public AbstractDBUnitTest(String driver, String url) throws SQLException, FileNotFoundException
  {
    dbcontroller = new DBController(driver, url);
    connection = dbcontroller.getConnection();
  }
  
  public AbstractDBUnitTest(String driver, String url, String user, String pass) throws SQLException, FileNotFoundException
  {
    dbcontroller = new DBController(driver, url, user, pass);
    connection = dbcontroller.getConnection();
  }

  public void executeInsert(final String insertSQL) throws SQLException
  {
    statement = connection.createStatement();
    statement.executeUpdate(insertSQL);
    statement.close();
  }

  public void clearTable(final String tableName) throws SQLException
  {
    String deleteSQL = "delete from " + tableName;
    statement = connection.createStatement();
    statement.executeUpdate(deleteSQL);
  }

  public ResultSet executeSelect(final String selectSQL) throws SQLException
  {
    statement = connection.createStatement();
    ResultSet rs = statement.executeQuery(selectSQL);
    return rs;
  }

  public DBController getController()
  {
    return dbcontroller;
  }

  public Connection getConnection()
  {
    return connection;
  }
}
