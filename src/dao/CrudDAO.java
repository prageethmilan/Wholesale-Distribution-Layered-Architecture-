package dao;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Prageeth Milan
 * @e-mail : prageethmilan99@gmail.com
 **/


public interface CrudDAO<T, NIC> extends SuperDAO{
    boolean add(T t) throws SQLException, ClassNotFoundException;

    T search(NIC s) throws SQLException, ClassNotFoundException;

    boolean update(T t) throws SQLException, ClassNotFoundException;

    ArrayList<T> getAll() throws SQLException, ClassNotFoundException;

    boolean delete(NIC nic) throws SQLException, ClassNotFoundException;

}
