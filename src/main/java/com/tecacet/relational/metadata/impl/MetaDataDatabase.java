package com.tecacet.relational.metadata.impl;


import com.tecacet.relational.metadata.Database;
import com.tecacet.relational.metadata.DatabaseTable;
import com.tecacet.relational.metadata.ForeignKey;
import com.tecacet.relational.metadata.LookupKey;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of database that retrieves table definition
 * directly from the database meta-data
 */
public class MetaDataDatabase implements Database {

    private static final String[] TABLE_TYPES = {"TABLE"};

    private final DatabaseMetaData metadata;

    public MetaDataDatabase(DataSource dataSource) throws SQLException {
        this(dataSource.getConnection());
    }

    public MetaDataDatabase(Connection connection) throws SQLException {
        this(connection.getMetaData());
    }

    public MetaDataDatabase(DatabaseMetaData metadata) {
        this.metadata = metadata;
    }

    @Override
    public List<DatabaseTable> getTables(String catalog, String schema) throws SQLException {
        List<DatabaseTable> tables = new ArrayList<>();
        try (ResultSet rs = metadata.getTables(catalog, schema, "%", TABLE_TYPES)) {
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                tables.add(getDatabaseTable(catalog, schema, tableName));
            }
        }
        return tables;
    }


    @Override
    public DatabaseTable getTable(String tableName) throws SQLException {
        try (ResultSet rs = metadata.getTables(null, null, tableName, TABLE_TYPES)) {
            if (!rs.next()) {
                return null;
            }
            return getDatabaseTable(null, null, tableName);
        }
    }

    private DatabaseTable getDatabaseTable(String catalog, String schema, String tableName) throws SQLException {
        DatabaseTableImpl table = new DatabaseTableImpl(tableName);
        cacheColumns(catalog, schema, table);
        cachePrimaryKey(metadata, table);
        cacheIndices(metadata, table);
        return table;
    }

    private void cacheColumns(String catalog, String schema, DatabaseTableImpl table) throws SQLException {
        try (ResultSet rs = metadata.getColumns(catalog, schema, table.getName(), "%")) {
            while (rs.next()) {
                String name = rs.getString("COLUMN_NAME").toUpperCase();
                int type = rs.getInt("DATA_TYPE");
                String isNullable = rs.getString("IS_NULLABLE");
                String isAutoIncrement = rs.getString("IS_AUTOINCREMENT");
                int size = rs.getInt("COLUMN_SIZE");
                int decimals = rs.getInt("DECIMAL_DIGITS");
                DatabaseColumnImpl column = new DatabaseColumnImpl(table, name, type);
                column.setNullable(isNullable.equals("YES"));
                column.setAutoIncrement(isAutoIncrement.equals("YES"));
                column.setLength(size);
                column.setPrecision(decimals);
            }
        }
    }

    private static void cachePrimaryKey(DatabaseMetaData metadata, DatabaseTableImpl table) throws SQLException {
        try (ResultSet rs = metadata.getPrimaryKeys(null, null, table.getName())) {
            String pkname = null;
            LookupKey pk = new LookupKey();
            while (rs.next()) {
                String name = rs.getString("PK_NAME");
                if (pkname == null && name != null) {
                    pkname = name;
                    pk.setName(name);
                }
                String columnName = rs.getString("COLUMN_NAME").toUpperCase();
                pk.addColumn(columnName);
            }
            table.setPrimaryKey(pk);
        }
    }

    // TODO: Only caches unique indexes. rewrite to cache all indices
    private static void cacheIndices(DatabaseMetaData metadata, DatabaseTableImpl table) throws SQLException {
        // NOTE: for now this only caches unique indices
        ResultSet rs = metadata.getIndexInfo(null, null, table.getName(), true, true);
        while (rs.next()) {
            String name = rs.getString("INDEX_NAME");
            String columnName = rs.getString("COLUMN_NAME").toUpperCase();
            table.addUniqueIndex(name, columnName);
        }
    }

    @Override
    public List<ForeignKey> getForeignKeys(String tableName) throws SQLException {
        try (ResultSet rs = metadata.getImportedKeys(null, null, tableName)) {
            List<ForeignKey> foreignKeys = new ArrayList<>();
            while(rs.next()) {
                ForeignKey foreignKey = new ForeignKey();
                foreignKey.setTargetTableName(rs.getString("PKTABLE_NAME"));
                foreignKey.setTargetColumnName(rs.getString("PKCOLUMN_NAME"));
                foreignKey.setSourceColumnName(rs.getString("FKCOLUMN_NAME"));
                foreignKeys.add(foreignKey);
            }
            return foreignKeys;
        }

    }

}
