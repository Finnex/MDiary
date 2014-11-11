package org.finnex.mdiary.diary.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;

import org.finnex.mdiary.diary.db.exceptions.DBCloseConnectionException;
import org.finnex.mdiary.diary.db.exceptions.DatenbankCommitException;

/**
 * Delivers all functionalities to manage a database connection.
 * 
 * @author Finn Leighfy
 *
 */
public class DBController
{
    private Connection con;


    /**
     * The constructor loads the database driver and creates a connection to the
     * database.
     * Is any of this steps not possible the program closes immediately.
     */
    public DBController(String driver, String url, String user, String pass)
      throws SQLException, FileNotFoundException
    {
      loadDriver(driver);
      checkForDBFile(url);

      con = DriverManager.getConnection(url, user, pass);
      con.setAutoCommit(false);
    }

    /**
     * The constructor loads the database driver and creates a connection to it.
     * Is any of this steps not possible the program closes immediately.
     */
    public DBController(String driver, String url) throws SQLException, FileNotFoundException
    {
      loadDriver(driver);
      checkForDBFile(url.split(":")[2]);
      
      con = DriverManager.getConnection(url);
    }

    private void loadDriver(String driver) {
          try
      {
        Class.forName(driver);
      }
      catch (ClassNotFoundException e)
      {
        throw new IllegalStateException("Unable to load the database driver!", e);
      }
    }


    /**
     * If you have done changes to your table content
     * you need to commit it, because this querys are part
     * of the <b>DML</b>(Data Manipulation Language). that
     * changes are not automatically applied. For this you
     * need this method.
     * 
     * @throws SQLException
     */
    public void commit()
    {
            try
            {
                    if(!con.getAutoCommit())
                            this.con.commit();
            }
            catch(SQLException e)
            {
                    throw new DatenbankCommitException(e);
            }
    }


    public void close()
    {
            try{
                    getConnection().close();
            }
            catch(SQLException e)
            {
                    throw new DBCloseConnectionException(e);
            }
    }

    public void createTable(String sql)
    {
            try
            {
                    Statement statement = con.createStatement();
                    statement.executeQuery(sql);
            }
            catch (SQLException e)
            {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            }
    }


    /* (non-Javadoc)
     * @see de.schluetersche.azubi.xmind.core.datenbank.StorageManager#getConnection()
     */
    public Connection getConnection()
    {
      return con;
    }
  
  
    private void checkForDBFile(String dbName) throws FileNotFoundException
    {
        if(dbName != null && !dbName.equals(""))
        {
            File file = new File(dbName);
            if(!file.exists())
            {
                throw new FileNotFoundException("Database-file is not at the expected location!");
            }
        }
        
    }

}