package com.tecacet.relational.metadata;

import com.tecacet.relational.metadata.impl.MetaDataDatabase;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * A database abstraction as a collection of tables
 */
public interface Database {

    List<DatabaseTable> getTables(String catalog, String schema) throws SQLException;

    default List<DatabaseTable> getAllTables() throws SQLException {
        return getTables(null, null);
    }

    default List<DatabaseTable> getSchemaTables(String schema) throws SQLException {
        return getTables(null, schema);
    }

    DatabaseTable getTable(String tableName) throws SQLException;

    List<ForeignKey> getForeignKeys(String tableName) throws SQLException;

    static Database create(Connection connection) {
        try {
            return new MetaDataDatabase(connection.getMetaData());
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }
}
