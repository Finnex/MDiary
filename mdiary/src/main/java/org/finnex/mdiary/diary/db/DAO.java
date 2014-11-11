package org.finnex.mdiary.diary.db;

import java.sql.SQLException;


public interface DAO<T>
{
  void update(T obj)
    throws SQLException;


  void delete(T obj)
    throws SQLException;


  long create(T obj)
    throws SQLException;
}
